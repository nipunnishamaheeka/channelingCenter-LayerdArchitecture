package lk.ijse.channelingCenter.controller;

import animatefx.animation.Shake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.channelingCenter.BO.Custom.LoginBo;
import lk.ijse.channelingCenter.BO.BOFactory;
import lk.ijse.channelingCenter.dto.LoginDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CreateAccountFromController {
    @FXML
    private AnchorPane createAccountPane;
    @FXML
    private Button btnlogin;

    @FXML
    private PasswordField txtConfromPassword;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtFullname;

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    LoginBo loginBo = (LoginBo) BOFactory.getInstance().getBO(BOFactory.BOTypes.LOGIN);
    @FXML
    void txtLoginOnMouseClicked(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/loginFrom.fxml"))));
        stage.show();

        Stage stage1 = (Stage) createAccountPane.getScene().getWindow();
        stage1.close();
    }
    private boolean validateTextField(TextField textField, String patternRegex) {
        String text = textField.getText();
        boolean isValid = Pattern.compile(patternRegex).matcher(text).matches();

        if (!isValid) {
            textField.setStyle("-fx-border-color: red");
            new Shake(textField).play();
            return false;
        } else {
            textField.setStyle("-fx-border-color: green");
            return true;
        }
    }

    private boolean validatePassword() {
        return validateTextField(txtFullname, "^[A-Za-z\\s]{3,50}$")
                && validateTextField(txtUsername, "^[A-Za-z\\s]{3,50}$")
                && validateTextField(txtEmail, "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")
                && validateTextField(txtpassword, "\\d{2,}");


    }
    @FXML
    void btncreateYourAccountOnAction(ActionEvent event) throws IOException {
        boolean isPasswordValid = validatePassword();
        if (isPasswordValid) {
            try {
                String fullname = txtFullname.getText();
                String username = txtUsername.getText();
                String password = txtpassword.getText();
                String email = txtEmail.getText();

                if (password.equals(txtConfromPassword.getText())) {

                    try {
                        boolean isSaved = loginBo.save(new LoginDto(fullname, username, password, email));
                        if (isSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "User Added Successfully").show();
                            return;
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Password Not Matched").show();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}
