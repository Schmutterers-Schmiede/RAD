package swe4.Client.adminClient.gui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import swe4.Client.adminClient.RepositoryFactory;
import swe4.Client.sharedUI.ConfirmationPrompt;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.entities.User;
import swe4.Client.interfaces.Repository;


public class UserManager {

  private final Stage userManagerStage = new Stage();
  private final TableView<User> tbv;
  private final Repository repository;
  private final int windowWidth = 600;
  private final int windowHeight = 700;
  private final double tbvWidth = windowWidth ;
  private final double tbvHeight = windowHeight - 100;
  public UserManager(Window owner){
    tbv = new TableView<>();
    tbv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tbv.setPrefSize(tbvWidth, tbvHeight);
    TableColumn nameCol = new TableColumn("Name");
    TableColumn usernameCol = new TableColumn("Benutzername");
    TableColumn passwordCol = new TableColumn("Passwort");
    TableColumn typeCol = new TableColumn("Art");

    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
    passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

    tbv.getColumns().addAll(nameCol, usernameCol, passwordCol, typeCol);

    Button btnAddUser = new Button("Hinzufügen");
    btnAddUser.setId("btn_add-user");
    btnAddUser.setOnAction(event -> addUser());

    Button btnEditUser = new Button("Bearbeiten");
    btnEditUser.setId("btn_edit-user");
    btnEditUser.setOnAction(event -> editUser(tbv.getSelectionModel().getSelectedItem()));

    Button btnDeleteUser = new Button( "Löschen");
    btnDeleteUser.setId("btn_delete-user");
    btnDeleteUser.setOnAction(event -> deleteUser(tbv.getSelectionModel().getSelectedItem().getUsername()));

    Button btnBack = new Button("Zurück");
    btnBack.setId("btn_back");
    btnBack.setOnAction(event -> userManagerStage.hide());

    VBox tablePane = new VBox(tbv);
    tablePane.setPrefSize(tbvWidth, tbvHeight);

    HBox tableContainer = new HBox(tablePane);
    tableContainer.setAlignment(Pos.CENTER);
    tableContainer.setPadding(new Insets (8,8,0,8));

    GridPane buttonPane = new GridPane();
    buttonPane.setPadding(new Insets(8,8,0,8));
    buttonPane.setVgap(20);
    buttonPane.setHgap(8);
    buttonPane.add(btnAddUser,0,0);
    buttonPane.add(btnEditUser,1,0);
    buttonPane.add(btnDeleteUser,2,0);
    buttonPane.add(btnBack, 0,1);

    VBox rootPane = new VBox();
    rootPane.getChildren().addAll(tableContainer, buttonPane);
    Scene userManagerScene = new Scene(rootPane, windowWidth, windowHeight);

    userManagerStage.setScene(userManagerScene);
    userManagerStage.setTitle("RAD Admin User Manager");
    userManagerStage.initModality(Modality.WINDOW_MODAL);
    userManagerStage.initOwner(owner);
    userManagerStage.setResizable(false);

    repository = RepositoryFactory.getRepository();
  }
  public void show() {
    tbv.setItems(repository.getAllUsers());
    userManagerStage.show();
  }

  private void addUser(){
    AddUserDialogue addUserDialogue = new AddUserDialogue(userManagerStage);
    addUserDialogue.show();
    tbv.refresh();
    System.out.println("add refresh");
  }

  private void deleteUser(String username){
    if(new ConfirmationPrompt().show("Sind Sie sicher, dass sie diesen Benutzer löschen wollen?")){
      repository.deleteUser(username);
      tbv.refresh();
    }
  }

  private void editUser(User user){
    if(user == null) {
      ErrorPrompt.show("Es ist kein Benutzer ausgewählt.");
    }
    else {
      EditUserDialogue editUserDialogue = new EditUserDialogue(userManagerStage, user);
      editUserDialogue.show();
      tbv.refresh();
      System.out.println("edit refresh");
    }
  }
}
