package controller;

import domain.core.Branch;

public record BranchRequest(String name, String id) {
    public BranchRequest(Branch branch) {
        this(branch.getName(), branch.getScanCode());
    }

    public String getName() {
        return name();
    }
}