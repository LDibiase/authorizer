package com.challenge.authorizer.unit;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.account.AccountStatus;
import com.challenge.authorizer.core.entities.account.Transaction;
import com.challenge.authorizer.core.entities.enums.Violation;
import com.challenge.authorizer.core.entities.transaction.TransactionInfo;
import com.challenge.authorizer.core.usecases.validates.ValidateTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class ValidateTransactionTest {
	private ValidateTransaction validateTransaction;

	@Before
	public void before() {
		validateTransaction = new ValidateTransaction();
	}

	@Test
	public void validate_transaction_ok() {
		Account account = Account.newBuilder()
				.withTransactions(new ArrayList<>())
				.withViolations(new ArrayList<>())
				.withAccountStatus(AccountStatus.newBuilder()
						.withActiveCard(true)
						.withAvailableLimit(100)
						.build())
				.build();

		Transaction transaction = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 1")
						.build())
				.build();


		assertThatCode(() -> this.validateTransaction.execute(account, transaction))
				.doesNotThrowAnyException();

		// Assert

		List<Violation> result = this.validateTransaction.execute(account, transaction);
		assertEquals(0, result.size());
	}

	@Test
	public void validate_transaction_not_active_card() {
		Account account = Account.newBuilder()
				.withTransactions(new ArrayList<>())
				.withViolations(new ArrayList<>())
				.withAccountStatus(AccountStatus.newBuilder()
						.withActiveCard(false)
						.withAvailableLimit(100)
						.build())
				.build();

		Transaction transaction = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 1")
						.build())
				.build();


		assertThatCode(() -> this.validateTransaction.execute(account, transaction))
				.doesNotThrowAnyException();

		// Assert

		List<Violation> result = this.validateTransaction.execute(account, transaction);
		assertTrue(result.contains(Violation.CARD_NOT_ACTIVE));
	}

	@Test
	public void validate_transaction_not_limit() {
		Account account = Account.newBuilder()
				.withTransactions(new ArrayList<>())
				.withViolations(new ArrayList<>())
				.withAccountStatus(AccountStatus.newBuilder()
						.withActiveCard(true)
						.withAvailableLimit(0)
						.build())
				.build();

		Transaction transaction = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 1")
						.build())
				.build();


		assertThatCode(() -> this.validateTransaction.execute(account, transaction))
				.doesNotThrowAnyException();

		// Assert

		List<Violation> result = this.validateTransaction.execute(account, transaction);
		assertTrue(result.contains(Violation.INSUFFICIENT_LIMIT));
	}

	@Test
	public void validate_transaction_high_frequency() {
		Account account = Account.newBuilder()
				.withTransactions(new ArrayList<>())
				.withViolations(new ArrayList<>())
				.withAccountStatus(AccountStatus.newBuilder()
						.withActiveCard(true)
						.withAvailableLimit(1000)
						.build())
				.build();

		Transaction transaction1 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 1")
						.build())
				.build();

		Transaction transaction2 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(10)
						.withMerchant("Test 2")
						.build())
				.build();

		Transaction transaction3 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(5)
						.withMerchant("Test 3")
						.build())
				.build();

		Transaction transaction4 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(15)
						.withMerchant("Test 4")
						.build())
				.build();

		Transaction transaction5 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 5")
						.build())
				.build();

		account.setTransactions(Arrays.asList(transaction1, transaction2, transaction3, transaction4));

		assertThatCode(() -> this.validateTransaction.execute(account, transaction5))
				.doesNotThrowAnyException();

		// Assert

		List<Violation> result = this.validateTransaction.execute(account, transaction5);
		assertTrue(result.contains(Violation.HIGH_FREQUENCY_SMALL_INTERVAL));
	}

	@Test
	public void validate_transaction_double() {
		Account account = Account.newBuilder()
				.withTransactions(new ArrayList<>())
				.withViolations(new ArrayList<>())
				.withAccountStatus(AccountStatus.newBuilder()
						.withActiveCard(true)
						.withAvailableLimit(1000)
						.build())
				.build();

		Transaction transaction1 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 1")
						.build())
				.build();

		Transaction transaction2 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 1")
						.build())
				.build();

		Transaction transaction3 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 1")
						.build())
				.build();

		Transaction transaction4 = Transaction.newBuilder()
				.withViolations(new ArrayList<>())
				.withTransactionInfo(TransactionInfo.newBuilder()
						.withTime(LocalDateTime.now())
						.withAmount(20)
						.withMerchant("Test 1")
						.build())
				.build();

		account.setTransactions(Arrays.asList(transaction1, transaction2, transaction3, transaction4));

		assertThatCode(() -> this.validateTransaction.execute(account, transaction4))
				.doesNotThrowAnyException();

		// Assert

		List<Violation> result = this.validateTransaction.execute(account, transaction4);
		assertTrue(result.contains(Violation.DOUBLED_TRANSACTION));
	}
}
