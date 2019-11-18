package com.challenge.authorizer.core.entities.account;

import com.challenge.authorizer.core.entities.Operation;
import com.challenge.authorizer.core.entities.enums.Violation;
import com.challenge.authorizer.core.entities.transaction.TransactionInfo;

import java.util.List;

public class Transaction extends Operation {
	private TransactionInfo transactionInfo;

	private Transaction(Builder builder) {
		setViolations(builder.violations);
		setTransactionInfo(builder.transactionInfo);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public TransactionInfo getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(TransactionInfo transactionInfo) {
		this.transactionInfo = transactionInfo;
	}


	public static final class Builder {
		private List<Violation> violations;
		private TransactionInfo transactionInfo;

		private Builder() {
		}

		public Builder withViolations(List<Violation> val) {
			violations = val;
			return this;
		}

		public Builder withTransactionInfo(TransactionInfo val) {
			transactionInfo = val;
			return this;
		}

		public Transaction build() {
			return new Transaction(this);
		}
	}
}
