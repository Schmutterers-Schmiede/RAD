package swe4.Client.interfaces;

import javafx.collections.ObservableList;
import swe4.entities.User;

public interface Repository {
  ObservableList<User> getAllUsers();
  boolean addUser(String name, String username, String password, String type);
  void updateUser(String usernameBeforeUpdate, String name, String username, String password, String type);
  void deleteUser(String username);

}
