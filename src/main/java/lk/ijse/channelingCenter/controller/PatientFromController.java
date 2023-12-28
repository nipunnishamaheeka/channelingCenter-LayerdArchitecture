package lk.ijse.channelingCenter.controller;

import animatefx.animation.Shake;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.channelingCenter.dto.PatientDto;
import lk.ijse.channelingCenter.dto.tm.PatientTm;
import lk.ijse.channelingCenter.DAO.Impl.PatientDAOImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class PatientFromController {
    public TextField txtAge;
    public Label lblPatientId;
    public ComboBox<String> cmbBlood;
    public TableColumn colAddress;
    public TextField txtID;
    public TextField txtPatientId;
    public ComboBox<String>  cmbGender;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;
    private TextField txtId;
    @FXML
    private TextField txtType;
    public AnchorPane patientPane;

    @FXML
    private TableView<PatientTm> tblPatient;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colUpdate;

    @FXML
    private TableColumn<?, ?> colBloodGroup;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private TableColumn<?, ?> colPatientID;

    @FXML
    private TableColumn<?, ?> colPatientName;
    private TableCell<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colSex;

    PatientDAOImpl patientDAOImpl = new PatientDAOImpl();

    private void setCellValueFactory() {
        colPatientID.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("mobile_number"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("blood"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));

    }

    public void initialize() {
        setCellValueFactory();
        setFontAwesomeIcons();
        loadAllPatients();
        loadPatientsBloodGroup();
        loadGenderGroup();
        setPatientID();
    }

    private void setFontAwesomeIcons() {
        tblPatient.getItems().forEach(item -> {
            Button deleteButton = item.getDeleteButton();
            Button updateButton = item.getUpdateButton();

            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            FontAwesomeIconView updateIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);

            deleteButton.setGraphic(deleteIcon);
            updateButton.setGraphic(updateIcon);
        });
    }

    public void loadAllPatients() {
        ObservableList<PatientTm> obList = FXCollections.observableArrayList();

        try {
            List<PatientDto> dtoList = new PatientDAOImpl().getAllPatient();

            for (PatientDto dto : dtoList) {
                Button deleteButton = new Button();
                Button updateButton = new Button();

                deleteButton.setCursor(Cursor.HAND);
                updateButton.setCursor(Cursor.HAND);

                deleteButton.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this Patient?", yes, no).showAndWait();
                    if (result.orElse(no) == yes) {
                        int selectedIndex = tblPatient.getSelectionModel().getSelectedIndex();
                        String code = (String) colPatientID.getCellData(selectedIndex);
                        deletePatient(code);
                        obList.remove(selectedIndex);
                        tblPatient.refresh();
                    }
                });
                updateButton.setOnAction((e) -> {
                    int selectedIndex = tblPatient.getSelectionModel().getSelectedIndex();
                    String code = (String) colPatientID.getCellData(selectedIndex);
                    System.out.println(code);
                    try {
                        // patientPane.getChildren().clear();
                        patientPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/patientFromold.fxml")));
                    } catch (Exception e1) {
                    }
                });
                obList.add(
                        new PatientTm(
                                dto.getPatient_id(),
                                dto.getPatient_name(),
                                dto.getMobile_number(),
                                dto.getAddress(),
                                dto.getSex(),
                                dto.getEmail(),
                                dto.getAge(),
                                dto.getBlood(),

                                deleteButton,
                                updateButton
                        )
                );
            }

            tblPatient.setItems(obList);
            setFontAwesomeIcons();

//            modify area
            tblPatient.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) { // Check for a single click
                    int selectedIndex = tblPatient.getSelectionModel().getSelectedIndex();
                    if (selectedIndex != -1) {
                        PatientTm selectedPatient = obList.get(selectedIndex);

                        // Set the data to your text fields
                        lblPatientId.setText(selectedPatient.getPatient_id());
                        txtName.setText(selectedPatient.getPatient_name());
                        txtNumber.setText(selectedPatient.getMobile_number());
                        txtAddress.setText(selectedPatient.getAddress());
                        cmbGender.setValue(selectedPatient.getSex());
                        txtEmail.setText(selectedPatient.getEmail());
                        txtAge.setText(selectedPatient.getAge());
                        cmbBlood.setValue(selectedPatient.getBlood());
                    }
                }
            });

//            modify area









        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePatient(String code) {
        try {
            boolean b = patientDAOImpl.deletePatient(code);
            if (b) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted");
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClerOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        lblPatientId.setText("");
        txtName.setText("");
        txtNumber.setText("");
        txtAddress.setText("");
        cmbGender.setItems(null);
        txtEmail.setText("");
        txtAge.setText("");
        cmbBlood.setItems(null);

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

    private boolean validatePatient() {
        return validateTextField(txtName, "[A-Za-z]{3,}")
                && validateTextField(txtNumber, "^(?:7|0|(?:\\+94))[0-9]{9,10}$")
                && validateTextField(txtAge, "\\d{2}")
                && validateTextField(txtEmail, "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")
                && validateTextField(txtAddress, "[A-Za-z0-9]{3,}");
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isPatientValid = validatePatient();

        if (isPatientValid) {
            try {
                String id = lblPatientId.getText();
                String name = txtName.getText();
                String number = txtNumber.getText();
                String address = txtAddress.getText();
                String sex = cmbGender.getValue();
                String email = txtEmail.getText();
                String age = txtAge.getText();
                String blood = cmbBlood.getValue(); // Get the selected blood group

                PatientDto itemDto = new PatientDto(id, name, number, address, sex, email, age, blood);

                PatientDAOImpl patientDAOImpl = new PatientDAOImpl();
                boolean isSaved = patientDAOImpl.savePatient(itemDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Patient saved!").show();
                    clearFields();
                    loadAllPatients();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Patient not saved!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            lblPatientId.requestFocus();
        }
    }

    public void numberSearchOnAction(ActionEvent actionEvent) {
        String number = txtNumber.getText();

        try {
            PatientDto dto = patientDAOImpl.searchNumber(number);
            if (dto != null) {
                setFields(dto);

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Patient not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setFields(PatientDto dto) {
        lblPatientId.setText(dto.getPatient_id());
        txtName.setText(dto.getPatient_name());
        txtNumber.setText(dto.getMobile_number());
        txtAddress.setText(dto.getAddress());
        cmbGender.setValue(dto.getSex());
        txtEmail.setText(dto.getEmail());
       cmbBlood.setValue(dto.getBlood());
        txtAge.setText(dto.getAge());
    }

    private void loadPatientsBloodGroup() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        // Add "Blood Group" options
        obList.addAll("A_POSITIVE (A+)", "A_NEGATIVE (A-)", "B_POSITIVE (B+)", "B_NEGATIVE (B-)", "AB_POSITIVE (AB+)", "AB_NEGATIVE (AB-)", "O_POSITIVE (O+)", "O_NEGATIVE (O-)");
        cmbBlood.setItems(obList);
    }

    private void loadGenderGroup() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        // Add "Male" and "Female" options
        obList.addAll("Male", "Female");
        cmbGender.setItems(obList);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        //boolean isPatientValid = validatePatinet();
        if (validatePatient()) {
            String Patient_id = lblPatientId.getText();
            String Patient_name = txtName.getText();
            String Mobile_number = txtNumber.getText();
            String Address = txtAddress.getText();
            String Gender = cmbGender.getValue();
            String Email = txtEmail.getText();
            String Blood = cmbBlood.getValue();
            String Age = txtAge.getText();

            try {
                boolean isUpdated = patientDAOImpl.updatePatient(new PatientDto(Patient_id, Patient_name, Mobile_number, Address, Gender, Email, Blood, Age));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Patient updated").show();
                    clearFields();
                    loadAllPatients();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void setPatientID() {
        try {
            lblPatientId.setText(new PatientDAOImpl().autoGenaratePatientId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbBloodGroupOnAction(ActionEvent actionEvent) {

    }

}
