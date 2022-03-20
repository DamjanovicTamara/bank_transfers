package com.ixaris.interview.transfers;

import com.ixaris.interview.transfers.dto.TransactionDTO;
import com.ixaris.interview.transfers.service.AccountService;
import com.ixaris.interview.transfers.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


/**
 * <p>
 * 	A Command-line application to parse and process a transfers file and provide the business requirements, namely:
 * 	<ul>
 * 	    <li>1. Print the final balances on all bank accounts</li>
 * 	    <li>2. Print the bank account with the highest balance</li>
 * 	    <li>3. Print the most frequently used source bank account</li>
 * 	</ul>
 * </p>
 */
@SpringBootApplication
@Log4j2
public class  TransfersApplication implements CommandLineRunner {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(TransfersApplication.class, args);
	}

	@Override
	public void run(final String... args) throws IOException,URISyntaxException {
		// Below is some sample code to get you started. Good luck :)
		final URL file = getClass().getClassLoader().getResource("transfers.txt");
		Files.readAllLines(Path.of(file.toURI())).forEach(log::info);

		//Print formatted data from file using Apache CSV reader
		transactionService.printCSVData();

		accountService.getInitialAccountsFromTransaction().forEach(
				a->log.info(a.toString())
		);
		transactionService.writeResultToCSV("finalCSV.txt");
		log.info("Bank account with highest balance:" + accountService.bankAccountWithHighestBalance());
		log.info("Most used account in transaction is:" + accountService.mostUsedAccountInTransaction());


	}

}
