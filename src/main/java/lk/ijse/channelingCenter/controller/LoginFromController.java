package lk.ijse.channelingCenter.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.channelingCenter.BO.Custom.LoginBo;
import lk.ijse.channelingCenter.BO.BOFactory;
import lk.ijse.channelingCenter.dto.LoginDto;

import java.io.IOException;
import java.sql.SQLException;

public class LoginFromController {
    public AnchorPane loginPane;
    @FXML
    private Button btnlogin;
    @FXML
    private Text forgotPass;
    @FXML
    private PasswordField textpassword;

    @FXML
    private TextField txtUsername;
    LoginBo loginBo = (LoginBo) BOFactory.getInstance().getBO(BOFactory.BOTypes.LOGIN);

    @FXML
    void btnloginOnAction(ActionEvent event) throws IOException {

        String userName = txtUsername.getText();
        String password = textpassword.getText();

        System.out.println(txtUsername.getText().isEmpty());
        if (!txtUsername.getText().isEmpty()) {
            try {
                boolean isIn = loginBo.searchUser(new LoginDto(null, userName, password, null));
                if (!isIn) {
                    new Alert(Alert.AlertType.WARNING, "Invalid User Name or Password");
                    textpassword.setStyle("-fx-border-color: red");
                    new animatefx.animation.Shake(textpassword).play();
                    ;

                    return;
                } else {
                    navigateToMainWindow();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Enter Your UserName");
        }
    }

    @FXML
    void txtForgotOnAction(MouseEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/forgotPasswordFrom.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Forgot Password");
        stage.centerOnScreen();
        stage.show();
    }

    private void navigateToMainWindow() throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/navigationPannelFrom.fxml"));
        Scene scene = new Scene(rootNode);

        loginPane.getChildren().clear();
        Stage primaryStage = (Stage) loginPane.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("ToDo");
    }

    public void txtcreateAccountOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/createAccountFrom.fxml"))));
        stage.show();

        Stage stage1 = (Stage) loginPane.getScene().getWindow();
        stage1.close();
    }
}


