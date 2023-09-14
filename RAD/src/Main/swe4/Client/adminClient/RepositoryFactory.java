package swe4.Client.adminClient;

import swe4.Client.FakeRepository;
import swe4.Client.interfaces.Repository;

public class RepositoryFactory {
  public static Repository getRepository(){
    if(AdminPreferences.usingDatabase()) return null;
    else return FakeRepository.getInstance();
  }
}
