package com.ffi.financialanalysisservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ffi.financialanalysisservice.response.OneDriveResponseJson;
import com.ffi.financialanalysisservice.response.OneDriveResponseObject;

@FeignClient(name = "onedrive-service",url="http://localhost:8082/onedrive/service")
public interface OneDriveServiceProxy {
	
	@PostMapping(value="/createFile",consumes = "application/json",produces = "application/json")
	public OneDriveResponseJson<OneDriveResponseObject> createFile(@RequestParam("filePath") final String filePath);

}
