package de.telekom.excelsior;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;

@RestController
public class DataInputController {
    @PostMapping(produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE},consumes = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    InputStreamResource convert(@RequestBody String inputString) {
        System.out.println("Input: "+inputString);
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
        }
        byte[] buffer = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
        return new InputStreamResource(inputStream);
    }
}
