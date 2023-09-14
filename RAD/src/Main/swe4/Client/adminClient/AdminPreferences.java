package swe4.Client.adminClient;

public class AdminPreferences {
  private static final boolean usingDatabase;

  static {
    usingDatabase = false; // true to use Repository class, false to use FakeRepository class
  }

  public static boolean usingDatabase() {
    return usingDatabase;
  }
}
