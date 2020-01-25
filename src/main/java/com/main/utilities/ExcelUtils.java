package com.main.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ExcelUtils {
    private static Logger logger = LogManager.getLogger(ExcelUtils.class.getName());
    private static Workbook wb;
    private static DataFormatter objDefaultFormat;

    public static void main(String ares[]) {
        ExcelUtils excelUtils = new ExcelUtils();
       /* String data[] = excelUtils.getDataFromSingleRowUsingTestCaseID(new File("C:\\Users\\P7110877\\eclipse-workspace\\testautomation\\src\\test\\resources\\testdata\\commonTestData.xlsx")
                , "Sheet1", "22");*/

        String data2 [][] = excelUtils.getAllDataFromSheet(new File("C:\\Users\\P7110877\\eclipse-workspace\\testautomation\\src\\test\\resources\\testdata\\commonTestData.xlsx")
                , "Sheet1", false);
       // Arrays.asList(data).forEach(e -> logger.info(e));

        for(int i = 0; i < data2.length; i++) {
            for(int j = 0; j < data2[i].length; j++) {
                logger.info(data2[i][j] + ",");
            }
            System.out.println();
        }


    }

    public static int getColumnCount(Sheet mySheet) {
        int maxCell = 0;
        for (int i = mySheet.getFirstRowNum(); i <= mySheet.getLastRowNum(); i++) {
            Row row1 = mySheet.getRow(i);
            int lastCell1 = row1.getLastCellNum();
            if (lastCell1 > maxCell) {
                maxCell = lastCell1;
            }
        }
        logger.info("Number of columns found:" + maxCell);
        return maxCell;
    }

    public String[] getDataFromSingleRowUsingTestCaseID(File fileName, String sheetName, String testCaseID) {
        boolean isCellFound = false;
        String[] data;
        int i = 0;
        objDefaultFormat = new DataFormatter();
        try {
            wb = WorkbookFactory.create(fileName);
            Sheet sheet = wb.getSheet(sheetName);
            data = new String[getColumnCount(sheet)];
            Iterator<Row> itr = sheet.rowIterator();
            while (itr.hasNext()) {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = objDefaultFormat.formatCellValue(cell);
                    if (!isCellFound) {
                        if (cellValue.equalsIgnoreCase(testCaseID)) {
                            isCellFound = true;
                        }
                    } else {
                        data[i++] = cellValue;
                    }
                }
                if (isCellFound) {
                    return data;
                }
            }
        } catch (EncryptedDocumentException | IOException e) {
            logger.error(e.getMessage());
        }
        logger.error("Test Case ID is invalid");
        return null;
    }

    /**
     *
     * @param sheet Sheet Object
     * @param colNo Column Number to read. Col = 0,1,2..
     * @param textToSearch Text to look for in the specified col
     * @return Row number in which text is present
     */

    public int readOneColumnAndReturnRequiredRowNo(Sheet sheet, int colNo, String textToSearch) {
        int rowNumber = 0;
        int lastRowNo = sheet.getLastRowNum();
        try {
            Iterator<Row> itr = sheet.rowIterator();
            String data [][] = getAllDataFromSheet(sheet);
            while (itr.hasNext()) {
                Row row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = objDefaultFormat.formatCellValue(cell);
                    if(cellValue.equals(textToSearch)) {
                        return rowNumber;
                    } else {
                        rowNumber++;
                        if(rowNumber == lastRowNo) {
                            throw new IllegalArgumentException("Text Entered for Search not found");
                        }
                    }
                }
            }
        } catch (EncryptedDocumentException e) {
            throw new IllegalArgumentException("Text Entered for Search not found");
        }
        return rowNumber;
    }

    public synchronized static String[][] getAllDataFromSheet(File fileName, String sheetName, Boolean skipFirstRow)  {
        String[][] data = null;
        int skipFirstRowCount = 0;
        objDefaultFormat = new DataFormatter();
        try {
            wb = WorkbookFactory.create(fileName);
            Sheet sheet = wb.getSheet(sheetName);
            logger.debug("Row count " + (sheet.getLastRowNum() + 1));
            data = new String[sheet.getLastRowNum() + 1][getColumnCount(sheet)];
            Iterator<Row> itr = sheet.rowIterator();
            int rowNo = 0, colNo = 0;
            String cellValue;
            Row row;
            while (itr.hasNext()) {
                row = itr.next();
                if(skipFirstRow && (skipFirstRowCount == 0)) {
                    row = itr.next();
                    data = new String[sheet.getLastRowNum()][getColumnCount(sheet)];
                    skipFirstRowCount++;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cellValue = objDefaultFormat.formatCellValue(cell);
                    data[rowNo][colNo] = cellValue;
                    colNo++;
                }
                rowNo++;// row increment
                colNo = 0;
            }
            wb.close();
        } catch (EncryptedDocumentException | IOException e) {
            logger.error(e.getMessage());
        }
        return data;

    }

    public String[][] getAllDataFromSheet(Sheet sheet) {
        String[][] data = null;

        objDefaultFormat = new DataFormatter();
        try {
            logger.debug("Row count " + (sheet.getLastRowNum() + 1));
            data = new String[sheet.getLastRowNum() + 1][getColumnCount(sheet)];
            Iterator<Row> itr = sheet.rowIterator();
            int rowNo = 0, colNo = 0;
            String cellValue;
            while (itr.hasNext()) {
                Row row = itr.next();
                // row = itr.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cellValue = objDefaultFormat.formatCellValue(cell);
                    data[rowNo][colNo] = cellValue;
                    colNo++;
                }
                rowNo++;// row increment
                colNo = 0;
            }
        } catch (EncryptedDocumentException e) {
            logger.error(e.getMessage());
        }
        return data;

    }


}
