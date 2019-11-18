package com.challenge.authorizer.core.usecases;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.account.Transaction;
import com.challenge.authorizer.core.entities.enums.Violation;
import com.challenge.authorizer.core.usecases.validates.ValidateTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessTransaction {
	private ValidateTransaction validateTransaction;

	public ProcessTransaction(ValidateTransaction validateTransaction) {
		this.validateTransaction = validateTransaction;
	}

	public Account execute(Account account, Transaction transaction) {
		List<Violation> transactionViolations = this.validateTransaction.execute(account, transaction);

		if (transactionViolations.isEmpty()) {
			this.executeTransaction(account, transaction);
			this.removeViolations(account);
		}

		account.setViolations(transactionViolations);
		return account;
	}

	private void executeTransaction(Account account, Transaction transaction) {
		account.getTransactions().add(transaction);
		account.getAccountStatus()
				.setAvailableLimit(account.getAccountStatus().getAvailableLimit() - transaction.getTransactionInfo().getAmount());
	}

	private void removeViolations(Account account) {
		account.getViolations().clear();
	}

}
