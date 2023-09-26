package swe4.Server.Dal;

import swe4.entities.User;

import java.util.Collection;

public interface IUserDao extends AutoCloseable{

  Collection<User> getAll();
  User getByUsername(String username);
  void add(User user);
  void delete(String username);
  boolean authenticate(String username, String password);
  void update(String usernameBeforeUpdate, User user);

}
