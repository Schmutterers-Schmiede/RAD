package swe4.Client.UserClient.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SuccessPrompt {
  public static void show(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setContentText(message);
    alert.setTitle("RAD");
    alert.getButtonTypes().setAll(ButtonType.OK);

    alert.showAndWait();
  }
}
