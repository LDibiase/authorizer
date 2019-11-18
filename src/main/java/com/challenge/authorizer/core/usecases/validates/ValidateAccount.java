package com.challenge.authorizer.core.usecases.validates;

import com.challenge.authorizer.core.entities.enums.Violation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidateAccount {

	public List<Violation> execute(Boolean exist) {
		return this.validateExistance(exist);
	}

	private List<Violation> validateExistance(Boolean exist) {
		List<Violation> violations = new ArrayList<>();

		if(exist) {
			violations.add(Violation.ACCOUNT_ALREADY_INITIALIZED);
		}

		return violations;
	}
}
