package swe4.Client.adminClient.gui;

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
import swe4.Client.DateChecker;
import swe4.Client.adminClient.AdminPreferences;
import swe4.Client.RepositoryFactory;
import swe4.Client.interfaces.IRepository;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Reservation;

import java.time.LocalDate;

public class EditReservationDialogue {
  private final Stage stage = new Stage();
  private final IRepository repository;

  public EditReservationDialogue(Window owner, Reservation reservation) {
    double windowWidth = 250;
    double windowHeight = 110;

    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());

    DatePicker dpStartDate = new DatePicker();
    DatePicker dpEndDate = new DatePicker();

    dpStartDate.setValue(reservation.getStartDate());
    dpEndDate.setValue(reservation.getEndDate());

    Button btnConfirmUpdateReservation = new Button("Ändern");

    btnConfirmUpdateReservation.setOnAction(event -> updateReservation(
            reservation.getInvId(),
            reservation.getReservationId(),
            dpStartDate.getValue(),
            dpEndDate.getValue()));
    btnConfirmUpdateReservation.setPrefWidth(UIDimensions.buttonWidthShort());


    Button btnCancel = new Button("Abbrechen");
    btnCancel.setOnAction(event -> stage.hide());
    btnCancel.setPrefWidth(UIDimensions.buttonWidthShort());

    Label lbStartDate = new Label("Von:");
    Label lbEndDate = new Label("Bis:");

    GridPane formPane = new GridPane();
    formPane.setHgap(5);
    formPane.setVgap(5);

    formPane.add(lbStartDate, 0, 0);
    formPane.add(lbEndDate, 0, 1);

    formPane.add(dpStartDate, 1, 0);
    formPane.add(dpEndDate, 1, 1);


    HBox buttonPane = new HBox(10);
    buttonPane.getChildren().addAll(btnConfirmUpdateReservation, btnCancel);
    buttonPane.setAlignment(Pos.CENTER);

    VBox rootPane = new VBox(10);
    rootPane.getChildren().addAll(formPane, buttonPane);
    rootPane.setPadding(new Insets(10, 10, 10, 10));

    Scene editUserScene = new Scene(rootPane, windowWidth, windowHeight);
    stage.setScene(editUserScene);
    stage.setTitle("Edit Reservation");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);
  }

  public void show() {
    stage.showAndWait();
  }

  private void updateReservation(String invId, int reservationId, LocalDate startDate, LocalDate endDate) {
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

    if (repository.updateReservation(invId, reservationId, startDate, endDate)) {
      stage.hide();
    } else {
      ErrorPrompt.show("Dieser Zeitraum überschneidet sich mit einer anderen Reservierung.");
    }
  }
}
