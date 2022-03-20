package com.ixaris.interview.transfers.service;


import com.ixaris.interview.transfers.dto.AccountDTO;
import com.ixaris.interview.transfers.dto.TransactionDTO;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;
    private List<AccountDTO> accountDTOList;

    @BeforeEach
    void setUp() {
         accountDTOList= new ArrayList<>();
    }

    //Test in initialAccount if filter("0") is true
    @Test
    void testFilterEqualsZeroWhenInitializingAccounts() throws IOException {
        List<TransactionDTO> initialAccounts = transactionService.getTransactionStream()
                .filter(t -> t.getSourceAccount().equals("0")).collect(Collectors.toList());

        initialAccounts.forEach(i -> assertEquals("0", i.getSourceAccount()));
    }
/*
    @Test
    void createsCsvFile(@TempDir final Path temp) throws IOException {

        Path csv = temp.resolve("test.txt");
        accountDTOList.add(new AccountDTO("112233", BigDecimal.valueOf(10)));
        accountDTOList.add(new AccountDTO("223344",BigDecimal.valueOf(5)));

        Writer out = new BufferedWriter(new FileWriter("test.txt"));
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.Builder.create().setCommentMarker('#').build());
           accountDTOList.forEach(record-> {
               try {
                   printer.printRecord(record.getAccountNumber(),record.getBalance());
               } catch (IOException e) {
                   e.printStackTrace();
               }
           });

        assertTrue( Files.exists(csv));
    }*/
}