package api.library;

import domain.core.Branch;
import persistence.BranchStore;

import java.util.List;

public class BranchService {
    private final BranchStore store = new BranchStore();

    public List<Branch> allBranches() {
        return store.getAll();
    }

    public Branch find(String scanCode) {
        return store.findByScanCode(scanCode);
    }

    public String add(String name) {
        return save(new Branch(name));
    }

    public String add(String name, String scanCode) {
        if (!scanCode.startsWith("b")) throw new InvalidBranchCodeException();
        return save(new Branch(scanCode, name));
    }

    private String save(Branch branch) {
        if (store.findByScanCode(branch.getScanCode()) != null)
            throw new DuplicateBranchCodeException();
        store.save(branch);
        return branch.getScanCode();
    }
}