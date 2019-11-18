package com.challenge.authorizer.core.usecases;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.account.AccountStatus;
import com.challenge.authorizer.core.entities.enums.Violation;
import com.challenge.authorizer.core.usecases.validates.ValidateAccount;
import com.challenge.authorizer.repositories.dto.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateAccount {
	private ValidateAccount validateAccount;

	public CreateAccount(ValidateAccount validateAccount) {
		this.validateAccount = validateAccount;
	}

	public Account execute(AccountDTO accountDTO, Boolean exists, Account account) {
		List<Violation> violations = this.validateAccount.execute(exists);

		if (violations.isEmpty()) {
			account = Account.newBuilder()
					.withAccountStatus(AccountStatus.newBuilder()
							.withActiveCard(accountDTO.getAccountStatus().getActiveCard())
							.withAvailableLimit(accountDTO.getAccountStatus().getAvailableLimit())
							.build())
					.withViolations(new ArrayList<>())
					.withTransactions(new ArrayList<>())
					.build();
		}
		this.setViolations(account, violations);
		return account;
	}

	private void setViolations(Account account, List<Violation> violations) {
		if (!account.getViolations().contains(Violation.ACCOUNT_ALREADY_INITIALIZED)) {
			account.setViolations(violations);
		}
	}
}
