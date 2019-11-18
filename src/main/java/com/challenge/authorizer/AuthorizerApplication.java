package com.challenge.authorizer;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.account.Transaction;
import com.challenge.authorizer.core.entities.transaction.TransactionInfo;
import com.challenge.authorizer.core.usecases.CreateAccount;
import com.challenge.authorizer.core.usecases.ProcessTransaction;
import com.challenge.authorizer.repositories.dto.AccountDTO;
import com.challenge.authorizer.repositories.dto.TransactionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class AuthorizerApplication {
	private static String command;
	private static Boolean accountExist = false;
	private static Account account = null;

	private static CreateAccount createAccount = null;
	private static ProcessTransaction processTransaction = null;

	public AuthorizerApplication(final CreateAccount createAccount, final ProcessTransaction processTransaction) {
		this.createAccount = createAccount;
		this.processTransaction = processTransaction;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthorizerApplication.class, args);

		Scanner scanner = new Scanner(System.in);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		while (scanner.hasNext()) {
			command = scanner.nextLine();
			AccountDTO accountDTO = getAccountOperation(command, objectMapper);
			TransactionDTO transactionDTO = getTransactionOperation(command, objectMapper);

			accountFlow(objectMapper, accountDTO);
			transactionFlow(objectMapper, transactionDTO);
		}
	}

	private static void transactionFlow(ObjectMapper objectMapper, TransactionDTO transactionDTO) {
		if (transactionDTO != null && transactionDTO.getTransactionInfo() != null) {
			Transaction transaction = Transaction.newBuilder()
					.withTransactionInfo(TransactionInfo.newBuilder()
							.withAmount(transactionDTO.getTransactionInfo().getAmount())
							.withMerchant(transactionDTO.getTransactionInfo().getMerchant())
							.withTime(transactionDTO.getTransactionInfo().getTime())
							.build())
					.withViolations(new ArrayList<>())
					.build();

			account = processTransaction.execute(account, transaction);

			showResult(account, objectMapper);
		}
	}

	private static void accountFlow(ObjectMapper objectMapper, AccountDTO accountDTO) {
		if (accountDTO != null && accountDTO.getAccountStatus() != null) {
			account = createAccount.execute(accountDTO, accountExist, account);
			accountExist = true;

			showResult(account, objectMapper);
		}
	}

	private static TransactionDTO getTransactionOperation(String command, ObjectMapper objectMapper) {
		TransactionDTO transactionDTO = null;

		try {
			transactionDTO = objectMapper.readValue(command, TransactionDTO.class);
		} catch (IOException e) {
			return null;
		}
		return transactionDTO;
	}

	private static AccountDTO getAccountOperation(String command, ObjectMapper objectMapper) {
		AccountDTO accountDTO = null;
		try {
			accountDTO = objectMapper.readValue(command, AccountDTO.class);
		} catch (IOException e) {
			return null;
		}
		return accountDTO;
	}

	private static void showResult(Account account, ObjectMapper objectMapper) {
		JSONObject json = null;
		try {
			json = new JSONObject(objectMapper.writeValueAsString(account));
		} catch (JSONException | JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println(json);
	}
}
