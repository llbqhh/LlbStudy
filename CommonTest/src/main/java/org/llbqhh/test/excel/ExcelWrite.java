package org.llbqhh.test.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelWrite {
// InputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.xls");

    public static void main(String[] args) throws IOException {
        write2Excel(new File("C:\\Users\\Administrator\\Desktop\\test.xls"));
    }

    /**
     * @param targetFile
     * @throws IOException
     */
    private static void write2Excel(File targetFile) throws IOException {
        Workbook wb = null;
        String fileName = targetFile.getAbsolutePath();
        if (fileName.endsWith(".xls")) {
            wb = new HSSFWorkbook();
        } else if (fileName.endsWith(".xlsx")) {
            wb = new XSSFWorkbook();
        } else {
            System.out.println("aaaa");
        }

        Sheet sheet1 = (Sheet) wb.createSheet("sheet1");

        for (int i = 0; i < 65535; i++) {
            Row row = (Row) sheet1.createRow(i);
            for (int j = 0; j < 8; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue("55555" + j);
            }
            if (i % 10000 == 0) {
                System.out.println(i);
            }
        }

        OutputStream out = new FileOutputStream(targetFile);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        wb.write(bout);
        bout.close();
    }
}
