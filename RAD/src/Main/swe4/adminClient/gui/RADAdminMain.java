package swe4.adminClient.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import swe4.UIElementFactory;



public class RADAdminMain extends Application{



  @Override
  public void start(Stage primaryStage) throws Exception{

    primaryStage.setTitle("RAD Admin Main Menu");

    Button userManagerButton = UIElementFactory.createButton("bt_user-manager", "Benutzer");
    Button deviceManagerButton = UIElementFactory.createButton("bt_device-manager", "Geräte");
    Button reservationManagerButton = UIElementFactory.createButton("bt_reservation-manager", "Reservierungen");

    userManagerButton.setOnAction(event -> openUserManager());
    deviceManagerButton.setOnAction(event -> openDeviceManager());
    reservationManagerButton.setOnAction(event -> openReservationManager());

    VBox vBox = new VBox(10);
    vBox.getChildren().addAll(userManagerButton, deviceManagerButton, reservationManagerButton);

    Scene scene = new Scene(vBox, 300, 200);
    primaryStage.setScene(scene);

    primaryStage.show();
  }

  //======== BUTTON ACTIONS ==========
  private void openUserManager(){
//    Stage userManagerStage = new Stage();
//    userManagerStage.setTitle("RAD Admin User Manager");


  }

  private void openDeviceManager(){}

  private void openReservationManager(){}
}
