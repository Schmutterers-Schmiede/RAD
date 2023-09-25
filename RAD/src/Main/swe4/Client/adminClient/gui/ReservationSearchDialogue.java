package swe4.Client.adminClient.gui;

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
import swe4.Client.adminClient.AdminPreferences;
import swe4.Client.interfaces.IRepository;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.Reservation;

public class ReservationSearchDialogue {
  private final Stage stage;
  private final TextField tfSearchTerm;
  private final ComboBox<String> cbSearchFor;
  private ObservableList<Reservation> searchResults;
  private final IRepository repository;
  public ReservationSearchDialogue(Window owner){
    double windowWidth = 250;
    double windowHeight = 110;

    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());
    stage = new Stage();
    Label lbSearchTerm = new Label("Suchbegriff:");
    Label lbSearchFor = new Label("Suchen nach:");
    tfSearchTerm = new TextField();

    cbSearchFor = new ComboBox<>();
    cbSearchFor.setItems(FXCollections.observableArrayList("InventarNr", "Status"));
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
    String searchMode = cbSearchFor.getValue();
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
      searchResults = FXCollections.observableArrayList(repository.searchReservationsByInvId(searchTerm));
    }

    if(searchMode.equals("Status")){
      searchResults = FXCollections.observableArrayList(repository.searchReservationsByStatus(searchTerm));
    }

    if(searchMode.equals("Name")){
      searchResults = FXCollections.observableArrayList(repository.searchReservationsByName(searchTerm));
    }

    if(searchResults.isEmpty())
      InfoPrompt.show("Keine Geräte gefunden");
    else stage.hide();
  }

  public ObservableList<Reservation> show(){
    stage.showAndWait();
    if(searchResults == null) return null; //cancelled
    else if(searchResults.isEmpty()) return null; //empty
    else return searchResults;
  }
}
