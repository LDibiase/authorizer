package com.challenge.authorizer.core.entities.account;

import com.challenge.authorizer.core.entities.Operation;

public class Account extends Operation {
	private AccountStatus accountStatus;

	private Account(Builder builder) {
		setAccountStatus(builder.accountStatus);
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

	public static final class Builder {
		private AccountStatus accountStatus;

		private Builder() {
		}

		public Builder withAccountStatus(AccountStatus val) {
			accountStatus = val;
			return this;
		}

		public Account build() {
			return new Account(this);
		}
	}
}
