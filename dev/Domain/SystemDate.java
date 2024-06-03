package Domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.lang.String;
import java.util.List;

public class SystemDate {
    static protected DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private java.lang.String date;

    // Constructor
    public SystemDate(String date)  {
        // format the date
        if (date != null && !date.isEmpty())
            this.date = format.format(date);

    }

    // get this.date by String format.
    public java.lang.String getDateString() {
        return date;
    }

    // get this.date by Date format.
    public java.util.Date getDate() {
        java.util.Date mDate = null;
        try {
            mDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    /// Get 6 next days from this.date ///
    public List<String> get6NextDays() {
        List<String> mDates = new ArrayList<>();
        // Save Start Date
        mDates.add(this.date);
        // initialize calendar
        Calendar cal = Calendar.getInstance();
        // cast to Date format.
        Date startDate = this.getDate();
        // set calendar time.
        cal.setTime(startDate);
        for (int i = 1; i <= 6; i++) {
            // add 1 day each time
            cal.add(Calendar.DAY_OF_MONTH, i);
            // cast the date to string
            java.lang.String dateAfter = format.format(cal.getTime());
            // save it
            mDates.add(dateAfter);
        }

        return mDates;
    }
}
