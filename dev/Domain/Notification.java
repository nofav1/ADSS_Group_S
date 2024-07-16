package Domain;

public class Notification {
    private SystemDate message;

    public Notification(SystemDate message) {
        this.message = message;
    }

    public SystemDate getMessage() {
        return message;
    }

    public void setMessage(SystemDate message) {
        this.message = message;
    }
}
