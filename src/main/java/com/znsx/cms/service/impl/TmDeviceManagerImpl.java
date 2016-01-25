package com.znsx.cms.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Hibernate;
import org.hibernate.id.UUIDHexGenerator;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.CmsCommandDAO;
import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.CoviDAO;
import com.znsx.cms.persistent.dao.DasDAO;
import com.znsx.cms.persistent.dao.FireDetectorDAO;
import com.znsx.cms.persistent.dao.LoLiDAO;
import com.znsx.cms.persistent.dao.NoDetectorDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PlayItemDAO;
import com.znsx.cms.persistent.dao.PlaylistDAO;
import com.znsx.cms.persistent.dao.PlaylistFolderDAO;
import com.znsx.cms.persistent.dao.PushButtonDAO;
import com.znsx.cms.persistent.dao.StandardNumberDAO;
import com.znsx.cms.persistent.dao.TypeDefDAO;
import com.znsx.cms.persistent.dao.VehicleDetectorDAO;
import com.znsx.cms.persistent.dao.WeatherStatDAO;
import com.znsx.cms.persistent.dao.WindSpeedDAO;
import com.znsx.cms.persistent.model.CmsCommand;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.persistent.model.ControlDeviceFan;
import com.znsx.cms.persistent.model.ControlDeviceLight;
import com.znsx.cms.persistent.model.ControlDeviceLil;
import com.znsx.cms.persistent.model.ControlDeviceWp;
import com.znsx.cms.persistent.model.Covi;
import com.znsx.cms.persistent.model.Das;
import com.znsx.cms.persistent.model.FireDetector;
import com.znsx.cms.persistent.model.License;
import com.znsx.cms.persistent.model.LoLi;
import com.znsx.cms.persistent.model.NoDetector;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.PlayItem;
import com.znsx.cms.persistent.model.Playlist;
import com.znsx.cms.persistent.model.PlaylistFolder;
import com.znsx.cms.persistent.model.PushButton;
import com.znsx.cms.persistent.model.StandardNumber;
import com.znsx.cms.persistent.model.TypeDef;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.WeatherStat;
import com.znsx.cms.persistent.model.WindSpeed;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.TmDeviceManager;
import com.znsx.cms.service.model.CmsInfoVO;
import com.znsx.cms.service.model.CoviInfoVO;
import com.znsx.cms.service.model.GetTypeDefVO;
import com.znsx.cms.service.model.LoliInfoVO;
import com.znsx.cms.service.model.NodInfoVO;
import com.znsx.cms.service.model.VdInfoVO;
import com.znsx.cms.service.model.WsInfoVO;
import com.znsx.cms.service.model.WstInfoVO;
import com.znsx.util.xml.ElementUtil;

/**
 * 交通监控设备业务接口实现
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-1-15 下午3:04:06
 */
public class TmDeviceManagerImpl extends BaseManagerImpl implements
		TmDeviceManager {
	@Autowired
	private VehicleDetectorDAO vdDAO;
	@Autowired
	private CoviDAO coviDAO;
	@Autowired
	private WindSpeedDAO wsDAO;
	@Autowired
	private WeatherStatDAO wstDAO;
	@Autowired
	private LoLiDAO loliDAO;
	@Autowired
	private NoDetectorDAO noDetectorDAO;
	@Autowired
	private ControlDeviceDAO controlDeviceDAO;
	@Autowired
	private StandardNumberDAO snDAO;
	@Autowired
	private TypeDefDAO typeDefDAO;
	@Autowired
	private PlaylistDAO playlistDAO;
	@Autowired
	private PlayItemDAO playItemDAO;
	@Autowired
	private PlaylistFolderDAO folderDAO;
	@Autowired
	private CmsCommandDAO cmsCommandDAO;
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private DasDAO dasDAO;
	@Autowired
	private FireDetectorDAO fireDetectorDAO;
	@Autowired
	private PushButtonDAO pushButtonDAO;

	@Override
	public List<VdInfoVO> vdInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		List<VehicleDetector> vds = vdDAO.vdInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		List<VdInfoVO> list = new LinkedList<VdInfoVO>();
		for (VehicleDetector vd : vds) {
			VdInfoVO vo = new VdInfoVO();
			vo.setName(vd.getName());
			vo.setNavigation(vd.getNavigation());
			vo.setOLowLimit(vd.getoLowLimit() == null ? "" : vd.getoLowLimit()
					.toString());
			Organ o = vd.getOrgan();
			vo.setOrganName(o == null ? "" : o.getName());
			vo.setOUpLimit(vd.getoUpLimit() == null ? "" : vd.getoUpLimit()
					.toString());
			vo.setSLowLimit(vd.getsLowLimit() == null ? "" : vd.getsLowLimit()
					.toString());
			vo.setStakeNumber(vd.getStakeNumber());
			vo.setStandardNumber(vd.getStandardNumber());
			vo.setSUpLimit(vd.getsUpLimit() == null ? "" : vd.getsUpLimit()
					.toString());
			vo.setVLowLimit(vd.getvLowLimit() == null ? "" : vd.getvLowLimit()
					.toString());
			vo.setVUpLimit(vd.getvUpLimit() == null ? "" : vd.getvUpLimit()
					.toString());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countVdInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Integer count = vdDAO.countVdInfo(organId, deviceName, navigation,
				stakeNumber);
		return count.intValue();
	}

	@Override
	public List<WstInfoVO> wstInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		List<WeatherStat> wsts = wstDAO.wstInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		List<WstInfoVO> list = new LinkedList<WstInfoVO>();
		for (WeatherStat wst : wsts) {
			WstInfoVO vo = new WstInfoVO();
			vo.setName(wst.getName());
			vo.setNavigation(wst.getNavigation());
			Organ organ = wst.getOrgan();
			vo.setOrganName(organ == null ? "" : organ.getName());
			vo.setRUpLimit(wst.getrUpLimit() == null ? "" : wst.getrUpLimit()
					.toString());
			vo.setStakeNumber(wst.getStakeNumber());
			vo.setStandardNumber(wst.getStandardNumber());
			vo.setSUpLimit(wst.getsUpLimit() == null ? "" : wst.getsUpLimit()
					.toString());
			vo.setVLowLimit(wst.getvLowLimit() == null ? "" : wst
					.getvLowLimit().toString());
			vo.setWUpLimit(wst.getwUpLimit() == null ? "" : wst.getwUpLimit()
					.toString());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countWstInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Integer count = wstDAO.countWstInfo(organId, deviceName, navigation,
				stakeNumber);
		return count.intValue();
	}

	@Override
	public List<CoviInfoVO> coviInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		List<Covi> covis = coviDAO.coviInfo(organId, deviceName, navigation,
				stakeNumber, start, limit);
		List<CoviInfoVO> list = new LinkedList<CoviInfoVO>();
		for (Covi covi : covis) {
			CoviInfoVO vo = new CoviInfoVO();
			vo.setCUpLimit(covi.getCoConctLimit() == null ? "" : covi
					.getCoConctLimit().toString());
			vo.setName(covi.getName());
			vo.setNavigation(covi.getNavigation());
			Organ organ = covi.getOrgan();
			vo.setOrganName(organ == null ? "" : organ.getName());
			vo.setStakeNumber(covi.getStakeNumber());
			vo.setStandardNumber(covi.getStandardNumber());
			vo.setVLowLimit(covi.getVisibilityLimit() == null ? "" : covi
					.getVisibilityLimit().toString());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countCoviInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Integer count = coviDAO.countCoviInfo(organId, deviceName, navigation,
				stakeNumber);
		return count.intValue();
	}

	@Override
	public List<LoliInfoVO> loliInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		List<LoLi> lolis = loliDAO.loliInfo(organId, deviceName, navigation,
				stakeNumber, start, limit);
		List<LoliInfoVO> list = new LinkedList<LoliInfoVO>();
		for (LoLi loli : lolis) {
			LoliInfoVO vo = new LoliInfoVO();
			vo.setLiLowLimit(loli.getLiLumiMin() == null ? "" : loli
					.getLiLumiMin().toString());
			vo.setLiUpLimit(loli.getLiLumiMax() == null ? "" : loli
					.getLiLumiMax().toString());
			vo.setLoLowLimit(loli.getLoLumiMin() == null ? "" : loli
					.getLoLumiMin().toString());
			vo.setLoUpLimit(loli.getLoLumiMax() == null ? "" : loli
					.getLoLumiMax().toString());
			vo.setName(loli.getName());
			vo.setNavigation(loli.getNavigation());
			Organ organ = loli.getOrgan();
			vo.setOrganName(organ == null ? "" : organ.getName());
			vo.setStakeNumber(loli.getStakeNumber());
			vo.setStandardNumber(loli.getStandardNumber());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countLoliInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Integer count = loliDAO.countLoliInfo(organId, deviceName, navigation,
				stakeNumber);
		return count.intValue();
	}

	@Override
	public List<NodInfoVO> nodInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		List<NoDetector> nods = noDetectorDAO.nodInfo(organId, deviceName,
				navigation, stakeNumber, start, limit);
		List<NodInfoVO> list = new LinkedList<NodInfoVO>();
		for (NoDetector nod : nods) {
			NodInfoVO vo = new NodInfoVO();
			vo.setName(nod.getName());
			vo.setNavigation(nod.getNavigation());
			vo.setNooUpLimit(nod.getNooConctLimit() == null ? "" : nod
					.getNooConctLimit().toString());
			vo.setNoUpLimit(nod.getNoConctLimit() == null ? "" : nod
					.getNoConctLimit().toString());
			Organ organ = nod.getOrgan();
			vo.setOrganName(organId == null ? "" : organ.getName());
			vo.setStakeNumber(nod.getStakeNumber());
			vo.setStandardNumber(nod.getStandardNumber());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countNodInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Integer count = noDetectorDAO.countNodInfo(organId, deviceName,
				navigation, stakeNumber);
		return count.intValue();
	}

	@Override
	public List<WsInfoVO> wsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Integer start, Integer limit) {
		List<WindSpeed> wss = wsDAO.wsInfo(organId, deviceName, navigation,
				stakeNumber, start, limit);
		List<WsInfoVO> list = new LinkedList<WsInfoVO>();
		for (WindSpeed ws : wss) {
			WsInfoVO vo = new WsInfoVO();
			vo.setName(ws.getName());
			vo.setNavigation(ws.getNavigation());
			Organ organ = ws.getOrgan();
			vo.setOrganName(organ == null ? "" : organ.getName());
			vo.setStakeNumber(ws.getStakeNumber());
			vo.setStandardNumber(ws.getStandardNumber());
			vo.setWUpLimit(ws.getwUpLimit() == null ? "" : ws.getwUpLimit()
					.toString());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countWsInfo(String organId, String deviceName,
			String navigation, String stakeNumber) {
		Integer count = wsDAO.countWsInfo(organId, deviceName, navigation,
				stakeNumber);
		return count.intValue();
	}

	@Override
	public List<CmsInfoVO> cmsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Short subType,
			Integer start, Integer limit) {
		List<ControlDevice> cmss = controlDeviceDAO.cmsInfo(organId,
				deviceName, navigation, stakeNumber, subType, start, limit);
		List<CmsInfoVO> list = new LinkedList<CmsInfoVO>();
		for (ControlDevice cms : cmss) {
			CmsInfoVO vo = new CmsInfoVO();
			vo.setName(cms.getName());
			vo.setNavigation(cms.getNavigation());
			Organ organ = cms.getOrgan();
			vo.setOrganName(organ == null ? "" : organ.getName());
			vo.setStakeNumber(cms.getStakeNumber());
			vo.setStandardNumber(cms.getStandardNumber());
			vo.setSubType(cms.getSubType() == null ? "" : cms.getSubType()
					.toString());
			list.add(vo);
		}
		return list;
	}

	@Override
	public int countCmsInfo(String organId, String deviceName,
			String navigation, String stakeNumber, Short subType) {
		Integer count = controlDeviceDAO.countCmsInfo(organId, deviceName,
				navigation, stakeNumber, subType);
		return count.intValue();
	}

	@Override
	public int countDeviceAmount() {
		return snDAO.countDeviceAmount();
	}

	@Override
	public List<TypeDef> listTypeDef(Integer type) {
		return typeDefDAO.listTypeDef(type);
	}

	@Override
	public Playlist savePlaylist(String folderId, String playlistId,
			String playlistName, Short type, List<Element> items, String cmsSize)
			throws BusinessException {
		Playlist playlist = null;
		// 修改
		if (StringUtils.isNotBlank(playlistId)) {
			playlist = playlistDAO.findById(playlistId);
			playlist.setName(playlistName);
			playlist.setType(type);
			playlist.setCmsSize(cmsSize);
			// 记录新的内容条目ID
			List<String> itemList = new ArrayList<String>();
			Set<PlayItem> itemSet = playlist.getItems();
			for (Element item : items) {
				PlayItem playItem = null;
				// 内容修改
				if (StringUtils.isNotBlank(item.getAttributeValue("Id"))) {
					Iterator<PlayItem> it = itemSet.iterator();
					while (it.hasNext()) {
						playItem = it.next();
						if (playItem.getId().equals(
								item.getAttributeValue("Id"))) {
							playItem.setColor(item.getAttributeValue("Color"));
							playItem.setContent(item
									.getAttributeValue("Content"));
							playItem.setDuration(ElementUtil.getInteger(item,
									"Duration"));
							playItem.setFont(item.getAttributeValue("Font"));
							playItem.setSize(item.getAttributeValue("Size"));
							playItem.setWordSpace(ElementUtil.getShort(item,
									"Space"));
							playItem.setX(item.getAttributeValue("X"));
							playItem.setY(item.getAttributeValue("Y"));
							playItem.setType(ElementUtil.getShort(item, "Type"));
							break;
						}
						continue;
					}
				}
				// 新增内容
				else {
					playItem = new PlayItem();
					playItem.setColor(item.getAttributeValue("Color"));
					playItem.setContent(item.getAttributeValue("Content"));
					playItem.setDuration(ElementUtil.getInteger(item,
							"Duration"));
					playItem.setFont(item.getAttributeValue("Font"));
					playItem.setSize(item.getAttributeValue("Size"));
					playItem.setWordSpace(ElementUtil.getShort(item, "Space"));
					playItem.setX(item.getAttributeValue("X"));
					playItem.setY(item.getAttributeValue("Y"));
					playItem.setType(ElementUtil.getShort(item, "Type"));
					playItem.setPlaylist(playlist);
					playItemDAO.save(playItem);
					itemSet.add(playItem);
				}
				// 记录xml中的item ID
				itemList.add(playItem.getId());
			}
			// 比较PlayItem原有ID与xml中的ID，移除xml中不存在的
			List<PlayItem> removeList = new LinkedList<PlayItem>();
			Iterator<PlayItem> it = itemSet.iterator();
			while (it.hasNext()) {
				PlayItem playItem = it.next();
				if (!itemList.contains(playItem.getId())) {
					removeList.add(playItem);
					playItemDAO.delete(playItem);
				}
			}
			itemSet.removeAll(removeList);
		}
		// 新增
		else {
			playlist = new Playlist();
			playlist.setName(playlistName);
			playlist.setType(type);
			playlist.setCmsSize(cmsSize);
			// 关联folder
			PlaylistFolder folder = folderDAO.findById(folderId);
			folder.getPlaylists().add(playlist);
			playlist.setFolder(folder);
			playlistDAO.save(playlist);
			for (Element item : items) {
				PlayItem playItem = new PlayItem();
				playItem.setColor(item.getAttributeValue("Color"));
				playItem.setContent(item.getAttributeValue("Content"));
				playItem.setDuration(ElementUtil.getInteger(item, "Duration"));
				playItem.setFont(item.getAttributeValue("Font"));
				playItem.setSize(item.getAttributeValue("Size"));
				playItem.setWordSpace(ElementUtil.getShort(item, "Space"));
				playItem.setX(item.getAttributeValue("X"));
				playItem.setY(item.getAttributeValue("Y"));
				playItem.setType(ElementUtil.getShort(item, "Type"));
				playItem.setPlaylist(playlist);
				playItemDAO.save(playItem);
				playlist.getItems().add(playItem);
			}
		}

		return playlist;
	}

	@Override
	public void deletePlaylist(String id) throws BusinessException {
		playlistDAO.deleteById(id);
	}

	@Override
	public List<Playlist> listPlaylist(String folderId, Short type) {
		List<Playlist> list = playlistDAO.listPlaylist(folderId, type);
		// 初始化集合数据
		for (Playlist playlist : list) {
			Set<PlayItem> items = playlist.getItems();
			for (PlayItem playitem : items) {
				playitem.getId();
			}
		}
		return list;
	}

	@Override
	public List<PlaylistFolder> listFolder(Integer subType) {
		return folderDAO.listFolder(subType);
	}

	@Override
	public String createFolder(@LogParam("name") String name, Integer subType)
			throws BusinessException {
		PlaylistFolder folder = new PlaylistFolder();
		folder.setName(name);
		folder.setSubType(subType);
		folderDAO.save(folder);
		return folder.getId();
	}

	@Override
	public void deleteFolder(@LogParam("id") String id)
			throws BusinessException {
		folderDAO.deleteById(id);
	}

	@Override
	public List<GetTypeDefVO> listDeviceType(Integer type) {
		List<TypeDef> typeDefs = typeDefDAO.listTypeDef(type);
		List<GetTypeDefVO> list = new ArrayList<GetTypeDefVO>();
		for (TypeDef td : typeDefs) {
			GetTypeDefVO vo = new GetTypeDefVO();
			vo.setId(td.getId());
			vo.setSubType(td.getSubType() != null ? td.getSubType().toString()
					: "");
			vo.setSubTypeName(td.getSubTypeName());
			vo.setType(td.getType() != null ? td.getType().toString() : "");
			vo.setTypeName(td.getTypeName());
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<String> saveVmsCommand(List<InputStream> ins) {
		List<String> ids = new LinkedList<String>();
		try {
			for (InputStream in : ins) {
				CmsCommand entity = new CmsCommand();
				entity.setContent(Hibernate.createBlob(in));
				cmsCommandDAO.save(entity);
				ids.add(entity.getId());
			}
			return ids;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"image stream read error !");
		}
	}

	@Override
	public CmsCommand getCmsCommand(String id) {
		return cmsCommandDAO.findById(id);
	}

	@Override
	public Set<String> listCmsSize() {
		Set<String> rtnSet = new LinkedHashSet<String>();
		List<ControlDevice> list = controlDeviceDAO.listControlDevices(null,
				TypeDefinition.DEVICE_TYPE_CMS);
		for (ControlDevice cd : list) {
			ControlDeviceCms cms = (ControlDeviceCms) cd;
			if (cms.getWidth() == null || cms.getHeight() == null) {
				continue;
			}
			StringBuffer sb = new StringBuffer();
			sb.append(cms.getWidth().intValue());
			sb.append("*");
			sb.append(cms.getHeight().intValue());
			rtnSet.add(sb.toString());
		}
		return rtnSet;
	}

	@Override
	public List<VehicleDetector> listAllVd() {
		// 将所有Organ对象加载到一级缓存
		List<Organ> organs = organDAO.findAll();

		List<VehicleDetector> vds = vdDAO.findAll();
		// 初始化机构数据
		for (VehicleDetector vd : vds) {
			Organ organ = vd.getOrgan();
			if (organ instanceof OrganRoad) {
				// 关联路段
				((OrganRoad) organ).getCapacity();
				vd.setOrgan(organ);
			}
		}
		return vds;
	}

	@Override
	public List<FireDetector> readFireDetectorWb(Workbook wb, License license) {
		List<FireDetector> list = new ArrayList<FireDetector>();
		try {
			if (wb == null) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			}
			Sheet sheet = wb.getSheetAt(0);
			list = readFireDetectors(sheet);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return list;
	}

	private List<FireDetector> readFireDetectors(Sheet sheet) {
		List<FireDetector> list = new ArrayList<FireDetector>();
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {
					list.add(readFireDetector(row, rowIndex));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}

		return list;
	}

	private FireDetector readFireDetector(Row row, int rowIndex) {
		FireDetector entity = new FireDetector();
		Cell cell = row.getCell(0);
		if (cell != null) {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String name = row.getCell(0).getStringCellValue();
			entity.setName(name);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 1 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",name is not null");
		}

		cell = row.getCell(1);
		if (cell != null) {
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String dasSn = row.getCell(1).getStringCellValue();
			Das das = dasDAO.findBySN(dasSn);
			entity.setDas(das);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 2 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dasSn is not null");
		}

		cell = row.getCell(2);
		if (cell != null) {
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			String organSn = row.getCell(2).getStringCellValue();
			Organ organf = organDAO.findBySN(organSn);
			entity.setOrgan(organf);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 3 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",organSn is not null");
		}

		cell = row.getCell(3);
		if (cell != null) {
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String sn = row.getCell(3).getStringCellValue();
			entity.setStandardNumber(sn);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 4 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL + ",sn is not null");
		}

		cell = row.getCell(4);
		if (cell != null) {
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String stakeNumber = row.getCell(4).getStringCellValue();
			entity.setStakeNumber(stakeNumber);
		}

		cell = row.getCell(5);
		if (cell != null) {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String period = row.getCell(5).getStringCellValue();
			entity.setPeriod(Integer.parseInt(period));
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 6 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",period is not null");
		}

		cell = row.getCell(6);
		if (cell != null) {
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String navigation = row.getCell(6).getStringCellValue();
			entity.setNavigation(navigation);
		}

		cell = row.getCell(7);
		if (cell != null) {
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			String reserve = row.getCell(7).getStringCellValue();
			entity.setReserve(reserve);
		}

		cell = row.getCell(8);
		if (cell != null) {
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			String note = row.getCell(8).getStringCellValue();
			entity.setNote(note);
		}

		cell = row.getCell(9);
		if (cell != null) {
			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
			String ip = row.getCell(9).getStringCellValue();
			entity.setIp(ip);
		}

		cell = row.getCell(10);
		if (cell != null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
			String port = row.getCell(10).getStringCellValue();
			entity.setPort(Integer.parseInt(port));
		}

		cell = row.getCell(11);
		if (cell != null) {
			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			String latitude = row.getCell(11).getStringCellValue();
			entity.setLatitude(latitude);
		}

		cell = row.getCell(12);
		if (cell != null) {
			row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
			String longitude = row.getCell(12).getStringCellValue();
			entity.setLongitude(longitude);
		}
		String id = (String) new UUIDHexGenerator().generate(null, null);
		entity.setId(id);
		return entity;
	}

	@Override
	public List<ControlDeviceLight> readControlDeviceLightWb(Workbook wb,
			License license) {
		List<ControlDeviceLight> list = new ArrayList<ControlDeviceLight>();
		try {
			if (wb == null) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			}
			Sheet sheet = wb.getSheetAt(0);
			list = readControlDeviceLightWbs(sheet);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return list;
	}

	private List<ControlDeviceLight> readControlDeviceLightWbs(Sheet sheet) {
		List<ControlDeviceLight> list = new ArrayList<ControlDeviceLight>();
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {
					list.add(readControlDeviceLightWb(row, rowIndex));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}

		return list;
	}

	private ControlDeviceLight readControlDeviceLightWb(Row row, int rowIndex) {
		ControlDeviceLight entity = new ControlDeviceLight();
		Cell cell = row.getCell(0);
		if (cell != null) {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String name = row.getCell(0).getStringCellValue();
			entity.setName(name);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 1 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",name is not null");
		}

		cell = row.getCell(1);
		if (cell != null) {
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String dasSn = row.getCell(1).getStringCellValue();
			Das das = dasDAO.findBySN(dasSn);
			entity.setDas(das);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 2 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dasSn is not null");
		}

		cell = row.getCell(2);
		if (cell != null) {
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			String organSn = row.getCell(2).getStringCellValue();
			Organ organf = organDAO.findBySN(organSn);
			entity.setOrgan(organf);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 3 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",organSn is not null");
		}

		cell = row.getCell(3);
		if (cell != null) {
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String sn = row.getCell(3).getStringCellValue();
			entity.setStandardNumber(sn);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 4 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL + ",sn is not null");
		}

		cell = row.getCell(4);
		if (cell != null) {
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String stakeNumber = row.getCell(4).getStringCellValue();
			entity.setStakeNumber(stakeNumber);
		}

		cell = row.getCell(5);
		if (cell != null) {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String period = row.getCell(5).getStringCellValue();
			entity.setPeriod(Integer.parseInt(period));
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 6 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",period is not null");
		}

		cell = row.getCell(6);
		if (cell != null) {
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String navigation = row.getCell(6).getStringCellValue();
			entity.setNavigation(navigation);
		}

		cell = row.getCell(7);
		if (cell != null) {
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			String reserve = row.getCell(7).getStringCellValue();
			entity.setReserve(reserve);
		}

		cell = row.getCell(8);
		if (cell != null) {
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			String note = row.getCell(8).getStringCellValue();
			entity.setNote(note);
		}

		cell = row.getCell(9);
		if (cell != null) {
			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
			String subType = row.getCell(9).getStringCellValue();
			entity.setSubType(Short.parseShort(subType));
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 10 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",subType is not null");
		}

		cell = row.getCell(10);
		if (cell != null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
			String sectionType = row.getCell(10).getStringCellValue();
			entity.setSectionType(Short.parseShort(sectionType));
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 11 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",sectionType is not null");
		}

		cell = row.getCell(11);
		if (cell != null) {
			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			String ip = row.getCell(11).getStringCellValue();
			entity.setIp(ip);
		}

		cell = row.getCell(12);
		if (cell != null) {
			row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
			String port = row.getCell(12).getStringCellValue();
			entity.setPort(Integer.parseInt(port));
		}

		cell = row.getCell(13);
		if (cell != null) {
			row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
			String latitude = row.getCell(13).getStringCellValue();
			entity.setLatitude(latitude);
		}

		cell = row.getCell(14);
		if (cell != null) {
			row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
			String longitude = row.getCell(14).getStringCellValue();
			entity.setLongitude(longitude);
		}

		String id = (String) new UUIDHexGenerator().generate(null, null);
		entity.setId(id);
		return entity;
	}

	@Override
	public List<ControlDeviceWp> readControlDeviceWpWb(Workbook wb,
			License license) {
		List<ControlDeviceWp> list = new ArrayList<ControlDeviceWp>();
		try {
			if (wb == null) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			}
			Sheet sheet = wb.getSheetAt(0);
			list = readControlDeviceWps(sheet);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return list;
	}

	private List<ControlDeviceWp> readControlDeviceWps(Sheet sheet) {
		List<ControlDeviceWp> list = new ArrayList<ControlDeviceWp>();
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {
					list.add(readControlDeviceWp(row, rowIndex));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}

		return list;
	}

	private ControlDeviceWp readControlDeviceWp(Row row, int rowIndex) {
		ControlDeviceWp entity = new ControlDeviceWp();
		Cell cell = row.getCell(0);
		if (cell != null) {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String name = row.getCell(0).getStringCellValue();
			entity.setName(name);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 1 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",name is not null");
		}

		cell = row.getCell(1);
		if (cell != null) {
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String dasSn = row.getCell(1).getStringCellValue();
			Das das = dasDAO.findBySN(dasSn);
			entity.setDas(das);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 2 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dasSn is not null");
		}

		cell = row.getCell(2);
		if (cell != null) {
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			String organSn = row.getCell(2).getStringCellValue();
			Organ organf = organDAO.findBySN(organSn);
			entity.setOrgan(organf);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 3 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",organSn is not null");
		}

		cell = row.getCell(3);
		if (cell != null) {
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String sn = row.getCell(3).getStringCellValue();
			entity.setStandardNumber(sn);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 4 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL + ",sn is not null");
		}

		cell = row.getCell(4);
		if (cell != null) {
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String stakeNumber = row.getCell(4).getStringCellValue();
			entity.setStakeNumber(stakeNumber);
		}

		cell = row.getCell(5);
		if (cell != null) {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String period = row.getCell(5).getStringCellValue();
			entity.setPeriod(Integer.parseInt(period));
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 6 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",period is not null");
		}

		cell = row.getCell(6);
		if (cell != null) {
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String navigation = row.getCell(6).getStringCellValue();
			entity.setNavigation(navigation);
		}

		cell = row.getCell(7);
		if (cell != null) {
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			String reserve = row.getCell(7).getStringCellValue();
			entity.setReserve(reserve);
		}

		cell = row.getCell(8);
		if (cell != null) {
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			String note = row.getCell(8).getStringCellValue();
			entity.setNote(note);
		}

		cell = row.getCell(9);
		if (cell != null) {
			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
			String ip = row.getCell(9).getStringCellValue();
			entity.setIp(ip);
		}

		cell = row.getCell(10);
		if (cell != null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
			String port = row.getCell(10).getStringCellValue();
			entity.setPort(Integer.parseInt(port));
		}

		cell = row.getCell(11);
		if (cell != null) {
			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			String latitude = row.getCell(11).getStringCellValue();
			entity.setLatitude(latitude);
		}

		cell = row.getCell(12);
		if (cell != null) {
			row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
			String longitude = row.getCell(12).getStringCellValue();
			entity.setLongitude(longitude);
		}
		String id = (String) new UUIDHexGenerator().generate(null, null);
		entity.setId(id);
		return entity;
	}

	@Override
	public List<PushButton> readPushButtonWb(Workbook wb, License license) {
		List<PushButton> list = new ArrayList<PushButton>();
		try {
			if (wb == null) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			}
			Sheet sheet = wb.getSheetAt(0);
			list = readPushButtonWbs(sheet);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return list;
	}

	private List<PushButton> readPushButtonWbs(Sheet sheet) {
		List<PushButton> list = new ArrayList<PushButton>();
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {
					list.add(readPushButtonWb(row, rowIndex));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}

		return list;
	}

	private PushButton readPushButtonWb(Row row, int rowIndex) {
		PushButton entity = new PushButton();
		Cell cell = row.getCell(0);
		if (cell != null) {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String name = row.getCell(0).getStringCellValue();
			entity.setName(name);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 1 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",name is not null");
		}

		cell = row.getCell(1);
		if (cell != null) {
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String dasSn = row.getCell(1).getStringCellValue();
			Das das = dasDAO.findBySN(dasSn);
			entity.setDas(das);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 2 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dasSn is not null");
		}

		cell = row.getCell(2);
		if (cell != null) {
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			String organSn = row.getCell(2).getStringCellValue();
			Organ organf = organDAO.findBySN(organSn);
			entity.setOrgan(organf);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 3 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",organSn is not null");
		}

		cell = row.getCell(3);
		if (cell != null) {
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String sn = row.getCell(3).getStringCellValue();
			entity.setStandardNumber(sn);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 4 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL + ",sn is not null");
		}

		cell = row.getCell(4);
		if (cell != null) {
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String stakeNumber = row.getCell(4).getStringCellValue();
			entity.setStakeNumber(stakeNumber);
		}

		cell = row.getCell(5);
		if (cell != null) {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String period = row.getCell(5).getStringCellValue();
			entity.setPeriod(Integer.parseInt(period));
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 6 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",period is not null");
		}

		cell = row.getCell(6);
		if (cell != null) {
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String navigation = row.getCell(6).getStringCellValue();
			entity.setNavigation(navigation);
		}

		cell = row.getCell(7);
		if (cell != null) {
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			String reserve = row.getCell(7).getStringCellValue();
			entity.setReserve(reserve);
		}

		cell = row.getCell(8);
		if (cell != null) {
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			String note = row.getCell(8).getStringCellValue();
			entity.setNote(note);
		}

		cell = row.getCell(9);
		if (cell != null) {
			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
			String ip = row.getCell(9).getStringCellValue();
			entity.setIp(ip);
		}

		cell = row.getCell(10);
		if (cell != null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
			String port = row.getCell(10).getStringCellValue();
			entity.setPort(Integer.parseInt(port));
		}

		cell = row.getCell(11);
		if (cell != null) {
			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			String latitude = row.getCell(11).getStringCellValue();
			entity.setLatitude(latitude);
		}

		cell = row.getCell(12);
		if (cell != null) {
			row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
			String longitude = row.getCell(12).getStringCellValue();
			entity.setLongitude(longitude);
		}
		String id = (String) new UUIDHexGenerator().generate(null, null);
		entity.setId(id);
		return entity;
	}

	@Override
	public List<ControlDeviceLil> readControlDeviceLilWb(Workbook wb,
			License license) {
		List<ControlDeviceLil> list = new ArrayList<ControlDeviceLil>();
		try {
			if (wb == null) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			}
			Sheet sheet = wb.getSheetAt(0);
			list = readControlDeviceLilWbs(sheet);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return list;
	}

	private List<ControlDeviceLil> readControlDeviceLilWbs(Sheet sheet) {
		List<ControlDeviceLil> list = new ArrayList<ControlDeviceLil>();
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {
					list.add(readControlDeviceLilWb(row, rowIndex));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}

		return list;
	}

	private ControlDeviceLil readControlDeviceLilWb(Row row, int rowIndex) {
		ControlDeviceLil entity = new ControlDeviceLil();
		Cell cell = row.getCell(0);
		if (cell != null) {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String name = row.getCell(0).getStringCellValue();
			entity.setName(name);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 1 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",name is not null");
		}

		cell = row.getCell(1);
		if (cell != null) {
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String dasSn = row.getCell(1).getStringCellValue();
			Das das = dasDAO.findBySN(dasSn);
			entity.setDas(das);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 2 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dasSn is not null");
		}

		cell = row.getCell(2);
		if (cell != null) {
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			String organSn = row.getCell(2).getStringCellValue();
			Organ organf = organDAO.findBySN(organSn);
			entity.setOrgan(organf);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 3 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",organSn is not null");
		}

		cell = row.getCell(3);
		if (cell != null) {
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String sn = row.getCell(3).getStringCellValue();
			entity.setStandardNumber(sn);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 4 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL + ",sn is not null");
		}

		cell = row.getCell(4);
		if (cell != null) {
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String stakeNumber = row.getCell(4).getStringCellValue();
			entity.setStakeNumber(stakeNumber);
		}

		cell = row.getCell(5);
		if (cell != null) {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String period = row.getCell(5).getStringCellValue();
			entity.setPeriod(Integer.parseInt(period));
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 6 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",period is not null");
		}

		cell = row.getCell(6);
		if (cell != null) {
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String navigation = row.getCell(6).getStringCellValue();
			entity.setNavigation(navigation);
		}

		cell = row.getCell(7);
		if (cell != null) {
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			String reserve = row.getCell(7).getStringCellValue();
			entity.setReserve(reserve);
		}

		cell = row.getCell(8);
		if (cell != null) {
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			String note = row.getCell(8).getStringCellValue();
			entity.setNote(note);
		}

		cell = row.getCell(9);
		if (cell != null) {
			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
			String ip = row.getCell(9).getStringCellValue();
			entity.setIp(ip);
		}

		cell = row.getCell(10);
		if (cell != null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
			String port = row.getCell(10).getStringCellValue();
			entity.setPort(Integer.parseInt(port));
		}

		cell = row.getCell(11);
		if (cell != null) {
			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			String latitude = row.getCell(11).getStringCellValue();
			entity.setLatitude(latitude);
		}

		cell = row.getCell(12);
		if (cell != null) {
			row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
			String longitude = row.getCell(12).getStringCellValue();
			entity.setLongitude(longitude);
		}
		String id = (String) new UUIDHexGenerator().generate(null, null);
		entity.setId(id);
		return entity;
	}

	@Override
	public List<ControlDeviceFan> readControlDeviceFanWb(Workbook wb,
			License license) {
		List<ControlDeviceFan> list = new ArrayList<ControlDeviceFan>();
		try {
			if (wb == null) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			}
			Sheet sheet = wb.getSheetAt(0);
			list = readControlDeviceFanWbs(sheet);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return list;
	}

	private List<ControlDeviceFan> readControlDeviceFanWbs(Sheet sheet) {
		List<ControlDeviceFan> list = new ArrayList<ControlDeviceFan>();
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {
					list.add(readControlDeviceFanWb(row, rowIndex));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}

		return list;
	}

	private ControlDeviceFan readControlDeviceFanWb(Row row, int rowIndex) {
		ControlDeviceFan entity = new ControlDeviceFan();
		Cell cell = row.getCell(0);
		if (cell != null) {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String name = row.getCell(0).getStringCellValue();
			entity.setName(name);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 1 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",name is not null");
		}

		cell = row.getCell(1);
		if (cell != null) {
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String dasSn = row.getCell(1).getStringCellValue();
			Das das = dasDAO.findBySN(dasSn);
			entity.setDas(das);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 2 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",dasSn is not null");
		}

		cell = row.getCell(2);
		if (cell != null) {
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			String organSn = row.getCell(2).getStringCellValue();
			Organ organf = organDAO.findBySN(organSn);
			entity.setOrgan(organf);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 3 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",organSn is not null");
		}

		cell = row.getCell(3);
		if (cell != null) {
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String sn = row.getCell(3).getStringCellValue();
			entity.setStandardNumber(sn);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 4 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL + ",sn is not null");
		}

		cell = row.getCell(4);
		if (cell != null) {
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String stakeNumber = row.getCell(4).getStringCellValue();
			entity.setStakeNumber(stakeNumber);
		}

		cell = row.getCell(5);
		if (cell != null) {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String period = row.getCell(5).getStringCellValue();
			entity.setPeriod(Integer.parseInt(period));
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 6 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",period is not null");
		}

		cell = row.getCell(6);
		if (cell != null) {
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String navigation = row.getCell(6).getStringCellValue();
			entity.setNavigation(navigation);
		}

		cell = row.getCell(7);
		if (cell != null) {
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			String reserve = row.getCell(7).getStringCellValue();
			entity.setReserve(reserve);
		}

		cell = row.getCell(8);
		if (cell != null) {
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			String note = row.getCell(8).getStringCellValue();
			entity.setNote(note);
		}

		cell = row.getCell(9);
		if (cell != null) {
			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
			String ip = row.getCell(9).getStringCellValue();
			entity.setIp(ip);
		}

		cell = row.getCell(10);
		if (cell != null) {
			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
			String port = row.getCell(10).getStringCellValue();
			entity.setPort(Integer.parseInt(port));
		}

		cell = row.getCell(11);
		if (cell != null) {
			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
			String latitude = row.getCell(11).getStringCellValue();
			entity.setLatitude(latitude);
		}

		cell = row.getCell(12);
		if (cell != null) {
			row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
			String longitude = row.getCell(12).getStringCellValue();
			entity.setLongitude(longitude);
		}
		String id = (String) new UUIDHexGenerator().generate(null, null);
		entity.setId(id);
		return entity;
	}

	@Override
	public void batchInsertFd(List<FireDetector> fds) {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < fds.size(); i++) {
			// dvrs.get(i).setId(dvrIds[i]);
			fireDetectorDAO.batchInsertFd(fds.get(i));
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(fds.get(i).getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_FD);
			list.add(sn);
		}
		fireDetectorDAO.excuteBatchFd();
		batchInsertSN(list);

	}

	@Override
	public void batchInsertLight(List<ControlDeviceLight> cdls) {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < cdls.size(); i++) {
			// dvrs.get(i).setId(dvrIds[i]);
			controlDeviceDAO.batchInsertLight(cdls.get(i));
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(cdls.get(i).getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_CD);
			list.add(sn);
		}
		controlDeviceDAO.excuteBatchLight();
		batchInsertSN(list);
	}

	@Override
	public void batchInsertWp(List<ControlDeviceWp> cdws) {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < cdws.size(); i++) {
			// dvrs.get(i).setId(dvrIds[i]);
			controlDeviceDAO.batchInsertWp(cdws.get(i));
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(cdws.get(i).getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_CD);
			list.add(sn);
		}
		controlDeviceDAO.excuteBatchWp();
		batchInsertSN(list);
	}

	@Override
	public void batchInsertPb(List<PushButton> pbs) {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < pbs.size(); i++) {
			// dvrs.get(i).setId(dvrIds[i]);
			pushButtonDAO.batchInsertPb(pbs.get(i));
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(pbs.get(i).getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_PB);
			list.add(sn);
		}
		pushButtonDAO.excuteBatchPb();
		batchInsertSN(list);
	}

	@Override
	public void batchInsertFan(List<ControlDeviceFan> fans) {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < fans.size(); i++) {
			// dvrs.get(i).setId(dvrIds[i]);
			controlDeviceDAO.batchInsertFan(fans.get(i));
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(fans.get(i).getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_CD);
			list.add(sn);
		}
		controlDeviceDAO.excuteBatchFan();
		batchInsertSN(list);
	}

	@Override
	public void batchInsertLil(List<ControlDeviceLil> cdlis) {
		List<StandardNumber> list = new LinkedList<StandardNumber>();
		for (int i = 0; i < cdlis.size(); i++) {
			// dvrs.get(i).setId(dvrIds[i]);
			controlDeviceDAO.batchInsertLil(cdlis.get(i));
			// 同步SN
			StandardNumber sn = new StandardNumber();
			sn.setSn(cdlis.get(i).getStandardNumber());
			sn.setClassType(TypeDefinition.RESOURCE_TYPE_CD);
			list.add(sn);
		}
		controlDeviceDAO.excuteBatchLil();
		batchInsertSN(list);
	}
}
