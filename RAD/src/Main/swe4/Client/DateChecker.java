package swe4.Client;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateChecker {
  public static boolean datesAreWithinThreeWeeks(LocalDate startDate, LocalDate endDate) {
    long differenceInDays = Math.abs(ChronoUnit.DAYS.between(startDate, endDate));
    return differenceInDays <= 21;
  }

  public static boolean notNull(LocalDate date){
    return date != null;
  }

  public static boolean datesAreInOrder(LocalDate startDate, LocalDate endDate){
    return startDate.isBefore(endDate);
  }

  public static boolean dateIsPresentOrFuture(LocalDate startDate){
    return startDate.isAfter(LocalDate.now()) || startDate.isEqual(LocalDate.now());
  }

  public static boolean timeSpanOverlap(
    LocalDate startDate1,
    LocalDate endDate1,
    LocalDate startDate2,
    LocalDate endDate2) {
    return
      (startDate1.isEqual(startDate2) || endDate1.isEqual(endDate2)) ||
      (startDate1.isBefore(endDate2) && startDate1.isAfter(startDate2)) ||
      (endDate1.isBefore(endDate2) && endDate1.isAfter(startDate2));
  }
}
