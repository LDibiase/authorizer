package com.challenge.authorizer;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.usecases.CreateAccount;
import com.challenge.authorizer.repositories.dto.AccountDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class AuthorizerApplication implements Runnable {
	private static String command;
	private static Boolean accountExist = false;
	private static Account account = null;

	private static CreateAccount createAccount = null;

	public AuthorizerApplication(final CreateAccount createAccount) {
		this.createAccount = createAccount;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthorizerApplication.class, args);

		AuthorizerApplication reader = new AuthorizerApplication(createAccount);
		Thread thread = new Thread(reader);
		thread.start();
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		ObjectMapper objectMapper = new ObjectMapper();

		while (true) {
			command = scanner.nextLine();

			AccountDTO accountDTO = this.getAccountOperation(command, objectMapper);

			if (accountDTO != null) {
				account = createAccount.execute(accountDTO, accountExist, account);
				accountExist = true;

				try {
					JSONObject json = new JSONObject(objectMapper.writeValueAsString(account));
					this.showResult(json);
				} catch (JSONException | JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private AccountDTO getAccountOperation(String command, ObjectMapper objectMapper) {
		AccountDTO accountDTO = null;
		try {
			accountDTO = objectMapper.readValue(command, AccountDTO.class);
		} catch (IOException e) {
			return null;
		}
		return accountDTO;
	}

	private void showResult(JSONObject json) {
		System.out.println(json);
	}
}
