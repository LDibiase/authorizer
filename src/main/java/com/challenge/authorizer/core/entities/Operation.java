package com.challenge.authorizer.core.entities;

import com.challenge.authorizer.core.entities.enums.Violation;

import java.util.List;

public abstract class Operation {
	private List<Violation> violations;

	public List<Violation> getViolations() {
		return violations;
	}

	public void setViolations(List<Violation> violations) {
		this.violations = violations;
	}
}
