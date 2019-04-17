package com.ffi.financialanalysisservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties("financial-analysis")
public class FinancialAnalysisConfiguration {

	private Success success;
	private Error error;
	private Template template;

	public static class Success {

		private String code;
		private String retrieve;
		private String delete;
		private String modify;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getRetrieve() {
			return retrieve;
		}

		public void setRetrieve(String retrieve) {
			this.retrieve = retrieve;
		}

		public String getDelete() {
			return delete;
		}

		public void setDelete(String delete) {
			this.delete = delete;
		}

		public String getModify() {
			return modify;
		}

		public void setModify(String modify) {
			this.modify = modify;
		}
	}

	public static class Error {

		private String record;
		private String code;
		private String retrieve;
		private String delete;
		private String modify;

		public String getRecord() {
			return record;
		}

		public void setRecord(String record) {
			this.record = record;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getRetrieve() {
			return retrieve;
		}

		public void setRetrieve(String retrieve) {
			this.retrieve = retrieve;
		}

		public String getDelete() {
			return delete;
		}

		public void setDelete(String delete) {
			this.delete = delete;
		}

		public String getModify() {
			return modify;
		}

		public void setModify(String modify) {
			this.modify = modify;
		}
	}
	
	public static class Template{
		
		private String dataSheet;
		private String balanceSheet;
		private String incomeStatement;
		private String period;
		public String getDataSheet() {
			return dataSheet;
		}
		public void setDataSheet(String dataSheet) {
			this.dataSheet = dataSheet;
		}
		public String getBalanceSheet() {
			return balanceSheet;
		}
		public void setBalanceSheet(String balanceSheet) {
			this.balanceSheet = balanceSheet;
		}
		public String getIncomeStatement() {
			return incomeStatement;
		}
		public void setIncomeStatement(String incomeStatement) {
			this.incomeStatement = incomeStatement;
		}
		public String getPeriod() {
			return period;
		}
		public void setPeriod(String period) {
			this.period = period;
		}
		
	}
	
	public Success getSuccess() {
		return success;
	}

	public void setSuccess(Success success) {
		this.success = success;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

}
