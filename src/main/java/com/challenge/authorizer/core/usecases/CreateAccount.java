package com.challenge.authorizer.core.usecases;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.account.AccountStatus;
import com.challenge.authorizer.core.entities.enums.Violations;
import com.challenge.authorizer.core.usecases.validates.ValidateAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateAccount {
	private ValidateAccount validateAccount;

	public CreateAccount(ValidateAccount validateAccount) {
		this.validateAccount = validateAccount;
	}

	public Account execute(Boolean active, Integer credit, Boolean exists, Account account) {
		List<Violations> violations = this.validateAccount.execute(exists);

		if (violations.size() == 0) {
			account = Account.newBuilder()
					.withAccountStatus(AccountStatus.newBuilder()
							.withActiveCard(active)
							.withAvailableLimit(credit)
							.build())
					.build();
		}
		this.setViolations(account, violations);
		return account;
	}

	private void setViolations(Account account, List<Violations> violations) {
		if (!account.getViolations().contains(Violations.ACCOUNT_ALREADY_INITIALIZED)) {
			account.setViolations(violations);
		}
	}
}
