package com.ffi.financialanalysisservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ffi.financialanalysisservice.request.FinancialServiceRequestJson;
import com.ffi.financialanalysisservice.response.FinancialServiceResponseJson;
import com.ffi.financialanalysisservice.response.FinancialServiceResponseObject;

@FeignClient(name = "financial-service",url="http://localhost:8081/financial/service")
public interface FinancialServiceProxy {
	
	@PostMapping(value="/loadData",consumes = "application/json",produces = "application/json")
	public FinancialServiceResponseJson<FinancialServiceResponseObject> loadFinancial(@RequestBody FinancialServiceRequestJson fRequestJson);

}
