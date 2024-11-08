package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreateExcelFile {
    public static void main(String[] args) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");
            Row headerRow = sheet.createRow(0);

            String[] headers = {"Name", "Age", "Occupation"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            String[][] data = {
                    {"John Doe", "30", "Engineer"},
                    {"Jane Smith", "25", "Teacher"},
                    {"Michael Brown", "40", "Doctor"}
            };

            for (int i = 0; i < data.length; i++) {
                Row dataRow = sheet.createRow(i + 1);

                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = dataRow.createCell(j);
                    cell.setCellValue(data[i][j]);
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream("testdata.xlsx")) {
                workbook.write(fileOut);
            }

            System.out.println("Excel file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
