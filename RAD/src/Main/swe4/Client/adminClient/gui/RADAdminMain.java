package swe4.Client.adminClient.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Locale;


public class RADAdminMain extends Application {



  @Override
  public void start(Stage primaryStage) throws Exception {
    double buttonWidth = 200;
    double buttonHeight = 50;
    double windowWidth = 300;
    double windowHeight = 370;

    Locale.setDefault(Locale.GERMAN);
    primaryStage.setTitle("RAD Admin Main Menu");

    Button btOpenUserManager = new Button("Benutzer");
    btOpenUserManager.setOnAction(event -> openUserManager(primaryStage));
    btOpenUserManager.setPrefSize(buttonWidth, buttonHeight);
    btOpenUserManager.setFont(new Font(15));

    Button btOpenDeviceManager = new Button("Geräte");
    btOpenDeviceManager.setOnAction(event -> openDeviceManager(primaryStage));
    btOpenDeviceManager.setPrefSize(buttonWidth, buttonHeight);
    btOpenDeviceManager.setFont(new Font(15));

    Button btOpenReservationManager = new Button("Reservierungen");
    btOpenReservationManager.setOnAction(event -> openReservationManager(primaryStage));
    btOpenReservationManager.setPrefSize(buttonWidth, buttonHeight);
    btOpenReservationManager.setFont(new Font(15));

    Button btExit = new Button("Beenden");
    btExit.setOnAction(event -> primaryStage.close());
    btExit.setPrefSize(buttonWidth, buttonHeight);
    btExit.setFont(new Font(15));

    Label header = new Label("RAD");
    header.setFont(new Font(30));
    Label subHeader = new Label("Admin Client");
    VBox headerPane = new VBox(header, subHeader);
    headerPane.setAlignment(Pos.CENTER);
    headerPane.requestFocus();

    VBox buttonPane = new VBox(10);
    buttonPane.setAlignment(Pos.CENTER);
    buttonPane.getChildren().addAll(btOpenUserManager, btOpenDeviceManager, btOpenReservationManager, btExit);


    VBox rootPane = new VBox(20);
    rootPane.setPadding(new Insets(15, 0, 0, 0));
    rootPane.getChildren().addAll(headerPane, buttonPane);

    Scene scene = new Scene(rootPane, windowWidth, windowHeight);
    primaryStage.setScene(scene);


    Platform.runLater(primaryStage::show);
  }

  //======== BUTTON ACTIONS ==========
  private void openUserManager(Stage primaryStage) {
    UserManager userManager = new UserManager(primaryStage);
    Platform.runLater(userManager::show);
  }

  private void openDeviceManager(Stage primaryStage) {
    DeviceManager deviceManager = new DeviceManager(primaryStage);
    Platform.runLater(deviceManager::show);
  }

  private void openReservationManager(Stage primaryStage) {
    ReservationManager reservationManager = new ReservationManager(primaryStage);
    Platform.runLater(reservationManager::show);
  }
}
