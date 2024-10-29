package api.library;

import domain.core.Branch;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

// TODO JUnit 5

public class BranchServiceTest {
    private BranchService service;

    @Before
    public void initialize() {
        service = new BranchService();
        LibraryData.deleteAll();
    }

    @Test
    public void findsByScanCode() {
        service.add("name", "b2");

        Branch branch = service.find("b2");

        assertEquals("name", branch.getName());
    }

    @Test(expected = DuplicateBranchCodeException.class)
    public void rejectsDuplicateScanCode() {
        service.add("", "b559");
        service.add("", "b559");
    }

    @Test(expected = InvalidBranchCodeException.class)
    public void rejectsScanCodeNotStartingWithB() {
        service.add("", "c2234");
    }

    @Test
    public void answersGeneratedId() {
       assertTrue(service.add("").startsWith("b"));
    }

    @Test
    public void findsBranchMatchingScanCode() {
        var scanCode = service.add("a branch");

        var branch = service.find(scanCode);

        assertEquals("a branch", branch.getName());
        assertEquals(scanCode, branch.getScanCode());
    }

    @Test
    public void returnsListOfAllPersistedBranches() {
        var eastScanCode = service.add("e");
        var westScanCode = service.add("w");

        var all = service.allBranches();

        var scanCodes = all.stream().map(Branch::getScanCode).toList();
        assertEquals(List.of(eastScanCode, westScanCode), scanCodes);
    }
}
