package Domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SystemDate {
    static protected DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private String date;

    // Constructor
    public SystemDate(Date date) {
        // format the date
        if (date != null)
            this.date = format.format(date);

    }

    // get this.date by String format.
    public String getDateString() {
        return date;
    }

    // get this.date by Date format.
    public Date getDate() {
        Date mDate = null;
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
            String dateAfter = format.format(cal.getTime());
            // save it
            mDates.add(dateAfter);
        }
        return mDates;
    }

    public static int getTodayDate() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getEndOfWeekDate() {
        Calendar cal = Calendar.getInstance();
        // Get today's date
        int today = cal.get(Calendar.DAY_OF_WEEK);
        // How many days until saturday ?
        int difference = 7 - today;
        // change calendar time to last day of week
        cal.add(Calendar.DAY_OF_WEEK, difference);
        // return
        return format.format(cal.getTime());
    }
}
