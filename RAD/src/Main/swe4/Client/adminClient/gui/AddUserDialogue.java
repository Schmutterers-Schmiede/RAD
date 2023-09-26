package swe4.Client.adminClient.gui;

import javafx.collections.FXCollections;
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
import swe4.Client.adminClient.AdminPreferences;
import swe4.Client.RepositoryFactory;
import swe4.Client.interfaces.IRepository;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;


public class AddUserDialogue {

  private final Stage stage = new Stage();
  private final TextField tfName;
  private final TextField tfUsername;
  private final TextField tfPassword;
  private final ComboBox<String> cbType;

  private final IRepository repository;

  public AddUserDialogue(Window owner) {
    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());

    tfName = new TextField();
    tfUsername = new TextField();
    tfPassword = new TextField();
    cbType = new ComboBox<>();
    cbType.setItems(FXCollections.observableArrayList("FH-Mitarbeiter", "Student"));
    cbType.setPromptText("Auswählen...");

    Button btnConfirmAddUser = new Button("Hinzufügen");
    btnConfirmAddUser.setId("btn_confirm-add-user");

    btnConfirmAddUser.setOnAction(event -> addUser(tfName.getText(),
            tfUsername.getText(),
            tfPassword.getText(),
            cbType.getSelectionModel().getSelectedItem()));
    btnConfirmAddUser.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btnCancel = new Button("Abbrechen");
    btnCancel.setId("btn_cancel-add-user");
    btnCancel.setOnAction(event -> stage.hide());
    btnCancel.setPrefWidth(UIDimensions.buttonWidthShort());

    Label lbName = new Label("Name:");
    Label lbUsername = new Label("Username:");
    Label lbPassword = new Label("Password");
    Label lbType = new Label("Art:");

    GridPane formPane = new GridPane();
    formPane.setHgap(UIDimensions.gridPaneSpacing());
    formPane.setVgap(UIDimensions.gridPaneSpacing());

    formPane.add(lbName, 0, 0);
    formPane.add(lbUsername, 0, 1);
    formPane.add(lbPassword, 0, 2);
    formPane.add(lbType, 0, 3);
    formPane.add(tfName, 1, 0);
    formPane.add(tfUsername, 1, 1);
    formPane.add(tfPassword, 1, 2);
    formPane.add(cbType, 1, 3);

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btnConfirmAddUser, btnCancel);
    buttonPane.setAlignment(Pos.CENTER);

    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.getChildren().addAll(formPane, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());

    Scene addUserScene = new Scene(rootPane);
    stage.setScene(addUserScene);
    stage.setTitle("Benutzer hinzufügen");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);
  }

  void addUser(String name, String username, String password, String type) {
    if (name == null ||
            username == null ||
            password == null ||
            type == null) {
      ErrorPrompt.show("Unvollständige Eingabe");
    } else if (repository.addUser(
            name,
            username,
            password,
            type)) {
      stage.hide();
    } else {
      ErrorPrompt.show("Dieser Benutzername existiert bereits.");
    }

  }

  public void show() {
    tfName.requestFocus();
    stage.showAndWait();
  }

}
