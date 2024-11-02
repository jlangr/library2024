package controller;

import domain.core.Patron;

public class PatronRequest {
    private String name;
    private String id;
    private int fineBalance;

    public PatronRequest(Patron patron) {
        this.id = patron.getId();
        this.name = patron.getName();
        this.fineBalance = patron.fineBalance();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PatronRequest{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", fineBalance=" + fineBalance +
                '}';
    }
}
