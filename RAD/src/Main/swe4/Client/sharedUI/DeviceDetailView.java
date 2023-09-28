package swe4.Client.sharedUI;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.entities.Device;


public class DeviceDetailView {
  public static void show(Window owner, Device device) {
    Stage stage = new Stage();
    double windowWidth = 400;
    double windowHeight = 340;
    double commentPaneWidth = windowWidth - 10;
    double commentPaneHeight = 100;

    Label lbInvId = new Label("InventarNr:");
    Label lbInvCode = new Label("Inventarcode:");
    Label lbName = new Label("Bezeichnung:");
    Label lbBrand = new Label("Marke:");
    Label lbModel = new Label("Modell:");
    Label lbCategory = new Label("Kategorie:");
    Label lbSNr = new Label("SerienNr:");
    Label lbRoomNr = new Label("RaumNr:");
    Label lbBuyDate = new Label("Kaufdatum:");
    Label lbLogDate = new Label("ErfassungsDatum:");
    Label lbDisposalDate = new Label("Ausscheidungsdatum:");
    Label lbprice = new Label("Preis:");
    Label lStatus = new Label("Status:");

    Label lbInvIdValue = new Label(device.getInventoryId());
    Label lbInvCodeValue = new Label(device.getInventoryCode());
    Label lbNameValue = new Label(device.getName());
    Label lbBrandValue = new Label(device.getBrand());
    Label lbModelValue = new Label(device.getModel());
    Label lbCategoryValue = new Label(device.getCategory());
    Label lbSNrValue = new Label(device.getSerialNr());
    Label lbRoomNrValue = new Label(device.getRoomNr());
    Label lbBuyDateValue = new Label(device.getBuyDate().toString());
    Label lbLogDateValue = new Label(device.getLogDate().toString());
    Label lbDisposalDateValue = new Label(device.getDisposalDate() == null ? "-" : device.getDisposalDate().toString());
    Label lbpriceValue = new Label(device.getPrice().toString());
    Label lStatusValue = new Label(device.getStatus());

    GridPane dataPane = new GridPane();
    dataPane.setVgap(UIDimensions.gridPaneSpacing());
    dataPane.setHgap(50);

    dataPane.add(lbInvId, 0, 0);
    dataPane.add(lbInvIdValue, 1, 0);

    dataPane.add(lbInvCode, 0, 1);
    dataPane.add(lbInvCodeValue, 1, 1);

    dataPane.add(lbName, 0, 2);
    dataPane.add(lbNameValue, 1, 2);

    dataPane.add(lbBrand, 0, 3);
    dataPane.add(lbBrandValue, 1, 3);

    dataPane.add(lbModel, 0, 4);
    dataPane.add(lbModelValue, 1, 4);

    dataPane.add(lbCategory, 0, 5);
    dataPane.add(lbCategoryValue, 1, 5);

    dataPane.add(lbSNr, 0, 6);
    dataPane.add(lbSNrValue, 1, 6);

    dataPane.add(lbRoomNr, 0, 7);
    dataPane.add(lbRoomNrValue, 1, 7);

    dataPane.add(lbBuyDate, 0, 8);
    dataPane.add(lbBuyDateValue, 1, 8);

    dataPane.add(lbLogDate, 0, 9);
    dataPane.add(lbLogDateValue, 1, 9);

    dataPane.add(lbDisposalDate, 0, 10);
    dataPane.add(lbDisposalDateValue, 1, 10);

    dataPane.add(lbprice, 0, 11);
    dataPane.add(lbpriceValue, 1, 11);

    dataPane.add(lStatus, 0, 12);
    dataPane.add(lStatusValue, 1, 12);

    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.setPadding(UIDimensions.windowPadding());
    rootPane.getChildren().add(dataPane);

    String comments = device.getComments();
    if (comments != null) {
      windowHeight += commentPaneHeight;
      TextArea taComments = new TextArea(comments);
      Label lbComments = new Label("Anmerkungen:");
      taComments.setWrapText(true);
      taComments.setEditable(false);
      taComments.setMaxHeight(commentPaneHeight - lbComments.getBoundsInLocal().getHeight());

      VBox commentPane = new VBox(2);
      commentPane.setPrefSize(commentPaneWidth, commentPaneHeight);
      commentPane.getChildren().addAll(lbComments, taComments);
      rootPane.getChildren().add(commentPane);
    }

    Button btClose = new Button("Schließen");
    btClose.setPrefWidth(70);
    btClose.setOnAction(event -> stage.hide());
    HBox closeButtonPane = new HBox();
    closeButtonPane.getChildren().add(btClose);

    rootPane.getChildren().add(closeButtonPane);

    Scene deviceDetailViewScene = new Scene(rootPane, windowWidth, windowHeight);
    stage.setScene(deviceDetailViewScene);
    stage.setTitle("Gerätedetails");
    stage.setResizable(false);
    stage.initOwner(owner);

    stage.show();
  }
}
