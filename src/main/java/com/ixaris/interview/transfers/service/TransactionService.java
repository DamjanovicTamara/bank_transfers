package com.ixaris.interview.transfers.service;

import com.ixaris.interview.transfers.domain.Account;
import com.ixaris.interview.transfers.domain.Transaction;
import com.ixaris.interview.transfers.dto.TransactionDTO;
import com.ixaris.interview.transfers.repository.AccountRepository;
import com.ixaris.interview.transfers.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * Service which is reading data from CVS file, transforming it to a stream and converting to Transaction object
 */
@Service
@Slf4j
public class TransactionService {


    /**
     * Transactions CVS file
     */
    public static final String cvsFileName="transfers.txt";
    /**
     * Constant number of rows in CVS file which should be skipped
     */
    public static final long N = 3L;
    String[] inputHeaders = {"source_acct", "destination_acct", "amount", "date", "transferid"};

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    private CSVParser getParser() throws IOException {
        final URL resourceUrl = getClass().getClassLoader().getResource("transfers.txt");
        return CSVParser.parse(Objects.requireNonNull(resourceUrl), Charset.defaultCharset(),
                CSVFormat.Builder.create().setHeader(inputHeaders).setIgnoreSurroundingSpaces(true).build());
    }

    private Stream<CSVRecord> getCSVStream() throws IOException {
        return StreamSupport.stream(getParser().spliterator(), false).skip(N);
    }

    private static TransactionDTO recordToTransaction(CSVRecord record) {
        return new TransactionDTO(record.get("source_acct"),record.get("destination_acct"),
                record.get("amount"), record.get("date"), record.get("transferid"));
    }

    /**
     * @return Stream of Transaction DTO objects retrieved form a CVS file
     * @throws IOException
     */
    public Stream<TransactionDTO> getTransactionStream() throws IOException {
        return getCSVStream()
                .map(TransactionService::recordToTransaction);
    }


    /**
     * @return List of transaction collected from a transaction stream
     * @throws IOException
     */
    public List<TransactionDTO> getAllTransaction() throws IOException {

        return getTransactionStream().collect(Collectors.toList());

    }

    /**Read and log the data from the CVS stream
     * @throws IOException
     */
    public void printCSVData() throws IOException {
        getCSVStream().forEach(record ->
                log.info("{}, {}, {}, {}, {}", record.get("source_acct"), record.get("destination_acct"), record.get("amount"), record.get("date"), record.get("transferid")));

    }

    /**
     * Method used to write calculated data to a file
     * @param filename
     * @throws IOException
     */
    public void writeResultToCSV(String filename) throws IOException {
        log.info("Writing results to CVS file, first initialise accounts, setting balance to 0");
        accountService.getInitialAccountsFromTransaction().forEach(a -> log.info(a.toString()));
        try (Writer out = new BufferedWriter(new FileWriter(filename))) {
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.Builder.create().setCommentMarker('#').build());
            printer.printComment("Balances");
            accountService.calculateBalancesFromCSV()
                    .forEach(record -> {
                        try {

                            printer.printRecord(record.getAccountNumber(), record.getBalance().doubleValue());
                        } catch (final IOException e) {
                            e.printStackTrace();
                        }
                    });
            printer.printComment("Bank account with highest balance");
            printer.printRecord(accountService.bankAccountWithHighestBalance().getAccountNumber());
            printer.printComment("Frequently used bank account");
            printer.printRecord(accountService.mostUsedAccountInTransaction());
        }
    }


}
