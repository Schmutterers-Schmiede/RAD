package swe4.Client.UserClient.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.DateChecker;
import swe4.Client.RepositoryFactory;
import swe4.Client.adminClient.AdminPreferences;
import swe4.Client.interfaces.Repository;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public class CheckAvailabilityDialogue {
  private final Stage stage = new Stage();
  private final Repository repository;
  private final DatePicker dpStartDate;
  private final DatePicker dpEndDate;

  public CheckAvailabilityDialogue(Window owner, String invId) {
    double windowWidth = 250;
    double windowHeight = 110;

    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());

    Label lbStartDate = new Label("Von:");
    Label lbEndDate = new Label("Bis:");

    dpStartDate = new DatePicker();
    dpEndDate = new DatePicker();


    Button btnCheckAvailability = new Button("Prüfen");
    btnCheckAvailability.setOnAction(event -> checkAvailability(invId));
    btnCheckAvailability.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btnCancel = new Button("Abbrechen");
    btnCancel.setOnAction(event -> stage.hide());
    btnCancel.setPrefWidth(UIDimensions.buttonWidthShort());

    GridPane inputPane = new GridPane();
    inputPane.setHgap(5);
    inputPane.setVgap(5);
    inputPane.setAlignment(Pos.CENTER);
    inputPane.add(lbStartDate, 0, 0);
    inputPane.add(dpStartDate, 1, 0);
    inputPane.add(lbEndDate, 0, 1);
    inputPane.add(dpEndDate, 1, 1);

    HBox buttonPane = new HBox(5);
    buttonPane.getChildren().addAll(btnCheckAvailability, btnCancel);
    buttonPane.setAlignment(Pos.CENTER);

    VBox rootPane = new VBox(10);
    rootPane.getChildren().addAll(inputPane, buttonPane);
    rootPane.setPadding(new Insets(10, 0, 10, 0));

    Scene scene = new Scene(rootPane, windowWidth, windowHeight);

    stage.setScene(scene);
    stage.setTitle("Check Availability");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);
  }

  private void checkAvailability(String invId) {
    LocalDate startDate = dpStartDate.getValue();
    LocalDate endDate = dpEndDate.getValue();

    if( !(DateChecker.notNull(startDate) || DateChecker.notNull(endDate)) ||
        !DateChecker.dateIsPresentOrFuture(startDate) ||
        !DateChecker.datesAreInOrder(startDate, endDate)){
      ErrorPrompt.show("Ungültige Eingabe");
      return;
    }

    if(!DateChecker.datesAreWithinThreeWeeks(startDate, endDate)){
      ErrorPrompt.show("Geräte können maximal drei Wochen ausgeborgt werden.");
      return;
    }

    List<Reservation> conflicts = repository.getReservationConflicts(invId, startDate, endDate);
    if (conflicts.isEmpty()) {
      SuccessPrompt.show("Das Gerät ist in ihrem gewünschten Zeitraum verfügbar");
    } else {
      ObservableList<Reservation> conflictsOL = FXCollections.observableArrayList(conflicts);
      ReservationConflictView rcv = new ReservationConflictView(stage, conflictsOL);
      rcv.show();
    }

  }

  public void show() {
    stage.show();
  }
}
