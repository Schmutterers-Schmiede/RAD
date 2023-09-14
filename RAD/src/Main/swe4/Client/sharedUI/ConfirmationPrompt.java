package swe4.Client.sharedUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmationPrompt {
  public static boolean show(String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setContentText(message);
    alert.setTitle("RAD");
    alert.setHeaderText("ACHTUNG!");
    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

    // Show the confirmation dialog and wait for the user's response
    return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
  }
}