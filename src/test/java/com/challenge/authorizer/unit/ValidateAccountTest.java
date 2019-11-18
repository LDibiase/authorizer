package com.challenge.authorizer.unit;

import com.challenge.authorizer.core.entities.enums.Violation;
import com.challenge.authorizer.core.usecases.validates.ValidateAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class ValidateAccountTest {
	private ValidateAccount validateAccount;

	@Before
	public void before() {
		validateAccount = new ValidateAccount();
	}

	@Test
	public void validate_Account_ok() {

		assertThatCode(() -> this.validateAccount.execute(false))
				.doesNotThrowAnyException();

		// Assert

		List<Violation> result = this.validateAccount.execute(false);
		assertEquals(0, result.size());
	}

	@Test
	public void validate_Account_return_violation() {
		assertThatCode(() -> this.validateAccount.execute(true))
				.doesNotThrowAnyException();

		List<Violation> result = this.validateAccount.execute(true);
		assertTrue(result.contains(Violation.ACCOUNT_ALREADY_INITIALIZED));
	}
}
