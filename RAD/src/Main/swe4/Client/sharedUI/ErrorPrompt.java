package swe4.Client.sharedUI;


import javafx.scene.control.Alert;

import javafx.scene.control.ButtonType;




public class ErrorPrompt {
  public static boolean show(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(message);
    alert.setTitle("RAD");
    alert.setHeaderText("FEHLER");
    alert.getButtonTypes().setAll(ButtonType.OK);

    // Show the confirmation dialog and wait for the user's response
    return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
  }
}
