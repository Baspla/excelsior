package de.telekom.excelsior;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
public class DataInputController {
    @PostMapping(produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE},consumes = {MediaType.TEXT_PLAIN_VALUE}) // Octet Stream, da wir einen byte Array zurück geben. Plain Text damit wir kein URL-Encoded Zeug bekommen.
    @ResponseBody
    InputStreamResource convert(@RequestBody String inputString) {
        System.out.println("Input: "+inputString); //TODO Gescheites Logging

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(inputString);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        byte[] buffer = outputStream.toByteArray(); //Excel Dokument als byte Array
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
        return new InputStreamResource(inputStream);
    }
}
