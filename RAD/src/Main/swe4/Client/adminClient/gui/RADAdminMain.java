package swe4.Client.adminClient.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;


public class RADAdminMain extends Application{



  @Override
  public void start(Stage primaryStage) throws Exception{
    Locale.setDefault(Locale.GERMAN);
    primaryStage.setTitle("RAD Admin Main Menu");

    Button userManagerButton = new Button("Benutzer");
    userManagerButton.setId("btn_user-manager");

    Button deviceManagerButton = new Button("Geräte");
    deviceManagerButton.setId("btn_device-manager");

    Button reservationManagerButton = new Button("Reservierungen");
    reservationManagerButton.setId("btn_reservation-manager");

    userManagerButton.setOnAction(event -> openUserManager(primaryStage));
    deviceManagerButton.setOnAction(event -> openDeviceManager());
    reservationManagerButton.setOnAction(event -> openReservationManager());

    VBox vBox = new VBox(10);
    vBox.setAlignment(Pos.CENTER);
    vBox.getChildren().addAll(userManagerButton, deviceManagerButton, reservationManagerButton);

    Scene scene = new Scene(vBox, 300, 200);
    primaryStage.setScene(scene);

    Platform.runLater(primaryStage::show);
  }

  //======== BUTTON ACTIONS ==========
  private void openUserManager(Stage primaryStage){
    UserManager userManager = new UserManager(primaryStage);
    Platform.runLater(userManager::show);
  }

  private void openDeviceManager(){}

  private void openReservationManager(){}
}
