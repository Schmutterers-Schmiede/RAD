package swe4.Client.UserClient.gui;

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
import swe4.Client.interfaces.IRepository;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Device;

import java.time.LocalDate;

public class SelectTimeSpanDialogue {
  private final Stage stage = new Stage();
  private final IRepository repository;
  private final DatePicker dpStartDate;
  private final DatePicker dpEndDate;
  private final Device device;

  public SelectTimeSpanDialogue(Window owner, Device device) {
    double windowWidth = 250;
    double windowHeight = 110;

    this.device = device;
    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());

    Label lbStartDate = new Label("Von:");
    Label lbEndDate = new Label("Bis:");

    dpStartDate = new DatePicker();
    dpEndDate = new DatePicker();


    Button btnCheckAvailability = new Button("Reservieren");
    btnCheckAvailability.setOnAction(event -> reserve(device.getInventoryId()));

    Button btnCancel = new Button("Abbrechen");
    btnCancel.setOnAction(event -> stage.hide());

    GridPane inputPane = new GridPane();
    inputPane.setHgap(5);
    inputPane.setVgap(5);
    inputPane.setAlignment(Pos.CENTER);
    inputPane.add(lbStartDate, 0, 0);
    inputPane.add(dpStartDate, 1, 0);
    inputPane.add(lbEndDate, 0, 1);
    inputPane.add(dpEndDate, 1, 1);

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btnCheckAvailability, btnCancel);
    buttonPane.setAlignment(Pos.CENTER);

    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.getChildren().addAll(inputPane, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());

    Scene scene = new Scene(rootPane, windowWidth, windowHeight);

    stage.setScene(scene);
    stage.setTitle("Check Availability");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);
  }

  private void reserve(String invId) {
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

    if (repository.reservationsOverlap(invId, startDate, endDate)) {
      ErrorPrompt.show("Ihr gewünschter Zeitraum steht mit anderen Reservierungen im Konflikt. Bitte nutzen sie die " +
              "Prüffunktion für weitere Informationen.");
    } else {
      LoginDialogue login = new LoginDialogue(stage, device, startDate, endDate);
      login.show();
    }

    stage.hide();
  }



  public void show() {
    stage.showAndWait();
  }
}
