package com.ffi.financialanalysisservice.delegator;

import java.util.Map;

import com.ffi.financialanalysisservice.request.FinancialServiceRequestJson;

public interface FinancialAnalysisServiceDelegator {
	
	public String loadFinancialTemplate(FinancialServiceRequestJson fServiceRequestJson);
	public Map<String,Object> loadTemplate(String templateName,String user);
	public Map<String,Object> loadFinancial(FinancialServiceRequestJson fServiceRequestJson);
	public String createFile(String filePath);

}
