package swe4.Client;

import swe4.Client.interfaces.IRepository;

public class RepositoryFactory {
  public static IRepository getRepository(boolean usingServer) {
    if (usingServer) return ServerRepository.getInstance();
    else return FakeRepository.getInstance();
  }
}
