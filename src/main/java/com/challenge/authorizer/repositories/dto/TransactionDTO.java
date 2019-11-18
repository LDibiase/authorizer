package com.challenge.authorizer.repositories.dto;

import com.challenge.authorizer.core.entities.transaction.TransactionInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDTO {

	@JsonProperty("transaction")
	private TransactionInfo transactionInfo;

	public TransactionInfo getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(TransactionInfo transactionInfo) {
		this.transactionInfo = transactionInfo;
	}
}
