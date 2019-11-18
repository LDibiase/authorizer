package com.challenge.authorizer.core.usecases.validates;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.account.Transaction;
import com.challenge.authorizer.core.entities.enums.Violation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidateTransaction {
	private final int TIME_INTERVAL_VALIDATION = 2;
	private final int FREQUENCY_AMOUNT_VALIDATION = 3;
	private final int DOUBLE_TRANSACTION_AMOUNT_VALIDATION = 2;

	public List<Violation> execute(Account account, Transaction transaction) {
		List<Violation> violations = new ArrayList<>();

		this.validateLimit(account, transaction, violations);
		this.validateActive(account, violations);
		this.validateFrequency(account, transaction, violations);
		this.validateDoubleTransaction(account, transaction, violations);

		return violations;
	}

	private void validateDoubleTransaction(Account account, Transaction transaction, List<Violation> violations) {
		List<Transaction> transactions = account.getTransactions();
		transactions.add(transaction);

		List<Transaction> result = transactions.stream().filter(trans -> (
				trans.getTransactionInfo().getAmount().equals(transaction.getTransactionInfo().getAmount()) &&
				trans.getTransactionInfo().getMerchant().equalsIgnoreCase(transaction.getTransactionInfo().getMerchant())) &&
				this.getDiffMinutes(transaction.getTransactionInfo().getTime(), trans.getTransactionInfo().getTime()) < TIME_INTERVAL_VALIDATION)
				.collect(Collectors.toList());

		if (result.size() > DOUBLE_TRANSACTION_AMOUNT_VALIDATION) {
			violations.add(Violation.DOUBLED_TRANSACTION);
		}
	}

	private void validateFrequency(Account account, Transaction transaction, List<Violation> violations) {
		List<Transaction> transactions = account.getTransactions();
		transactions.add(transaction);

		List<Transaction> result = transactions.stream().filter(trans ->
				this.getDiffMinutes(transaction.getTransactionInfo().getTime(),
						trans.getTransactionInfo().getTime()) < TIME_INTERVAL_VALIDATION)
				.collect(Collectors.toList());

		if (result.size() > FREQUENCY_AMOUNT_VALIDATION) {
			violations.add(Violation.HIGH_FREQUENCY_SMALL_INTERVAL);
		}
	}

	private void validateLimit(Account account, Transaction transaction, List<Violation> violations) {
		if (account.getAccountStatus().getAvailableLimit() < transaction.getTransactionInfo().getAmount()) {
			violations.add(Violation.INSUFFICIENT_LIMIT);
		}
	}

	private void validateActive(Account account, List<Violation> violations) {
		if(!account.getAccountStatus().getActiveCard()) {
			violations.add(Violation.CARD_NOT_ACTIVE);
		}
	}

	private long getDiffMinutes(LocalDateTime firstTime, LocalDateTime secondTime) {
		return ChronoUnit.MINUTES.between(firstTime, secondTime);
	}
}
