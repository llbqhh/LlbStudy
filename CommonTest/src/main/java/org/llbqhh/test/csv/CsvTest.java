package org.llbqhh.test.csv;

import com.opencsv.CSVReader;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CsvTest {
    @Test
    public void testCsvRead() throws IOException {
        String fileName = "/home/lilibiao/Desktop/test1.csv";
        File file = new File(fileName);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
        CSVReader csvReader = new CSVReader(isr, ',', '\"', '\0');
        String[] line = csvReader.readNext();
        while (line != null) {
            System.out.println(Arrays.asList(line));
            line = csvReader.readNext();
        }
    }
}
