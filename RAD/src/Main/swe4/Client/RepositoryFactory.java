package swe4.Client;

import swe4.Client.interfaces.Repository;

public class RepositoryFactory {
  public static Repository getRepository(boolean usingServer) {
    if (usingServer) return ServerRepository.getInstance();
    else return FakeRepository.getInstance();
  }
}
