package kr.co.test.common.spring.view.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import common.util.ResponseUtil;

public class ExcelListView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 엑셀 워크시트 생성
		HSSFSheet excelSheet = workbook.createSheet("테스트");

		// 셀의 스타일 설정
		HSSFCellStyle celFormat = workbook.createCellStyle();
		HSSFFont celFont = workbook.createFont();
		this.setExcelStyle(celFormat, celFont);

		// 제목 컬럼 설정
		this.setExcelHeader(excelSheet, celFormat, (String[]) model.get("headerNames"));

		// 엑셀에 출력할 내용 가져오기
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> excelContent = (List<Map<String, Object>>)model.get("list");

		// 엑셀에 출력할 내용 설정
		this.setExcelRows(excelSheet, excelContent, (String[]) model.get("sortedKeys"));

		String fileName = (String) model.get("excelFileName");

		// 엑셀 다운로드
		ResponseUtil.downloadReportFile(request, response, fileName);
	}

	private void setExcelStyle(HSSFCellStyle celFormat, HSSFFont celFont) {
		celFont.setBold(true);

		celFormat.setFont(celFont);
		celFormat.setFillForegroundColor(HSSFColorPredefined.LIGHT_GREEN.getIndex());
		
		celFormat.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		celFormat.setAlignment(HorizontalAlignment.CENTER);
		celFormat.setVerticalAlignment(VerticalAlignment.CENTER);
	}

	private void setExcelHeader(HSSFSheet excelSheet, HSSFCellStyle celFormat, String[] headerNames) {
		HSSFRow excelHeader = excelSheet.createRow(0);

		for (int i=0; i<headerNames.length; i++) {
			excelHeader.createCell(i).setCellValue(headerNames[i]);
			excelHeader.getCell(i).setCellStyle(celFormat);
		}
	}

	private void setExcelRows(HSSFSheet excelSheet, List<Map<String, Object>> excelContent, String[] sortedKeys) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		for (int i=0; i<excelContent.size(); i++) {
			dataMap = excelContent.get(i);

			HSSFRow excelRow = excelSheet.createRow(i+1);
			excelRow.createCell(0).setCellValue(i+1);

			for (int j=0; j<sortedKeys.length; j++) {
				excelRow.createCell(j+1).setCellValue(dataMap.get(sortedKeys[j]).toString());
			}
		}
	}

}
