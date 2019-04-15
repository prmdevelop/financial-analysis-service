package com.ffi.financialanalysisservice.vo;

public class TemplateLabelVO {

	private String templateLabelId;
	private String templateLineItem;

	public String getTemplateLabelId() {
		return templateLabelId;
	}

	public void setTemplateLabelId(String templateLabelId) {
		this.templateLabelId = templateLabelId;
	}

	public String getTemplateLineItem() {
		return templateLineItem;
	}

	public void setTemplateLineItem(String templateLineItem) {
		this.templateLineItem = templateLineItem;
	}

	@Override
	public String toString() {
		return "TemplateLabelVO [templateLabelId=" + templateLabelId + ", templateLineItem=" + templateLineItem + "]";
	}
}
