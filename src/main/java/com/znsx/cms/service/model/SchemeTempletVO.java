package com.znsx.cms.service.model;

import java.util.List;

/**
 * SchemeTempletVO
 * 
 * @author huangbuji
 *         <p />
 *         Create at 2014-6-5 下午5:01:10
 */
public class SchemeTempletVO {
	private String id;
	private String name;
	private String type;
	private String level;
	private List<Step> steps;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public class Step {
		private String seq;
		private String content;

		public String getSeq() {
			return seq;
		}

		public void setSeq(String seq) {
			this.seq = seq;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
}
