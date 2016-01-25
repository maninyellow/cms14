package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 预案库与预案字典关联对象
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-5-29 下午3:03:45
 */
public class TempletDictionary implements Serializable {
	private static final long serialVersionUID = 6996087330083920691L;
	private String id;
	private SchemeTemplet schemeTemplet;
	private SchemeDictionary schemeDictionary;
	private Short seq;
	private Integer range;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SchemeTemplet getSchemeTemplet() {
		return schemeTemplet;
	}

	public void setSchemeTemplet(SchemeTemplet schemeTemplet) {
		this.schemeTemplet = schemeTemplet;
	}

	public SchemeDictionary getSchemeDictionary() {
		return schemeDictionary;
	}

	public void setSchemeDictionary(SchemeDictionary schemeDictionary) {
		this.schemeDictionary = schemeDictionary;
	}

	public Short getSeq() {
		return seq;
	}

	public void setSeq(Short seq) {
		this.seq = seq;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

}
