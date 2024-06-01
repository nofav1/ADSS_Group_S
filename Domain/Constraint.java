package Domain;

public class Constraint {
    private Worker currWorker;
    private Shift currShift;
    private int shiftType;


    // Constructor
    public Constraint(Worker currWorker, Shift currShift) {
        this.currWorker = currWorker;
        this.currShift = currShift;
    }


    // Getters & Setters
    public void setShiftType(int shiftType) {
        this.shiftType = shiftType;
    }

    public int getShiftType() {
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
}
