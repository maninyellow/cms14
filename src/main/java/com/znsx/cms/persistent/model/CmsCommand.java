package com.znsx.cms.persistent.model;

import java.io.Serializable;
import java.sql.Blob;

/**
 * @author huangbuji
 *         <p />
 *         2014-11-20 下午3:34:57
 */
public class CmsCommand implements Serializable {
	private static final long serialVersionUID = -1378209963668896467L;
	private String id;
	private Blob content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Blob getContent() {
		return content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

}
