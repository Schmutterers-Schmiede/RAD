package swe4.Client.sharedUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.RepositoryFactory;
import swe4.Client.adminClient.gui.InfoPrompt;
import swe4.Client.interfaces.IRepository;
import swe4.entities.Device;

public class DeviceSearchDialogue {
  private final Stage stage;
  private final TextField tfSearchTerm;
  private final ComboBox<String> cbSearchFor;
  private ObservableList<Device> searchResults;
  private final IRepository repository;
  private final boolean isUser;
  public DeviceSearchDialogue(Window owner, boolean isUser){
    this.isUser = isUser;
    double windowWidth = 250;
    double windowHeight = 110;

    repository = RepositoryFactory.getRepository();
    stage = new Stage();
    Label lbSearchTerm = new Label("Suchbegriff:");
    Label lbSearchFor = new Label("Suchen nach:");
    tfSearchTerm = new TextField();

    cbSearchFor = new ComboBox<>();
    cbSearchFor.setItems(FXCollections.observableArrayList("InventarNr", "Bezeichnung", "Marke", "Modell", "Kategorie"));
    cbSearchFor.setPromptText("Auswählen...");

    Button btSearch = new Button("Suchen");
    btSearch.setOnAction(event -> search());
    btSearch.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btCancel = new Button("Abbrechen");
    btCancel.setOnAction(event -> stage.hide());
    btCancel.setPrefWidth(UIDimensions.buttonWidthShort());

    GridPane inputPane = new GridPane();
    inputPane.add(lbSearchTerm, 0,0);
    inputPane.add(tfSearchTerm, 1,0);

    inputPane.add(lbSearchFor, 0,1);
    inputPane.add(cbSearchFor, 1,1);

    inputPane.setAlignment(Pos.CENTER);
    inputPane.setHgap(UIDimensions.gridPaneSpacing());
    inputPane.setVgap(UIDimensions.gridPaneSpacing());

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.setAlignment(Pos.CENTER);
    buttonPane.getChildren().addAll(btSearch, btCancel);

    VBox rootPane = new VBox(UIDimensions.buttonSpacing());
    rootPane.getChildren().addAll(inputPane, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());

    Scene scene = new Scene(rootPane, windowWidth, windowHeight);


    stage.setScene(scene);
    stage.setTitle("RAD Admin Gerätesuche");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);


  }

  private void search() {
    String searchMode = cbSearchFor.getValue().toString();
    String searchTerm = tfSearchTerm.getText();

    if(searchMode.isEmpty()){
      ErrorPrompt.show("Bitte Suchkriterium auswählen.");
      return;
    }

    if(searchTerm.isEmpty()){
      ErrorPrompt.show("Bitte Suchbegriff eingeben.");
      return;
    }

    if(searchMode.equals("InventarNr")){
      searchResults = FXCollections.observableArrayList(repository.searchDevicesByInventoryId(searchTerm, isUser));
    }

    if(searchMode.equals("Bezeichnung")){
      searchResults = FXCollections.observableArrayList(repository.searchDevicesByName(searchTerm, isUser));
    }

    if(searchMode.equals("Marke")){
      searchResults = FXCollections.observableArrayList(repository.searchDevicesByBrand(searchTerm, isUser));
    }

    if(searchMode.equals("Modell")){
      searchResults = FXCollections.observableArrayList(repository.searchDevicesByModel(searchTerm, isUser));
    }

    if(searchMode.equals("Kategorie")){
      searchResults = FXCollections.observableArrayList(repository.searchDevicesByCategory(searchTerm, isUser));
    }

    if(searchResults.isEmpty())
      InfoPrompt.show("Keine Geräte gefunden");
    else stage.hide();
  }

  public ObservableList<Device> show(){
    stage.showAndWait();
    if(searchResults == null) return null; //cancelled
    else if(searchResults.isEmpty()) return null; //empty
    else return searchResults;
  }
}
