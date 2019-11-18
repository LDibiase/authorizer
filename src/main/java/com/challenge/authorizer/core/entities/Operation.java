package com.challenge.authorizer.core.entities;

import com.challenge.authorizer.core.entities.enums.Violations;

import java.util.List;

public abstract class Operation {
	private List<Violations> violations;

	public List<Violations> getViolations() {
		return violations;
	}

	public void setViolations(List<Violations> violations) {
		this.violations = violations;
	}
}
