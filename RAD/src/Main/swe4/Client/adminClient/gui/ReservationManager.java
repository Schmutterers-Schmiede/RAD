package swe4.Client.adminClient.gui;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.adminClient.AdminPreferences;
import swe4.Client.RepositoryFactory;
import swe4.Client.interfaces.Repository;
import swe4.Client.sharedUI.ConfirmationPrompt;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Reservation;


public class ReservationManager {
  private final Stage stage = new Stage();
  private final TableView<Reservation> tbv;
  private final Repository repository;
  private final int windowWidth = 600;
  private final int windowHeight = 700;
  private final double tbvWidth = windowWidth;
  private final double tbvHeight = windowHeight - 50;
  private final double minColWidth = 120;

  public ReservationManager(Window owner) {
    double tbvWidth = windowWidth;
    double tbvHeight = windowHeight - 50;
    double minColWidth = 120;
    tbv = new TableView<>();
    tbv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tbv.setPrefSize(tbvWidth, tbvHeight);

    TableColumn nameCol = new TableColumn("reserviert von");
    TableColumn modelCol = new TableColumn("Modell");
    TableColumn brandCol = new TableColumn("Hersteller");
    TableColumn invIdCol = new TableColumn("InventarNr");
    TableColumn invCodeCol = new TableColumn("InventarCode");
    TableColumn startDateCol = new TableColumn("Von");
    TableColumn endDateCol = new TableColumn("Bis");

    nameCol.setMinWidth(minColWidth);
    modelCol.setMinWidth(minColWidth);
    brandCol.setMinWidth(minColWidth);
    invIdCol.setMinWidth(minColWidth);
    invCodeCol.setMinWidth(minColWidth);
    startDateCol.setMinWidth(minColWidth);
    endDateCol.setMinWidth(minColWidth);


    nameCol.setCellValueFactory(new PropertyValueFactory<>("rentedByName"));
    modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
    brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
    invIdCol.setCellValueFactory(new PropertyValueFactory<>("invId"));
    invCodeCol.setCellValueFactory(new PropertyValueFactory<>("invCode"));
    startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

    tbv.getColumns().addAll(nameCol, modelCol, brandCol, invIdCol, invCodeCol, startDateCol, endDateCol);

    Button btEditReservation = new Button("Bearbeiten");
    btEditReservation.setOnAction(event -> editReservation(tbv.getSelectionModel().getSelectedItem()));
    btEditReservation.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btSearch = new Button("suchen");
    btSearch.setOnAction(event -> search());
    btSearch.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btDeleteReservation = new Button("Stornieren");
    btDeleteReservation.setOnAction(event -> deleteReservation(tbv.getSelectionModel().getSelectedItem().getReservationId()));
    btDeleteReservation.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btBack = new Button("Zurück");
    btBack.setOnAction(event -> stage.hide());
    btBack.setPrefWidth(UIDimensions.buttonWidthShort());

    VBox tablePane = new VBox(tbv);
    tablePane.setPrefSize(tbvWidth, tbvHeight);

    HBox tableContainer = new HBox(tablePane);
    tableContainer.setAlignment(Pos.CENTER);

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btEditReservation, btSearch, btDeleteReservation, UIDimensions.createSpacer(), btBack);


    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.getChildren().addAll(tableContainer, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());
    Scene reservationManagerScene = new Scene(rootPane, windowWidth, windowHeight);

    stage.setScene(reservationManagerScene);
    stage.setTitle("RAD Admin Reservierungsmanager");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);

    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());
  }

  private void search() {
    ReservationSearchDialogue searchDialogue = new ReservationSearchDialogue(stage);
    ObservableList<Reservation> searchResults = searchDialogue.show();
    if (searchResults != null)
      tbv.setItems(searchResults);
  }

  public void show() {
    ObservableList<Reservation> reservations = FXCollections.observableArrayList(repository.getAllReservations());
    tbv.setItems(reservations);
    stage.show();
  }


  private void deleteReservation(int reservationId) {
    if (ConfirmationPrompt.show("Sind Sie sicher, dass sie diese Reservierung stornieren wollen?")) {
      repository.deleteReservation(reservationId);
      tbv.refresh();
    }
  }

  private void editReservation(Reservation reservation) {
    if (reservation == null) {
      ErrorPrompt.show("Es ist keine Reservierung ausgewählt.");
    } else {
      EditReservationDialogue editReservationDialogue = new EditReservationDialogue(stage, reservation);
      editReservationDialogue.show();
      tbv.refresh();
    }
  }
}
