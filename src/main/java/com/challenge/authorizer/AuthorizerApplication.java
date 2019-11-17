package com.challenge.authorizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class AuthorizerApplication implements Runnable {
	private static String command;

	public static void main(String[] args) {
		SpringApplication.run(AuthorizerApplication.class, args);

		AuthorizerApplication reader = new AuthorizerApplication();
		Thread thread = new Thread(reader);
		thread.start();
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			command = scanner.nextLine();
			System.out.println("Input: " + command);
		}
	}
}
