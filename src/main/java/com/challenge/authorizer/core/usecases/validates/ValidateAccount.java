package com.challenge.authorizer.core.usecases.validates;

import com.challenge.authorizer.core.entities.enums.Violations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidateAccount {

	public List<Violations> execute(Boolean exist) {
		return this.validateExistance(exist);
	}

	private List<Violations> validateExistance(Boolean exist) {
		List<Violations> violations = new ArrayList<>();

		if(exist) {
			violations.add(Violations.ACCOUNT_ALREADY_INITIALIZED);
		}

		return violations;
	}
}
