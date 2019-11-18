package com.challenge.authorizer.core.entities.account;

public class AccountStatus {
	private Boolean activeCard;
	private Integer availableLimit;

	private AccountStatus(Builder builder) {
		setActiveCard(builder.activeCard);
		setAvailableLimit(builder.availableLimit);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public Boolean getActiveCard() {
		return activeCard;
	}

	public void setActiveCard(Boolean activeCard) {
		this.activeCard = activeCard;
	}

	public Integer getAvailableLimit() {
		return availableLimit;
	}

	public void setAvailableLimit(Integer availableLimit) {
		this.availableLimit = availableLimit;
	}


	public static final class Builder {
		private Boolean activeCard;
		private Integer availableLimit;

		private Builder() {
		}

		public Builder withActiveCard(Boolean val) {
			activeCard = val;
			return this;
		}

		public Builder withAvailableLimit(Integer val) {
			availableLimit = val;
			return this;
		}

		public AccountStatus build() {
			return new AccountStatus(this);
		}
	}
}
