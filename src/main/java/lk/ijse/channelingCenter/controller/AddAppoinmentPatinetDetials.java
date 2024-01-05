package lk.ijse.channelingCenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import lk.ijse.channelingCenter.BO.Impl.PatientBOImpl;
import lk.ijse.channelingCenter.BO.PatientBO;
import lk.ijse.channelingCenter.dto.PatientDto;
import lk.ijse.channelingCenter.DAO.Impl.PatientDAOImpl;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAppoinmentPatinetDetials {
    public Label lblPatientId;
    public TextField txtAge;
    public TextField txtName;

    public static String name = null;
    public TextField txtEmail;
    public TextField txtAddress;
    public ComboBox cmbBlood;
    public TextField txtType;
    public Button btnOk;
    public TextField txtNumber;

private PatientBO patientBO = new PatientBOImpl();
    public void initialize() throws SQLException {

        setPatientID();
        loadPatientsBloodGroup();
    }


    private void loadPatientsBloodGroup() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        // Add "Blood Group" options
        obList.addAll("A_POSITIVE (A+)", "A_NEGATIVE (A-)", "B_POSITIVE (B+)", "B_NEGATIVE (B-)", "AB_POSITIVE (AB+)", "AB_NEGATIVE (AB-)", "O_POSITIVE (O+)", "O_NEGATIVE (O-)");
        cmbBlood.setItems(obList);
    }

    public void btnOkOnAction(ActionEvent actionEvent) {
        boolean isPatientValid = validatePatinet();

        if (isPatientValid) {
            String id = lblPatientId.getText();
            String name = txtName.getText();
            String number = txtNumber.getText();
            String address = txtAddress.getText();
            String sex = txtType.getText();
            String email = txtEmail.getText();
            String age = txtAge.getText();
            String blood = (String) cmbBlood.getValue();

            PatientDto itemDto = new PatientDto(id, name, number, address, sex, email, age,blood);

            try {

                boolean isSaved = patientBO.save(itemDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Patient saved!").show();
                    clearFields();
//                   AddAppoinmentFromController addAppoinmentFromController = new AddAppoinmentFromController();
//                     addAppoinmentFromController.initialize();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Patient not saved!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
           // new Alert(Alert.AlertType.ERROR, "Invalid Patient Details").show();
            lblPatientId.requestFocus();
        }

    }

    private void clearFields() {
        lblPatientId.setText("");
        txtName.setText("");
        txtNumber.setText("");
        txtAddress.setText("");
        txtType.setText("");
        txtEmail.setText("");
        cmbBlood.setItems(null);
        txtAge.setText("");
    }
    private void setFields(PatientDto dto) {
        lblPatientId.setText(dto.getPatient_id());
        txtName.setText(dto.getPatient_name());
        txtNumber.setText(dto.getMobile_number());
        txtAddress.setText(dto.getAddress());
        txtType.setText(dto.getSex());
        txtEmail.setText(dto.getEmail());
        // cmbBlood.setItems(dto.getBlood());
        txtAge.setText(dto.getAge());
    }
    private void setPatientID() {
        try {
            lblPatientId.setText(new PatientDAOImpl().generateNextId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean validatePatinet() {
        String nameText = txtName.getText();
        boolean isNameValid = Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();
        if (!isNameValid) {
            txtName.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtName).play();
            return false;
        }
        String gender_title = txtType.getText();
        Pattern compile1 = Pattern.compile("Male|Female");
        Matcher matcher1 = compile1.matcher(gender_title);
        boolean isTitelValid = matcher1.matches();

        if (!isTitelValid) {
            txtType.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtType).play();
            return false;
        }
        String numberText = txtNumber.getText();
        boolean isNumberValid = Pattern.compile(" ^(?:7|0|(?:\\+94))[0-9]{9,10}$").matcher(numberText).matches();
        if (!isNumberValid) {
            txtNumber.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtNumber).play();
            return false;
        }
        String ageText = txtAge.getText();
        boolean isAgeValid = Pattern.compile("\\d{2}").matcher(ageText).matches();
        if (!isAgeValid) {
            txtAge.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtAge).play();
            return false;
        }
        String emailText = txtEmail.getText();
        boolean isEmailValid = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}").matcher(emailText).matches();
        if (!isEmailValid) {
            txtEmail.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtEmail).play();
            return false;
        }
        return true;
    }

    public void nameSearchOnAction(ActionEvent actionEvent) {
    }

    public void cmbBloodGroupOnAction(ActionEvent actionEvent) {
    }
}
