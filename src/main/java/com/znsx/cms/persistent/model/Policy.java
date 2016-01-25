package com.znsx.cms.persistent.model;

import java.io.Serializable;

/**
 * 策略实体
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2013-11-26 下午3:11:18
 */
public class Policy implements Serializable {
	private static final long serialVersionUID = -6939884548921687448L;
	private String id;
	private String name;
	private Short type;
	private Organ organ;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

}
