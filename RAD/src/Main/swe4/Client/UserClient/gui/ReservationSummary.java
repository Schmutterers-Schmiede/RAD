package swe4.Client.UserClient.gui;

import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.RepositoryFactory;
import swe4.Client.UserClient.UserPerferences;
import swe4.Client.interfaces.IRepository;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Device;
import swe4.entities.User;

import java.time.LocalDate;

public class ReservationSummary {

  public void show(Window owner, Device device, String username, LocalDate startDate, LocalDate endDate) {
    IRepository repository;
    Stage stage = new Stage();
    double windowWidth = 280;
    double windowHeight = 500;
    double qrSize = 150;

    repository = RepositoryFactory.getRepository(UserPerferences.usingServer());
    User user = repository.getUserByUsername(username);

    Label lbPrompt = new Label("Das Gerät wurde für sie Reserviert. Mit diesem\n" +
            "Leihschein können sie es ab dem ersten Tag\n" +
            "des gewählten Zeitraums abholen.");

    Label lbRenteeName = new Label("Reserviert von:");
    Label lbModel = new Label("Modell:");
    Label lbBrand = new Label("Marke:");
    Label lbInvId = new Label("InventarNr:");
    Label lbInvCode = new Label("Inventarcode:");
    Label lbSNr = new Label("SerienNr:");
    Label lbStartDate = new Label("Von:");
    Label lbEndDate = new Label("Bis:");

    Label lbRenteeNameValue = new Label(user.getName());
    Label lbModelValue = new Label(device.getModel());
    Label lbBrandValue = new Label(device.getBrand());
    Label lbInvIdValue = new Label(device.getInventoryId());
    Label lbInvCodeValue = new Label(device.getInventoryCode());
    Label lbSNrValue = new Label(device.getSerialNr());
    Label lbStartDateValue = new Label(startDate.toString());
    Label lbEndDateValue = new Label(endDate.toString());

    VBox promptTextPane = new VBox(lbPrompt);

    GridPane dataPane = new GridPane();

    dataPane.setVgap(UIDimensions.gridPaneSpacing());
    dataPane.setHgap(50);

    dataPane.add(lbRenteeName, 0,0);
    dataPane.add(lbRenteeNameValue, 1,0);

    dataPane.add(lbModel, 0,1);
    dataPane.add(lbModelValue,1,1);

    dataPane.add(lbBrand,0,2);
    dataPane.add(lbBrandValue,1,2);

    dataPane.add(lbInvId,0,3);
    dataPane.add(lbInvIdValue,1,3);

    dataPane.add(lbInvCode,0,4);
    dataPane.add(lbInvCodeValue,1,4);

    dataPane.add(lbSNr, 0,5);
    dataPane.add(lbSNrValue,1,5);

    dataPane.add(lbStartDate,0,6);
    dataPane.add(lbStartDateValue,1,6);

    dataPane.add(lbEndDate,0,7);
    dataPane.add(lbEndDateValue,1,7);

    Image image = new Image(getClass().getResource("../../../../Assets/qr.png").toExternalForm());
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(qrSize);
    imageView.setFitHeight(qrSize);

    HBox imagePane = new HBox(imageView);
    imagePane.setAlignment(Pos.CENTER);
    imagePane.setPrefSize(170,170);

//    VBox printPane = new VBox(UIDimensions.containerSpacing());
//    printPane.setPadding(UIDimensions.windowPadding());
//    printPane.getChildren().addAll(promptTextPane, dataPane, imagePane);

    Button btPrint = new Button("Drucken");
    btPrint.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btClose = new Button("Schließen");
    btClose.setOnAction(event -> stage.hide());
    btClose.setPrefWidth(UIDimensions.buttonWidthShort());

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btPrint, btClose);
    buttonPane.setAlignment(Pos.CENTER);

    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.setPadding(UIDimensions.windowPadding());
    rootPane.getChildren().addAll(promptTextPane, dataPane, imagePane, buttonPane);

    btPrint.setOnAction(event -> print(stage, rootPane));




    Scene deviceDetailViewScene = new Scene(rootPane, windowWidth, windowHeight);
    stage.setScene(deviceDetailViewScene);
    stage.setTitle("Leihschein");
    stage.setResizable(false);
    stage.initOwner(owner);
    stage.showAndWait();
  }

  private static void print(Stage ownerStage, Node printPane) {
    PrinterJob printerJob = PrinterJob.createPrinterJob();
    if (printerJob != null) {
      boolean showDialog = printerJob.showPrintDialog(ownerStage); // 'stage' is your application's primary stage
      if (showDialog) {
        boolean printSuccess = printerJob.printPage(printPane);
        if(printSuccess){
          printerJob.endJob();
        }
        else
          System.out.println("Printing failed");
      } else {
        System.out.println("No printer available");
      }
    } else {
      // Handle the case where printing is not possible or canceled
      System.out.println("No printer available or printing canceled.");
    }
  }
}
