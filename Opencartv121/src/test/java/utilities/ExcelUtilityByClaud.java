package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilityByClaud {

    public FileInputStream fileInputStream;
    public FileOutputStream fileOutputStream;
    public XSSFWorkbook workbook;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;
    public CellStyle cellStyle;

    String path;

    public ExcelUtilityByClaud(String path) {
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
            data = "";
        }

        workbook.close();
        fileInputStream.close();

        return data;
    }

    public void setCellData(String sheetname, int rownum, int colnum, String data) throws IOException {
        File xlfile = new File(path);

        // create the file if it does not exist yet
        if (!xlfile.exists()) {
            workbook = new XSSFWorkbook();
            fileOutputStream = new FileOutputStream(path);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
        }

        fileInputStream = new FileInputStream(path);
        workbook = new XSSFWorkbook(fileInputStream);

        // create the sheet if it does not exist yet
        if (workbook.getSheetIndex(sheetname) == -1) {
            workbook.createSheet(sheetname);
        }
        sheet = workbook.getSheet(sheetname);

        // create the row if it does not exist yet
        if (sheet.getRow(rownum) == null) {
            sheet.createRow(rownum);
        }
        row = sheet.getRow(rownum);

        cell = row.createCell(colnum);
        cell.setCellValue(data);

        fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);

        workbook.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

    public void fillGreenColor(String sheetname, int rownum, int colnum) throws IOException {
        fileInputStream = new FileInputStream(path);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetname);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(cellStyle);

        fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);

        workbook.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

    public void fillRedColor(String sheetname, int rownum, int colnum) throws IOException {
        fileInputStream = new FileInputStream(path);
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = workbook.getSheet(sheetname);
        row = sheet.getRow(rownum);
        cell = row.getCell(colnum);

        cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(cellStyle);

        fileOutputStream = new FileOutputStream(path);
        workbook.write(fileOutputStream);

        workbook.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}

//Things to know while learning:

//getRowCount uses getLastRowNum(), which counts rows after the header. If your sheet has a header plus 5 data rows, it returns 5. Your loops then run for (int i = 1; i <= totalrows; i++).
//getCellCount uses getLastCellNum(), which returns the actual number of columns, so loop with j < totalcols.
//getCellData uses DataFormatter so numbers, dates, and text all come back as plain Strings.
//setCellData, fillGreenColor, and fillRedColor are typically used at the end of a data-driven test to write “Pass” or “Fail” back to the Excel sheet and color the cell.

