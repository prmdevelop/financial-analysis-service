package com.ffi.financialanalysisservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ffi.financialanalysisservice.response.TemplateResponseJson;
import com.ffi.financialanalysisservice.response.TemplateResponseObject;


@FeignClient(name = "template-service",url="http://localhost:8083/template/service")
public interface TemplateServiceProxy {
	
	@GetMapping(value="/loadTemplate/{templateName}/{user}" ,consumes = "application/json",produces = "application/json")
	public TemplateResponseJson<TemplateResponseObject> loadTemplate(@PathVariable("templateName") String templateName,@PathVariable("user") String user);

}
