package com.challenge.authorizer.core.entities.transaction;

import com.challenge.authorizer.repositories.utils.DateHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

public class TransactionInfo {
	private String merchant;
	private Integer amount;

	@JsonDeserialize(using = DateHandler.class)
	private LocalDateTime time;

	public TransactionInfo() {

	}

	private TransactionInfo(Builder builder) {
		setMerchant(builder.merchant);
		setAmount(builder.amount);
		setTime(builder.time);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}


	public static final class Builder {
		private String merchant;
		private Integer amount;
		private LocalDateTime time;

		private Builder() {
		}

		public Builder withMerchant(String val) {
			merchant = val;
			return this;
		}

		public Builder withAmount(Integer val) {
			amount = val;
			return this;
		}

		public Builder withTime(LocalDateTime val) {
			time = val;
			return this;
		}

		public TransactionInfo build() {
			return new TransactionInfo(this);
		}
	}
}
