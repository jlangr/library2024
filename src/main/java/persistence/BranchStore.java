package persistence;

import domain.core.Branch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchStore {
    private static final Map<String, Branch> branches = new HashMap<>();
    private static int idIndex = 0;

    private static int incrementIdIndex() {
        return ++idIndex;
    }

    public static boolean isEmpty() {
        return branches.isEmpty();
    }

    public void save(Branch branch) {
        if (branch.getScanCode().isEmpty())
            branch.setScanCode("b" + incrementIdIndex());
        branches.put(branch.getName(), copy(branch));
    }

    public Branch findByName(String name) {
        return branches.get(name);
    }

    public Branch findByScanCode(String scanCode) {
        if (scanCode.equals(Branch.CHECKED_OUT.getScanCode()))
            return Branch.CHECKED_OUT;

        return branches.values().stream()
           .filter(branch -> branch.getScanCode().equals(scanCode))
           .findFirst()
           .orElse(null);
    }

    private Branch copy(Branch branch) {
        var newBranch = new Branch(branch.getName());
        newBranch.setScanCode(branch.getScanCode());
        return newBranch;
    }

    public List<Branch> getAll() {
        return new ArrayList<>(branches.values());
    }

    public static void deleteAll() {
        branches.clear();
    }
}
