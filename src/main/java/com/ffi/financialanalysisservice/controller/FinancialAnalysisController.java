package com.ffi.financialanalysisservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ffi.financialanalysisservice.config.FinancialAnalysisConfiguration;
import com.ffi.financialanalysisservice.delegator.FinancialAnalysisServiceDelegator;
import com.ffi.financialanalysisservice.request.FinancialServiceRequestJson;
import com.ffi.financialanalysisservice.response.FinancialServiceResponseJson;
import com.ffi.financialanalysisservice.response.FinancialServiceResponseObject;

@RestController
@RequestMapping("/financial/analysis/")
public class FinancialAnalysisController {

	@Autowired
	FinancialAnalysisConfiguration finAnalysis;

	@Autowired
	FinancialAnalysisServiceDelegator financialAnalysisServiceDelegator;
	
	@PostMapping(value = "/loadFinancialTemplate", consumes = "application/json", produces = "application/json")
	public FinancialServiceResponseJson<FinancialServiceResponseObject> loadFinancialTemplate(@RequestBody FinancialServiceRequestJson fRequestJson) {
		FinancialServiceResponseJson<FinancialServiceResponseObject> responseJson = new FinancialServiceResponseJson<>();
		try {
			FinancialServiceResponseObject responseObject = new FinancialServiceResponseObject();
			Map<String, Object> data = new HashMap<>();
			data.put("url", financialAnalysisServiceDelegator.loadFinancialTemplate(fRequestJson));
			responseObject.setFinancialServiceResponse(data);
			responseJson.setStatusMessage(finAnalysis.getSuccess().getRetrieve());
			responseJson.setStatusCode(finAnalysis.getSuccess().getCode());
			responseJson.setData(responseObject);
		} catch (Exception e) {
			responseJson.setErrorMessage(finAnalysis.getError().getRetrieve());
			responseJson.setErrorCode(finAnalysis.getError().getCode());
		}
		return responseJson;
	}
}
