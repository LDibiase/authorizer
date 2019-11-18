package com.challenge.authorizer.repositories.dto;

import com.challenge.authorizer.core.entities.account.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDTO {

	@JsonProperty("account")
	private AccountStatus accountStatus;

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
}
