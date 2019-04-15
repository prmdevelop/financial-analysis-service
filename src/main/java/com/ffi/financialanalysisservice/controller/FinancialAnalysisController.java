package com.ffi.financialanalysisservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ffi.financialanalysisservice.config.FinancialAnalysisConfiguration;
import com.ffi.financialanalysisservice.delegator.FinancialAnalysisServiceDelegator;
import com.ffi.financialanalysisservice.request.FinancialServiceRequestJson;
import com.ffi.financialanalysisservice.response.FinancialServiceResponseJson;
import com.ffi.financialanalysisservice.response.FinancialServiceResponseObject;
import com.ffi.financialanalysisservice.response.OneDriveResponseJson;
import com.ffi.financialanalysisservice.response.OneDriveResponseObject;
import com.ffi.financialanalysisservice.response.TemplateResponseJson;
import com.ffi.financialanalysisservice.response.TemplateResponseObject;

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
	

	/*@GetMapping(value = "/loadTemplate/{templateName}/{user}", consumes = "application/json", produces = "application/json")
	public TemplateResponseJson<TemplateResponseObject> loadTemplate(@PathVariable("templateName") String templateName,@PathVariable("user") String user) {
		TemplateResponseJson<TemplateResponseObject> responseJson = new TemplateResponseJson<>();
		try {
			TemplateResponseObject responseObject = new TemplateResponseObject();
			Map<String, Object> data = new HashMap<>();
			data.put("template", financialAnalysisServiceDelegator.loadTemplate(templateName,user));
			responseObject.setTemplateResponse(data);
			responseJson.setStatusMessage(finAnalysis.getSuccess().getRetrieve());
			responseJson.setStatusCode(finAnalysis.getSuccess().getCode());
			responseJson.setData(responseObject);
		} catch (Exception e) {
			responseJson.setErrorMessage(finAnalysis.getError().getRetrieve());
			responseJson.setErrorCode(finAnalysis.getError().getCode());
		}
		return responseJson;
	}
	
	@PostMapping(value = "/loadData", consumes = "application/json", produces = "application/json")
	public FinancialServiceResponseJson<FinancialServiceResponseObject> getFinancialData(@RequestBody FinancialServiceRequestJson fRequestJson) {
		FinancialServiceResponseJson<FinancialServiceResponseObject> responseJson = new FinancialServiceResponseJson<>();
		try {
			FinancialServiceResponseObject responseObject = new FinancialServiceResponseObject();
			Map<String, Object> data = new HashMap<>();
			data.put("financialData", financialAnalysisServiceDelegator.loadFinancial(fRequestJson));
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
	
	@PostMapping(value = "/createFile", produces = "application/json")
	public OneDriveResponseJson<OneDriveResponseObject> uploadFile(@RequestParam("filePath") final String filePath) {
		OneDriveResponseJson<OneDriveResponseObject> responseJson = new OneDriveResponseJson<>();
		try {
			OneDriveResponseObject response = new OneDriveResponseObject();
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("url",financialAnalysisServiceDelegator.createFile(filePath));
			response.setOneDriveResponse(responseObject);
			responseJson.setData(response);
			responseJson.setStatusMessage(finAnalysis.getSuccess().getRetrieve());
			responseJson.setStatusCode(finAnalysis.getSuccess().getCode());
		} catch (Exception e) {
			responseJson.setErrorMessage(finAnalysis.getError().getRetrieve());
			responseJson.setErrorCode(finAnalysis.getError().getCode());
		}
		return responseJson;
	}*/
	
	

}
