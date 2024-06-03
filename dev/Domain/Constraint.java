package Domain;

public class Constraint {
    private final Worker currWorker;
    private final Shift currShift;
    private Shift.sTypes shiftType;


    // Constructor
    public Constraint(Worker currWorker, Shift currShift) {
        this.currWorker = currWorker;
        this.currShift = currShift;
        // No shift type here, because it can be none.
    }


    // Getters & Setters
    public void setShiftType(Shift.sTypes shiftType) {
        this.shiftType = shiftType;
    }

    public Shift.sTypes getShiftType() {
        return shiftType;
    }

    public Worker getCurrWorker() {
        return currWorker;
    }

    public Shift getCurrShift() {
        return currShift;
    }


    // toString..
    @Override
    public String toString() {
        return "Constraint{" +
                "currWorker=" + currWorker +
                ", currShift=" + currShift +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return currShift.equals(((Constraint) obj).currShift) &&
                shiftType == ((Constraint) obj).shiftType &&
                currWorker.equals(((Constraint) obj).currWorker);
    }
}
