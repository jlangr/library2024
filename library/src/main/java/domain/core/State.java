package domain.core;

public class State {
    public State(String name, String capital) {
        this.name = name;
        this.capital = capital;
    }

    @Override
    public String toString() {
        return name + " (" + capital + ")";
    }

    public final String name;
    public final String capital;
}
