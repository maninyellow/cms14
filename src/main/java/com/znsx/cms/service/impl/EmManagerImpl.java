package com.znsx.cms.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Hibernate;
import org.jdom.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.znsx.cms.aop.annotation.LogParam;
import com.znsx.cms.persistent.dao.AddressBookDAO;
import com.znsx.cms.persistent.dao.CameraDAO;
import com.znsx.cms.persistent.dao.ControlDeviceDAO;
import com.znsx.cms.persistent.dao.EmUnitDAO;
import com.znsx.cms.persistent.dao.EventDAO;
import com.znsx.cms.persistent.dao.FireDAO;
import com.znsx.cms.persistent.dao.HospitalDAO;
import com.znsx.cms.persistent.dao.ImageDAO;
import com.znsx.cms.persistent.dao.OrganDAO;
import com.znsx.cms.persistent.dao.PoliceDAO;
import com.znsx.cms.persistent.dao.ResourceDAO;
import com.znsx.cms.persistent.dao.ResourceUserDAO;
import com.znsx.cms.persistent.dao.RoadAdminDAO;
import com.znsx.cms.persistent.dao.RoadMouthDAO;
import com.znsx.cms.persistent.dao.SchemeDictionaryDAO;
import com.znsx.cms.persistent.dao.SchemeInstanceDAO;
import com.znsx.cms.persistent.dao.SchemeStepInstanceDAO;
import com.znsx.cms.persistent.dao.SchemeTempletDAO;
import com.znsx.cms.persistent.dao.StakeNumberLibDAO;
import com.znsx.cms.persistent.dao.StandardNumberDAO;
import com.znsx.cms.persistent.dao.StoreDAO;
import com.znsx.cms.persistent.dao.TeamDAO;
import com.znsx.cms.persistent.dao.TempletDictionaryDAO;
import com.znsx.cms.persistent.dao.TollgateDAO;
import com.znsx.cms.persistent.dao.VehicleDAO;
import com.znsx.cms.persistent.dao.VehicleDetectorDAO;
import com.znsx.cms.persistent.dao.WareHouseDAO;
import com.znsx.cms.persistent.model.AddressBook;
import com.znsx.cms.persistent.model.Camera;
import com.znsx.cms.persistent.model.ControlDevice;
import com.znsx.cms.persistent.model.ControlDeviceCms;
import com.znsx.cms.persistent.model.EmUnit;
import com.znsx.cms.persistent.model.Event;
import com.znsx.cms.persistent.model.Fire;
import com.znsx.cms.persistent.model.Hospital;
import com.znsx.cms.persistent.model.Image;
import com.znsx.cms.persistent.model.Organ;
import com.znsx.cms.persistent.model.OrganRoad;
import com.znsx.cms.persistent.model.Police;
import com.znsx.cms.persistent.model.Resource;
import com.znsx.cms.persistent.model.ResourceUser;
import com.znsx.cms.persistent.model.RoadAdmin;
import com.znsx.cms.persistent.model.RoadMouth;
import com.znsx.cms.persistent.model.SchemeDictionary;
import com.znsx.cms.persistent.model.SchemeInstance;
import com.znsx.cms.persistent.model.SchemeStepInstance;
import com.znsx.cms.persistent.model.SchemeTemplet;
import com.znsx.cms.persistent.model.StakeNumberLib;
import com.znsx.cms.persistent.model.StandardNumber;
import com.znsx.cms.persistent.model.Store;
import com.znsx.cms.persistent.model.Team;
import com.znsx.cms.persistent.model.TempletDictionary;
import com.znsx.cms.persistent.model.Tollgate;
import com.znsx.cms.persistent.model.Vehicle;
import com.znsx.cms.persistent.model.VehicleDetector;
import com.znsx.cms.persistent.model.WareHouse;
import com.znsx.cms.service.common.TypeDefinition;
import com.znsx.cms.service.comparator.SchemeInstanceStepComparator;
import com.znsx.cms.service.comparator.SchemeTempletStepComparator;
import com.znsx.cms.service.exception.BusinessException;
import com.znsx.cms.service.exception.ErrorCode;
import com.znsx.cms.service.iface.EmManager;
import com.znsx.cms.service.model.SchemeInstanceVO;
import com.znsx.cms.service.model.SchemeTempletVO;
import com.znsx.cms.service.model.UnitFireVO;
import com.znsx.cms.service.model.UnitHospitalVO;
import com.znsx.cms.service.model.UnitRoadAdminVO;
import com.znsx.cms.service.model.UnitVO;
import com.znsx.cms.service.model.UnitWareHouseVO;
import com.znsx.cms.web.dto.omc.GetAddressBookVO;
import com.znsx.cms.web.dto.omc.ResourceUserVO;
import com.znsx.cms.web.dto.omc.ResourceVO;
import com.znsx.cms.web.dto.omc.TeamVO;
import com.znsx.cms.web.dto.omc.VehicleVO;
import com.znsx.util.number.NumberUtil;
import com.znsx.util.string.MyStringUtil;
import com.znsx.util.xml.ElementUtil;

/**
 * 应急预案业务接口实现
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午3:49:09
 */
public class EmManagerImpl extends BaseManagerImpl implements EmManager {
	@Autowired
	private OrganDAO organDAO;
	@Autowired
	private SchemeTempletDAO schemeTempletDAO;
	@Autowired
	private SchemeDictionaryDAO schemeDictionaryDAO;
	@Autowired
	private TempletDictionaryDAO templetDictionaryDAO;
	@Autowired
	private EmUnitDAO emUnitDAO;
	@Autowired
	private ResourceDAO resourceDAO;
	@Autowired
	private StoreDAO storeDAO;
	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private TeamDAO teamDAO;
	@Autowired
	private ResourceUserDAO resourceUserDAO;
	@Autowired
	private SchemeInstanceDAO schemeDAO;
	@Autowired
	private SchemeStepInstanceDAO schemeStepDAO;
	@Autowired
	private FireDAO fireDAO;
	@Autowired
	private HospitalDAO hospitalDAO;
	@Autowired
	private PoliceDAO policeDAO;
	@Autowired
	private RoadAdminDAO roadAdminDAO;
	@Autowired
	private EventDAO eventDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private WareHouseDAO wareHouseDAO;
	@Autowired
	private ControlDeviceDAO controlDeviceDAO;
	@Autowired
	private TollgateDAO tollgateDAO;
	@Autowired
	private RoadMouthDAO roadMouthDAO;
	@Autowired
	private StakeNumberLibDAO stakeLibDAO;
	@Autowired
	private AddressBookDAO addressbookDAO;
	@Autowired
	private VehicleDetectorDAO vdDAO;
	@Autowired
	private CameraDAO cameraDAO;
	@Autowired
	private StandardNumberDAO snDAO;

	@Override
	public void bindTempletDictionary(String templetId, String dictionaryId,
			Short seq, Integer range) throws BusinessException {
		SchemeTemplet templet = schemeTempletDAO.findById(templetId);
		SchemeDictionary dictionary = schemeDictionaryDAO
				.findById(dictionaryId);
		TempletDictionary td = new TempletDictionary();
		td.setSeq(seq);
		td.setRange(range);
		td.setSchemeDictionary(dictionary);
		td.setSchemeTemplet(templet);
		templetDictionaryDAO.save(td);
	}

	@Override
	public String createDictionary(String className, String description)
			throws BusinessException {
		SchemeDictionary entity = new SchemeDictionary();
		entity.setTargetClass(className);
		entity.setDescription(description);
		schemeDictionaryDAO.save(entity);
		return entity.getId();
	}

	@Override
	public String createTemplet(String name, Short eventType, Short eventLevel,
			String organId) throws BusinessException {
		SchemeTemplet entity = new SchemeTemplet();
		entity.setName(name);
		entity.setEventType(eventType);
		entity.setEventLevel(eventLevel);
		Organ organ = organDAO.findById(organId);
		entity.setOrgan(organ);
		schemeTempletDAO.save(entity);
		return entity.getId();
	}

	@Override
	public void deleteTemplet(String templetId) throws BusinessException {
		SchemeTemplet entity = schemeTempletDAO.findById(templetId);
		schemeTempletDAO.delete(entity);
	}

	@Override
	public String saveSchemeTemplet(@LogParam("name") String name,
			String organId, Element schemeTemplet) throws BusinessException {
		// 保存预案库
		String id = schemeTemplet.getAttributeValue("Id");
		Short eventType = ElementUtil.getShort(schemeTemplet, "Type");
		Short eventLevel = ElementUtil.getShort(schemeTemplet, "Level");
		List<Element> steps = schemeTemplet.getChildren();
		SchemeTemplet entity = null;
		Set<TempletDictionary> set = null;
		// 修改
		if (StringUtils.isNotBlank(id)) {
			entity = schemeTempletDAO.findById(id);
			entity.setName(name);
			entity.setEventType(eventType);
			entity.setEventLevel(eventLevel);
			set = entity.getTempletDictionarys();
			// 修改预案字典
			for (Element step : steps) {
				Short seq = ElementUtil.getShort(step, "Seq");
				String content = step.getAttributeValue("Content");
				String targetClass = extractClass(content);
				Integer range = extractRange(content);
				Short targetType = class2Type(targetClass);
				boolean existFlag = false;
				for (TempletDictionary td : set) {
					if (td.getSeq().shortValue() == seq.shortValue()) {
						existFlag = true;
						SchemeDictionary dictionary = td.getSchemeDictionary();
						dictionary.setDescription(content);
						dictionary.setTargetClass(targetClass);
						dictionary.setTargetType(targetType);
						break;
					}
				}
				// 新增的步骤
				if (!existFlag) {
					// 新增字典
					SchemeDictionary dictionary = new SchemeDictionary();
					dictionary.setDescription(content);
					dictionary.setTargetClass(targetClass);
					dictionary.setTargetType(targetType);
					schemeDictionaryDAO.save(dictionary);
					// 关联预案库
					TempletDictionary td = new TempletDictionary();
					td.setSchemeDictionary(dictionary);
					td.setSchemeTemplet(entity);
					td.setSeq(seq);
					td.setRange(range);
					templetDictionaryDAO.save(td);
				}
			}
		}
		// 新增
		else {
			entity = new SchemeTemplet();
			entity.setName(name);
			entity.setEventType(eventType);
			entity.setEventLevel(eventLevel);
			entity.setOrgan(organDAO.findById(organId));
			schemeTempletDAO.save(entity);
			// 保存预案字典
			for (Element step : steps) {
				Short seq = ElementUtil.getShort(step, "Seq");
				String content = step.getAttributeValue("Content");
				String targetClass = extractClass(content);
				Integer range = extractRange(content);
				Short targetType = class2Type(targetClass);
				// 新增字典
				SchemeDictionary dictionary = new SchemeDictionary();
				dictionary.setDescription(content);
				dictionary.setTargetClass(targetClass);
				dictionary.setTargetType(targetType);
				schemeDictionaryDAO.save(dictionary);
				// 关联预案库
				TempletDictionary td = new TempletDictionary();
				td.setSchemeDictionary(dictionary);
				td.setSchemeTemplet(entity);
				td.setSeq(seq);
				td.setRange(range);
				templetDictionaryDAO.save(td);
			}
		}
		return entity.getId();
	}

	@Override
	public List<SchemeTempletVO> listSchemeTemplet(String organId, Short type) {
		List<SchemeTemplet> templets = schemeTempletDAO.listSchemeTemplet(
				organId, type);
		List<SchemeTempletVO> list = new LinkedList<SchemeTempletVO>();
		for (SchemeTemplet templet : templets) {
			SchemeTempletVO vo = new SchemeTempletVO();
			vo.setId(templet.getId());
			vo.setName(templet.getName());
			vo.setLevel(templet.getEventLevel().toString());
			vo.setType(templet.getEventType().toString());

			Set<TempletDictionary> set = templet.getTempletDictionarys();
			List<SchemeTempletVO.Step> steps = new LinkedList<SchemeTempletVO.Step>();
			for (TempletDictionary step : set) {
				SchemeTempletVO.Step stepVO = vo.new Step();
				stepVO.setSeq(step.getSeq().toString());
				stepVO.setContent(step.getSchemeDictionary().getDescription());
				steps.add(stepVO);
			}
			Collections.sort(steps, new SchemeTempletStepComparator());
			vo.setSteps(steps);
			list.add(vo);
		}
		return list;
	}

	@Override
	public String saveScheme(String id, String name, String organId,
			String templetId, String eventId, String createUserId,
			String createUserName, Element scheme) throws BusinessException {
		SchemeInstance si = null;
		List<Element> steps = scheme.getChildren("Step");
		// 新增
		if (StringUtils.isBlank(id)) {
			si = new SchemeInstance();
			si.setName(name);
			si.setTempletId(templetId);
			si.setEventId(eventId);
			si.setCreateUserId(createUserId);
			si.setCreateUserName(createUserName);
			si.setCreateTime(System.currentTimeMillis());
			si.setUpdateTime(System.currentTimeMillis());
			si.setActionStatus(TypeDefinition.ACTION_STATUS_0);
			// si.setOrganId(organId);
			schemeDAO.save(si);
			// 保存步骤
			for (Element step : steps) {
				Short seq = ElementUtil.getShort(step, "Seq");
				String type = step.getAttributeValue("Type");
				Short targetType = class2Type(type);
				List<Element> items = step.getChildren();
				for (Element item : items) {
					SchemeStepInstance entity = new SchemeStepInstance();
					entity.setSeq(seq);
					entity.setTargetType(targetType);
					entity.setTargetId(item.getAttributeValue("TargetId"));
					entity.setTargetName(item.getAttributeValue("TargetName"));
					entity.setTelephone(item.getAttributeValue("Telephone"));
					entity.setLinkMan(item.getAttributeValue("LinkMan"));
					entity.setRequestContent(item
							.getAttributeValue("RequestContent"));
					entity.setResponseContent(item
							.getAttributeValue("ResponseContent"));
					entity.setBeginTime(ElementUtil.getLong(item, "BeginTime"));
					entity.setArriveTime(ElementUtil
							.getLong(item, "ArriveTime"));
					entity.setEndTime(ElementUtil.getLong(item, "EndTime"));
					entity.setActionStatus(TypeDefinition.ACTION_STATUS_0);
					entity.setColor(item.getAttributeValue("Color"));
					entity.setContent(item.getAttributeValue("Content"));
					entity.setDuration(ElementUtil.getInteger(item, "Duration"));
					entity.setFont(item.getAttributeValue("Font"));
					entity.setNote(item.getAttributeValue("Note"));
					entity.setPlaySize(item.getAttributeValue("Size"));
					entity.setSpace(ElementUtil.getShort(item, "Space"));
					entity.setState(ElementUtil.getShort(item, "Status"));
					entity.setTargetNumber(ElementUtil.getInteger(item,
							"Number"));
					entity.setSchemeInstance(si);
					schemeStepDAO.save(entity);
				}
			}
		}
		// 修改
		else {
			si = schemeDAO.findById(id);
			// 只允许修改名称
			si.setName(name);
			si.setTempletId(templetId);
			// 记录保存中的所有StepId
			Map<String, Boolean> stepMap = new HashMap<String, Boolean>();
			// 保存步骤
			for (Element step : steps) {
				Short seq = ElementUtil.getShort(step, "Seq");
				String type = step.getAttributeValue("Type");
				Short targetType = class2Type(type);
				List<Element> items = step.getChildren();
				for (Element item : items) {
					SchemeStepInstance entity = null;
					// 增加一个步骤
					if (StringUtils.isBlank(item.getAttributeValue("Id"))) {
						entity = new SchemeStepInstance();
					}
					// 修改当前步骤
					else {
						entity = schemeStepDAO.findById(item
								.getAttributeValue("Id"));
					}
					entity.setSeq(seq);
					entity.setTargetType(targetType);
					entity.setTargetId(item.getAttributeValue("TargetId"));
					entity.setTargetName(item.getAttributeValue("TargetName"));
					entity.setTelephone(item.getAttributeValue("Telephone"));
					entity.setLinkMan(item.getAttributeValue("LinkMan"));
					entity.setRequestContent(item
							.getAttributeValue("RequestContent"));
					entity.setResponseContent(item
							.getAttributeValue("ResponseContent"));
					entity.setBeginTime(ElementUtil.getLong(item, "BeginTime"));
					entity.setArriveTime(ElementUtil
							.getLong(item, "ArriveTime"));
					entity.setEndTime(ElementUtil.getLong(item, "EndTime"));
					entity.setActionStatus(TypeDefinition.ACTION_STATUS_0);
					entity.setColor(item.getAttributeValue("Color"));
					entity.setContent(item.getAttributeValue("Content"));
					entity.setDuration(ElementUtil.getInteger(item, "Duration"));
					entity.setFont(item.getAttributeValue("Font"));
					entity.setNote(item.getAttributeValue("Note"));
					entity.setPlaySize(item.getAttributeValue("Size"));
					entity.setSpace(ElementUtil.getShort(item, "Space"));
					entity.setState(ElementUtil.getShort(item, "Status"));
					entity.setTargetNumber(ElementUtil.getInteger(item,
							"Number"));
					entity.setSchemeInstance(si);
					schemeStepDAO.saveorupdate(entity);

					stepMap.put(entity.getId(), Boolean.TRUE);
				}
			}
			// 需要删除的步骤
			// 查询保存完毕后的该预案实例的所有步骤
			Set<SchemeStepInstance> stepSet = si.getSteps();
			// 存放需要删除的StepId
			List<String> deleteList = new LinkedList<String>();
			for (SchemeStepInstance stepInstance : stepSet) {
				if (stepMap.get(stepInstance.getId()) == null) {
					deleteList.add(stepInstance.getId());
				}
			}
			for (String stepId : deleteList) {
				SchemeStepInstance ssi = schemeStepDAO.findById(stepId);
				stepSet.remove(ssi);
				schemeStepDAO.delete(ssi);
			}
		}

		return si.getId();
	}

	@Override
	public SchemeInstanceVO getSchemeInstance(String eventId) {
		SchemeInstance si = schemeDAO.getSchemeInstance(eventId);
		if (si == null) {
			return null;
		} else {
			SchemeInstanceVO vo = new SchemeInstanceVO();
			vo.setEventId(eventId);
			vo.setId(si.getId());
			vo.setName(si.getName());
			vo.setSchemeTempletId(si.getTempletId());
			vo.setUserId(si.getCreateUserId());
			vo.setUserName(si.getCreateUserName());

			Set<SchemeStepInstance> steps = si.getSteps();
			List<SchemeInstanceVO.Step> stepVOList = new LinkedList<SchemeInstanceVO.Step>();
			int index = -1;
			for (SchemeStepInstance step : steps) {
				String targetType = type2Class(step.getTargetType());
				SchemeInstanceVO.Step stepVO = vo.new Step();
				stepVO.setType(targetType);
				Short seq = step.getSeq();

				index = stepVOList.indexOf(stepVO);
				if (index >= 0) {
					stepVO = stepVOList.get(index);
					// 设置动作类型的步骤为较小的值
					if (Short.parseShort(stepVO.getSeq()) > seq.shortValue()) {
						stepVO.setSeq(seq.toString());
					}
				} else {
					stepVO.setSeq(seq.toString());
					stepVOList.add(stepVO);
				}

				SchemeInstanceVO.Item item = vo.new Item();
				item.setActionStatus(step.getActionStatus().toString());
				item.setArriveTime(MyStringUtil.object2StringNotNull(step
						.getArriveTime()));
				item.setBeginTime(MyStringUtil.object2StringNotNull(step
						.getBeginTime()));
				item.setColor(MyStringUtil.object2StringNotNull(step.getColor()));
				item.setContent(MyStringUtil.object2StringNotNull(step
						.getContent()));
				item.setDuration(MyStringUtil.object2StringNotNull(step
						.getDuration()));
				item.setEndTime(MyStringUtil.object2StringNotNull(step
						.getEndTime()));
				item.setFont(MyStringUtil.object2StringNotNull(step.getFont()));
				item.setId(step.getId());
				item.setLinkMan(MyStringUtil.object2StringNotNull(step
						.getLinkMan()));
				item.setNote(MyStringUtil.object2StringNotNull(step.getNote()));
				item.setNumber(MyStringUtil.object2StringNotNull(step
						.getTargetNumber()));
				item.setRequestContent(MyStringUtil.object2StringNotNull(step
						.getRequestContent()));
				item.setResponseContent(MyStringUtil.object2StringNotNull(step
						.getResponseContent()));
				item.setSize(MyStringUtil.object2StringNotNull(step
						.getPlaySize()));
				item.setSpace(MyStringUtil.object2StringNotNull(step.getSpace()));
				item.setStatus(MyStringUtil.object2StringNotNull(step
						.getState()));
				item.setTargetId(MyStringUtil.object2StringNotNull(step
						.getTargetId()));
				item.setTargetName(MyStringUtil.object2StringNotNull(step
						.getTargetName()));
				item.setTelephone(MyStringUtil.object2StringNotNull(step
						.getTelephone()));
				item.setTargetType(type2Class(step.getTargetType()));
				item.setBeginStake(step.getBeginStake());
				item.setEndStake(step.getEndStake());
				item.setNavigation(step.getNavigation());
				item.setManagerUnit(step.getManagerUnit());
				item.setLocation(step.getLocation());
				stepVO.getItems().add(item);
			}
			Collections.sort(stepVOList, new SchemeInstanceStepComparator());
			vo.setSteps(stepVOList);
			return vo;
		}
	}

	@Override
	public void saveEventAction(List<SchemeStepInstance> steps, String eventId,
			String userId, String userName) throws BusinessException {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("eventId", eventId);
		List<SchemeInstance> schemes = schemeDAO.findByPropertys(params);
		SchemeInstance si = null;
		short seq = 1;
		// 已经保存过的预案
		if (schemes.size() > 0) {
			si = schemes.get(0);
			// 获得步骤序列最大值
			Set<SchemeStepInstance> set = si.getSteps();
			for (SchemeStepInstance stepInstance : set) {
				if (stepInstance.getSeq().shortValue() >= seq) {
					seq = (short) (stepInstance.getSeq().shortValue() + 1);
				}
			}
		}
		// 第一次保存该事件预案
		else {
			Event event = eventDAO.findById(eventId);
			si = new SchemeInstance();
			si.setActionStatus(TypeDefinition.EVENT_STATUS_1);
			si.setCreateTime(System.currentTimeMillis());
			si.setCreateUserId(userId);
			si.setCreateUserName(userName);
			si.setEventId(eventId);
			si.setName(event.getName() + "预案");
			si.setUpdateTime(System.currentTimeMillis());
			schemeDAO.save(si);
		}
		// 保存执行动作
		for (SchemeStepInstance step : steps) {
			step.setActionStatus(TypeDefinition.EVENT_STATUS_1);
			step.setBeginTime(System.currentTimeMillis());
			step.setSchemeInstance(si);
			step.setSeq(seq);
			schemeStepDAO.save(step);
		}
	}

	/**
	 * 从content内容中抽取操作对象名称
	 * 
	 * @param content
	 *            步骤内容
	 * @return 操作对象名称
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-5 上午11:32:25
	 */
	private String extractClass(String content) {
		String clazz = null;
		try {
			String key = content.substring(content.indexOf("[[") + 2,
					content.indexOf("]]"));
			clazz = key.substring(0, key.indexOf('('));
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Step content[" + content + "] not invalid !");
		}
		return clazz;
	}

	/**
	 * 从content内容中抽取范围
	 * 
	 * @param content
	 *            步骤内容
	 * @return 范围
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-5 上午11:32:25
	 */
	private Integer extractRange(String content) {
		Integer range = null;
		try {
			String key = content.substring(content.indexOf("[[") + 2,
					content.indexOf("]]"));
			range = Integer.valueOf(key.substring(key.indexOf('(') + 1,
					key.length() - 1));
		} catch (NumberFormatException e) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Step content[" + content + "] not invalid !");
		}
		return range;
	}

	/**
	 * 对象名称转换为对象类型
	 * 
	 * @param clazz
	 *            对象名称
	 * @return 对象类型
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-6-5 上午11:40:11
	 */
	public Short class2Type(String clazz) throws BusinessException {
		if (TypeDefinition.RESOURCE_TYPE_CAMERA.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_CAMERA;
		} else if (TypeDefinition.RESOURCE_TYPE_CMS.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_CMS;
		} else if (TypeDefinition.RESOURCE_TYPE_COVI.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_COVI;
		} else if (TypeDefinition.RESOURCE_TYPE_FD.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_FD;
		} else if (TypeDefinition.RESOURCE_TYPE_LOLI.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_LOLI;
		} else if (TypeDefinition.RESOURCE_TYPE_NOD.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_NOD;
		} else if (TypeDefinition.RESOURCE_TYPE_PB.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_PB;
		} else if (TypeDefinition.RESOURCE_TYPE_VD.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_VD;
		} else if (TypeDefinition.RESOURCE_TYPE_WS.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_WS;
		} else if (TypeDefinition.RESOURCE_TYPE_WST.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_WST;
		} else if (TypeDefinition.RESOURCE_TYPE_WP.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_WP;
		} else if (TypeDefinition.RESOURCE_TYPE_FAN.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_FAN;
		} else if (TypeDefinition.RESOURCE_TYPE_LIGHT.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_LIGHT;
		} else if (TypeDefinition.RESOURCE_TYPE_RD.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_RD;
		} else if (TypeDefinition.RESOURCE_TYPE_RAIL.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_RAIL;
		} else if (TypeDefinition.RESOURCE_TYPE_IS.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_IS;
		} else if (TypeDefinition.RESOURCE_TYPE_FIRE.equals(clazz)) {
			return Short.valueOf(TypeDefinition.ORGAN_TYPE_FIRE);
		} else if (TypeDefinition.RESOURCE_TYPE_HOSPITAL.equals(clazz)) {
			return Short.valueOf(TypeDefinition.ORGAN_TYPE_HOSPITAL);
		} else if (TypeDefinition.RESOURCE_TYPE_POLICE.equals(clazz)) {
			return Short.valueOf(TypeDefinition.ORGAN_TYPE_POLICE);
		} else if (TypeDefinition.RESOURCE_TYPE_ROAD_ADMIN.equals(clazz)) {
			return Short.valueOf(TypeDefinition.ORGAN_TYPE_ROAD_ADMIN);
		} else if (TypeDefinition.RESOURCE_TYPE_EM_RESOURCE.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_EM_RESOURCE;
		} else if (TypeDefinition.RESOURCE_TYPE_ROAD_CONTROL.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_ROAD_MOUTH;
		} else if (TypeDefinition.RESOURCE_TYPE_TOLLGATE.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_TOLLGATE;
		} else if (TypeDefinition.RESOURCE_TYPE_WALL.equals(clazz)) {
			return (short) TypeDefinition.DEVICE_TYPE_DISPLAY_WALL;
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID, "Class["
					+ clazz + "] not support !");
		}
	}

	private String type2Class(Short type) throws BusinessException {
		if (TypeDefinition.ORGAN_TYPE_HOSPITAL.equals(type.toString())) {
			return TypeDefinition.RESOURCE_TYPE_HOSPITAL;
		} else if (TypeDefinition.ORGAN_TYPE_POLICE.equals(type.toString())) {
			return TypeDefinition.RESOURCE_TYPE_POLICE;
		} else if (TypeDefinition.ORGAN_TYPE_FIRE.equals(type.toString())) {
			return TypeDefinition.RESOURCE_TYPE_FIRE;
		} else if (TypeDefinition.ORGAN_TYPE_ROAD_ADMIN.equals(type.toString())) {
			return TypeDefinition.RESOURCE_TYPE_ROAD_ADMIN;
		} else if (TypeDefinition.DEVICE_TYPE_CAMERA == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_CAMERA;
		} else if (TypeDefinition.DEVICE_TYPE_CMS == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_CMS;
		} else if (TypeDefinition.DEVICE_TYPE_COVI == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_COVI;
		} else if (TypeDefinition.DEVICE_TYPE_FAN == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_FAN;
		} else if (TypeDefinition.DEVICE_TYPE_FD == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_FD;
		} else if (TypeDefinition.DEVICE_TYPE_IS == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_IS;
		} else if (TypeDefinition.DEVICE_TYPE_LIGHT == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_LIGHT;
		} else if (TypeDefinition.DEVICE_TYPE_LOLI == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_LOLI;
		} else if (TypeDefinition.DEVICE_TYPE_NOD == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_NOD;
		} else if (TypeDefinition.DEVICE_TYPE_PB == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_PB;
		} else if (TypeDefinition.DEVICE_TYPE_RAIL == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_RAIL;
		} else if (TypeDefinition.DEVICE_TYPE_RD == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_RD;
		} else if (TypeDefinition.DEVICE_TYPE_VD == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_VD;
		} else if (TypeDefinition.DEVICE_TYPE_WP == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_WP;
		} else if (TypeDefinition.DEVICE_TYPE_WS == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_WS;
		} else if (TypeDefinition.DEVICE_TYPE_WST == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_WST;
		} else if (TypeDefinition.DEVICE_TYPE_EM_RESOURCE == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_EM_RESOURCE;
		} else if (TypeDefinition.DEVICE_TYPE_TOLLGATE == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_TOLLGATE;
		} else if (TypeDefinition.DEVICE_TYPE_ROAD_MOUTH == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_ROAD_CONTROL;
		} else if (TypeDefinition.DEVICE_TYPE_DISPLAY_WALL == type.intValue()) {
			return TypeDefinition.RESOURCE_TYPE_WALL;
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"ClassType[" + type.toString() + "] not support !");
		}
	}

	@Override
	public String createUnit(String name, String linkMan, String mobile,
			String location, String longitude, String latitude, String fax,
			String email, String telephone, String note, String organId,
			String standardNumber) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<EmUnit> list = emUnitDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = emUnitDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }
		EmUnit unit = new EmUnit();
		unit.setEmail(email);
		unit.setFax(fax);
		unit.setLatitude(latitude);
		unit.setLinkMan(linkMan);
		unit.setLocation(location);
		unit.setLongitude(longitude);
		unit.setMobile(mobile);
		unit.setName(name);
		unit.setNote(note);
		unit.setStandardNumber(standardNumber);
		unit.setTelephone(telephone);
		unit.setOrgan(organDAO.findById(organId));
		unit.setCreateTime(System.currentTimeMillis());
		emUnitDAO.save(unit);
		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_UNIT);
		return unit.getId();
	}

	@Override
	public void updateUnit(String id, String standardNumber, String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<EmUnit> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = emUnitDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = emUnitDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		EmUnit unit = emUnitDAO.findById(id);
		if (null != standardNumber) {
			unit.setStandardNumber(standardNumber);
		}
		if (null != name) {
			unit.setName(name);
		}
		if (null != linkMan) {
			unit.setLinkMan(linkMan);
		}
		if (null != mobile) {
			unit.setMobile(mobile);
		}
		if (null != location) {
			unit.setLocation(location);
		}
		if (null != longitude) {
			unit.setLongitude(longitude);
		}
		if (null != latitude) {
			unit.setLatitude(latitude);
		}
		if (null != fax) {
			unit.setFax(fax);
		}
		if (null != email) {
			unit.setEmail(email);
		}
		if (null != telephone) {
			unit.setTelephone(telephone);
		}
		if (null != note) {
			unit.setNote(note);
		}
		if (null != organId) {
			unit.setOrgan(organDAO.findById(organId));
		}
	}

	@Override
	public void deleteUnit(String id) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("unit.id", id);
		List<Store> stores = storeDAO.findByPropertys(params);
		for (Store store : stores) {
			storeDAO.delete(store);
		}

		EmUnit unit = emUnitDAO.findById(id);
		// 同步更新sn
		syncSN(unit.getStandardNumber(), null,
				TypeDefinition.RESOURCE_TYPE_UNIT);
		// 删除应急单位
		emUnitDAO.delete(unit);

	}

	@Override
	public UnitVO getUnit(String id) {
		EmUnit unit = emUnitDAO.findById(id);
		UnitVO vo = new UnitVO();
		vo.setId(unit.getId());
		vo.setEmail(unit.getEmail());
		vo.setFax(unit.getFax());
		vo.setLatitude(unit.getLatitude());
		vo.setLinkMan(unit.getLinkMan());
		vo.setLocation(unit.getLocation());
		vo.setLongitude(unit.getLongitude());
		vo.setMobile(unit.getMobile());
		vo.setName(unit.getName());
		vo.setNote(unit.getNote());
		vo.setOrganId(unit.getOrgan().getId());
		vo.setStandardNumber(unit.getStandardNumber());
		vo.setTelephone(unit.getTelephone());
		vo.setCreateTime(unit.getCreateTime() + "");
		vo.setType("3");
		return vo;
	}

	@Override
	public List<UnitVO> listUnit(String name, String organId,
			Integer startIndex, Integer limit) {
		List<EmUnit> units = emUnitDAO.listUnit(name, organId, startIndex,
				limit);
		List<UnitVO> list = new ArrayList<UnitVO>();
		for (EmUnit unit : units) {
			UnitVO vo = new UnitVO();
			if (unit instanceof Fire) {
				vo.setType("1");
			} else if (unit instanceof Hospital) {
				vo.setType("2");
			} else if (unit instanceof Police) {
				vo.setType("3");
			} else if (unit instanceof RoadAdmin) {
				vo.setType("4");
			} else if (unit instanceof WareHouse) {
				vo.setType("5");
			}
			vo.setEmail(unit.getEmail());
			vo.setFax(unit.getFax());
			vo.setId(unit.getId());
			vo.setLatitude(unit.getLatitude());
			vo.setLinkMan(unit.getLinkMan());
			vo.setLocation(unit.getLocation());
			vo.setLongitude(unit.getLongitude());
			vo.setMobile(unit.getMobile());
			vo.setName(unit.getName());
			vo.setNote(unit.getNote());
			vo.setOrganId(unit.getOrgan() != null ? unit.getOrgan().getId()
					: "");
			vo.setStandardNumber(unit.getStandardNumber());
			vo.setTelephone(unit.getTelephone());
			vo.setCreateTime(unit.getCreateTime() + "");
			list.add(vo);
		}
		return list;
	}

	@Override
	public Integer countTotalUnit(String name, String organId) {
		return emUnitDAO.countTotalUnit(name, organId);
	}

	@Override
	public String createResource(String name, String unitId,
			String standardNumber, String abilityType, String note,
			String unitType, String minNumber, String resourceNumber) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Resource> list = resourceDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = resourceDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		Resource resource = new Resource();
		resource.setAbilityType(abilityType);
		resource.setName(name);
		resource.setNote(note);
		resource.setStandardNumber(standardNumber);
		resource.setUnit(emUnitDAO.findById(unitId));
		resource.setUnitType(unitType);
		resourceDAO.save(resource);

		Store store = new Store();
		store.setMinNumber(minNumber);
		store.setName(name);
		store.setResourceNumber(resourceNumber);
		store.setResource(resourceDAO.findById(resource.getId()));
		store.setUnit(emUnitDAO.findById(unitId));
		storeDAO.save(store);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_RESOURCE);
		return resource.getId();
	}

	@Override
	public void updateResource(String id, String name, String unitId,
			String standardNumber, String abilityType, String note,
			String unitType, String minNumber, String resourceNumber) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Resource> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = resourceDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = resourceDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		Resource resource = resourceDAO.findById(id);
		if (null != name) {
			resource.setName(name);
		}
		if (StringUtils.isNotBlank(unitId)) {
			resource.setUnit(emUnitDAO.findById(unitId));
		}
		if (null != standardNumber) {
			resource.setStandardNumber(standardNumber);
		}
		if (null != abilityType) {
			resource.setAbilityType(abilityType);
		}
		if (null != note) {
			resource.setNote(note);
		}
		if (null != unitType) {
			resource.setUnitType(unitType);
		}

		params.clear();
		params.put("resource.id", id);

		List<Store> stores = storeDAO.findByPropertys(params);
		Store store = stores.get(0);
		if (null != minNumber) {
			store.setMinNumber(minNumber);
		}
		if (null != resourceNumber) {
			store.setResourceNumber(resourceNumber);
		}
	}

	@Override
	public ResourceVO getResource(String id) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("resource.id", id);
		Resource resource = resourceDAO.findById(id);
		List<Store> stores = storeDAO.findByPropertys(params);
		ResourceVO vo = new ResourceVO();
		vo.setAbilityType(resource.getAbilityType());
		vo.setId(resource.getId());
		vo.setName(resource.getName());
		vo.setNote(resource.getNote());
		vo.setStandardNumber(resource.getStandardNumber());
		vo.setUnitId(resource.getUnit().getId());
		vo.setUnitType(resource.getUnitType());
		vo.setMinNumber(stores.get(0).getMinNumber());
		vo.setResourceNumber(stores.get(0).getResourceNumber());
		return vo;
	}

	@Override
	public Integer countTotalResource(String unitId, String abilityType,
			String unitType) {
		return resourceDAO.countTotalResource(unitId, abilityType, unitType);
	}

	@Override
	public List<ResourceVO> listResource(String unitId, String abilityType,
			String unitType, Integer startIndex, Integer limit) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();

		List<Resource> resources = resourceDAO.listResource(unitId,
				abilityType, unitType, startIndex, limit);
		List<ResourceVO> list = new ArrayList<ResourceVO>();
		for (Resource resource : resources) {
			params.clear();
			params.put("resource.id", resource.getId());
			List<Store> stores = storeDAO.findByPropertys(params);
			ResourceVO vo = new ResourceVO();
			vo.setAbilityType(resource.getAbilityType());
			vo.setId(resource.getId());
			vo.setName(resource.getName());
			vo.setNote(resource.getNote());
			vo.setStandardNumber(resource.getStandardNumber());
			vo.setUnitId(resource.getUnit().getId());
			vo.setUnitType(resource.getUnitType());
			vo.setMinNumber(stores.get(0).getMinNumber());
			vo.setResourceNumber(stores.get(0).getResourceNumber());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void deleteResource(String id) {
		// 查询store下是否有资源
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("resource.id", id);
		List<Store> stores = storeDAO.findByPropertys(params);
		storeDAO.deleteById(stores.get(0).getId());

		resourceDAO.deleteById(id);

	}

	@Override
	public String createVehicle(String unitId, String name,
			String standardNumber, String abilityType, String seatNumber,
			String vehicleNumber, String status, String note) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Vehicle> list = vehicleDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = vehicleDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		Vehicle vehicle = new Vehicle();
		vehicle.setAbilityType(abilityType);
		vehicle.setName(name);
		vehicle.setNote(note);
		vehicle.setSeatNumber(seatNumber);
		vehicle.setStandardNumber(standardNumber);
		vehicle.setStatus(status);
		vehicle.setUnit(emUnitDAO.findById(unitId));
		vehicle.setVehicleNumber(vehicleNumber);
		vehicleDAO.save(vehicle);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_VEHICLE);
		return vehicle.getId();
	}

	@Override
	public void updateVehicle(String id, String unitId, String name,
			String standardNumber, String abilityType, String seatNumber,
			String vehicleNumber, String status, String note) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Vehicle> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = vehicleDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = vehicleDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		Vehicle vehicle = vehicleDAO.findById(id);
		if (null != unitId) {
			vehicle.setUnit(emUnitDAO.findById(unitId));
		}
		if (null != name) {
			vehicle.setName(name);
		}
		if (null != standardNumber) {
			vehicle.setStandardNumber(standardNumber);
		}
		if (null != abilityType) {
			vehicle.setAbilityType(abilityType);
		}
		if (null != seatNumber) {
			vehicle.setSeatNumber(seatNumber);
		}
		if (null != vehicleNumber) {
			vehicle.setVehicleNumber(vehicleNumber);
		}
		if (null != status) {
			vehicle.setStatus(status);
		}
		if (null != note) {
			vehicle.setNote(note);
		}
	}

	@Override
	public void deleteVehicle(String id) {
		vehicleDAO.deleteById(id);
	}

	@Override
	public VehicleVO getVehicle(String id) {
		Vehicle vehicle = vehicleDAO.findById(id);
		VehicleVO vo = new VehicleVO();
		vo.setAbilityType(vehicle.getAbilityType());
		vo.setId(vehicle.getId());
		vo.setName(vehicle.getName());
		vo.setNote(vehicle.getNote());
		vo.setSeatNumber(vehicle.getSeatNumber());
		vo.setStandardNumber(vehicle.getStandardNumber());
		vo.setStatus(vehicle.getStatus());
		vo.setUnitId(vehicle.getUnit().getId());
		vo.setVehicleNumber(vehicle.getVehicleNumber());
		return vo;
	}

	@Override
	public Integer countTotalVehilce(String unitId, String abilityType,
			String name) {
		return vehicleDAO.countTotalVehilce(unitId, abilityType, name);
	}

	@Override
	public List<VehicleVO> listVehicle(String unitId, String abilityType,
			String name, Integer startIndex, Integer limit) {
		List<Vehicle> vehicles = vehicleDAO.listVehicle(unitId, abilityType,
				name, startIndex, limit);
		List<VehicleVO> list = new ArrayList<VehicleVO>();
		for (Vehicle vehicle : vehicles) {
			VehicleVO vo = new VehicleVO();
			vo.setAbilityType(vehicle.getAbilityType());
			vo.setId(vehicle.getId());
			vo.setName(vehicle.getName());
			vo.setNote(vehicle.getNote());
			vo.setSeatNumber(vehicle.getSeatNumber());
			vo.setStandardNumber(vehicle.getStandardNumber());
			vo.setStatus(vehicle.getStatus());
			vo.setUnitId(vehicle.getUnit().getId());
			vo.setVehicleNumber(vehicle.getVehicleNumber());
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createTeam(String name, String standardNumber, String unitId,
			String type, String note) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Team> list = teamDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = teamDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		Team team = new Team();
		team.setName(name);
		team.setNote(note);
		team.setStandardNumber(standardNumber);
		team.setType(type);
		team.setUnit(emUnitDAO.findById(unitId));

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_TEAM);
		teamDAO.save(team);
		return team.getId();
	}

	@Override
	public void updateTeam(String id, String name, String standardNumber,
			String unitId, String type, String note) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Team> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = teamDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = teamDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		Team team = teamDAO.findById(id);
		if (null != name) {
			team.setName(name);
		}
		if (null != standardNumber) {
			team.setStandardNumber(standardNumber);
		}
		if (null != unitId) {
			team.setUnit(emUnitDAO.findById(unitId));
		}
		if (null != type) {
			team.setType(type);
		}
		if (null != note) {
			team.setNote(note);
		}
	}

	@Override
	public void deleteTeam(String id) {
		// 删除子资源
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("team.id", id);
		List<ResourceUser> rus = resourceUserDAO.findByPropertys(params);
		if (rus.size() > 0) {
			throw new BusinessException(ErrorCode.CHILDREN_EXIST,
					"Team resource user found !");
		}

		teamDAO.deleteById(id);
	}

	@Override
	public TeamVO getTeam(String id) {
		Team team = teamDAO.findById(id);
		TeamVO vo = new TeamVO();
		vo.setId(team.getId());
		vo.setName(team.getName());
		vo.setNote(team.getNote());
		vo.setStandardNumber(team.getStandardNumber());
		vo.setType(team.getType());
		vo.setUnitId(team.getUnit().getId());
		return vo;
	}

	@Override
	public Integer countTotalTeam(String unitId, String type, String name) {
		return teamDAO.countTotalTeam(unitId, type, name);
	}

	@Override
	public List<TeamVO> listTeam(String unitId, String type, String name,
			Integer startIndex, Integer limit) {
		List<Team> teams = teamDAO.listTeam(unitId, type, name, startIndex,
				limit);
		List<TeamVO> list = new ArrayList<TeamVO>();
		for (Team team : teams) {
			TeamVO vo = new TeamVO();
			vo.setId(team.getId());
			vo.setName(team.getName());
			vo.setNote(team.getNote());
			vo.setStandardNumber(team.getStandardNumber());
			vo.setType(team.getType());
			vo.setUnitId(team.getUnit().getId());
			list.add(vo);
		}
		return list;
	}

	@Override
	public String createResourceUser(String standardNumber, String teamId,
			String telephone, String name, String type, String isAdmin,
			String speciality, String note) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<ResourceUser> list = resourceUserDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// 同一个队伍只有一个队长
		if (isAdmin.equals("1")) {
			params.clear();
			params.put("isAdmin", isAdmin);
			params.put("team.id", teamId);
			list = resourceUserDAO.findByPropertys(params);
			if (list.size() >= 1) {
				throw new BusinessException(ErrorCode.IS_ADMIN_EXIST,
						"isAdmin[" + isAdmin + "] is already exist !");
			}
		}
		ResourceUser ru = new ResourceUser();
		ru.setIsAdmin(isAdmin);
		ru.setName(name);
		ru.setNote(note);
		ru.setSpeciality(speciality);
		ru.setStandardNumber(standardNumber);
		ru.setTeam(teamDAO.findById(teamId));
		ru.setTelephone(telephone);
		ru.setType(type);
		resourceUserDAO.save(ru);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_RESOURCE_USER);
		return ru.getId();
	}

	@Override
	public void updateResourceUser(String id, String standardNumber,
			String teamId, String telephone, String name, String type,
			String isAdmin, String speciality, String note) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<ResourceUser> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = resourceUserDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		if (null != isAdmin && null != teamId) {
			// name重复检查
			if (isAdmin.equals("1")) {
				params.clear();
				params.put("isAdmin", isAdmin);
				params.put("team.id", teamId);
				list = resourceUserDAO.findByPropertys(params);
				if (list.size() >= 1) {
					if (!list.get(0).getId().equals(id)) {
						throw new BusinessException(ErrorCode.IS_ADMIN_EXIST,
								"isAdmin[" + isAdmin + "] is already exist !");
					}
				}
			}
		}

		ResourceUser ru = resourceUserDAO.findById(id);
		if (null != standardNumber) {
			ru.setStandardNumber(standardNumber);
		}
		if (null != teamId) {
			ru.setTeam(teamDAO.findById(teamId));
		}
		if (null != telephone) {
			ru.setTelephone(telephone);
		}
		if (null != name) {
			ru.setName(name);
		}
		if (null != type) {
			ru.setType(type);
		}
		if (null != isAdmin) {
			ru.setIsAdmin(isAdmin);
		}
		if (null != speciality) {
			ru.setSpeciality(speciality);
		}
		if (null != note) {
			ru.setNote(note);
		}

	}

	@Override
	public ResourceUserVO getResourceUser(String id) {
		ResourceUser ru = resourceUserDAO.findById(id);
		ResourceUserVO vo = new ResourceUserVO();
		vo.setId(ru.getId());
		vo.setIsAdmin(ru.getIsAdmin());
		vo.setName(ru.getName());
		vo.setNote(ru.getNote());
		vo.setSpeciality(ru.getSpeciality());
		vo.setStandardNumber(ru.getStandardNumber());
		vo.setTeamId(ru.getTeam().getId());
		vo.setTelephone(ru.getTelephone());
		vo.setType(ru.getType());
		return vo;
	}

	@Override
	public Integer countTotalResourceUser(String name, String type,
			String teamId) {
		return resourceUserDAO.countTotalResourceUser(name, type, teamId);
	}

	@Override
	public List<ResourceUserVO> listResourceUser(String name, String type,
			String teamId, Integer startIndex, Integer limit) {
		List<ResourceUser> rus = resourceUserDAO.listResourceUser(name, type,
				teamId, startIndex, limit);
		List<ResourceUserVO> list = new ArrayList<ResourceUserVO>();
		for (ResourceUser ru : rus) {
			ResourceUserVO vo = new ResourceUserVO();
			vo.setId(ru.getId());
			vo.setIsAdmin(ru.getIsAdmin());
			vo.setName(ru.getName());
			vo.setNote(ru.getNote());
			vo.setSpeciality(ru.getSpeciality());
			vo.setStandardNumber(ru.getStandardNumber());
			vo.setTeamId(ru.getTeam().getId());
			vo.setTelephone(ru.getTelephone());
			vo.setType(ru.getType());
			list.add(vo);
		}
		return list;
	}

	@Override
	public void deleteResourceUser(String id) {
		resourceUserDAO.deleteById(id);
	}

	@Override
	public String createUnitFire(String name, String linkMan, String mobile,
			String location, String longitude, String latitude, String fax,
			String email, String telephone, String note, String organId,
			String standardNumber, Short rescueCapability,
			Short fireEngineNumber, String gisId) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Fire> list = fireDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = fireDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		Fire fire = new Fire();
		fire.setCreateTime(System.currentTimeMillis());
		fire.setEmail(email);
		fire.setFax(fax);
		fire.setFireEngineNumber(fireEngineNumber);
		fire.setLatitude(latitude);
		fire.setLinkMan(linkMan);
		fire.setLocation(location);
		fire.setLongitude(longitude);
		fire.setMobile(mobile);
		fire.setName(name);
		fire.setNote(note);
		fire.setOrgan(organDAO.findById(organId));
		fire.setRescueCapability(rescueCapability);
		fire.setStandardNumber(standardNumber);
		fire.setTelephone(telephone);
		fire.setGisId(gisId);
		fireDAO.save(fire);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_UNIT);
		return fire.getId();
	}

	@Override
	public String createUnitHospital(String name, String linkMan,
			String mobile, String location, String longitude, String latitude,
			String fax, String email, String telephone, String note,
			String organId, String standardNumber, Short ambulanceNumber,
			Short unitLevel, Short rescueCapability, String gisId) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Hospital> list = hospitalDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = hospitalDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		Hospital hospital = new Hospital();
		hospital.setAmbulanceNumber(ambulanceNumber);
		hospital.setCreateTime(System.currentTimeMillis());
		hospital.setEmail(email);
		hospital.setFax(fax);
		hospital.setLatitude(latitude);
		hospital.setLinkMan(linkMan);
		hospital.setLocation(location);
		hospital.setLongitude(longitude);
		hospital.setMobile(mobile);
		hospital.setName(name);
		hospital.setNote(note);
		hospital.setOrgan(organDAO.findById(organId));
		hospital.setRescueCapability(rescueCapability);
		hospital.setStandardNumber(standardNumber);
		hospital.setTelephone(telephone);
		hospital.setUnitLevel(unitLevel);
		hospital.setGisId(gisId);
		hospitalDAO.save(hospital);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_UNIT);
		return hospital.getId();
	}

	@Override
	public String createUnitPolice(String name, String linkMan, String mobile,
			String location, String longitude, String latitude, String fax,
			String email, String telephone, String note, String organId,
			String standardNumber, String gisId) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<Police> list = policeDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = policeDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		Police police = new Police();
		police.setCreateTime(System.currentTimeMillis());
		police.setEmail(email);
		police.setFax(fax);
		police.setLatitude(latitude);
		police.setLinkMan(linkMan);
		police.setLocation(location);
		police.setLongitude(longitude);
		police.setMobile(mobile);
		police.setName(name);
		police.setNote(note);
		police.setOrgan(organDAO.findById(organId));
		police.setStandardNumber(standardNumber);
		police.setTelephone(telephone);
		police.setGisId(gisId);
		policeDAO.save(police);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_UNIT);
		return police.getId();
	}

	@Override
	public String createUnitRoadAdmin(String name, String linkMan,
			String mobile, String location, String longitude, String latitude,
			String fax, String email, String telephone, String note,
			String organId, String standardNumber, Short carNumber,
			Short teamNumber, String gisId) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<RoadAdmin> list = roadAdminDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = roadAdminDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		RoadAdmin ra = new RoadAdmin();
		ra.setCarNumber(carNumber);
		ra.setCreateTime(System.currentTimeMillis());
		ra.setEmail(email);
		ra.setFax(fax);
		ra.setLatitude(latitude);
		ra.setLinkMan(linkMan);
		ra.setLocation(location);
		ra.setLongitude(longitude);
		ra.setMobile(mobile);
		ra.setName(name);
		ra.setNote(note);
		ra.setOrgan(organDAO.findById(organId));
		ra.setStandardNumber(standardNumber);
		ra.setTeamNumber(teamNumber);
		ra.setTelephone(telephone);
		ra.setGisId(gisId);
		roadAdminDAO.save(ra);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_UNIT);
		return ra.getId();
	}

	@Override
	public void updateUnitFire(String id, String standardNumber, String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId, Short rescueCapability,
			Short fireEngineNumber) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Fire> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = fireDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = fireDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		Fire fire = fireDAO.findById(id);
		if (null != standardNumber) {
			fire.setStandardNumber(standardNumber);
		}
		if (null != name) {
			fire.setName(name);
		}
		if (null != linkMan) {
			fire.setLinkMan(linkMan);
		}
		if (null != mobile) {
			fire.setMobile(mobile);
		}
		if (null != location) {
			fire.setLocation(location);
		}
		if (null != longitude) {
			fire.setLongitude(longitude);
		}
		if (null != latitude) {
			fire.setLatitude(latitude);
		}
		if (null != fax) {
			fire.setFax(fax);
		}
		if (null != email) {
			fire.setEmail(email);
		}
		if (null != telephone) {
			fire.setTelephone(telephone);
		}
		if (null != note) {
			fire.setNote(note);
		}
		if (null != organId) {
			fire.setOrgan(organDAO.findById(organId));
		}
		if (null != rescueCapability) {
			fire.setRescueCapability(rescueCapability);
		}
		if (null != fireEngineNumber) {
			fire.setFireEngineNumber(fireEngineNumber);
		}
	}

	@Override
	public void updateUnitHospital(String id, String standardNumber,
			String name, String linkMan, String mobile, String location,
			String longitude, String latitude, String fax, String email,
			String telephone, String note, String organId,
			Short ambulanceNumber, Short unitLevel, Short rescueCapability) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Hospital> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = hospitalDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = hospitalDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		Hospital hospital = hospitalDAO.findById(id);
		if (null != standardNumber) {
			hospital.setStandardNumber(standardNumber);
		}
		if (null != name) {
			hospital.setName(name);
		}
		if (null != linkMan) {
			hospital.setLinkMan(linkMan);
		}
		if (null != mobile) {
			hospital.setMobile(mobile);
		}
		if (null != location) {
			hospital.setLocation(location);
		}
		if (null != longitude) {
			hospital.setLongitude(longitude);
		}
		if (null != latitude) {
			hospital.setLatitude(latitude);
		}
		if (null != fax) {
			hospital.setFax(fax);
		}
		if (null != email) {
			hospital.setEmail(email);
		}
		if (null != telephone) {
			hospital.setTelephone(telephone);
		}
		if (null != note) {
			hospital.setNote(note);
		}
		if (null != organId) {
			hospital.setOrgan(organDAO.findById(organId));
		}
		if (null != ambulanceNumber) {
			hospital.setAmbulanceNumber(ambulanceNumber);
		}
		if (null != unitLevel) {
			hospital.setUnitLevel(unitLevel);
		}
		if (null != rescueCapability) {
			hospital.setRescueCapability(rescueCapability);
		}

	}

	@Override
	public void updateUnitPolice(String id, String standardNumber, String name,
			String linkMan, String mobile, String location, String longitude,
			String latitude, String fax, String email, String telephone,
			String note, String organId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<Police> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = policeDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = policeDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		Police police = policeDAO.findById(id);
		if (null != standardNumber) {
			police.setStandardNumber(standardNumber);
		}
		if (null != name) {
			police.setName(name);
		}
		if (null != linkMan) {
			police.setLinkMan(linkMan);
		}
		if (null != mobile) {
			police.setMobile(mobile);
		}
		if (null != location) {
			police.setLocation(location);
		}
		if (null != longitude) {
			police.setLongitude(longitude);
		}
		if (null != latitude) {
			police.setLatitude(latitude);
		}
		if (null != fax) {
			police.setFax(fax);
		}
		if (null != email) {
			police.setEmail(email);
		}
		if (null != telephone) {
			police.setTelephone(telephone);
		}
		if (null != note) {
			police.setNote(note);
		}
		if (null != organId) {
			police.setOrgan(organDAO.findById(organId));
		}

	}

	@Override
	public void updateUnitRoadAdmin(String id, String standardNumber,
			String name, String linkMan, String mobile, String location,
			String longitude, String latitude, String fax, String email,
			String telephone, String note, String organId, Short carNumber,
			Short teamNumber) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<RoadAdmin> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = roadAdminDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = roadAdminDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		RoadAdmin roadAdmin = roadAdminDAO.findById(id);
		if (null != standardNumber) {
			roadAdmin.setStandardNumber(standardNumber);
		}
		if (null != name) {
			roadAdmin.setName(name);
		}
		if (null != linkMan) {
			roadAdmin.setLinkMan(linkMan);
		}
		if (null != mobile) {
			roadAdmin.setMobile(mobile);
		}
		if (null != location) {
			roadAdmin.setLocation(location);
		}
		if (null != longitude) {
			roadAdmin.setLongitude(longitude);
		}
		if (null != latitude) {
			roadAdmin.setLatitude(latitude);
		}
		if (null != fax) {
			roadAdmin.setFax(fax);
		}
		if (null != email) {
			roadAdmin.setEmail(email);
		}
		if (null != telephone) {
			roadAdmin.setTelephone(telephone);
		}
		if (null != note) {
			roadAdmin.setNote(note);
		}
		if (null != organId) {
			roadAdmin.setOrgan(organDAO.findById(organId));
		}
		if (null != carNumber) {
			roadAdmin.setCarNumber(carNumber);
		}
		if (null != teamNumber) {
			roadAdmin.setTeamNumber(teamNumber);
		}

	}

	@Override
	public UnitFireVO getUnitFire(String id) {
		Fire fire = fireDAO.findById(id);
		UnitFireVO vo = new UnitFireVO();
		vo.setId(fire.getId());
		vo.setEmail(fire.getEmail());
		vo.setFax(fire.getFax());
		vo.setLatitude(fire.getLatitude());
		vo.setLinkMan(fire.getLinkMan());
		vo.setLocation(fire.getLocation());
		vo.setLongitude(fire.getLongitude());
		vo.setMobile(fire.getMobile());
		vo.setName(fire.getName());
		vo.setNote(fire.getNote());
		vo.setOrganId(fire.getOrgan().getId());
		vo.setStandardNumber(fire.getStandardNumber());
		vo.setTelephone(fire.getTelephone());
		vo.setCreateTime(fire.getCreateTime() + "");
		vo.setRescueCapability(fire.getRescueCapability() != null ? fire
				.getRescueCapability() + "" : "");
		vo.setFireEngineNumber(fire.getFireEngineNumber() != null ? fire
				.getFireEngineNumber() + "" : "");
		vo.setType("1");
		return vo;
	}

	@Override
	public UnitHospitalVO getHospital(String id) {
		Hospital hospital = hospitalDAO.findById(id);
		UnitHospitalVO vo = new UnitHospitalVO();
		vo.setId(hospital.getId());
		vo.setEmail(hospital.getEmail());
		vo.setFax(hospital.getFax());
		vo.setLatitude(hospital.getLatitude());
		vo.setLinkMan(hospital.getLinkMan());
		vo.setLocation(hospital.getLocation());
		vo.setLongitude(hospital.getLongitude());
		vo.setMobile(hospital.getMobile());
		vo.setName(hospital.getName());
		vo.setNote(hospital.getNote());
		vo.setOrganId(hospital.getOrgan().getId());
		vo.setStandardNumber(hospital.getStandardNumber());
		vo.setTelephone(hospital.getTelephone());
		vo.setCreateTime(hospital.getCreateTime() + "");
		vo.setAmbulanceNumber(hospital.getAmbulanceNumber() != null ? hospital
				.getAmbulanceNumber() + "" : "");
		vo.setRescueCapability(hospital.getRescueCapability() != null ? hospital
				.getRescueCapability() + ""
				: "");
		vo.setUnitLevel(hospital.getUnitLevel() != null ? hospital
				.getUnitLevel() + "" : "");
		vo.setType("2");
		return vo;
	}

	@Override
	public UnitRoadAdminVO getRoadAdmin(String id) {
		RoadAdmin roadAdmin = roadAdminDAO.findById(id);
		UnitRoadAdminVO vo = new UnitRoadAdminVO();
		vo.setId(roadAdmin.getId());
		vo.setEmail(roadAdmin.getEmail());
		vo.setFax(roadAdmin.getFax());
		vo.setLatitude(roadAdmin.getLatitude());
		vo.setLinkMan(roadAdmin.getLinkMan());
		vo.setLocation(roadAdmin.getLocation());
		vo.setLongitude(roadAdmin.getLongitude());
		vo.setMobile(roadAdmin.getMobile());
		vo.setName(roadAdmin.getName());
		vo.setNote(roadAdmin.getNote());
		vo.setOrganId(roadAdmin.getOrgan().getId());
		vo.setStandardNumber(roadAdmin.getStandardNumber());
		vo.setTelephone(roadAdmin.getTelephone());
		vo.setCreateTime(roadAdmin.getCreateTime() != null ? roadAdmin
				.getCreateTime() + "" : "");
		vo.setTeamNumber(roadAdmin.getTeamNumber() != null ? roadAdmin
				.getTeamNumber() + "" : "");
		vo.setCarNumber(roadAdmin.getCarNumber() != null ? roadAdmin
				.getCarNumber() + "" : "");
		vo.setType("4");
		return vo;
	}

	@Override
	public String eventEntry(String name, String organId, Short type,
			String subType, Short eventLevel, Short roadType, String location,
			String beginStake, String endStake, Short navigation,
			String sendUser, String phone, String impactProvince,
			Long createTime, Long estimatesRecoverTime, Short hurtNumber,
			Short deathNumber, Integer delayHumanNumber, Integer crowdMeter,
			Short damageCarNumber, Integer delayCarNumber, Integer lossAmount,
			Long recoverTime, String description, Short isFire,
			Short laneNumber, String note, String administration,
			String managerUnit, String roadName, String recoverStatus,
			String longitude, String latitude) {

		Event event = new Event();
		event.setAdministration(administration);
		event.setBeginStake(beginStake);
		event.setCreateTime(System.currentTimeMillis());
		event.setCrowdMeter(crowdMeter);
		event.setDamageCarNumber(damageCarNumber);
		event.setDeathNumber(deathNumber);
		event.setDelayCarNumber(delayCarNumber);
		event.setDelayHumanNumber(delayHumanNumber);
		event.setDescription(description);
		event.setEndStake(endStake);
		event.setEstimatesRecoverTime(estimatesRecoverTime);
		event.setEventLevel(eventLevel);
		event.setHurtNumber(hurtNumber);
		event.setImpactProvince(impactProvince);
		event.setLocation(location);
		event.setLossAmount(lossAmount);
		event.setManagerUnit(managerUnit);
		if (StringUtils.isBlank(name)) {
			if (type.equals("0")) {
				name = "交通事故" + beginStake;
				event.setName(name);
			} else if (type.equals("1")) {
				name = "恶劣天气" + beginStake;
				event.setName(name);
			} else if (type.equals("2")) {
				name = "道路养护" + beginStake;
			} else if (type.equals("3")) {
				name = "公共事件" + beginStake;
				event.setName(name);
			} else {
				name = beginStake + "";
				event.setName(name);
			}
		} else {
			event.setName(name);
		}
		event.setNavigation(navigation);
		event.setNote(note);
		event.setOrgan(organDAO.findById(organId));
		event.setPhone(phone);
		event.setRecoverTime(recoverTime);
		event.setRoadType(roadType);
		event.setSendUser(sendUser);
		event.setSubType(subType);
		event.setType(type);
		event.setIsFire(isFire);
		event.setLaneNumber(laneNumber);
		event.setRoadName(roadName);
		event.setRecoverStatus(recoverStatus);
		event.setStatus((short) 0);
		event.setLongitude(longitude);
		event.setLatitude(latitude);
		eventDAO.save(event);

		return event.getId();
	}

	@Override
	public void updateEvent(String id, String name, String standardNumber,
			Short type, String subType, Short eventLevel, Short roadType,
			String location, String beginStake, String endStake,
			Short navigation, String sendUser, String phone,
			String impactProvince, Long createTime, Long estimatesRecoverTime,
			Short hurtNumber, Short deathNumber, Integer delayHumanNumber,
			Integer crowdMeter, Short damageCarNumber, Integer delayCarNumber,
			Integer lossAmount, Long recoverTime, String description,
			Short isFire, Short laneNumber, String note, String administration,
			String managerUnit, Short flag, String roadName,
			String recoverStatus, Short eventStatus) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		if (null != standardNumber) {
			params.put("standardNumber", standardNumber);
			List<Organ> organs = organDAO.findByPropertys(params);
			if (organs.size() <= 0) {
				throw new BusinessException(ErrorCode.PARAMETER_INVALID,
						"standardNumber[" + standardNumber + "] invalid !");
			}
		}
		Event event = eventDAO.findById(id);
		if (null != name) {
			event.setName(name);
		}
		if (null != standardNumber) {
			event.setOrgan(organDAO.findByPropertys(params).get(0));
		}
		if (null != type) {
			event.setType(type);
		}
		if (null != subType) {
			event.setSubType(subType);
		}
		if (null != eventLevel) {
			event.setEventLevel(eventLevel);
		}
		if (null != roadType) {
			event.setRoadType(roadType);
		}
		if (null != location) {
			event.setLocation(location);
		}
		if (null != beginStake) {
			event.setBeginStake(beginStake);
		}
		if (null != endStake) {
			event.setEndStake(endStake);
		}
		if (null != navigation) {
			event.setNavigation(navigation);
		}
		if (null != sendUser) {
			event.setSendUser(sendUser);
		}
		if (null != phone) {
			event.setPhone(phone);
		}
		if (null != impactProvince) {
			event.setImpactProvince(impactProvince);
		}
		if (null != createTime) {
			event.setCreateTime(createTime);
		}
		if (null != estimatesRecoverTime) {
			event.setEstimatesRecoverTime(estimatesRecoverTime);
		}
		if (null != hurtNumber) {
			event.setHurtNumber(hurtNumber);
		}
		if (null != deathNumber) {
			event.setDeathNumber(deathNumber);
		}
		if (null != delayHumanNumber) {
			event.setDelayHumanNumber(delayHumanNumber);
		}
		if (null != crowdMeter) {
			event.setCrowdMeter(crowdMeter);
		}
		if (null != damageCarNumber) {
			event.setDamageCarNumber(damageCarNumber);
		}
		if (null != delayCarNumber) {
			event.setDelayCarNumber(delayCarNumber);
		}
		if (null != lossAmount) {
			event.setLossAmount(lossAmount);
		}
		if (null != recoverTime) {
			event.setRecoverTime(recoverTime);
		}
		if (null != description) {
			event.setDescription(description);
		}
		if (null != isFire) {
			event.setIsFire(isFire);
		}
		if (null != laneNumber) {
			event.setLaneNumber(laneNumber);
		}
		if (null != note) {
			event.setNote(note);
		}
		if (null != administration) {
			event.setAdministration(administration);
		}
		if (null != managerUnit) {
			event.setManagerUnit(managerUnit);
		}
		if (null != roadName) {
			event.setRoadName(roadName);
		}
		if (null != recoverStatus) {
			event.setRecoverStatus(recoverStatus);
		}
		// 如果修改过图片先把事件对应的所有图片删除
		if (null != flag) {
			if (flag == 0) {
				params.clear();
				params.put("userId", id);
				List<Image> images = imageDAO.findByPropertys(params);
				for (Image image : images) {
					imageDAO.delete(image);
				}
			}
		}
		if (null != eventStatus) {
			event.setStatus(eventStatus);
		}
	}

	@Override
	public Image getResourceImageId(String resourceId) {
		Event event = eventDAO.findById(resourceId);
		Image image = new Image();
		image.setCreateTime(System.currentTimeMillis());
		image.setName(event.getName());
		image.setUserId(event.getId());
		imageDAO.save(image);
		// image.setId(resourceId);
		return image;
	}

	@Override
	public void upload(String id, InputStream in) {
		try {
			Image image = imageDAO.findById(id);
			image.setContent(Hibernate.createBlob(in));
			imageDAO.update(image);
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.NETWORK_IO_ERROR,
					"image stream read error !");
		}
	}

	@Override
	public Element getEvent(String id) {
		Event event = eventDAO.findById(id);
		Element element = new Element("Event");
		element.setAttribute("Administration",
				event.getAdministration() != null ? event.getAdministration()
						: "");
		element.setAttribute("BeginStake",
				event.getBeginStake() != null ? event.getBeginStake() : "");
		element.setAttribute("Description",
				event.getDescription() != null ? event.getDescription() : "");
		element.setAttribute("EndStake",
				event.getEndStake() != null ? event.getEndStake() : "");
		element.setAttribute("Id", event.getId() != null ? event.getId() : "");
		element.setAttribute("ImpactProvince",
				event.getImpactProvince() != null ? event.getImpactProvince()
						: "");
		element.setAttribute("Location",
				event.getLocation() != null ? event.getLocation() : "");
		element.setAttribute("ManagerUnit",
				event.getManagerUnit() != null ? event.getManagerUnit() : "");
		element.setAttribute("Name", event.getName() != null ? event.getName()
				: "");
		element.setAttribute("Note", event.getNote() != null ? event.getNote()
				: "");
		element.setAttribute("Phone",
				event.getPhone() != null ? event.getPhone() : "");
		element.setAttribute("RoadName",
				event.getRoadName() != null ? event.getRoadName() : "");
		element.setAttribute("SendUser",
				event.getSendUser() != null ? event.getSendUser() : "");
		element.setAttribute("CreateTime",
				event.getCreateTime() != null ? event.getCreateTime()
						.toString() : "");
		element.setAttribute("CrowdMeter",
				event.getCrowdMeter() != null ? event.getCrowdMeter()
						.toString() : "");
		element.setAttribute("DamageCarNumber",
				event.getDamageCarNumber() != null ? event.getDamageCarNumber()
						.toString() : "");
		element.setAttribute("DeathNumber",
				event.getDeathNumber() != null ? event.getDeathNumber()
						.toString() : "");
		element.setAttribute("DelayCarNumber",
				event.getDelayCarNumber() != null ? event.getDelayCarNumber()
						.toString() : "");
		element.setAttribute("DelayHumanNumber",
				event.getDelayHumanNumber() != null ? event
						.getDelayHumanNumber().toString() : "");
		element.setAttribute("EstimatesRecoverTime", event
				.getEstimatesRecoverTime() != null ? event
				.getEstimatesRecoverTime().toString() : "");
		element.setAttribute("EventLevel",
				event.getEventLevel() != null ? event.getEventLevel()
						.toString() : "");
		element.setAttribute("HurtNumber",
				event.getHurtNumber() != null ? event.getHurtNumber()
						.toString() : "");
		element.setAttribute("IsFire", event.getIsFire() != null ? event
				.getIsFire().toString() : "");
		element.setAttribute("LaneNumber",
				event.getLaneNumber() != null ? event.getLaneNumber()
						.toString() : "");
		element.setAttribute("LossAmount",
				event.getLossAmount() != null ? event.getLossAmount()
						.toString() : "");
		element.setAttribute("Navigation",
				event.getNavigation() != null ? event.getNavigation()
						.toString() : "");
		element.setAttribute("StandardNumber", event.getOrgan() != null ? event
				.getOrgan().getStandardNumber() : "");
		element.setAttribute("RecoverTime",
				event.getRecoverTime() != null ? event.getRecoverTime()
						.toString() : "");
		element.setAttribute("RoadType", event.getRoadType() != null ? event
				.getRoadType().toString() : "");
		element.setAttribute("SubType",
				event.getSubType() != null ? event.getSubType() : "");
		element.setAttribute("Type", event.getType() != null ? event.getType()
				.toString() : "");
		element.setAttribute("RecoverStatus",
				event.getRecoverStatus() != null ? event.getRecoverStatus()
						: "");
		return element;
	}

	@Override
	public Element getImageIds(String id) {
		Element imageIds = new Element("Images");
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("userId", id);
		List<Image> images = imageDAO.findByPropertys(params);
		if (images.size() > 0) {
			for (Image image : images) {
				Element imageElement = new Element("Image");
				imageElement.setAttribute("Id", image.getId());
				imageIds.addContent(imageElement);
			}
		}
		return imageIds;
	}

	@Override
	public String createUnitWareHouse(String name, String linkMan,
			String mobile, String location, String longitude, String latitude,
			String fax, String email, String telephone, String note,
			String organId, String standardNumber, String gisId,
			String managerUnit) {
		// standardNumber重复检查
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("standardNumber", standardNumber);
		List<WareHouse> list = wareHouseDAO.findByPropertys(params);
		if (list.size() >= 1) {
			throw new BusinessException(ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
					"standardNumber[" + standardNumber + "] is already exist !");
		}
		// name重复检查
		// params.clear();
		// params.put("name", name);
		// list = wareHouseDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name[" + name
		// + "] is already exist !");
		// }

		WareHouse warehouse = new WareHouse();
		warehouse.setCreateTime(System.currentTimeMillis());
		warehouse.setEmail(email);
		warehouse.setFax(fax);
		warehouse.setLatitude(latitude);
		warehouse.setLinkMan(linkMan);
		warehouse.setLocation(location);
		warehouse.setLongitude(longitude);
		warehouse.setMobile(mobile);
		warehouse.setName(name);
		warehouse.setNote(note);
		warehouse.setOrgan(organDAO.findById(organId));
		warehouse.setStandardNumber(standardNumber);
		warehouse.setTelephone(telephone);
		warehouse.setGisId(gisId);
		warehouse.setManagerUnit(managerUnit);
		wareHouseDAO.save(warehouse);

		syncSN(null, standardNumber, TypeDefinition.RESOURCE_TYPE_UNIT);
		return warehouse.getId();
	}

	@Override
	public void updateUnitWareHouse(String id, String standardNumber,
			String name, String linkMan, String mobile, String location,
			String longitude, String latitude, String fax, String email,
			String telephone, String note, String organId, String managerUnit) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		List<WareHouse> list = null;
		if (null != standardNumber) {
			// standardNumber重复检查
			params.put("standardNumber", standardNumber);
			list = wareHouseDAO.findByPropertys(params);
			if (list.size() >= 1) {
				if (!list.get(0).getId().equals(id)) {
					throw new BusinessException(
							ErrorCode.UNIQUE_PROPERTY_DUPLICATE,
							"standardNumber[" + standardNumber
									+ "] is already exist !");
				}
			}
		}
		// if (null != name) {
		// // name重复检查
		// params.clear();
		// params.put("name", name);
		// list = wareHouseDAO.findByPropertys(params);
		// if (list.size() >= 1) {
		// if (!list.get(0).getId().equals(id)) {
		// throw new BusinessException(ErrorCode.NAME_EXIST, "name["
		// + name + "] is already exist !");
		// }
		// }
		// }

		WareHouse warehouse = wareHouseDAO.findById(id);
		if (null != standardNumber) {
			warehouse.setStandardNumber(standardNumber);
		}
		if (null != name) {
			warehouse.setName(name);
		}
		if (null != linkMan) {
			warehouse.setLinkMan(linkMan);
		}
		if (null != mobile) {
			warehouse.setMobile(mobile);
		}
		if (null != location) {
			warehouse.setLocation(location);
		}
		if (null != longitude) {
			warehouse.setLongitude(longitude);
		}
		if (null != latitude) {
			warehouse.setLatitude(latitude);
		}
		if (null != fax) {
			warehouse.setFax(fax);
		}
		if (null != email) {
			warehouse.setEmail(email);
		}
		if (null != telephone) {
			warehouse.setTelephone(telephone);
		}
		if (null != note) {
			warehouse.setNote(note);
		}
		if (null != organId) {
			warehouse.setOrgan(organDAO.findById(organId));
		}
		if (null != managerUnit) {
			warehouse.setManagerUnit(managerUnit);
		}

	}

	@Override
	public Event getEmEvent(String id) {
		Event event = eventDAO.findById(id);
		event.getOrgan().getName();
		return event;
	}

	@Override
	public List<ControlDeviceCms> listCmsByRange(String beginStake,
			String endStake, int range, int navigation, String organId) {
		// 获取机构及子机构ID集合
		List<Organ> organs = organDAO.listOrganById(organId);
		List<String> organIds = new LinkedList<String>();
		for (Organ organ : organs) {
			organIds.add(organ.getId());
		}
		// 获取机构及子机构下的CMS集合
		List<ControlDevice> list = controlDeviceDAO.listControlDevices(
				organIds, TypeDefinition.DEVICE_TYPE_CMS);
		// 根据方向计算事发点前方范围的CMS
		List<ControlDeviceCms> rtnList = new LinkedList<ControlDeviceCms>();
		float begin = NumberUtil.floatStake(beginStake);
		float end = NumberUtil.floatStake(endStake);
		float fRange = range / 1000;
		// 上行，计算小于起始桩号的设施
		if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
			for (ControlDevice cd : list) {
				// 只搜索本侧方向的设施
				if (cd.getNavigation().equals(
						TypeDefinition.NAVIGATION_DOWNLOAD + "")) {
					continue;
				}
				ControlDeviceCms cms = (ControlDeviceCms) cd;
				if (StringUtils.isBlank(cms.getStakeNumber())) {
					continue;
				}
				float stakeNumber = NumberUtil.floatStake(cms.getStakeNumber());
				if ((begin > stakeNumber) && (stakeNumber > (begin - fRange))) {
					initCms(cms);
					rtnList.add(cms);
				}
			}
		}
		// 下行，计算大于结束桩号的设施
		else if (TypeDefinition.NAVIGATION_DOWNLOAD == navigation) {
			for (ControlDevice cd : list) {
				// 只搜索本侧方向的设施
				if (cd.getNavigation().equals(
						TypeDefinition.NAVIGATION_UPLOAD + "")) {
					continue;
				}
				ControlDeviceCms cms = (ControlDeviceCms) cd;
				if (StringUtils.isBlank(cms.getStakeNumber())) {
					continue;
				}
				float stakeNumber = NumberUtil.floatStake(cms.getStakeNumber());
				if ((end < stakeNumber) && (stakeNumber < (end + fRange))) {
					initCms(cms);
					rtnList.add(cms);
				}
			}
		}
		// 双向，计算小于起始桩号的设施和计算大于结束桩号的设施
		else {
			for (ControlDevice cd : list) {
				ControlDeviceCms cms = (ControlDeviceCms) cd;
				if (StringUtils.isBlank(cms.getStakeNumber())) {
					continue;
				}
				float stakeNumber = NumberUtil.floatStake(cms.getStakeNumber());
				// 上行方向, 计算小于起始桩号的设施
				if (cd.getNavigation().equals(
						TypeDefinition.NAVIGATION_UPLOAD + "")) {
					if ((begin > stakeNumber)
							&& (stakeNumber > (begin - fRange))) {
						initCms(cms);
						rtnList.add(cms);
					}
				}
				// 下行方向, 计算大于结束桩号的设施
				else {
					if ((end < stakeNumber) && (stakeNumber < (end + fRange))) {
						initCms(cms);
						rtnList.add(cms);
					}
				}
			}
		}
		return rtnList;
	}

	@Override
	public List<Tollgate> listTollgateByRange(String beginStake,
			String endStake, int range, int navigation, String organId) {
		// 获取机构及子机构ID集合
		List<Organ> organs = organDAO.listOrganById(organId);
		List<String> organIds = new LinkedList<String>();
		for (Organ organ : organs) {
			organIds.add(organ.getId());
		}
		// 获取机构及子机构下的收费站集合
		List<Tollgate> tollgates = tollgateDAO.listTollgates(organIds);
		// 根据方向计算事发点前方范围的收费站
		List<Tollgate> list = new LinkedList<Tollgate>();
		float begin = NumberUtil.floatStake(beginStake);
		float end = NumberUtil.floatStake(endStake);
		// 上行方向，计算小于起始桩号的设施
		if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
			for (Tollgate tollgate : tollgates) {
				// 只搜索本侧方向的设施
				if (tollgate.getNavigation().equals(
						TypeDefinition.NAVIGATION_DOWNLOAD + "")) {
					continue;
				}
				float stakeNumber = NumberUtil.floatStake(tollgate
						.getStakeNumber());
				if (begin > stakeNumber) {
					list.add(tollgate);
				}
			}
		}
		// 下行方向，计算大于结束桩号的设施
		else if (TypeDefinition.NAVIGATION_DOWNLOAD == navigation) {
			for (Tollgate tollgate : tollgates) {
				// 只搜索本侧方向的设施
				if (tollgate.getNavigation().equals(
						TypeDefinition.NAVIGATION_UPLOAD + "")) {
					continue;
				}
				float stakeNumber = NumberUtil.floatStake(tollgate
						.getStakeNumber());
				if (end < stakeNumber) {
					list.add(tollgate);
				}
			}
		}
		// 双向，计算小于起始桩号的设施和计算大于结束桩号的设施
		else {
			for (Tollgate tollgate : tollgates) {
				float stakeNumber = NumberUtil.floatStake(tollgate
						.getStakeNumber());
				// 上行方向, 计算小于起始桩号的设施
				if (tollgate.getNavigation().equals(
						TypeDefinition.NAVIGATION_UPLOAD + "")) {
					if (begin > stakeNumber) {
						list.add(tollgate);
					}
				}
				// 下行方向, 计算大于结束桩号的设施
				else {
					if (end < stakeNumber) {
						list.add(tollgate);
					}
				}
			}
		}
		return list;
	}

	@Override
	public List<RoadMouth> listRoadMouthByRange(String beginStake,
			String endStake, int range, int navigation, String organId) {
		// 获取机构及子机构ID集合
		List<Organ> organs = organDAO.listOrganById(organId);
		List<String> organIds = new LinkedList<String>();
		for (Organ organ : organs) {
			organIds.add(organ.getId());
		}
		// 获取机构及子机构下的出口匝道集合
		List<RoadMouth> roadMouths = roadMouthDAO.listRoadMouths(organIds);
		// 根据方向计算事发点前方范围的出口匝道
		List<RoadMouth> list = new LinkedList<RoadMouth>();
		float begin = NumberUtil.floatStake(beginStake);
		float end = NumberUtil.floatStake(endStake);
		// 上行方向，计算小于起始桩号的设施
		if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
			for (RoadMouth roadMouth : roadMouths) {
				// 只搜索本侧方向的设施
				if (roadMouth.getNavigation().equals(
						TypeDefinition.NAVIGATION_DOWNLOAD + "")) {
					continue;
				}
				float stakeNumber = NumberUtil.floatStake(roadMouth
						.getStakeNumber());
				if (begin > stakeNumber) {
					list.add(roadMouth);
				}
			}
		}
		// 下行方向，计算大于结束桩号的设施
		else if (TypeDefinition.NAVIGATION_DOWNLOAD == navigation) {
			for (RoadMouth roadMouth : roadMouths) {
				// 只搜索本侧方向的设施
				if (roadMouth.getNavigation().equals(
						TypeDefinition.NAVIGATION_UPLOAD + "")) {
					continue;
				}
				float stakeNumber = NumberUtil.floatStake(roadMouth
						.getStakeNumber());
				if (end < stakeNumber) {
					list.add(roadMouth);
				}
			}
		}
		// 双向，计算小于起始桩号的设施和计算大于结束桩号的设施
		else {
			for (RoadMouth roadMouth : roadMouths) {
				float stakeNumber = NumberUtil.floatStake(roadMouth
						.getStakeNumber());
				// 上行方向, 计算小于起始桩号的设施
				if (roadMouth.getNavigation().equals(
						TypeDefinition.NAVIGATION_UPLOAD + "")) {
					if (begin > stakeNumber) {
						list.add(roadMouth);
					}
				}
				// 下行方向, 计算大于结束桩号的设施
				else {
					if (end < stakeNumber) {
						list.add(roadMouth);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 级联查询cms相关信息
	 * 
	 * @param cms
	 *            cms
	 * @author huangbuji
	 *         <p />
	 *         Create at 2014-7-17 上午9:41:24
	 */
	private void initCms(ControlDeviceCms cms) {
		cms.getDas().getStandardNumber();
	}

	@Override
	public String saveEventScheme(String schemeId, String name, String eventId,
			String templetId, String createUserId, String createUserName,
			Element scheme) {
		SchemeInstance si = null;
		Event event = eventDAO.findById(eventId);
		List<Element> steps = scheme.getChildren("Step");
		// 新增
		if (StringUtils.isBlank(schemeId)) {
			si = new SchemeInstance();
			si.setName(name);
			si.setEventId(eventId);
			si.setTempletId(templetId);
			si.setCreateUserId(createUserId);
			si.setCreateUserName(createUserName);
			si.setCreateTime(System.currentTimeMillis());
			si.setUpdateTime(System.currentTimeMillis());
			si.setActionStatus(TypeDefinition.ACTION_STATUS_0);
			schemeDAO.save(si);
			// 保存步骤
			for (Element step : steps) {
				Short seq = ElementUtil.getShort(step, "Seq");
				String type = step.getAttributeValue("Type");
				Short targetType = class2Type(type);
				List<Element> items = step.getChildren();
				for (Element item : items) {
					SchemeStepInstance entity = new SchemeStepInstance();
					entity.setSeq(seq);
					// 如果是交通控制类
					if (targetType.shortValue() == (short) -1) {
						entity.setTargetType(class2Type(item
								.getAttributeValue("TargetType")));
					} else {
						entity.setTargetType(targetType);
					}
					entity.setTargetId(item.getAttributeValue("TargetId"));
					entity.setTargetName(item.getAttributeValue("TargetName"));
					entity.setTelephone(item.getAttributeValue("Telephone"));
					entity.setLinkMan(item.getAttributeValue("LinkMan"));
					entity.setRequestContent(item
							.getAttributeValue("RequestContent"));
					entity.setResponseContent(item
							.getAttributeValue("ResponseContent"));
					entity.setBeginTime(ElementUtil.getLong(item, "BeginTime"));
					entity.setArriveTime(ElementUtil
							.getLong(item, "ArriveTime"));
					entity.setEndTime(ElementUtil.getLong(item, "EndTime"));
					entity.setActionStatus(TypeDefinition.ACTION_STATUS_0);
					entity.setColor(item.getAttributeValue("Color"));
					entity.setContent(item.getAttributeValue("Content"));
					entity.setDuration(ElementUtil.getInteger(item, "Duration"));
					entity.setFont(item.getAttributeValue("Font"));
					entity.setNote(item.getAttributeValue("Note"));
					entity.setPlaySize(item.getAttributeValue("Size"));
					entity.setSpace(ElementUtil.getShort(item, "Space"));
					entity.setState(ElementUtil.getShort(item, "Status"));
					entity.setTargetNumber(ElementUtil.getInteger(item,
							"Number"));
					entity.setSchemeInstance(si);
					schemeStepDAO.save(entity);
				}
			}
		}
		// 修改
		else {
			si = schemeDAO.findById(schemeId);
			// 只允许修改名称
			si.setName(name);
			// 记录保存中的所有StepId
			Map<String, Boolean> stepMap = new HashMap<String, Boolean>();
			// 保存步骤
			for (Element step : steps) {
				Short seq = ElementUtil.getShort(step, "Seq");
				String type = step.getAttributeValue("Type");
				Short targetType = class2Type(type);
				List<Element> items = step.getChildren();
				for (Element item : items) {
					SchemeStepInstance entity = null;
					// 增加一个步骤
					if (StringUtils.isBlank(item.getAttributeValue("Id"))) {
						entity = new SchemeStepInstance();
					}
					// 修改当前步骤
					else {
						entity = schemeStepDAO.findById(item
								.getAttributeValue("Id"));
					}
					entity.setSeq(seq);
					// 如果是交通控制类
					if (targetType.shortValue() == (short) -1) {
						entity.setTargetType(class2Type(item
								.getAttributeValue("TargetType")));
					} else {
						entity.setTargetType(targetType);
					}
					entity.setTargetId(item.getAttributeValue("TargetId"));
					entity.setTargetName(item.getAttributeValue("TargetName"));
					entity.setTelephone(item.getAttributeValue("Telephone"));
					entity.setLinkMan(item.getAttributeValue("LinkMan"));
					entity.setRequestContent(item
							.getAttributeValue("RequestContent"));
					entity.setResponseContent(item
							.getAttributeValue("ResponseContent"));
					entity.setBeginTime(ElementUtil.getLong(item, "BeginTime"));
					entity.setArriveTime(ElementUtil
							.getLong(item, "ArriveTime"));
					entity.setEndTime(ElementUtil.getLong(item, "EndTime"));
					entity.setActionStatus(TypeDefinition.ACTION_STATUS_0);
					entity.setColor(item.getAttributeValue("Color"));
					entity.setContent(item.getAttributeValue("Content"));
					entity.setDuration(ElementUtil.getInteger(item, "Duration"));
					entity.setFont(item.getAttributeValue("Font"));
					entity.setNote(item.getAttributeValue("Note"));
					entity.setPlaySize(item.getAttributeValue("Size"));
					entity.setSpace(ElementUtil.getShort(item, "Space"));
					entity.setState(ElementUtil.getShort(item, "Status"));
					entity.setTargetNumber(ElementUtil.getInteger(item,
							"Number"));
					entity.setSchemeInstance(si);
					schemeStepDAO.saveorupdate(entity);

					stepMap.put(entity.getId(), Boolean.TRUE);
				}
			}
			// 需要删除的步骤
			// 查询保存完毕后的该预案实例的所有步骤
			Set<SchemeStepInstance> stepSet = si.getSteps();
			// 存放需要删除的StepId
			List<String> deleteList = new LinkedList<String>();
			for (SchemeStepInstance stepInstance : stepSet) {
				if (stepMap.get(stepInstance.getId()) == null) {
					deleteList.add(stepInstance.getId());
				}
			}
			for (String stepId : deleteList) {
				SchemeStepInstance ssi = schemeStepDAO.findById(stepId);
				stepSet.remove(ssi);
				schemeStepDAO.delete(ssi);
			}
		}
		// 修改事件状态
		event.setStatus(TypeDefinition.EVENT_STATUS_1);

		return si.getId();
	}

	@Override
	public UnitWareHouseVO getWareHouse(String id) {
		WareHouse unit = wareHouseDAO.findById(id);
		UnitWareHouseVO vo = new UnitWareHouseVO();
		vo.setId(unit.getId());
		vo.setEmail(unit.getEmail());
		vo.setFax(unit.getFax());
		vo.setLatitude(unit.getLatitude());
		vo.setLinkMan(unit.getLinkMan());
		vo.setLocation(unit.getLocation());
		vo.setLongitude(unit.getLongitude());
		vo.setMobile(unit.getMobile());
		vo.setName(unit.getName());
		vo.setNote(unit.getNote());
		vo.setOrganId(unit.getOrgan().getId());
		vo.setStandardNumber(unit.getStandardNumber());
		vo.setTelephone(unit.getTelephone());
		vo.setCreateTime(unit.getCreateTime() + "");
		vo.setManagerUnit(unit.getManagerUnit());
		vo.setType("5");
		return vo;
	}

	@Override
	public Element listEvent(String organId, Long beginTime, Long endTime,
			Short type, Short status) {
		String organs[] = organDAO.findOrgansByOrganId(organId);
		List<Event> events = eventDAO.listEventByOrganId(organs, beginTime,
				endTime, type, status);
		Element eventsElement = new Element("Events");
		String ImageId = "ImageId";
		Map<String, Camera> cameraMap = cameraDAO.statelessMapCamera();
		Map<String, List<String>> imageMap = imageDAO.mapUserImage();
		for (Event event : events) {
			Element element = new Element("Event");
			element.setAttribute(
					"Administration",
					event.getAdministration() != null ? event
							.getAdministration() : "");
			element.setAttribute("BeginStake",
					event.getBeginStake() != null ? event.getBeginStake() : "");
			element.setAttribute("Description",
					event.getDescription() != null ? event.getDescription()
							: "");
			element.setAttribute("EndStake",
					event.getEndStake() != null ? event.getEndStake() : "");
			element.setAttribute("Id", event.getId() != null ? event.getId()
					: "");
			element.setAttribute(
					"ImpactProvince",
					event.getImpactProvince() != null ? event
							.getImpactProvince() : "");
			element.setAttribute("Location",
					event.getLocation() != null ? event.getLocation() : "");
			element.setAttribute("ManagerUnit",
					event.getManagerUnit() != null ? event.getManagerUnit()
							: "");
			element.setAttribute("Name",
					event.getName() != null ? event.getName() : "");
			element.setAttribute("Note",
					event.getNote() != null ? event.getNote() : "");
			element.setAttribute("Phone",
					event.getPhone() != null ? event.getPhone() : "");
			element.setAttribute("RoadName",
					event.getRoadName() != null ? event.getRoadName() : "");
			// 如果是视频监测事件，转换摄像头ID为摄像头名称
			if (event.getType().intValue() == TypeDefinition.EVENT_TYPE_VD) {
				Camera c = cameraMap.get(event.getSendUser());
				element.setAttribute("SendUser",
						null == c ? event.getSendUser() : c.getName());
				;
			} else {
				element.setAttribute("SendUser",
						event.getSendUser() != null ? event.getSendUser() : "");
			}
			element.setAttribute("CreateTime",
					event.getCreateTime() != null ? event.getCreateTime()
							.toString() : "");
			element.setAttribute("CrowdMeter",
					event.getCrowdMeter() != null ? event.getCrowdMeter()
							.toString() : "");
			element.setAttribute("DamageCarNumber",
					event.getDamageCarNumber() != null ? event
							.getDamageCarNumber().toString() : "");
			element.setAttribute("DeathNumber",
					event.getDeathNumber() != null ? event.getDeathNumber()
							.toString() : "");
			element.setAttribute("DelayCarNumber",
					event.getDelayCarNumber() != null ? event
							.getDelayCarNumber().toString() : "");
			element.setAttribute("DelayHumanNumber", event
					.getDelayHumanNumber() != null ? event
					.getDelayHumanNumber().toString() : "");
			element.setAttribute("EstimatesRecoverTime", event
					.getEstimatesRecoverTime() != null ? event
					.getEstimatesRecoverTime().toString() : "");
			element.setAttribute("EventLevel",
					event.getEventLevel() != null ? event.getEventLevel()
							.toString() : "");
			element.setAttribute("HurtNumber",
					event.getHurtNumber() != null ? event.getHurtNumber()
							.toString() : "");
			element.setAttribute("IsFire", event.getIsFire() != null ? event
					.getIsFire().toString() : "");
			element.setAttribute("LaneNumber",
					event.getLaneNumber() != null ? event.getLaneNumber()
							.toString() : "");
			element.setAttribute("LossAmount",
					event.getLossAmount() != null ? event.getLossAmount()
							.toString() : "");
			element.setAttribute("Navigation",
					event.getNavigation() != null ? event.getNavigation()
							.toString() : "");
			// element.setAttribute("StandardNumber",
			// event.getOrgan() != null ? event.getOrgan()
			// .getStandardNumber() : "");
			element.setAttribute("RecoverTime",
					event.getRecoverTime() != null ? event.getRecoverTime()
							.toString() : "");
			element.setAttribute("RoadType",
					event.getRoadType() != null ? event.getRoadType()
							.toString() : "");
			element.setAttribute("SubType",
					event.getSubType() != null ? event.getSubType() : "");
			element.setAttribute("Type", event.getType() != null ? event
					.getType().toString() : "");
			element.setAttribute("RecoverStatus",
					event.getRecoverStatus() != null ? event.getRecoverStatus()
							: "");
			element.setAttribute("Status", event.getStatus() != null ? event
					.getStatus().toString() : "0");
			element.setAttribute("Longitude",
					event.getLongitude() != null ? event.getLongitude() : "");
			element.setAttribute("Latitude",
					event.getLatitude() != null ? event.getLatitude() : "");
			// element.setAttribute("OrganId", event.getOrgan() != null ? event
			// .getOrgan().getId() : "");
			List<String> imageIds = imageMap.get(event.getId());
			if (null != imageIds) {
				for (int i = 1; i <= imageIds.size(); i++) {
					element.setAttribute(ImageId + i, imageIds.get(i - 1));
				}
			}

			eventsElement.addContent(element);
		}

		return eventsElement;
	}

	@Override
	public Element listResourceById(String id) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("unit.id", id);
		List<Store> stores = storeDAO.findByPropertys(params);
		Element resourcesElement = new Element("Resources");
		for (Store store : stores) {
			Element resourceElement = new Element("Resource");
			resourceElement.setAttribute("Id",
					store.getResource() != null ? store.getResource().getId()
							: "");
			resourceElement.setAttribute("Name",
					store.getName() != null ? store.getName() : "");
			resourceElement.setAttribute("Unit",
					store.getResource() != null ? store.getResource()
							.getUnitType() : "");
			resourceElement.setAttribute("ResourceNumber", store
					.getResourceNumber() != null ? store.getResourceNumber()
					: "");
			resourceElement.setAttribute("MinNumber",
					store.getMinNumber() != null ? store.getMinNumber() : "");
			resourceElement.setAttribute("Status",
					store.getStatus() != null ? store.getStatus() : "");
			resourcesElement.addContent(resourceElement);
		}
		return resourcesElement;
	}

	@Override
	public List<WareHouse> listWareHouse(String organId) {
		String organs[] = organDAO.findOrgansByOrganId(organId);
		List<WareHouse> wareHouses = wareHouseDAO.listWareHouse(organs);
		return wareHouses;
	}

	@Override
	public StakeNumberLib getStakeNumberLib(String stakeNumber, String organId)
			throws BusinessException {
		if (StringUtils.isBlank(stakeNumber)) {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"stakeNumber can not be null !");
		}
		// 去掉米，只保留千米
		int index = stakeNumber.indexOf("+");
		if (index > 0) {
			stakeNumber = stakeNumber.substring(0, index);
		}
		index = stakeNumber.indexOf(".");
		if (index > 0) {
			stakeNumber = stakeNumber.substring(0, index);
		}
		// 转换小写字母为大写
		stakeNumber = stakeNumber.toUpperCase();
		// 处理前面的ZK,AK等
		index = stakeNumber.indexOf("K");
		if (index >= 0) {
			stakeNumber = stakeNumber.substring(index);
		} else {
			stakeNumber += "K" + stakeNumber;
		}

		return stakeLibDAO.findByStakeNumber(stakeNumber, organId);
	}

	@Override
	public void updateAddressBook(String id, String linkMan, String phone,
			String sex, String address, String note, String email, String fax,
			String organId, String position) {

		AddressBook ab = addressbookDAO.findById(id);
		if (null != linkMan) {
			ab.setLinkMan(linkMan);
		}
		if (null != phone) {
			ab.setPhone(phone);
		}
		if (null != sex) {
			ab.setSex(sex);
		}
		if (null != address) {
			ab.setAddress(address);
		}
		if (null != note) {
			ab.setNote(note);
		}
		if (null != email) {
			ab.setEmail(email);
		}
		if (null != fax) {
			ab.setFax(fax);
		}
		if (null != organId) {
			ab.setOrgan(organDAO.findById(organId));
		}
		if (null != position) {
			ab.setPosition(position);
		}

	}

	@Override
	public void deleteAddressBook(String id) {
		addressbookDAO.deleteById(id);
	}

	@Override
	public GetAddressBookVO getAddressBook(String id) {
		AddressBook ab = addressbookDAO.findById(id);
		GetAddressBookVO vo = new GetAddressBookVO();
		vo.setAddress(ab.getAddress());
		vo.setEmail(ab.getEmail());
		vo.setFax(ab.getFax());
		vo.setId(ab.getId());
		vo.setLinkMan(ab.getLinkMan());
		vo.setNote(ab.getNote());
		vo.setOrganId(ab.getOrgan().getId());
		vo.setPhone(ab.getPhone());
		vo.setPosition(ab.getPosition());
		vo.setSex(ab.getSex());
		return vo;
	}

	@Override
	public Integer addressBookTotalCount(String linkMan, String organId) {
		return addressbookDAO.addressBookTotalCount(linkMan, organId);
	}

	@Override
	public List<GetAddressBookVO> listAddressBook(String linkMan,
			String organId, Integer startIndex, Integer limit) {
		List<AddressBook> addressBooks = addressbookDAO.listAddressBook(
				linkMan, organId, startIndex, limit);
		List<GetAddressBookVO> list = new ArrayList<GetAddressBookVO>();
		for (AddressBook ab : addressBooks) {
			GetAddressBookVO vo = new GetAddressBookVO();
			vo.setAddress(ab.getAddress());
			vo.setEmail(ab.getEmail());
			vo.setFax(ab.getFax());
			vo.setId(ab.getId());
			vo.setLinkMan(ab.getLinkMan());
			vo.setNote(ab.getNote());
			vo.setOrganId(ab.getOrgan().getId());
			vo.setPhone(ab.getPhone());
			vo.setPosition(ab.getPosition());
			vo.setSex(ab.getSex());
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<AddressBook> readAddressBookWb(String organId, Workbook wb) {
		Organ organ = organDAO.findById(organId);
		List<AddressBook> list = new ArrayList<AddressBook>();
		try {
			if (wb == null) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"Format of excel file invalid !");
			}
			Sheet sheet = wb.getSheetAt(0);
			list = readAddressBookRows(sheet, organ);
		} catch (POIXMLException p) {
			p.printStackTrace();
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"Format of excel file invalid !");
		}
		return list;
	}

	private List<AddressBook> readAddressBookRows(Sheet sheet, Organ organ) {
		List<AddressBook> list = new ArrayList<AddressBook>();
		int rows = sheet.getPhysicalNumberOfRows();
		int rowIndex = 0; // 每行索引
		int notnullRowIndex = 0; // 非空行索引
		isExcelModel(sheet.getRow(0)); // 验证列名称
		while (notnullRowIndex < rows) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				if (rowIndex != 0) {
					list.add(readRowAddressBook(row, organ, rowIndex));
				}
				notnullRowIndex++;
			}
			rowIndex++;
		}

		return list;
	}

	private AddressBook readRowAddressBook(Row row, Organ organ, int rowIndex) {
		AddressBook ab = new AddressBook();
		Cell cell = row.getCell(0);
		if (cell != null) {
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String linkMan = row.getCell(0).getStringCellValue();
			ab.setLinkMan(linkMan);
		} else {
			throw new BusinessException(ErrorCode.EXCEL_CONTENT_ERROR,
					"excel row:" + (rowIndex + 1) + ",cellIndex: " + 1 + ","
							+ TypeDefinition.DVR_TEMPLATE + ","
							+ TypeDefinition.PARAMETER_NULL
							+ ",linkMan is not null");
		}

		cell = row.getCell(1);
		if (cell != null) {
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String phone = row.getCell(1).getStringCellValue();
			ab.setPhone(phone);
		}

		cell = row.getCell(2);
		if (cell != null) {
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			String sex = row.getCell(2).getStringCellValue();
			if (StringUtils.isNotBlank(sex)) {
				if (sex.equals("男")) {
					ab.setSex("0");
				}
				if (sex.equals("女 ")) {
					ab.setSex("1");
				}
			}
		}

		cell = row.getCell(3);
		if (cell != null) {
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String address = row.getCell(3).getStringCellValue();
			ab.setAddress(address);
		}

		cell = row.getCell(4);
		if (cell != null) {
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String email = row.getCell(4).getStringCellValue();
			ab.setEmail(email);
		}

		cell = row.getCell(5);
		if (cell != null) {
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String fax = row.getCell(5).getStringCellValue();
			ab.setFax(fax);
		}

		cell = row.getCell(6);
		if (cell != null) {
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String position = row.getCell(6).getStringCellValue();
			ab.setPosition(position);
		}

		cell = row.getCell(7);
		if (cell != null) {
			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
			String note = row.getCell(7).getStringCellValue();
			ab.setNote(note);
		}

		ab.setOrgan(organ);
		return ab;
	}

	private void isExcelModel(Row row) {
		if (row == null) {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format invalid");
		}

		if (row.getCell(0) != null) {
			if (!replaceBlank(row.getCell(0).getStringCellValue())
					.equals("联系人")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 1cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(1) != null) {
			if (!replaceBlank(row.getCell(1).getStringCellValue()).equals(
					"联系电话")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 2cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(2) != null) {
			if (!replaceBlank(row.getCell(2).getStringCellValue()).equals("性别")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 3cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(3) != null) {
			if (!replaceBlank(row.getCell(3).getStringCellValue()).equals(
					"联系地址")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 4cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(4) != null) {
			if (!replaceBlank(row.getCell(4).getStringCellValue()).equals(
					"电子邮件")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 5cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(5) != null) {
			if (!replaceBlank(row.getCell(5).getStringCellValue()).equals("传真")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 6cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(6) != null) {
			if (!replaceBlank(row.getCell(6).getStringCellValue()).equals("职务")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 7cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}

		if (row.getCell(7) != null) {
			if (!replaceBlank(row.getCell(7).getStringCellValue()).equals("备注")) {
				throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
						"excel 8cell name error");
			}
		} else {
			throw new BusinessException(ErrorCode.EXCEL_FORMAT_INVALID,
					"excel format error");
		}
	}

	/**
	 * 
	 * 去掉字符串空格换行
	 * 
	 * @param str
	 * @return string
	 * @author wangbinyu
	 *         <p />
	 *         Create at 2013 上午9:47:56
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	@Override
	public void batchInsertAddressBook(List<AddressBook> abs, String organId) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("organ.id", organId);
		List<AddressBook> list = addressbookDAO.findByPropertys(params);
		for (AddressBook ab : list) {
			addressbookDAO.delete(ab);
		}
		for (AddressBook ab : abs) {
			addressbookDAO.batchInsertAddressBook(ab);
		}
		addressbookDAO.excuteBatchAddressBook();
	}

	@Override
	public Element listUnitByGisId(List<Element> listElement) {
		Element units = new Element("Units");
		String type = "";
		String gisId = "";
		for (Element e : listElement) {
			type = e.getAttributeValue("Type");
			gisId = e.getAttributeValue("GisId");
			EmUnit unit = emUnitDAO.getUnit(type, gisId);
			if (null != unit) {
				Element unitElement = new Element("Unit");
				unitElement.setAttribute("Id", unit.getId());
				unitElement.setAttribute("Name",
						unit.getName() != null ? unit.getName() : "");
				unitElement.setAttribute("StandardNumber", unit
						.getStandardNumber() != null ? unit.getStandardNumber()
						: "");
				unitElement.setAttribute("Location",
						unit.getLocation() != null ? unit.getLocation() : "");
				unitElement.setAttribute("Telephone",
						unit.getTelephone() != null ? unit.getTelephone() : "");
				unitElement.setAttribute("Fax",
						unit.getFax() != null ? unit.getFax() : "");
				unitElement.setAttribute("Email",
						unit.getEmail() != null ? unit.getEmail() : "");
				unitElement.setAttribute("LinkMan",
						unit.getLinkMan() != null ? unit.getLinkMan() : "");
				unitElement.setAttribute("Mobile",
						unit.getMobile() != null ? unit.getMobile() : "");
				unitElement.setAttribute("OrganId",
						unit.getOrgan() != null ? unit.getOrgan().getId() : "");
				unitElement.setAttribute("Longitude",
						unit.getLongitude() != null ? unit.getLongitude() : "");
				unitElement.setAttribute("Latitude",
						unit.getLatitude() != null ? unit.getLatitude() : "");
				unitElement.setAttribute("CreateTime",
						unit.getCreateTime() != null ? unit.getCreateTime()
								.toString() : "");
				unitElement.setAttribute("Note",
						unit.getNote() != null ? unit.getNote() : "");
				unitElement.setAttribute("GisId",
						unit.getGisId() != null ? unit.getGisId() : "");
				setUnitParamter(unitElement, unit, type);
				units.addContent(unitElement);
			}
		}

		return units;
	}

	private void setUnitParamter(Element unitElement, EmUnit unit, String type) {
		String rescueCapability = "";
		String fireEngineNumber = "";
		String unitLevel = "";
		String ambulanceNumber = "";
		String carNumber = "";
		String teamNumber = "";
		String managerUnit = "";
		if (type.equals(TypeDefinition.DEVICE_TYPE_FIRE + "")) {
			Fire fire = (Fire) unit;
			rescueCapability = fire.getRescueCapability() != null ? fire
					.getRescueCapability().toString() : "";
			fireEngineNumber = fire.getFireEngineNumber() != null ? fire
					.getFireEngineNumber().toString() : "";
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_POLICE + "")) {
			Police policy = (Police) unit;
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_HOSPITAL + "")) {
			Hospital hospital = (Hospital) unit;
			unitLevel = hospital.getUnitLevel() != null ? hospital
					.getUnitLevel().toString() : "";
			ambulanceNumber = hospital.getAmbulanceNumber() != null ? hospital
					.getAmbulanceNumber().toString() : "";
			rescueCapability = hospital.getRescueCapability() != null ? hospital
					.getRescueCapability().toString() : "";
		} else if (type.equals(TypeDefinition.DEVICE_TYPE_ROAD_ADMIN + "")) {
			RoadAdmin rd = (RoadAdmin) unit;
			carNumber = rd.getCarNumber() != null ? rd.getCarNumber()
					.toString() : "";
			teamNumber = rd.getTeamNumber() != null ? rd.getTeamNumber()
					.toString() : "";
		} else {
			throw new BusinessException(ErrorCode.PARAMETER_INVALID,
					"Unit type[" + type + "] not support !");
		}

		unitElement.setAttribute("RescueCapability", rescueCapability);
		unitElement.setAttribute("FireEngineNumber", fireEngineNumber);
		unitElement.setAttribute("UnitLevel", unitLevel);
		unitElement.setAttribute("AmbulanceNumber", ambulanceNumber);
		unitElement.setAttribute("CarNumber", carNumber);
		unitElement.setAttribute("TeamNumber", teamNumber);
		unitElement.setAttribute("ManagerUnit", managerUnit);
	}

	@Override
	public List<VehicleDetector> listVd(String organId) {
		return vdDAO.listRoadVd(organId, null);
	}

	@Override
	public List<Camera> listEventCamera(List<String> cameraTypes,
			List<String> tollgates, float beginStake, float endStake,
			int frontSearchRange, int backSearchRange, int navigation,
			String organId) {
		List<Camera> cameras = null;
		List<Camera> rtnList = new LinkedList<Camera>();
		Set<String> organIds = new HashSet<String>();
		Organ organ = organDAO.findById(organId);
		// 主线事件
		if (organ instanceof OrganRoad) {
			organIds.add(organId);
			// 搜索收费站满足条件的摄像头
			for (String tollgate : tollgates) {
				// 根据范围搜索
				if (StringUtils.isNumeric(tollgate)) {
					List<Tollgate> tolls = tollgateDAO.findAll();
					for (Tollgate toll : tolls) {
						float stakeNumber = NumberUtil.floatStake(toll
								.getStakeNumber());
						// 上行方向
						if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
							if (Math.abs(beginStake - stakeNumber) <= Float
									.parseFloat(tollgate) / 1000) {
								organIds.add(toll.getId());
							}
						}
						// 下行方向
						else {
							if (Math.abs(endStake - stakeNumber) <= Float
									.parseFloat(tollgate) / 1000) {
								organIds.add(toll.getId());
							}
						}
					}
				}
				// 根据收费站名称搜索
				else {
					List<String> ids = tollgateDAO.listByName(tollgate);
					for (String id : ids) {
						organIds.add(id);
					}
				}
			}
			// 搜索机构下满足条件的摄像头
			cameras = cameraDAO.listCameraByType(organIds, cameraTypes);
		}
		// 收费站事件
		else if (organ instanceof Tollgate) {
			// 获取到事件所属高速路段
			Organ parent = organ.getParent();
			organIds.add(parent.getId());
			for (String tollgate : tollgates) {
				// 根据收费站名称搜索
				List<String> ids = tollgateDAO.listByName(tollgate);
				for (String id : ids) {
					organIds.add(id);
				}
			}
			// 搜索机构下满足条件的摄像头
			cameras = cameraDAO.listCameraByType(organIds, cameraTypes);
		} else {
			return null;
		}

		// 存在桩号的摄像头，根据起止桩号和搜索范围过滤
		for (Camera vic : cameras) {
			if (StringUtils.isBlank(vic.getStakeNumber())) {
				rtnList.add(vic);
				continue;
			}
			float vicStake = NumberUtil.floatStake(vic.getStakeNumber());
			// 上行方向
			if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
				if (Math.abs(beginStake - vicStake) <= frontSearchRange / 1000) {
					rtnList.add(vic);
					continue;
				}
				if (Math.abs(endStake - vicStake) <= backSearchRange / 1000) {
					rtnList.add(vic);
				}
			}
			// 下行方向
			else {
				if (Math.abs(endStake - vicStake) <= frontSearchRange / 1000) {
					rtnList.add(vic);
					continue;
				}
				if (Math.abs(beginStake - vicStake) <= backSearchRange / 1000) {
					rtnList.add(vic);
				}
			}
		}
		return rtnList;
	}

	@Override
	public ControlDevice getEventVms(String organId, String beginStake,
			String endStake, int navigation, String location) {
		List<String> organIds = new LinkedList<String>();
		organIds.add(organId);
		List<ControlDevice> list = controlDeviceDAO.listControlDevices(
				organIds, TypeDefinition.DEVICE_TYPE_CMS);
		float floatBegin = NumberUtil.floatStake(beginStake);
		float floatEnd = NumberUtil.floatStake(endStake);
		// 距离
		float distance = 9999.0f;
		ControlDevice cms = null;
		// 入口
		if ("entrance".equals(location)) {
			// 上行
			if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
				for (ControlDevice cd : list) {
					if ((cd.getName().indexOf("入口")) < 0) {
						continue;
					}
					float floatStake = NumberUtil.floatStake(cd
							.getStakeNumber());
					float sub = floatStake - floatBegin;
					if (sub < distance && sub >= 0) {
						cms = cd;
						distance = sub;
					}
				}
			}
			// 下行
			else if (TypeDefinition.NAVIGATION_DOWNLOAD == navigation) {
				for (ControlDevice cd : list) {
					if ((cd.getName().indexOf("入口")) < 0) {
						continue;
					}
					float floatStake = NumberUtil.floatStake(cd
							.getStakeNumber());
					float sub = floatEnd - floatStake;
					if (sub < distance && sub >= 0) {
						cms = cd;
						distance = sub;
					}
				}
			}
		}
		// 出口
		else if ("exit".equals(location)) {
			// 上行
			if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
				for (ControlDevice cd : list) {
					if ((cd.getName().indexOf("出口")) < 0) {
						continue;
					}
					float floatStake = NumberUtil.floatStake(cd
							.getStakeNumber());
					float sub = floatStake - floatBegin;
					if (sub < distance && sub >= 0) {
						cms = cd;
						distance = sub;
					}
				}
			}
			// 下行
			else if (TypeDefinition.NAVIGATION_DOWNLOAD == navigation) {
				for (ControlDevice cd : list) {
					if ((cd.getName().indexOf("出口")) < 0) {
						continue;
					}
					float floatStake = NumberUtil.floatStake(cd
							.getStakeNumber());
					float sub = floatEnd - floatStake;
					if (sub < distance && sub >= 0) {
						cms = cd;
						distance = sub;
					}
				}
			}
		}
		// 主线
		else if ("road".equals(location)) {
			// 上行
			if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
				for (ControlDevice cd : list) {
					// if ((cd.getName().indexOf("主线")) < 0) {
					// continue;
					// }
					if (cd.getNavigation().equals(
							TypeDefinition.NAVIGATION_DOWNLOAD + "")) {
						continue;
					}
					float floatStake = NumberUtil.floatStake(cd
							.getStakeNumber());
					float sub = floatStake - floatBegin;
					if (sub < distance && sub >= 0) {
						cms = cd;
						distance = sub;
					}
				}
			}
			// 下行
			else if (TypeDefinition.NAVIGATION_DOWNLOAD == navigation) {
				for (ControlDevice cd : list) {
					// if ((cd.getName().indexOf("主线")) < 0) {
					// continue;
					// }
					if (cd.getNavigation().equals(
							TypeDefinition.NAVIGATION_UPLOAD + "")) {
						continue;
					}
					float floatStake = NumberUtil.floatStake(cd
							.getStakeNumber());
					float sub = floatEnd - floatStake;
					if (sub < distance && sub >= 0) {
						cms = cd;
						distance = sub;
					}
				}
			}
		}
		// 对侧主线
		else if ("oppRoad".equals(location)) {
			// 上行
			if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
				for (ControlDevice cd : list) {
					if ((cd.getName().indexOf("主线")) < 0) {
						continue;
					}
					if (cd.getNavigation().equals(
							TypeDefinition.NAVIGATION_UPLOAD + "")) {
						continue;
					}
					float floatStake = NumberUtil.floatStake(cd
							.getStakeNumber());
					float sub = floatEnd - floatStake;
					if (sub < distance && sub >= 0) {
						cms = cd;
						distance = sub;
					}
				}
			}
			// 下行
			else if (TypeDefinition.NAVIGATION_DOWNLOAD == navigation) {
				for (ControlDevice cd : list) {
					if ((cd.getName().indexOf("主线")) < 0) {
						continue;
					}
					if (cd.getNavigation().equals(
							TypeDefinition.NAVIGATION_DOWNLOAD + "")) {
						continue;
					}
					float floatStake = NumberUtil.floatStake(cd
							.getStakeNumber());
					float sub = floatStake - floatBegin;
					if (sub < distance && sub >= 0) {
						cms = cd;
						distance = sub;
					}
				}
			}
		}
		// 对侧入口
		// else if ("oppEntrance".equals(location)) {
		// // 上行
		// if (TypeDefinition.NAVIGATION_UPLOAD == navigation) {
		// for (ControlDevice cd : list) {
		// if ((cd.getName().indexOf("入口")) < 0) {
		// continue;
		// }
		// float floatStake = NumberUtil.floatStake(cd.getStakeNumber());
		// float sub = floatEnd - floatStake;
		// if (sub < distance && sub >= 0) {
		// cms = cd;
		// distance = sub;
		// }
		// }
		// }
		// // 下行
		// else if (TypeDefinition.NAVIGATION_DOWNLOAD == navigation) {
		// for (ControlDevice cd : list) {
		// if ((cd.getName().indexOf("入口")) < 0) {
		// continue;
		// }
		// float floatStake = NumberUtil.floatStake(cd.getStakeNumber());
		// float sub = floatStake - floatBegin;
		// if (sub < distance && sub >= 0) {
		// cms = cd;
		// distance = sub;
		// }
		// }
		// }
		// }
		return cms;
	}

	@Override
	public String createEvent(String standardNumber, Short type,
			String subType, Long createTime) throws BusinessException {
		Event event = new Event();
		event.setCreateTime(createTime);
		event.setType(type);
		event.setSubType(subType);
		event.setLaneNumber(Short.valueOf("1"));
		// 查找設備信息
		StandardNumber sn = snDAO.getBySN(standardNumber);
		if (null == sn) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
					"StandardNumber[" + standardNumber + "] not found !");
		}
		// 攝像頭事件
		if (sn.getClassType().equals(TypeDefinition.RESOURCE_TYPE_CAMERA)) {
			Camera camera = cameraDAO.findBySN(standardNumber);
			event.setBeginStake(camera.getStakeNumber());
			event.setEndStake(camera.getStakeNumber());
			event.setLatitude(camera.getLatitude());
			event.setLongitude(camera.getLongitude());
			event.setName(camera.getName());
			event.setNavigation(NumberUtil.getShort(camera.getNavigation()));
			event.setOrgan(camera.getOrgan());
			event.setRoadName(camera.getOrgan().getName());
			event.setLocation(camera.getLocation());
			event.setSendUser(camera.getId());
			event.setStatus(TypeDefinition.EVENT_STATUS_0);
		}
		eventDAO.save(event);
		return event.getId();
	}
}
