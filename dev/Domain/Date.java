package Domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date {
    static protected DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private String date;

    public Date(String date) throws ParseException {
        // format the date
        if (date != null && !date.isEmpty())
            format.parse(date);
        // save it
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
