package swe4.Client;

import swe4.Client.interfaces.IRepository;

public class RepositoryFactory {
  public static IRepository getRepository() {
    return ServerRepository.getInstance();
  }
}
