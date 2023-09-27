package swe4.entities;

import java.io.Serializable;

public class User implements Serializable {
  private String name,
          username,
          password,
          role;

  public User(String name, String username, String password, String role) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.role = role;
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
