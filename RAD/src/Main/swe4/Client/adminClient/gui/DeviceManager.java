package swe4.Client.adminClient.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.adminClient.AdminPreferences;
import swe4.Client.RepositoryFactory;
import swe4.Client.interfaces.IRepository;
import swe4.Client.sharedUI.ConfirmationPrompt;
import swe4.Client.sharedUI.DeviceDetailView;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Device;

public class DeviceManager {
  private final Stage stage = new Stage();
  private ObservableList<Device> devices;
  private final TableView<Device> tbv;
  private final IRepository repository;
  private final int windowWidth = 600;
  private final int windowHeight = 650;

  public DeviceManager(Window owner) {
    double tbvWidth = windowWidth;
    double tbvHeight = windowHeight - 50;

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
        openDetailView(tbv.getSelectionModel().getSelectedItem());
      }
    });

    Button btnAddDevice = new Button("Hinzufügen");
    btnAddDevice.setOnAction(event -> addDevice());
    btnAddDevice.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btnEditDevice = new Button("Bearbeiten");
    btnEditDevice.setOnAction(event -> editDevice(tbv.getSelectionModel().getSelectedItem()));
    btnEditDevice.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btSearch = new Button("Suchen");
    btSearch.setOnAction(event -> search());
    btSearch.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btnDeleteDevice = new Button("Löschen");
    btnDeleteDevice.setOnAction(event -> deleteDevice(tbv.getSelectionModel().getSelectedItem().getInventoryId()));
    btnDeleteDevice.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btReset = new Button("Suche zurücksetzen");
    btReset.setOnAction(event -> reset());
    btReset.setPrefWidth(UIDimensions.buttonWidthMedium());

    Button btnBack = new Button("Zurück");
    btnBack.setOnAction(event -> stage.hide());
    btnBack.setPrefWidth(UIDimensions.buttonWidthShort());


    VBox tablePane = new VBox(tbv);
    tablePane.setPrefSize(tbvWidth, tbvHeight);

    HBox tableContainer = new HBox(tablePane);
    tableContainer.setAlignment(Pos.CENTER);

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btnAddDevice, btnEditDevice, btSearch, btReset, btnDeleteDevice, UIDimensions.createSpacer(), btnBack);


    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.getChildren().addAll(tableContainer, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());

    Scene deviceManagerScene = new Scene(rootPane, windowWidth, windowHeight);

    stage.setScene(deviceManagerScene);
    stage.setTitle("RAD Admin Gerätemanager");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);

    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());
  }

  private void reset() {
    tbv.setItems(FXCollections.observableArrayList(repository.getAllDevicesAdmin()));
  }

  private void openDetailView(Device device) {
    DeviceDetailView.show(stage, device);
  }

  public void show() {
    devices = FXCollections.observableArrayList(repository.getAllDevicesAdmin());
    tbv.setItems(devices);
    stage.show();
  }

  private void addDevice() {
    AddDeviceDialogue addDeviceDialogue = new AddDeviceDialogue(stage);
    addDeviceDialogue.show();
    devices = FXCollections.observableArrayList(repository.getAllDevicesAdmin());
    tbv.refresh();
  }

  private void deleteDevice(String invId) {
    if (ConfirmationPrompt.show("Sind Sie sicher, dass sie dieses Gerät löschen wollen?")) {
      repository.deleteDevice(invId);
      devices = FXCollections.observableArrayList(repository.getAllDevicesAdmin());
      tbv.refresh();
    }
  }

  private void editDevice(Device device) {
    if (device == null) {
      ErrorPrompt.show("Es ist kein Gerät ausgewählt.");
    } else {
      EditDeviceDialogue editDeviceDialogue = new EditDeviceDialogue(stage, device);
      editDeviceDialogue.show();
      devices = FXCollections.observableArrayList(repository.getAllDevicesAdmin());
      tbv.refresh();
    }
  }

  private void search() {
    DeviceSearchDialogue searchDialogue = new DeviceSearchDialogue(stage);
    ObservableList<Device> searchResults = searchDialogue.show();
    if (searchResults != null)
      tbv.setItems(searchResults);
  }
}
