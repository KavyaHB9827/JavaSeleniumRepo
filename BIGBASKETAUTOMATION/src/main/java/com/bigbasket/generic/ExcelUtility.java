package com.bigbasket.generic;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {

    public String getDataFromExcel(String sheetName, int rowNum, int colNum) throws EncryptedDocumentException, IOException {
        FileInputStream fis = new FileInputStream("./src/test/resources/TestScriptData.xlsx");
        Workbook workbook = WorkbookFactory.create(fis);
        return workbook.getSheet(sheetName).getRow(rowNum).getCell(colNum).getStringCellValue();
    }
}