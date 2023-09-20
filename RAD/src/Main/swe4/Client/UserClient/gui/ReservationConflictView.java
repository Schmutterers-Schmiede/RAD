package swe4.Client.UserClient.gui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Reservation;

public class ReservationConflictView {
  private final TableView<Reservation> tbv;
  private final ObservableList<Reservation> conflicts;
  private final Stage stage;

  public ReservationConflictView(Window owner, ObservableList<Reservation> conflicts) {

    int windowWidth = 500;
    int windowHeight = 350;
    double tbvWidth = windowWidth - 100;
    double tbvHeight = windowHeight - 50;

    stage = new Stage();

    this.conflicts = conflicts;

    tbv = new TableView<>();
    tbv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tbv.setPrefSize(tbvWidth, tbvHeight);

    TableColumn nameCol = new TableColumn("Name");
    TableColumn startDateCol = new TableColumn("Von");
    TableColumn endDateCol = new TableColumn("Bis");

    nameCol.setCellValueFactory(new PropertyValueFactory<>("rentedByName"));
    startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));

    tbv.getColumns().addAll(nameCol, startDateCol, endDateCol);


    Label lbTableText = new Label("Folgende Reservierung stehen mit ihrem gewünschten Zeitraum im Konflikt:");

    Button btClose = new Button("Schließen");
    btClose.setOnAction(event -> stage.hide());
    btClose.setPrefWidth(UIDimensions.buttonWidthShort());

    VBox dataPane = new VBox(5);
    dataPane.getChildren().addAll(lbTableText, tbv);
    dataPane.setPrefSize(tbvWidth, tbvHeight);

    VBox buttonPane = new VBox();
    buttonPane.setAlignment(Pos.CENTER_LEFT);
    buttonPane.getChildren().add(btClose);

    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.getChildren().addAll(dataPane, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());

    Scene scene = new Scene(rootPane, windowWidth, windowHeight);

    stage.setScene(scene);
    stage.setTitle("Conflicts");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);
  }

  public void show() {
    tbv.setItems(conflicts);
    stage.show();
  }
}
