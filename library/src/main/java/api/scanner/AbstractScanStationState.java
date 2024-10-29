package api.scanner;

public abstract class AbstractScanStationState {
    @Override
    public String toString() {
        return "state: " + getClass().getSimpleName();
    }
}
