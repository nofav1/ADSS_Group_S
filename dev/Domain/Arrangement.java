package Domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Arrangement {
    private String startDate;
    private String endDate;
    private String managerID;
    private List<Shift> weeklyShifts;

    // Constructor
    public Arrangement() {
        // Get new arrangement shift dates
        List<String> weekDates = createShiftArrangement();
        this.startDate = weekDates.getFirst();
        this.endDate = weekDates.getLast();
        // Create new week shifts (6 days a week)
        this.weeklyShifts = new ArrayList<>(6);

        // Initialize weeklyShifts
        for (int i = 0; i < 6; i++) {
            weeklyShifts.add(new Shift(weekDates.get(i)));
        }

    }    // Other Constructor

    public Arrangement(List<String> weekDates) {
        // Get new arrangement shift dates
        this.startDate = weekDates.getFirst();
        this.endDate = weekDates.getLast();
        // Create new week shifts (6 days a week)
        this.weeklyShifts = new ArrayList<>(6);

        // Initialize weeklyShifts
        for (int i = 0; i < 6; i++) {
            weeklyShifts.add(new Shift(weekDates.get(i)));
        }

    }

    // Copy Ctr
    public Arrangement(String startDate, String endDate, String managerID, String weeklyShifts) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.managerID = managerID;

        // Conversion of Json to Shifts
        Gson gson = new Gson();
        Type sType = new TypeToken<List<Shift>>() {
        }.getType();
        // Convert Json String to List<Shift>
        this.weeklyShifts = gson.fromJson(weeklyShifts, sType);

    }

    // make shifts list into json string -- > for DB.
    public String shiftsToJson() {
        Gson gson = new Gson();
        return gson.toJson(weeklyShifts);
    }

    // Getters //
    public String getManager() {
        return managerID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<Shift> getWeeklyShifts() {
        return weeklyShifts;
    }


    // equals by same type and dates range.(Only 1 arrangement for each week.)
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Arrangement otherArrangement) {
            return otherArrangement.startDate.equals(this.startDate) && endDate.equals(otherArrangement.endDate);
        }
        return false;
    }

    // Method to create the shift arrangement
    private List<String> createShiftArrangement() {
        List<String> shiftDates = new ArrayList<>();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();

        // Move the calendar to the next week
        calendar.add(Calendar.WEEK_OF_YEAR, 1);

        // Adjust calendar to the start of the next week (Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        // Format the date by system date format.
        SystemDate formattedDate = new SystemDate(calendar.getTime());

        // Loop from Sunday to Friday of the next week
        for (int i = 0; i < 6; i++) {
            // Add the current date to the list
            shiftDates.add(formattedDate.getDateString());

            // Move to the next day
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            formattedDate = new SystemDate(calendar.getTime());

        }

        return shiftDates;
    }    // Method to create the shift arrangement

    public static List<String> createFirstArrangement() {
        List<String> shiftDates = new ArrayList<>();

        // Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();

        // Adjust calendar to the start of the current week(Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        // Format the date by system date format.
        SystemDate formattedDate = new SystemDate(calendar.getTime());

        // Loop from Sunday to Friday of the next week
        for (int i = 0; i < 6; i++) {
            // Add the current date to the list
            shiftDates.add(formattedDate.getDateString());

            // Move to the next day
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            formattedDate = new SystemDate(calendar.getTime());

        }

        return shiftDates;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startDate).append("-").append(endDate).append("\n");
        for (Shift weeklyShift : weeklyShifts) {
            stringBuilder.append("-").append(weeklyShift.toString());
        }
        return stringBuilder.toString();
    }

    // Return today's shift by date
    public Shift getTodayShift() {
        SystemDate systemDate = new SystemDate(new Date());
        Shift result = null;
        for (Shift weeklyShift : weeklyShifts) {
            if (systemDate.getDateString().equals(weeklyShift.getShiftDate())) {
                result = weeklyShift;
            }
        }
        return result;
    }
}
