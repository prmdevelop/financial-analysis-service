package com.ffi.financialanalysisservice.delegator;

import com.ffi.financialanalysisservice.request.FinancialServiceRequestJson;

public interface FinancialAnalysisServiceDelegator {
	
	public String loadFinancialTemplate(FinancialServiceRequestJson fServiceRequestJson);

}
