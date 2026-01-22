package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
	private String filePath;
    private Workbook workbook;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
    	public String getCellData(String sheetName, int rowNum, int colNum) {
    	    Sheet sheet = workbook.getSheet(sheetName);
    	    if (sheet == null) throw new RuntimeException("Sheet not found: " + sheetName);

    	    Row row = sheet.getRow(rowNum);
    	    if (row == null) throw new RuntimeException("Row not found: " + rowNum);

    	    Cell cell = row.getCell(colNum);
    	    if (cell == null) return "";

    	    CellType type = cell.getCellType();

    	    switch (type) {
    	        case STRING:
    	            return cell.getStringCellValue();
    	        case NUMERIC:
    	            // Convert numeric to string
    	            return String.valueOf((long) cell.getNumericCellValue()); 
    	        case BOOLEAN:
    	            return String.valueOf(cell.getBooleanCellValue());
    	        case FORMULA:
    	            return cell.getCellFormula();
    	        default:
    	            return "";
    	    }
    }

    public int getRowCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet.getPhysicalNumberOfRows();
    }


}
