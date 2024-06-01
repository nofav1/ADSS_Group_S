package Domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Arrangement {
    HashMap<Shift, ArrayList<Worker>> arrangement;

    // Constructor
    public Arrangement() {
        arrangement = new HashMap<>();
    }

    public HashMap<Shift, ArrayList<Worker>> getArrangement() {
        return arrangement;
    }


}
