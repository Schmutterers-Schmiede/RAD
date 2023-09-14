package swe4.Client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import swe4.entities.User;
import swe4.Client.interfaces.Repository;

public class FakeRepository  implements Repository {
  private final ObservableList<User> users;
  private static FakeRepository instance;

  public static FakeRepository getInstance(){
    if(instance == null) instance = new FakeRepository();
    return instance;
  }

  private FakeRepository(){
    users = FXCollections.observableArrayList();
    users.add(new User("name1", "username1", "pw1", "Student"));
    users.add(new User("name2", "username2", "pw2", "Student"));
    users.add(new User("name3", "username3", "pw3", "Student"));
  }

  public ObservableList<User> getAllUsers(){
    return users;
  }

  @Override
  public boolean addUser(String name, String username, String password, String type) {
    User newUser = new User(name, username, password, type);
    if(users.stream().anyMatch(user -> user.getUsername().equals(newUser.getUsername())))
      return false;

    users.add(newUser);
    return true;
  }

  @Override
  public void updateUser(String usernameBeforeUpdate, String name, String username, String password, String type) {
    User userToUpdate = users .stream()
                              .filter(user -> user.getUsername().equals(usernameBeforeUpdate))
                              .findFirst()
                              .orElse(null);
    if (userToUpdate != null) {
      userToUpdate.setName(name);
      userToUpdate.setUsername(username);
      userToUpdate.setPassword(password);
      userToUpdate.setType(type);
    }
  }

  @Override
  public void deleteUser(String username) {
    User userToDelete = users .stream()
                              .filter(user -> user.getUsername().equals(username))
                              .findFirst().orElse(null);
    if(userToDelete != null)
      users.remove(userToDelete);
  }
}
