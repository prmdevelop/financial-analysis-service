package com.ffi.financialanalysisservice.delegator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffi.financialanalysisservice.config.FinancialAnalysisConfiguration;
import com.ffi.financialanalysisservice.proxy.FinancialServiceProxy;
import com.ffi.financialanalysisservice.proxy.OneDriveServiceProxy;
import com.ffi.financialanalysisservice.proxy.TemplateServiceProxy;
import com.ffi.financialanalysisservice.request.FinancialServiceRequestJson;
import com.ffi.financialanalysisservice.response.FinancialServiceResponseJson;
import com.ffi.financialanalysisservice.response.FinancialServiceResponseObject;
import com.ffi.financialanalysisservice.response.OneDriveResponseJson;
import com.ffi.financialanalysisservice.response.OneDriveResponseObject;
import com.ffi.financialanalysisservice.response.TemplateResponseJson;
import com.ffi.financialanalysisservice.response.TemplateResponseObject;
import com.ffi.financialanalysisservice.vo.FinancialDataVO;
import com.ffi.financialanalysisservice.vo.TemplateLabelVO;


@Service
public class FinancialAnalysisServiceDelegatorImpl implements FinancialAnalysisServiceDelegator{
	
	@Autowired
	FinancialAnalysisConfiguration configuration;

	@Autowired
	TemplateServiceProxy templateProxy;
	
	@Autowired
	FinancialServiceProxy financialProxy;
	
	@Autowired
	OneDriveServiceProxy oneDriveProxy;
	
	@Override
	public String loadFinancialTemplate(FinancialServiceRequestJson fServiceRequestJson) {
		String url = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> templateDetails = loadTemplate(fServiceRequestJson.getTemplateName(), fServiceRequestJson.getUser());
			List<TemplateLabelVO> templateLabelVO = mapper.convertValue((List<Object>) templateDetails.get("templateDetail"),new TypeReference<List<TemplateLabelVO>>() { });
			Map<String,Object> financialDetails = loadFinancial(fServiceRequestJson);
			List<FinancialDataVO> financialDataVO = mapper.convertValue((List<Object>) financialDetails.get("financial"),new TypeReference<List<FinancialDataVO>>() { });
			
			//Change the lineItem id from financial to the template lineItem
			//Making the lineItem as the key & year and lineItem value as value
			//Sort the nested map based on the key i.e year in reverse order
			Map<String, TreeMap<String, Double>> lineItemData = new HashMap<>();
			outerloop:
			for(FinancialDataVO financialData : financialDataVO){
				TreeMap<String,Double> periodData = new TreeMap<>(Collections.reverseOrder());
				for(TemplateLabelVO templateLabel : templateLabelVO){
					if(financialData.getLineItem().equalsIgnoreCase(templateLabel.getTemplateLabelId())){
						if(lineItemData.containsKey(templateLabel.getTemplateLineItem())){
							periodData = lineItemData.get(templateLabel.getTemplateLineItem());
							periodData.put(financialData.getYear(), financialData.getLineItemValue());
						}else{
							periodData.put(financialData.getYear(), financialData.getLineItemValue());
						}
						lineItemData.put(templateLabel.getTemplateLineItem(), periodData);
						continue outerloop;
					}
				}
			}
			
			String filePath = (String)templateDetails.get("templatePath");
			populateTemplate(filePath, lineItemData);
			url = createFile(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return url;
	}
	
	
	private Map<String,Object> loadTemplate(String templateName,String user){
		TemplateResponseJson<TemplateResponseObject> responseJson = null;
		Map<String,Object> templateDetails = null;
		try {
			responseJson =  templateProxy.loadTemplate(templateName,user);
			TemplateResponseObject responseObject = responseJson.getData();
			templateDetails = responseObject.getTemplateResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateDetails;
	}

	private Map<String,Object> loadFinancial(FinancialServiceRequestJson fServiceRequestJson) {
		FinancialServiceResponseJson<FinancialServiceResponseObject> responseJson = null;
		Map<String,Object> financialData = null;
		try{
			responseJson =  financialProxy.loadFinancial(fServiceRequestJson);
			FinancialServiceResponseObject responseObject = responseJson.getData();
			financialData = responseObject.getFinancialServiceResponse();
		}catch(Exception e){
			e.printStackTrace();
		}
		return financialData;
	}

	private String createFile(String filePath) {
		OneDriveResponseJson<OneDriveResponseObject> responseJson = null;
		String url = "";
		try {
			responseJson = oneDriveProxy.createFile(filePath);
			OneDriveResponseObject responseObject = responseJson.getData();
			Map<String,Object> data = responseObject.getOneDriveResponse();
			url = (String)data.get("URL");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private void populateTemplate(String templatePath, Map<String, TreeMap<String, Double>> data) {
		try (InputStream uploadedFile = new FileInputStream(templatePath);
				Workbook workbook = new XSSFWorkbook(uploadedFile)) {
			for (Map.Entry<String, TreeMap<String, Double>> dataEntry : data.entrySet()) {
				int dataSheetLineItemIndex = workbook.getNameIndex(dataEntry.getKey());
				int count = 1;
				if (dataSheetLineItemIndex != -1) {
					Name namedLineItem = workbook.getNameAt(dataSheetLineItemIndex);
					AreaReference arefLineItem = new AreaReference(namedLineItem.getRefersToFormula(), null);
					CellReference crefsLineItem = arefLineItem.getAllReferencedCells()[0];
					for (Map.Entry<String, Double> periodEntry : dataEntry.getValue().entrySet()) {
						Name namedPeriod = workbook.getNameAt(workbook.getNameIndex(configuration.getTemplate().getDataSheet() + count));
						AreaReference arefPeriod = new AreaReference(namedPeriod.getRefersToFormula(), null);
						CellReference[] crefsPeriod = arefPeriod.getAllReferencedCells();
						for (int i = 0; i < crefsPeriod.length; i++) {
							Sheet dataSheet = workbook.getSheet(crefsPeriod[i].getSheetName());
							Row rowDataSheet = dataSheet.getRow(crefsLineItem.getRow());
							Cell cellDataSheet = rowDataSheet.getCell(crefsPeriod[i].getCol());
							cellDataSheet.setCellValue(periodEntry.getValue());
						}

						int balanceOrIncomeSheetLineItemIndex = workbook.getNameIndex(configuration.getTemplate().getBalanceSheet() + dataEntry.getKey() + configuration.getTemplate().getPeriod() + count);
						if(balanceOrIncomeSheetLineItemIndex == -1){
							balanceOrIncomeSheetLineItemIndex = workbook.getNameIndex(configuration.getTemplate().getIncomeStatement() + dataEntry.getKey() + configuration.getTemplate().getPeriod() + count);
						}
						Name aNamedBalanceSheet = workbook.getNameAt(balanceOrIncomeSheetLineItemIndex);
						AreaReference arefBalanceSheet = new AreaReference(aNamedBalanceSheet.getRefersToFormula(),
								null);
						CellReference crefsBalanceSheet = arefBalanceSheet.getAllReferencedCells()[0];
						Sheet balanceSheet = workbook.getSheet(crefsBalanceSheet.getSheetName());
						Row rowBalanceSheet = balanceSheet.getRow(crefsBalanceSheet.getRow());
						Cell cellBalanceSheet = rowBalanceSheet.getCell(crefsBalanceSheet.getCol());
						cellBalanceSheet.setCellValue(periodEntry.getValue());
						count++;
					}
				}
			}

			FileOutputStream outputFile = new FileOutputStream(new File(templatePath));
			FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
			formulaEvaluator.evaluateAll();
			workbook.setSheetHidden(workbook.getSheetIndex("Data Sheet"), true);
			workbook.write(outputFile);
			outputFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
