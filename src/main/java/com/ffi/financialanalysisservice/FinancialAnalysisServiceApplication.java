package com.ffi.financialanalysisservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FinancialAnalysisServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialAnalysisServiceApplication.class, args);
	}

}
