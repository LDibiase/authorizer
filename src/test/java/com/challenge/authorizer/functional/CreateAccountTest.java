package com.challenge.authorizer.functional;

import com.challenge.authorizer.core.entities.account.Account;
import com.challenge.authorizer.core.entities.account.AccountStatus;
import com.challenge.authorizer.core.entities.enums.Violation;
import com.challenge.authorizer.core.usecases.CreateAccount;
import com.challenge.authorizer.core.usecases.validates.ValidateAccount;
import com.challenge.authorizer.repositories.dto.AccountDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class CreateAccountTest {
	private CreateAccount createAccount;
	private ValidateAccount validateAccount;

	@Before
	public void before() {
		validateAccount = new ValidateAccount();
		createAccount = new CreateAccount(validateAccount);
	}

	@Test
	public void create_Account_ok() {

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountStatus(AccountStatus.newBuilder()
				.withActiveCard(true)
				.withAvailableLimit(1000)
				.build());

		assertThatCode(() -> this.createAccount.execute(accountDTO, false, null))
				.doesNotThrowAnyException();

		// Assert

		Account result = this.createAccount.execute(accountDTO, false, null);
		assertEquals(new Integer(1000), result.getAccountStatus().getAvailableLimit());
		assertEquals(true, result.getAccountStatus().getActiveCard());
		assertEquals(0, result.getTransactions().size());
		assertEquals(0, result.getViolations().size());

	}

	@Test
	public void create_Account_violation() {

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountStatus(AccountStatus.newBuilder()
				.withActiveCard(true)
				.withAvailableLimit(1000)
				.build());

		Account account = Account.newBuilder()
				.withTransactions(new ArrayList<>())
				.withViolations(new ArrayList<>())
				.withAccountStatus(AccountStatus.newBuilder()
						.withActiveCard(true)
						.withAvailableLimit(1000)
						.build())
				.build();

		assertThatCode(() -> this.createAccount.execute(accountDTO, true, account))
				.doesNotThrowAnyException();

		// Assert

		Account result = this.createAccount.execute(accountDTO, true, account);
		assertEquals(1, result.getViolations().size());
		assertTrue(result.getViolations().contains(Violation.ACCOUNT_ALREADY_INITIALIZED));
	}
}
