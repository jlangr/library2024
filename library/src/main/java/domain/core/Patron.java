package domain.core;

import java.util.Objects;

public class Patron {
    private final String name;
    private String id;
    private final HoldingMap holdings = new HoldingMap();
    private int balance = 0;

    public Patron(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public Patron(String name) {
        this("", name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + getId() + "] " + getName();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if ((object.getClass() != this.getClass()))
            return false;
        var that = (Patron) object;
        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public HoldingMap holdingMap() {
        return holdings;
    }

    public void add(Holding holding) {
        holdings.add(holding);
    }

    public void remove(Holding holding) {
        holdings.remove(holding);
    }

    public int fineBalance() {
        return balance;
    }

    public void addFine(int a) {
        balance += a;
    }

    public void remit(int a) {
        balance -= a;
    }
}
