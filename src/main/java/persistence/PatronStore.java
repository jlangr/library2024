package persistence;

import domain.core.Holding;
import domain.core.Patron;

import java.util.ArrayList;
import java.util.Collection;

public class PatronStore {
    private static final Collection<Patron> patrons = new ArrayList<>();
    private static int idIndex = 0;

    public static void deleteAll() {
        patrons.clear();
    }

    private static int incrementIdIndex() {
       return ++idIndex;
    }

    public void add(Patron patron) {
        if (patron.getId().isEmpty())
            patron.setId("p" + incrementIdIndex());
        patrons.add(copy(patron));
    }

    private Patron copy(Patron patron) {
        var newPatron = new Patron(patron.getName());
        newPatron.setId(patron.getId());
        return newPatron;
    }

    public Collection<Patron> getAll() {
        return patrons;
    }

    public void addHoldingToPatron(Patron patron, Holding holding) {
        var found = find(patron.getId());
        if (found == null)
            throw new PatronNotFoundException();
        found.add(holding);
    }

    public Patron find(String id) {
        return patrons.stream()
           .filter(each -> id.equals(each.getId()))
           .findFirst()
           .orElse(null);
    }
}
