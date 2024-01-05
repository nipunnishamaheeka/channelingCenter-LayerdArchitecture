package lk.ijse.channelingCenter.controller;

import animatefx.animation.Shake;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.channelingCenter.BO.AppoinmentBO;
import lk.ijse.channelingCenter.BO.CompleteOrderBO;
import lk.ijse.channelingCenter.BO.Impl.AppoinmentBOImpl;
import lk.ijse.channelingCenter.BO.Impl.CompleteOrderBOImpl;
import lk.ijse.channelingCenter.BO.Impl.MedicineBOImpl;
import lk.ijse.channelingCenter.BO.MedicineBO;
import lk.ijse.channelingCenter.dto.*;
import lk.ijse.channelingCenter.dto.tm.MedicineTm;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class MedicineFromController {
    public AnchorPane medicinePane;
    public Label lblCode;
    public TextField txtMedicineName;
    public TableView tblMedicine;
    public TableColumn colMediCode;
    public TableColumn colMedicineName;
    public TextField txtDescription;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn ColDelete;
    public TextField txtUniPrice;
    public TextField txtQty;
    public ComboBox cmbMedicineName;


    @FXML
    private Label completeOrders;
    @FXML
    private Label pendingOrders;
    @FXML
    private Label totalOrders;

    @FXML
    private Label totalStock;
   private AppoinmentBO appoinmentBO = new AppoinmentBOImpl();
    private MedicineBO medicineBO = new MedicineBOImpl();
    CompleteOrderBO completeOrderBO = new CompleteOrderBOImpl();
    public void initialize() throws SQLException, ClassNotFoundException {
        //Compete Order eka initialize  krpan
        //completeOrders.setText(completeOrderBO.getAllCompleteOrdersCount());
        pendingOrders.setText(appoinmentBO.getPendingOrders());
        totalStock.setText(medicineBO.getAllCount());
        totalOrders.setText(appoinmentBO.getAllCount());
        loadAllMedicine();
        setMedicineCode();
        setCellValueFactory();
        loadMedicineTypes();

    }

private void loadMedicineTypes() {
    ObservableList<String> obList = FXCollections.observableArrayList();

    // Add "Dr Types" options
    obList.addAll("Acetaminophen",
            "Paracetamol",
            "Ibuprofen",
            "Aspirin",
            "Amoxicillin",
            "Ciprofloxacin",
            "Lisinopril",
            "Atorvastatin",
            "Levothyroxine",
            "Metformin",
            "Omeprazole",
            "Simvastatin",
            "Gabapentin",
            "Hydrochlorothiazide",
            "Losartan",
            "Amlodipine",
            "Metoprolol",
            "Escitalopram",
            "Sertraline",
            "Citalopram",
            "Fluoxetine",
            "Insulin",
            "Warfarin",
            "Diazepam",
            "Furosemide",
            "Prednisone",
            "Acetaminophen",
            "Hydrocodone",
            "Oxycodone");
    cmbMedicineName.setItems(obList);
}
    private void setMedicineCode() {
        try {
            lblCode.setText(medicineBO.generateNextId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isMedicineValid = validateMedicine();

        if (isMedicineValid) {
            String id = lblCode.getText();
            String medicineName = String.valueOf(cmbMedicineName.getValue());
            String description = txtDescription.getText();
            String qty = txtQty.getText();
            String unitPrice = txtUniPrice.getText();

            MedicineDto itemDto = new MedicineDto(id, medicineName, description, qty, unitPrice);

            try {
                boolean isSaved = medicineBO.save(itemDto);
                //System.out.println(isSaved);
                if (isSaved) {

                    new Alert(Alert.AlertType.CONFIRMATION, "Medicine saved!").show();
                    clearFields();
                    loadAllMedicine();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Medicine not saved!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            // new Alert(Alert.AlertType.ERROR, "Invalid Medicine Details").show();
            lblCode.requestFocus();
        }
    }

    private void clearFields() {
        lblCode.setText("");
        cmbMedicineName.setValue("");
        txtDescription.setText("");
        txtQty.setText("");
        txtUniPrice.setText("");

    }

    private boolean validateTextField(TextField textField, String patternRegex) {
        String text = textField.getText();
        boolean isValid = Pattern.compile(patternRegex).matcher(text).matches();
        if (!isValid) {
            textField.setStyle("-fx-border-color: red");
            new Shake(textField).play();
        } else {
            textField.setStyle("-fx-border-color: green");
        }
        return isValid;
    }

    private boolean validateMedicine() {
        return validateTextField(txtQty, "^\\d+(\\.\\d+)?$")
                && validateTextField(txtUniPrice, "^\\d+(\\.\\d{1,2})?$");
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = lblCode.getText();
        String medicineName = String.valueOf(cmbMedicineName.getValue());
        String description = txtDescription.getText();
        String qty = txtQty.getText();
        String unitPrice = txtUniPrice.getText();

        MedicineDto itemDto = new MedicineDto(id, medicineName, description, qty, unitPrice);

        try {
            boolean isUpdated = medicineBO.update(itemDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Medicine updated!").show();
                clearFields();
                loadAllMedicine();
            } else {
                new Alert(Alert.AlertType.ERROR, "Medicine not updated!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {


        colMediCode.setCellValueFactory(new PropertyValueFactory<>("medi_code"));
        colMedicineName.setCellValueFactory(new PropertyValueFactory<>("medicine_name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        ColDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

    }

    private void setFontAwesomeIcons() {
        tblMedicine.getItems().forEach(item -> {
            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        });
    }

    private void loadAllMedicine() throws SQLException {
        try {
            List<MedicineDto> dtoList = medicineBO.getAll();

            ObservableList<MedicineTm> obList = FXCollections.observableArrayList();

            for (MedicineDto dto : dtoList) {
                Button deleteButton = new Button("");
                FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setCursor(Cursor.HAND);

                deleteButton.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        int selectedIndex = tblMedicine.getSelectionModel().getSelectedIndex();
                        String code = dto.getMedi_code(); // Use the code directly from the DTO

                        deleteItem(code);   // Delete item from the database

                        obList.remove(selectedIndex);   // Delete item from the JFX-Table
                    }
                });

                var tm = new MedicineTm(
                        dto.getMedi_code(),
                        dto.getMedicine_name(),
                        dto.getDescription(),
                        dto.getQty(),
                        dto.getUnit_price(),
                        deleteButton
                );
                obList.add(tm);
            }

            tblMedicine.setItems(obList);
            setFontAwesomeIcons();

            // Add a click event listener to the table rows
            tblMedicine.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) { // Check for a single click
                    int selectedIndex = tblMedicine.getSelectionModel().getSelectedIndex();
                    if (selectedIndex != -1) {
                        MedicineTm selectedMedicine = obList.get(selectedIndex);

                        // Set the data to your text fields using the DTO
                        setFields(new MedicineDto(
                                selectedMedicine.getMedi_code(),
                                selectedMedicine.getMedicine_name(),
                                selectedMedicine.getDescription(),
                                selectedMedicine.getQty(),
                                selectedMedicine.getUnit_price()
                        ));
                    }
                }
            });


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String code) {
        try {
            boolean isDeleted = medicineBO.delete(code);
            if (isDeleted) {
                //new Alert(Alert.AlertType.CONFIRMATION, "Medicine item deleted!").show();
            }
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void medicineSearchOnAction(ActionEvent actionEvent) {
        String medicine = txtMedicineName.getText();

        try {
            MedicineDto dto = medicineBO.searchMedicine(medicine);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Medicine not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFields(MedicineDto dto) {
        lblCode.setText(dto.getMedi_code());
        cmbMedicineName.setValue(dto.getMedicine_name());
        txtDescription.setText(dto.getDescription());
        txtQty.setText(dto.getQty());
        txtUniPrice.setText(dto.getUnit_price());
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }


}

