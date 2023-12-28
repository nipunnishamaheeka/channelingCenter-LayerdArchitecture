package lk.ijse.channelingCenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.EmployeeDto;
import lk.ijse.channelingCenter.DAO.Impl.EmployeeDAOImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class EmployeeDetailsFromController {
    public AnchorPane employeeDetailsPane;
    public Label lblEmpId;
    public TextField employeeId;
    public TextField txtEmpName;
    public TextField txtemployeeId;
    public ComboBox cmpJobRole;
    public ComboBox cmbQualification;
    @FXML
    private TextField txtAddress;


    @FXML
    private TextField txtId;

    @FXML
    private TextField txtJobRole;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtQulification;

    @FXML
    private TextField txtSalary;
    EmployeeDAOImpl employeeModel = new EmployeeDAOImpl();

    public void initialize() throws SQLException {
        setEmployeeID();
        loadJobRolesTypes();
        loadQualificationTypes();
        // validateEmployee();
    }

    private void setEmployeeID() {
        try {
            lblEmpId.setText(new EmployeeDAOImpl().generateNextId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

   /* public void idSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            EmployeeDto dto = employeeModel.searchEmployee(id);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "item not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }*/

    private boolean validateEmployee() {
        String nameText = txtName.getText();
        boolean isNameValid = nameText.matches("[A-Za-z][A-Za-z. ]{3,}");
        if (!isNameValid) {
            txtName.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtName).play();
            return false;
        } else {
            txtName.setStyle("-fx-border-color: green");
        }
        String numberText = txtNumber.getText();
        boolean isNumberValid = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9,10}$").matcher(numberText).matches();
        if (!isNumberValid) {
            txtNumber.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtNumber).play();
            return false;
        } else {
            txtNumber.setStyle("-fx-border-color: green");
        }
        String salaryText = txtSalary.getText();
        boolean isSalaryValid = salaryText.matches("^\\p{Sc}?\\d+(\\.\\d+)?$");
        if (!isSalaryValid) {
            txtSalary.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtSalary).play();
            return false;
        } else {
            txtSalary.setStyle("-fx-border-color: green");
        }
        String AddressText = txtSalary.getText();
        boolean isAddressValid = AddressText.matches("^\\p{Sc}?\\d+(\\.\\d+)?$");
        if (!isAddressValid) {
            txtAddress.setStyle("-fx-border-color: red");
            new animatefx.animation.Shake(txtSalary).play();
            return false;
        } else {
            txtAddress.setStyle("-fx-border-color: green");
        }
        return true;
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        boolean isEmployeeValid = validateEmployee();

        if (isEmployeeValid) {
            String id = lblEmpId.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String number = txtNumber.getText();
            String jobRole = String.valueOf(cmpJobRole.getValue());
            String qualification = String.valueOf(cmbQualification.getValue());
            String salary = txtSalary.getText();

            EmployeeDto itemDto = new EmployeeDto(id, name, address, number, jobRole, qualification, salary);

            try {
                EmployeeDAOImpl employeeModel = new EmployeeDAOImpl();
                boolean isAdded = employeeModel.save(itemDto);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee added", ButtonType.OK).show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee not added", ButtonType.OK).show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            //new Alert(Alert.AlertType.ERROR,"Invalid Employee Details", ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String id = txtId.getText();

        try {
            boolean isDeleted = employeeModel.delete(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
                clearFields();


            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String id = lblEmpId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String number = txtNumber.getText();
        String jobRole = String.valueOf(cmpJobRole.getValue());
        String qualification = String.valueOf(cmbQualification.getValue());
        String salary = txtSalary.getText();

        var EmployeeDto = new EmployeeDto(id, name, address, number, jobRole, qualification, salary);

        try {
            boolean isUpdated = employeeModel.update(EmployeeDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        //lblEmpId.setText("");
        txtEmpName.setText("");
        txtemployeeId.setText("");
        txtName.setText("");
        txtNumber.setText("");
        txtAddress.setText("");
        cmpJobRole.setValue("");
        cmbQualification.setValue("");
        txtSalary.setText("");
    }

    public void btnBackButtonOnAction(MouseEvent mouseEvent) throws IOException {
        employeeDetailsPane.getChildren().clear();
        employeeDetailsPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/employeeFrom.fxml")));

    }

    void ReportbtnOnActhion() throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/Reports/EmployeeReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JRDesignQuery jrDesignQuery = new JRDesignQuery();
        jrDesignQuery.setText("SELECT * FROM employee WHERE emp_id = " + "\"" + txtemployeeId.getText() + "\"");
        load.setQuery(jrDesignQuery);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void btnGenarateReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        ReportbtnOnActhion();

    }

    public void btnSearchEmpIdOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String id = txtemployeeId.getText();

        var model = new EmployeeDAOImpl();
        try {
            EmployeeDto dto = model.searchEmployee(id);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(EmployeeDto dto) {
        lblEmpId.setText(dto.getEmp_id());
        txtEmpName.setText(dto.getEmp_name());
        txtName.setText(dto.getEmp_name());
        txtAddress.setText(dto.getEmp_address());
        txtNumber.setText(dto.getMobile_number());
        cmpJobRole.setValue(dto.getJob_role());
        cmbQualification.setValue(dto.getQualification());
        txtSalary.setText(dto.getSalary());


    }

    private void loadJobRolesTypes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        // Add "job Types" options
        obList.addAll("Doctor", "Nurse", "Receptionist", "Pharmacist", "Lab Assistant", "Cleaner", "Clack", "Other");
        cmpJobRole.setItems(obList);
    }
    private void loadQualificationTypes() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        // Add "job Types" options
        obList.addAll("Degree(Hons)", "Degree(BA)", "Diploma", "Certificate Course","A/L", "O/L", "Other");
        cmbQualification.setItems(obList);
    }


    public void btnClearReportOnAction(ActionEvent actionEvent) {
        clearFields();
    }
}
