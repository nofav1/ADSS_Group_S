package Domain;

public class Constraint {
    private final Worker currWorker;
    private String shiftType;
    private String date;

    // Constructor
    public Constraint(Worker currWorker, String cType, String date) {
        this.currWorker = currWorker;
        this.shiftType = cType;
        this.date = date;
        // No shift type here, because it can be none.
    }


    // Getters
    public String getShiftType() {
        return shiftType;
    }

    public String getDate() {
        return date;
    }

    public Worker getCurrWorker() {
        return currWorker;
    }


    // toString..
    @Override
    public String toString() {
        return "Constraint{" +
                "currWorker=" + currWorker +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return currWorker.equals(((Constraint) obj).currWorker) && date.equals(((Constraint) obj).date);
    }
}
