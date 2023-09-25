package swe4.Client.UserClient.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import swe4.Client.RepositoryFactory;
import swe4.Client.UserClient.UserPerferences;
import swe4.Client.interfaces.IRepository;
import swe4.Client.sharedUI.DeviceDetailView;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Device;

import java.util.Locale;

public class RADUserMain extends Application {
  private TableView<Device> tbv;
  private final int windowWidth = 600;
  private final int windowHeight = 650;

  @Override
  public void start(Stage primaryStage) throws Exception {
    IRepository repository;
    double tbvWidth = windowWidth;
    double tbvHeight = windowHeight - 50;

    Locale.setDefault(Locale.GERMAN);
    primaryStage.setTitle("RAD");

    repository = RepositoryFactory.getRepository(UserPerferences.usingServer());

    tbv = new TableView<>();
    tbv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tbv.setPrefSize(tbvWidth, tbvHeight);
    TableColumn invIdCol = new TableColumn("InventarNr");
    TableColumn nameCol = new TableColumn("Bezeichnung");
    TableColumn modelCol = new TableColumn("Modell");
    TableColumn brandCol = new TableColumn("Hersteller");
    TableColumn statusCol = new TableColumn("Status");

    invIdCol.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
    brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

    tbv.getColumns().addAll(invIdCol, nameCol, modelCol, brandCol, statusCol);

    tbv.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        openDetailView(primaryStage, tbv.getSelectionModel().getSelectedItem());
      }
    });

    ObservableList<Device> devices = FXCollections.observableArrayList(repository.getAllDevicesUser());
    tbv.setItems(devices);

    Button btnReserve = new Button("Reservieren");
    btnReserve.setOnAction(event -> chooseTimeSpan(primaryStage));
    btnReserve.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btnCheckAvailability = new Button("Verfügbarkeit prüfen");
    btnCheckAvailability.setOnAction(event -> checkAvailability(primaryStage));
    btnCheckAvailability.setPrefWidth(UIDimensions.buttonWidthLong());


    Button btnExit = new Button("Beenden");
    btnExit.setOnAction(event -> primaryStage.close());
    btnExit.setPrefWidth(UIDimensions.buttonWidthShort());

    VBox tablePane = new VBox(tbv);
    tablePane.setPrefSize(tbvWidth, tbvHeight);
    tablePane.setAlignment(Pos.CENTER);


    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btnReserve, btnCheckAvailability, UIDimensions.createSpacer(), btnExit);


    VBox rootPane = new VBox(UIDimensions.containerSpacing());

    rootPane.setPadding(UIDimensions.windowPadding());
    rootPane.getChildren().addAll(tablePane, buttonPane);
    Scene scene = new Scene(rootPane, windowWidth, windowHeight);
    primaryStage.setScene(scene);

    Platform.runLater(primaryStage::show);
  }

  private void checkAvailability(Stage primaryStage) {
    Device selectedDevice = tbv.getSelectionModel().getSelectedItem();
    if (selectedDevice == null) {
      ErrorPrompt.show("Es ist kein Gerät ausgewählt");
    } else {
      CheckAvailabilityDialogue dialogue = new CheckAvailabilityDialogue(primaryStage, selectedDevice.getInventoryId());
      dialogue.show();
    }
  }

  private void chooseTimeSpan(Stage primaryStage) {
    Device device = tbv.getSelectionModel().getSelectedItem();

    if(device == null){
      ErrorPrompt.show("Es ist kein Gerät ausgewählt.");
      return;
    }

    if(device.getStatus().equals("defekt")) {
      ErrorPrompt.show("Dieses Gerät ist zur Zeit defekt und kann nicht verliehen werden.");
      return;
    }

    SelectTimeSpanDialogue dialogue = new SelectTimeSpanDialogue(primaryStage, device);
    dialogue.show();
  }

  //======== BUTTON ACTIONS ==========
  private void openDetailView(Stage primaryStage, Device device) {
    DeviceDetailView.show(primaryStage, device);
  }
}
