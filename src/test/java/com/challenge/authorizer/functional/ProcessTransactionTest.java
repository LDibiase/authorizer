package com.challenge.authorizer.functional;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.account.AccountStatus;
import com.challenge.authorizer.core.entities.account.Transaction;
import com.challenge.authorizer.core.entities.enums.Violation;
import com.challenge.authorizer.core.entities.transaction.TransactionInfo;
import com.challenge.authorizer.core.usecases.ProcessTransaction;
import com.challenge.authorizer.core.usecases.validates.ValidateTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class ProcessTransactionTest {
	private ValidateTransaction validateTransaction;
	private ProcessTransaction processTransaction;

	@Before
	public void before() {
		validateTransaction = new ValidateTransaction();
		processTransaction = new ProcessTransaction(validateTransaction);
	}

	@Test
	public void process_transaction_ok() {
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

		// Assert

		Account result = this.processTransaction.execute(account, transaction);
		assertEquals(new Integer(80), account.getAccountStatus().getAvailableLimit());
		assertEquals(0, result.getViolations().size());
		assertEquals(1, result.getTransactions().size());
		assertTrue(result.getTransactions().contains(transaction));

	}

	@Test
	public void process_transaction_violation() {
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

		// Assert

		Account result = this.processTransaction.execute(account, transaction);
		assertEquals(new Integer(100), account.getAccountStatus().getAvailableLimit());
		assertEquals(1, result.getViolations().size());
		assertEquals(0, result.getTransactions().size());
		assertTrue(result.getViolations().contains(Violation.CARD_NOT_ACTIVE));

	}
}
