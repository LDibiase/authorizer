package com.challenge.authorizer;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.enums.Operations;
import com.challenge.authorizer.core.usecases.CreateAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		while (true) {
			command = scanner.nextLine();

			if (Operations.ACCOUNT.name().equalsIgnoreCase(command)) {
				account = createAccount.execute(true, 100, accountExist, account);
				accountExist = true;

				ObjectMapper objectMapper = new ObjectMapper();
				try {
					JSONObject json = new JSONObject(objectMapper.writeValueAsString(account));
					this.showResult(json);
				} catch (JSONException | JsonProcessingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void showResult(JSONObject json) {
		System.out.println(json);
	}
}
