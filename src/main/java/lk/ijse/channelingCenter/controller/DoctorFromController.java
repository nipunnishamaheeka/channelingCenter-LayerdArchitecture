package lk.ijse.channelingCenter.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.channelingCenter.BO.DoctorBO;
import lk.ijse.channelingCenter.BO.Impl.DoctorBOImpl;
import lk.ijse.channelingCenter.dto.DoctorDto;
import lk.ijse.channelingCenter.dto.tm.DoctorTm;
import lk.ijse.channelingCenter.DAO.Impl.DoctorDAOImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


public class DoctorFromController {
    public AnchorPane doctorPane;
    public Label lblDoctorId;
    public TextField txtFee;
    public TableColumn tblDrFee;
    public ComboBox cmbDoctorType;
    @FXML
    private TableColumn<?, ?> colUpdate;
    @FXML
    private TableColumn<?, ?> colDelete;
    @FXML
    private TableColumn<?, ?> tblAddress;

    @FXML
    private TableColumn<?, ?> tblEmail;

    @FXML
    private TableColumn<?, ?> tblId;

    @FXML
    private TableColumn<?, ?> tblName;

    @FXML
    private TableColumn<?, ?> tblNumber;

    @FXML
    private TableColumn<?, ?> tblType;

    @FXML
    private TableView<DoctorTm> tblDoctor;


    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtType;
    DoctorDAOImpl doctorDAOImpl = new DoctorDAOImpl();
    private DoctorBO doctorBO = new DoctorBOImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadAllItems();
        setDoctorID();
        loadDoctorTypes();
    }
    private void setCellValueFactory() {
        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblName.setCellValueFactory(new PropertyValueFactory<>("doctor_name"));
        tblAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tblEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tblType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tblDrFee.setCellValueFactory(new PropertyValueFactory<>("drFee"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        //colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));

    }

    private void loadDoctorTypes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        // Add "Dr Types" options
        obList.addAll("General Practitioner", "Cardiologist", "Dermatologist", "Endocrinologist", "Gastroenterologist", "Orthopedic Surgeon", "Neurologist", "Obstetrician/Gynecologist (OB/GYN)","Ophthalmologist", "Pediatrician","Psychiatrist","Urologist","Allergist/Immunologist","Anesthesiologist","Colon and Rectal Surgeon","Emergency Medicine Specialist","Hematologist","Infectious Disease Specialist","Medical Geneticist","Nephrologist","Otolaryngologist","Pathologist","Plastic Surgeon","Radiologist","Rheumatologist","Thoracic Surgeon","Urologist","Vascular Surgeon");
        cmbDoctorType.setItems(obList);
    }
    public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        boolean isDoctorValid = validateDoctor();
        if (isDoctorValid) {
            String id = lblDoctorId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String email = txtEmail.getText();
            String number = txtNumber.getText();
            String type = String.valueOf(cmbDoctorType.getValue());
            double fee = Double.parseDouble(txtFee.getText());

            DoctorDto itemDto = new DoctorDto(id, name, address, email, number, type,fee);

            try {
                boolean isSaved = doctorBO.save(itemDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Doctor Saved!", ButtonType.OK).show();
                    clearFields();
                    loadAllItems();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Doctor Not Saved!", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
           // new Alert(Alert.AlertType.ERROR, "Invalid Doctor Details", ButtonType.OK).show();
        }
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        boolean isDoctorValid = validateDoctor();
        String id = lblDoctorId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String number = txtNumber.getText();
        String type = String.valueOf(cmbDoctorType.getValue());
        double fee = Double.parseDouble(txtFee.getText());

        try {
            boolean isUpdated = doctorBO.update(new DoctorDto(id, name, address, email, number, type, fee));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Doctor updated!").show();
                clearFields();

                loadAllItems();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void setFields(DoctorDto dto) {
        lblDoctorId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtEmail.setText(dto.getEmail());
        txtNumber.setText(dto.getNumber());
        cmbDoctorType.setValue(dto.getType());
        txtFee.setText(String.valueOf(dto.getDrFee()));
    }

    private void clearFields() {
        lblDoctorId.setText("");
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtNumber.clear();
        cmbDoctorType.setValue(null);
        txtFee.clear();
    }

    private void setFontAwesomeIcons() {

        tblDoctor.getItems().forEach(item -> {
            Button deleteButton = item.getDeleteButton();

            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

            deleteButton.setGraphic(deleteIcon);

        });
    }

    private void loadAllItems() throws SQLException, ClassNotFoundException {

        ObservableList<DoctorTm> obList = FXCollections.observableArrayList();

        try {
            List<DoctorDto> dtoList = doctorBO.getAll();
            for (DoctorDto dto : dtoList) {
                Button deleteButton = new Button();
                Button updateButton = new Button();

                deleteButton.setCursor(Cursor.HAND);
                updateButton.setCursor(Cursor.HAND);

                deleteButton.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this doctor?", yes, no).showAndWait();
                    if (result.orElse(no) == yes) {
                        int selectedIndex = tblDoctor.getSelectionModel().getSelectedIndex();
                        System.out.println("badu hari");
                        String code = (String) tblId.getCellData(selectedIndex);
                        System.out.println(code);
                        try {
                            doctorDAOImpl.delete(code);
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        obList.remove(selectedIndex);
                        tblDoctor.refresh();
                    }
                });
                obList.add(
                        new DoctorTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getEmail(),
                                dto.getNumber(),
                                dto.getType(),
                                String.valueOf(dto.getDrFee()),
                                deleteButton
                        )
                );
            }
            tblDoctor.setItems(obList);
            setFontAwesomeIcons();

            // Add a click event listener to the table rows
            tblDoctor.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) { // Check for a single click
                    int selectedIndex = tblDoctor.getSelectionModel().getSelectedIndex();
                    if (selectedIndex != -1) {
                        // Assuming that you have a method to set the data to text fields
                        setDoctorDataToFields(selectedIndex);
                    }
                }
            });
            setDoctorID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    // Add a method to set the data to text fields
    private void setDoctorDataToFields(int selectedIndex) {
        lblDoctorId.setText(tblId.getCellData(selectedIndex).toString());
        txtName.setText(tblName.getCellData(selectedIndex).toString());
        txtAddress.setText(tblAddress.getCellData(selectedIndex).toString());
        txtEmail.setText(tblEmail.getCellData(selectedIndex).toString());
        txtNumber.setText(tblNumber.getCellData(selectedIndex).toString());
        cmbDoctorType.setValue(tblType.getCellData(selectedIndex));
        txtFee.setText(tblDrFee.getCellData(selectedIndex).toString());
    }
    public void btnClerOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void btnRefershOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        setDoctorID();
        loadAllItems();
    }

    private void setDoctorID() throws ClassNotFoundException {
        try {
            lblDoctorId.setText(new DoctorDAOImpl().generateNextId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean validateDoctor() {
        String nameText = txtName.getText();
        boolean isNameValid = nameText.matches("[A-Za-z][A-Za-z. ]{3,}");
        if (!isNameValid) {
            txtName.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtName).play();
            return false;
        }
        String emailText = txtEmail.getText();
        boolean isEmailValid = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}").matcher(emailText).matches();
        if (!isEmailValid) {
           txtEmail.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtEmail).play();
            return false;
        }
        String numberText = txtNumber.getText();
        boolean isNumberValid = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$").matcher(numberText).matches();
        if (!isNumberValid) {
            txtNumber.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtNumber).play();
            return false;
        }
        return true;

    }

    public void mobileNumberSearchOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String number = txtNumber.getText();
        try {
            DoctorDto dto = doctorBO.getDetailByContact(number);
            setFields(dto);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
