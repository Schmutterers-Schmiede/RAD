package swe4.Client.adminClient.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.adminClient.AdminPreferences;
import swe4.Client.RepositoryFactory;
import swe4.Client.sharedUI.ConfirmationPrompt;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.Client.sharedUI.UIDimensions;
import swe4.entities.User;
import swe4.Client.interfaces.IRepository;


public class UserManager {

  private final Stage stage = new Stage();
  private final TableView<User> tbv;
  private final IRepository repository;
  private final int windowWidth = 600;
  private final int windowHeight = 700;
  private ObservableList<User> users;


  public UserManager(Window owner) {
    double tbvWidth = windowWidth;
    double tbvHeight = windowHeight - 50;

    tbv = new TableView<>();
    tbv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tbv.setPrefSize(tbvWidth, tbvHeight);
    TableColumn nameCol = new TableColumn("Name");
    TableColumn usernameCol = new TableColumn("Benutzername");
    TableColumn passwordCol = new TableColumn("Passwort");
    TableColumn roleCol = new TableColumn("Rolle");

    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
    passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
    roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

    tbv.getColumns().addAll(nameCol, usernameCol, passwordCol, roleCol);

    Button btAddUser = new Button("Hinzufügen");
    btAddUser.setId("btn_add-user");
    btAddUser.setOnAction(event -> addUser());
    btAddUser.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btEditUser = new Button("Bearbeiten");
    btEditUser.setId("btn_edit-user");
    btEditUser.setOnAction(event -> editUser(tbv.getSelectionModel().getSelectedItem()));
    btEditUser.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btDeleteUser = new Button("Löschen");
    btDeleteUser.setId("btn_delete-user");
    btDeleteUser.setOnAction(event -> deleteUser(tbv.getSelectionModel().getSelectedItem().getUsername()));
    btDeleteUser.setPrefWidth(UIDimensions.buttonWidthShort());

    Button btBack = new Button("Zurück");
    btBack.setId("btn_back");
    btBack.setOnAction(event -> stage.hide());
    btBack.setPrefWidth(UIDimensions.buttonWidthShort());

    VBox tablePane = new VBox(tbv);
    tablePane.setPrefSize(tbvWidth, tbvHeight);

    HBox tableContainer = new HBox(tablePane);
    tableContainer.setAlignment(Pos.CENTER);

    HBox buttonPane = new HBox(UIDimensions.buttonSpacing());
    buttonPane.getChildren().addAll(btAddUser, btEditUser, btDeleteUser, UIDimensions.createSpacer(), btBack);

    VBox rootPane = new VBox(UIDimensions.containerSpacing());
    rootPane.getChildren().addAll(tableContainer, buttonPane);
    rootPane.setPadding(UIDimensions.windowPadding());

    Scene userManagerScene = new Scene(rootPane, windowWidth, windowHeight);

    stage.setScene(userManagerScene);
    stage.setTitle("RAD Admin User Manager");
    stage.initModality(Modality.WINDOW_MODAL);
    stage.initOwner(owner);
    stage.setResizable(false);

    repository = RepositoryFactory.getRepository(AdminPreferences.usingServer());
  }

  public void show() {
    users = FXCollections.observableArrayList(repository.getAllUsers());
    tbv.setItems(users);
    stage.show();
  }

  private void addUser() {
    AddUserDialogue addUserDialogue = new AddUserDialogue(stage);
    addUserDialogue.show();
    users = FXCollections.observableArrayList(repository.getAllUsers());
    tbv.refresh();
  }

  private void deleteUser(String username) {
    if (ConfirmationPrompt.show("Sind Sie sicher, dass sie diesen Benutzer löschen wollen?")) {
      users.removeIf(user -> user.getUsername().equals(username));//delete from UI
      repository.deleteUser(username); //delete from database
      users = FXCollections.observableArrayList(repository.getAllUsers());
      tbv.refresh();
    }
  }

  private void editUser(User user) {
    if (user == null) {
      ErrorPrompt.show("Es ist kein Benutzer ausgewählt.");
    } else {
      EditUserDialogue editUserDialogue = new EditUserDialogue(stage, user);
      editUserDialogue.show();
      users = FXCollections.observableArrayList(repository.getAllUsers());
      tbv.refresh();
    }
  }
}
