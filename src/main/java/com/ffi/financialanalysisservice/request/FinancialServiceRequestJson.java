package com.ffi.financialanalysisservice.request;

import java.util.List;

public class FinancialServiceRequestJson {
	
	private String user;
	
	private String templateName;

	private String companyId;

	private String sourceName;

	private List<PeriodRequest> periodRequest;
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public List<PeriodRequest> getPeriodRequest() {
		return periodRequest;
	}

	public void setPeriodRequest(List<PeriodRequest> periodRequest) {
		this.periodRequest = periodRequest;
	}
}
