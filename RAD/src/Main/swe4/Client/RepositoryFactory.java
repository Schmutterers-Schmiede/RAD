package swe4.Client;

import swe4.Client.interfaces.Repository;

public class RepositoryFactory {
  public static Repository getRepository(boolean usingServer) {
    if (usingServer) return null;
    else return FakeRepository.getInstance();
  }
}
