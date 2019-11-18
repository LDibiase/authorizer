package com.challenge.authorizer.core.entities.account;

import com.challenge.authorizer.core.entities.Operation;
import com.challenge.authorizer.core.entities.enums.Violation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Account extends Operation {
	private AccountStatus accountStatus;
	@JsonIgnore
	private List<Transaction> transactions;

	private Account(Builder builder) {
		setViolations(builder.violations);
		setAccountStatus(builder.accountStatus);
		setTransactions(builder.transactions);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public static final class Builder {
		private List<Violation> violations;
		private AccountStatus accountStatus;
		private List<Transaction> transactions;

		private Builder() {
		}

		public Builder withViolations(List<Violation> val) {
			violations = val;
			return this;
		}

		public Builder withAccountStatus(AccountStatus val) {
			accountStatus = val;
			return this;
		}

		public Builder withTransactions(List<Transaction> val) {
			transactions = val;
			return this;
		}

		public Account build() {
			return new Account(this);
		}
	}
}
