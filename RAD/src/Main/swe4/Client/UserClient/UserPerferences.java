package swe4.Client.UserClient;

public class UserPerferences {
  private static final boolean usingServer;

  static {
    usingServer = false; // true to use Repository class, false to use FakeRepository class
  }

  public static boolean usingServer() {
    return usingServer;
  }
}
