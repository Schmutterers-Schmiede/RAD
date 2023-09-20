package swe4.Client.UserClient.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.RepositoryFactory;
import swe4.Client.adminClient.AdminPreferences;
import swe4.Client.interfaces.Repository;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Device;
import swe4.entities.Reservation;

import java.time.LocalDate;

public class LoginDialogue {
  private final Stage stage = new Stage();
  private final Repository repository;
  private final double windowWidth = 250;
  private final double windowHeight = 150;
  private final TextField tfUsername;
  private final PasswordField pwf;
  private final Device device;
  private final LocalDate startDate;
  private final LocalDate endDate;


  public LoginDialogue(Window owner, Device device, LocalDate startDate, LocalDate endDate) {
    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());
    this.device = device;
    this.startDate = startDate;
    this.endDate = endDate;

    Label lbPrompt = new Label("Bitte melden sie sich an, um fortzufahren.");

    Label lbUsername = new Label("Benutzername:");
    Label lbPassword = new Label("Passwort:");

    this.tfUsername = new TextField();
    pwf = new PasswordField();

    Button btLogin = new Button("Anmelden");
    btLogin.setOnAction(event -> login());
    btLogin.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btCancel = new Button("Abbrechen");
    btCancel.setOnAction(event -> stage.hide());
    btCancel.setPrefWidth(UIDimensions.buttonWidthShort());

    VBox usernameInput = new VBox();
    usernameInput.getChildren().addAll(lbUsername, tfUsername);

    VBox passwordInput = new VBox();
    passwordInput.getChildren().addAll(lbPassword, pwf);

    VBox inputPane = new VBox(UIDimensions.containerSpacing());
    inputPane.getChildren().addAll(usernameInput, passwordInput);
    inputPane.setAlignment(Pos.CENTER);

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btLogin, btCancel);
    buttonPane.setAlignment(Pos.CENTER);

    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.getChildren().addAll(inputPane, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());

    Scene scene = new Scene(rootPane, windowWidth, windowHeight);

    stage.setScene(scene);
    stage.setTitle("Anmelden & reservieren");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);
  }

  private void login() {
    if(tfUsername.getText().isEmpty() || pwf.getText().isEmpty())
      ErrorPrompt.show("Unvollständige Eingabe");
    else if(repository.authenticateUser(tfUsername.getText(), pwf.getText())){
      repository.addReservation(
              tfUsername.getText(),
              device.getInventoryId(),
              device.getInventoryCode(),
              device.getBrand(),
              device.getModel(),
              startDate,
              endDate,
              "reserviert");
      ReservationSummary summary = new ReservationSummary();
      summary.show(stage, device, tfUsername.getText(), startDate, endDate);
      stage.hide();
    }
    else {
      ErrorPrompt.show("Ungültige Benutzerdaten");
    }
  }

  public void show() {
    stage.showAndWait();
  }
}
