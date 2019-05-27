package net.project.pets.util;
import java.util.Calendar;

public class DateUtil {

    public static final int SUNDAY_INDEX = 0;
    public static final int MONDAY_INDEX = 1;
    public static final int TUESDAY_INDEX = 2;
    public static final int WEDNESDAY_INDEX = 3;
    public static final int THURSDAY_INDEX = 4;
    public static final int FRIDAY_INDEX = 5;
    public static final int SATURDAY_INDEX = 6;

    public static final String SUNDAY_IDENTIFIER = "Su";
    public static final String MONDAY_IDENTIFIER = "M";
    public static final String TUESDAY_IDENTIFIER = "Tu";
    public static final String WEDNESDAY_IDENTIFIER = "W";
    public static final String THURSDAY_IDENTIFIER = "Th";
    public static final String FRIDAY_IDENTIFIER = "F";
    public static final String SATURDAY_IDENTIFIER = "Sa";

    public static final String[] daysArray = {SUNDAY_IDENTIFIER, MONDAY_IDENTIFIER, TUESDAY_IDENTIFIER,
            WEDNESDAY_IDENTIFIER, THURSDAY_IDENTIFIER, FRIDAY_IDENTIFIER, SATURDAY_IDENTIFIER};

    public static boolean canContactStoreNow(String workingHours) throws IllegalArgumentException {

        String noSpacesHours = workingHours.replaceAll("\\s+","");

        int indexOfNum = 0;

        // Skip past non-digits.
        while (indexOfNum < noSpacesHours.length() && !Character.isDigit(noSpacesHours.charAt(indexOfNum))) {
            ++indexOfNum;
        }

        String daysRange = noSpacesHours.substring(0, indexOfNum);

        String [] arrayOfDaysRange = daysRange.split("-");

        String lowerLimitStr = "";
        String higherLimitStr = "";

        try {
            lowerLimitStr = arrayOfDaysRange[0];
            higherLimitStr = arrayOfDaysRange[1];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }

        String hoursString = noSpacesHours.substring(indexOfNum);

        String [] arrayOfTimeRange = hoursString.split("-");

        String openingTime = "";
        String closingTime = "";

        try {
            openingTime = arrayOfTimeRange[0];
            closingTime = arrayOfTimeRange[1];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }

        //check if times have all digits
        if (openingTime.length() < 5) {
            openingTime = "0" + openingTime;
        }

        if (closingTime.length() < 5) {
            closingTime = "0" + openingTime;
        }

        int currentDay = -1;
        int lowerLimit;
        int higherLimit;

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                currentDay = SUNDAY_INDEX;
                break;
            case Calendar.MONDAY:
                currentDay = MONDAY_INDEX;
                break;
            case Calendar.TUESDAY:
                currentDay = TUESDAY_INDEX;
                break;
            case Calendar.WEDNESDAY:
                currentDay = WEDNESDAY_INDEX;
                break;
            case Calendar.THURSDAY:
                currentDay = THURSDAY_INDEX;
                break;
            case Calendar.FRIDAY:
                currentDay = FRIDAY_INDEX;
                break;
            case Calendar.SATURDAY:
                currentDay = SATURDAY_INDEX ;
                break;
        }

        lowerLimit = getWeekDayNumericValue(lowerLimitStr);
        higherLimit = getWeekDayNumericValue(higherLimitStr);

        //check if -1, return error message of bad formatting

        if (lowerLimit == -1 || higherLimit == -1) {
            throw new IllegalArgumentException("Working hours contains wrong values");
        }

        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);

        String hourOfDayStr = "";
        String minutesOfDayStr = "";

        if (hourOfDay < 10) {
            hourOfDayStr = "0" + String.valueOf(hourOfDay);
        } else {
            hourOfDayStr = String.valueOf(hourOfDay);
        }

        if (minutes < 10) {
            minutesOfDayStr = "0" + String.valueOf(minutes);
        } else {
            minutesOfDayStr = String.valueOf(minutes);
        }

        String timeOfDayString = hourOfDayStr + ":" + minutesOfDayStr;

        if (lowerLimit > higherLimit) {

            if (currentDay >= lowerLimit || currentDay <= higherLimit) {
                //within range
                //check times
                return fitsHourRange(openingTime, closingTime, timeOfDayString);
            } else {
                return false;
            }

        } else {

            if (currentDay >= lowerLimit && currentDay <= higherLimit) {
                //within range
                //check times
                return fitsHourRange(openingTime, closingTime, timeOfDayString);
            } else {
                return false;
            }
        }
    }

    public static int getWeekDayNumericValue(String limitStr) {
        int result = -1;

        if (limitStr.equals(SUNDAY_IDENTIFIER)) {
            result = SUNDAY_INDEX;
        }

        if (limitStr.equals(MONDAY_IDENTIFIER)) {
            result = MONDAY_INDEX;
        }

        if (limitStr.equals(TUESDAY_IDENTIFIER)) {
            result = TUESDAY_INDEX;
        }

        if (limitStr.equals(WEDNESDAY_IDENTIFIER)) {
            result = WEDNESDAY_INDEX;
        }

        if (limitStr.equals(THURSDAY_IDENTIFIER)) {
            result = THURSDAY_INDEX;
        }

        if (limitStr.equals(FRIDAY_IDENTIFIER)) {
            result = FRIDAY_INDEX;
        }

        if (limitStr.equals(SATURDAY_IDENTIFIER)) {
            result = SATURDAY_INDEX;
        }

        return result;
    }

    public static boolean fitsHourRange(String lowerRange, String higherRange, String currentTime) {

        boolean result;

        int lowerRangeHour = Integer.valueOf(lowerRange.substring(0,2));
        int lowerRangeMinute = Integer.valueOf(lowerRange.substring(3));

        int higherRangeHour = Integer.valueOf(higherRange.substring(0,2));
        int higherRangeMinute = Integer.valueOf(higherRange.substring(3));

        int currentTimeHour = Integer.valueOf(currentTime.substring(0,2));
        int currentTimeMinute = Integer.valueOf(currentTime.substring(3));

        result = (lowerRangeHour <= currentTimeHour);

        if ((currentTimeHour == lowerRangeHour) && (lowerRangeMinute != 0)) {
            result = result && (currentTimeMinute >= currentTimeMinute);
        }

        result = result && (currentTimeHour <= higherRangeHour);

        if ((currentTimeHour == higherRangeHour) && (higherRangeMinute != 0)) {
            result = result && (currentTimeMinute <= higherRangeMinute);
        }

        return result;
    }

}
