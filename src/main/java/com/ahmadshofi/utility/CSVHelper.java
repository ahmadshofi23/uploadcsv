package com.ahmadshofi.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.ahmadshofi.entity.Book;

public class CSVHelper {
    
    private static final String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    
    public static List<Book> csvToBooks(InputStream is) {
        try{
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            
            List<Book> books = new ArrayList<Book>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Book book = new Book();
                book.setTitle(csvRecord.get("title"));
                book.setId(Long.parseLong(csvRecord.get("id")));
                book.setDescription(csvRecord.get("description"));
                book.setPrice(Double.parseDouble(csvRecord.get("price")));

                books.add(book);
            }
            csvParser.close();
            return books;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
