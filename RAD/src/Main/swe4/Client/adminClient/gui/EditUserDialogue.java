package swe4.Client.adminClient.gui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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
import swe4.Client.adminClient.RepositoryFactory;
import swe4.Client.interfaces.Repository;
import swe4.Client.sharedUI.ErrorPrompt;
import swe4.entities.User;

public class EditUserDialogue {
  private final Stage editUserStage = new Stage();
  private final String usernameBeforeUpdate;
  private final TextField tfName;
  private final TextField tfUsername;
  private final TextField tfPassword;
  private final ComboBox<String> cbType;
  private final Repository repository;

  public EditUserDialogue(Window owner, User user){
    repository = RepositoryFactory.getRepository();

    usernameBeforeUpdate = user.getUsername();

    tfName = new TextField();
    tfUsername = new TextField();
    tfPassword = new TextField();
    cbType = new ComboBox<>();
    cbType.setItems(FXCollections.observableArrayList("FH-Mitarbeiter", "Student"));
    cbType.setPromptText("Auswählen...");

    tfName.setText(user.getName());
    tfUsername.setText(user.getUsername());
    tfPassword.setText(user.getPassword());
    cbType.setValue(user.getType());

    Button btnConfirmUpdateUser = new Button("Ändern");
    btnConfirmUpdateUser.setId("btn_confirm-add-user");

    btnConfirmUpdateUser.setOnAction(event -> updateUser( usernameBeforeUpdate,
                                                          tfName.getText(),
                                                          tfUsername.getText(),
                                                          tfPassword.getText(),
                                                          cbType.getSelectionModel().getSelectedItem()));

    Button btnCancel = new Button("Abbrechen");
    btnCancel.setId("btn_cancel-add-user");
    btnCancel.setOnAction(event -> editUserStage.hide());

    Label lbName = new Label("Name:");
    Label lbUsername = new Label("Username:");
    Label lbPassword = new Label("Password");
    Label lbType = new Label("Art:");

    GridPane formPane = new GridPane();
    formPane.setHgap(5);
    formPane.setVgap(5);

    formPane.add(lbName, 0,0);
    formPane.add(lbUsername, 0,1);
    formPane.add(lbPassword, 0, 2);
    formPane.add(lbType, 0,3);
    formPane.add(tfName,1,0);
    formPane.add(tfUsername, 1, 1);
    formPane.add(tfPassword, 1,2);
    formPane.add(cbType,1,3);

    HBox buttonPane = new HBox(10);
    buttonPane.getChildren().addAll(btnConfirmUpdateUser, btnCancel);
    buttonPane.setAlignment(Pos.CENTER);

    VBox rootPane = new VBox(10);
    rootPane.getChildren().addAll(formPane, buttonPane);
    rootPane.setPadding(new Insets(10,10,10,10));

    Scene editUserScene = new Scene(rootPane);
    editUserStage.setScene(editUserScene);
    editUserStage.setTitle("Edit User");
    editUserStage.initModality(Modality.WINDOW_MODAL);
    editUserStage.initOwner(owner);
    editUserStage.setResizable(false);
  }

  public void show(){
    tfName.requestFocus();
    editUserStage.showAndWait();
  }

  private void updateUser(String usernameBeforeUpdate, String name, String username, String password, String type){
    if( name == null ||
            username == null ||
            password == null ||
            type == null){
      ErrorPrompt.show("Unvollständige Eingabe");
    }
    repository.updateUser(
            usernameBeforeUpdate,
            name,
            username,
            password,
            type);
    editUserStage.hide();
  }
}
