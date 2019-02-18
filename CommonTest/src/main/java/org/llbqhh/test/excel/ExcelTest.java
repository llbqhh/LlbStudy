package org.llbqhh.test.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ExcelTest {
// InputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\test.xls");

    public static void main(String[] args) throws IOException {
// String path = "C:\\Users\\Administrator\\Desktop\\";
// String fileName = "test11";
// String fileType = "xls";
        write2Excel(new File("C:\\Users\\Administrator\\Desktop\\test11.xls"));
        System.out.println("after write");
        readExcel(new File("C:\\Users\\Administrator\\Desktop\\test11.xls"));
        modifyExcel(new File("C:\\Users\\Administrator\\Desktop\\test11.xls"));
        System.out.println("after modify");
        readExcel(new File("C:\\Users\\Administrator\\Desktop\\test11.xls"));
    }

    /**
     * @param targetFile
     * @throws IOException
     */
    private static void write2Excel(File targetFile) throws IOException {
        //
        Workbook wb = null;
        String fileName = targetFile.getAbsolutePath();
        if (fileName.endsWith(".xls")) {
            wb = new HSSFWorkbook();
        } else if (fileName.endsWith(".xlsx")) {
            wb = new XSSFWorkbook();
        } else {
            System.out.println("");
        }
        //
        Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
        //
        for (int i = 0; i < 5; i++) {
            Row row = (Row) sheet1.createRow(i);
            //
            for (int j = 0; j < 8; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue("" + j);
            }
        }
        //
        OutputStream out = new FileOutputStream(targetFile);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        //
        wb.write(bout);
        bout.close();
    }

    /**
     * @param targetFile
     * @throws IOException
     */
    public static void modifyExcel(File targetFile) throws IOException {
        //
        InputStream in = new FileInputStream(targetFile);
        BufferedInputStream bin = new BufferedInputStream(in);
        String fileName = targetFile.getAbsolutePath();
        Workbook wb = null;
        if (fileName.endsWith(".xls")) {
            wb = new HSSFWorkbook(bin);
        } else if (fileName.endsWith(".xlsx")) {
            wb = new XSSFWorkbook(bin);
        } else {
            System.out.println("");
        }

        for (int si = 0; si < wb.getNumberOfSheets(); si++) {
            Sheet sheet1 = (Sheet) wb.getSheetAt(si);

            for (int i = 0; i < 2; i++) {
                Row row = (Row) sheet1.getRow(i);

                for (int j = 0; j < 3; j++) {
                    Cell cell = row.getCell(j);
                    cell.setCellValue("" + j);
                }
            }
            //
            OutputStream out = new FileOutputStream(targetFile);
            BufferedOutputStream bout = new BufferedOutputStream(out);
            //
            wb.write(bout);
            //
            bout.close();
        }
    }

    /**
     * @param targetFile
     * @throws IOException
     */
    public static void readExcel(File targetFile) throws IOException {
        InputStream stream = new FileInputStream(targetFile);
        BufferedInputStream bf = new BufferedInputStream(stream);
        String fileName = targetFile.getAbsolutePath();
        Workbook wb = null;
        if (fileName.endsWith(".xls")) {
            wb = new HSSFWorkbook(bf);
        } else if (fileName.endsWith(".xlsx")) {
            wb = new XSSFWorkbook(bf);
        } else {
            System.out.println("");
        }
// wb.getNumberOfSheets()
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            System.out.println("sheet:" + i);
            Sheet sheet1 = wb.getSheetAt(i);
            for (Row row : sheet1) {
                for (Cell cell : row) {
                    System.out.print(cell.getStringCellValue() + "\t");
                }
                System.out.println();
            }
        }
    }
}
