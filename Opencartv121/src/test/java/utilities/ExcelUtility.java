package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

    public FileInputStream fileInputStream;
    public FileOutputStream fileOutputStream;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle cellStyle;

    String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetname) throws IOException {
        fileInputStream = new FileInputStream(path);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetname);

        int rowcount = sheet.getLastRowNum();

        workbook.close();
        fileInputStream.close();

        return rowcount;
    }

    public int getCellCount(String sheetname, int rownum) throws IOException {
        fileInputStream = new FileInputStream(path);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetname);
        row = sheet.getRow(rownum);

        int cellcount = row.getLastCellNum();

        workbook.close();
        fileInputStream.close();

        return cellcount;
    }

    public String getCellData(String sheetname, int rownum, int colnum) throws IOException {
        fileInputStream = new FileInputStream(path);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetname);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        DataFormatter dataFormatter = new DataFormatter();
        String data;

        try {
            data = dataFormatter.formatCellValue(cell);
        } catch (Exception exception) {
            exception.getMessage();
            data = "";
        }

        workbook.close();
        fileInputStream.close();

        return data;
    }
}
