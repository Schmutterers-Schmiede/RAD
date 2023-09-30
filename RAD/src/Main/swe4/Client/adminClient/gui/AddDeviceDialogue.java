package swe4.Client.adminClient.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import swe4.Client.RepositoryFactory;
import swe4.Client.interfaces.IRepository;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AddDeviceDialogue {


  private final Stage stage = new Stage();
  private final TextField tfInvId;
  private final TextField tfInvCode;
  private final TextField tfName;
  private final TextField tfBrand;
  private final TextField tfModel;
  private final TextField tfSNr;
  private final TextField tfroomNr;
  private final DatePicker dpBuy;
  private final TextField tfPrice;
  private final ComboBox<String> cbStatus;
  private final ComboBox<String> cbCategory;
  private final TextArea taComments;
  private final IRepository repository;

  public AddDeviceDialogue(Window owner) {
    double windowWidth = 400;
    double windowHeight = 550;

    repository = RepositoryFactory.getRepository();
    ObservableList<String> deviceCategories = FXCollections.observableArrayList(repository.getDeviceCategories());

    Label lbInvId = new Label("InventarNr:");
    Label lbInvCode = new Label("Inventarcode:");
    Label lbName = new Label("Bezeichnung:");
    Label lbModel = new Label("Modell:");
    Label lbBrand = new Label("Marke:");
    Label lbSNr = new Label("SerienNr:");
    Label lbroomNr = new Label("RaumNr:");
    Label lbBuyDate = new Label("Kaufdatum:");
    Label lbPrice = new Label("Preis:");
    Label lbStatus = new Label("Status:");
    Label lbCategory = new Label("Kategorie");
    Label lbComments = new Label("Anmerkungen:");

    tfInvId = new TextField();
    tfInvCode = new TextField();
    tfName = new TextField();
    tfBrand = new TextField();
    tfModel = new TextField();
    tfSNr = new TextField();
    tfroomNr = new TextField();
    dpBuy = new DatePicker();
    tfPrice = new TextField();
    cbStatus = new ComboBox<>();
    cbCategory = new ComboBox<>();
    taComments = new TextArea();

    cbStatus.setItems(FXCollections.observableArrayList("verfügbar", "defekt"));
    cbStatus.setPromptText("Auswählen...");

    cbCategory.setItems(FXCollections.observableArrayList(deviceCategories));
    cbCategory.setPromptText("Auswählen...");

    GridPane formPane = new GridPane();
    formPane.setHgap(5);
    formPane.setVgap(5);

    formPane.add(lbInvId, 0, 0);
    formPane.add(tfInvId, 1, 0);

    formPane.add(lbInvCode, 0, 1);
    formPane.add(tfInvCode, 1, 1);

    formPane.add(lbName, 0, 2);
    formPane.add(tfName, 1, 2);

    formPane.add(lbModel, 0, 3);
    formPane.add(tfModel, 1, 3);

    formPane.add(lbBrand, 0, 4);
    formPane.add(tfBrand, 1, 4);

    formPane.add(lbSNr, 0, 5);
    formPane.add(tfSNr, 1, 5);

    formPane.add(lbroomNr, 0, 6);
    formPane.add(tfroomNr, 1, 6);

    formPane.add(lbBuyDate, 0, 7);
    formPane.add(dpBuy, 1, 7);

    formPane.add(lbPrice, 0, 8);
    formPane.add(tfPrice, 1, 8);

    formPane.add(lbCategory, 0, 9);
    formPane.add(cbCategory, 1, 9);

    VBox commentPane = new VBox();
    commentPane.getChildren().addAll(lbComments, taComments);
    GridPane.setColumnSpan(commentPane, 2);
    formPane.add(commentPane, 0, 10);


    Button btnConfirmAddDevice = new Button("Hinzufügen");
    btnConfirmAddDevice.setOnAction(event -> addDevice(
            tfInvId.getText(),
            tfInvCode.getText(),
            tfName.getText(),
            tfBrand.getText(),
            tfModel.getText(),
            tfSNr.getText(),
            tfroomNr.getText(),
            dpBuy.getValue(),
            tfPrice.getText(),
            taComments.getText(),
            cbCategory.getValue().toString()
    ));

    Button btnCancel = new Button("Abbrechen");
    btnCancel.setOnAction(event -> stage.hide());

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btnConfirmAddDevice, btnCancel);
    buttonPane.setAlignment(Pos.CENTER);

    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.getChildren().addAll(formPane, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());

    Scene addUserScene = new Scene(rootPane, windowWidth, windowHeight);
    stage.setScene(addUserScene);
    stage.setTitle("Gerät hinzufügen");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);
  }

  void addDevice(String inventoryId,
                 String inventoryCode,
                 String name,
                 String brand,
                 String model,
                 String serialNr,
                 String roomNr,
                 LocalDate buyDate,
                 String price,
                 String comments,
                 String category) {

    if (inventoryId.isEmpty() ||
            inventoryCode.isEmpty() ||
            name.isEmpty() ||
            brand.isEmpty() ||
            serialNr.isEmpty() ||
            roomNr.isEmpty() ||
            !(DateChecker.notNull(buyDate)) ||
            price.isEmpty() ||
            category.isEmpty() ||
            cbCategory.getValue() == null) {
      ErrorPrompt.show("Unvollständige Eingabe");
      return;
    }

    Pattern decimalPattern = Pattern.compile("[0-9]*(\\.[0-9]{1,2})?");
    if (!decimalPattern.matcher(price).matches()) {
      ErrorPrompt.show("Ungültiger Preis");
      return;
    }

    BigDecimal bdprice = new BigDecimal(price);
    if (repository.addDevice(inventoryId,
            inventoryCode,
            name,
            brand,
            model,
            serialNr,
            roomNr,
            buyDate,
            LocalDate.now(),
            bdprice,
            comments,
            category)) {
      stage.hide();
    } else {
      ErrorPrompt.show("Dieses Gerät existiert bereits.");
    }

  }

  public void show() {
    tfInvId.requestFocus();
    stage.showAndWait();
  }
}
