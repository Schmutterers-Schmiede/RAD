package swe4.entities;

import java.io.Serializable;

public class User implements Serializable {
  private String name,
          username,
          password,
          type;

  public User(String name, String username, String password, String type) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
