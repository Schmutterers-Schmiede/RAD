package swe4.Client.adminClient.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class InfoPrompt {
  public static boolean show(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setContentText(message);
    alert.setTitle("RAD");
    alert.setHeaderText("");
    alert.getButtonTypes().setAll(ButtonType.OK);

    // Show the confirmation dialog and wait for the user's response
    return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
  }
}
