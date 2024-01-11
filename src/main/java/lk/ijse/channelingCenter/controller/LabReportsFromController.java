package lk.ijse.channelingCenter.controller;

import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.channelingCenter.BO.Custom.DoctorBO;
import lk.ijse.channelingCenter.BO.Custom.LabReportBO;
import lk.ijse.channelingCenter.BO.Custom.PatientBO;
import lk.ijse.channelingCenter.BO.BOFactory;
import lk.ijse.channelingCenter.DAO.Custom.Impl.PatientDAOImpl;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.*;
import lk.ijse.channelingCenter.dto.tm.LabReportTm;
import lk.ijse.channelingCenter.email.SendEmail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class LabReportsFromController {
    public AnchorPane labReportsPane;
    public Label lblReportId;
    @FXML
    private TextField txtEmail;
    public Label lblPatientName;
    public Label lblDoctorName;
    public Label lblGender;
    public Label lblAge;
    public TableView tblReport;
    public JFXComboBox<String> cmboPationId;
    public JFXComboBox<String> cmbDoctor;
    public TextField txtSearchId;
    public TextField txtPatientName;
    public TextField txtReportName;
    public TextField txtReportResult;
    public TextField txtUnits;
    public TextField txtOthers;
    public TableColumn colDelete;
    @FXML
    private DatePicker datefelid;
    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDoctorName;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colLabRecord;

    @FXML
    private TableColumn<?, ?> colPatientName;

    @FXML
    private TableColumn<?, ?> colReportId;
    private DoctorBO doctorBO = (DoctorBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.DOCTOR);
    private PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PATIENT);
    private LabReportBO labReportBO = (LabReportBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.LABREPORT);

    public void initialize() throws SQLException {
        setLabReportsID();
        loadPatientsIds();
        loadDoctorIds();
        setCellValueFactory();
        loadAllReports();
    }

    private void loadAllReports() throws SQLException {
        try {
            List<LabReportDto> dtoList = labReportBO.getAll();

            ObservableList<LabReportTm> obList = FXCollections.observableArrayList();

            for (LabReportDto dto : dtoList) {
                Button deleteButton = new Button("");
                FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setCursor(Cursor.HAND);

                deleteButton.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        int selectedIndex = tblReport.getSelectionModel().getSelectedIndex();
                        String code = dto.getLab_reportid(); // Use the code directly from the DTO

                        deleteItem(code);   // Delete item from the database

                        obList.remove(selectedIndex);   // Delete item from the JFX-Table
                    }
                });

                var tm = new LabReportTm(
                        dto.getLab_reportid(),
                        dto.getPatient_id(),
                        dto.getDate(),
                        dto.getDoctor_id(),
                        dto.getDoctor_name(),
                        dto.getAge(),
                        dto.getGender(),
                        dto.getPatient_name(),
                        dto.getTest_name(),
                        dto.getTest_result(),
                        dto.getUnits(),
                        dto.getOthers(),
                        deleteButton
                );
                obList.add(tm);
            }

            tblReport.setItems(obList);
            setFontAwesomeIcons();
            // Add a click event listener to the table rows
            tblReport.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) { // Check for a single click
                    int selectedIndex = tblReport.getSelectionModel().getSelectedIndex();
                    if (selectedIndex != -1) {
                        LabReportTm selectedReport = obList.get(selectedIndex);

                        // Set the data to your text fields using the DTO
                        setReportDataToFields(selectedReport);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Add a method to set the data to text fields
    private void setReportDataToFields(LabReportTm selectedReport) {
        lblReportId.setText(selectedReport.getLab_reportid());
        datefelid.setValue(LocalDate.parse(selectedReport.getDate()));
        lblDoctorName.setText(selectedReport.getDoctor_name());
        lblAge.setText(selectedReport.getAge());
        lblGender.setText(selectedReport.getGender());
        lblPatientName.setText(selectedReport.getPatient_name());
        txtReportName.setText(selectedReport.getTest_name());
        txtReportResult.setText(selectedReport.getTest_result());
        txtUnits.setText(selectedReport.getUnits());
        txtOthers.setText(selectedReport.getOthers());
    }

    private void setFontAwesomeIcons() {
        tblReport.getItems().forEach(item -> {
            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        });
    }

    private void deleteItem(String code) {
        try {
            boolean isDeleted = labReportBO.delete(code);
            if (isDeleted) {
                //new Alert(Alert.AlertType.CONFIRMATION, "Medicine item deleted!").show();
            }
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        boolean isReportValid = validateLabReport();

        if (isReportValid) {
            try {
                String id = lblReportId.getText();
                String PatientId = cmboPationId.getValue();
                String Date = datefelid.getValue().toString();
                String DId = cmbDoctor.getValue();
                String DName = lblDoctorName.getText();
                String Age = lblAge.getText();
                String Gender = lblGender.getText();
                String PName = lblPatientName.getText();
                String TestName = txtReportName.getText();
                String TestResult = txtReportResult.getText();
                String Units = txtUnits.getText();
                String Others = txtOthers.getText();


                boolean isSaved = labReportBO.save(new LabReportDto(id, PatientId, Date, DId, DName, Age, Gender, PName, TestName, TestResult, Units, Others));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "User Added Successfully").show();
                    loadAllReports();
                    clearFields();
                    return;
                } else {
                    new Alert(Alert.AlertType.ERROR, "LabReport not saved!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            lblReportId.requestFocus();
        }
    }

    private void clearFields() {
        lblReportId.setText("");
        datefelid.setValue(null);
        lblDoctorName.setText("");
        lblAge.setText("");
        lblGender.setText("");
        lblPatientName.setText("");
        txtReportName.setText("");
        txtReportResult.setText("");
        txtUnits.setText("");
        txtOthers.setText("");
    }


    private boolean validateLabReport() {
        String testName = txtReportName.getText();
        boolean isNameValid = Pattern.compile("[A-Za-z]{3,}").matcher(testName).matches();
        if (!isNameValid) {
            txtReportName.setStyle("-fx-border-color: red");
            //new Alert(Alert.AlertType.ERROR, "Invalid Patient Number").show();
            return false;
        }
        String testResult = txtReportResult.getText();
        boolean isResultValid = Pattern.compile("[A-Za-z]{3,}").matcher(testResult).matches();
        if (!isResultValid) {
            txtReportResult.setStyle("-fx-border-color: red");
            //new Alert(Alert.AlertType.ERROR, "Invalid Patient Number").show();
            return false;
        }
        String units = txtUnits.getText();
        boolean isUnitsValid = Pattern.compile("^[A-Za-z0-9\\s\\-()&,.]+$").matcher(units).matches();
        if (!isUnitsValid) {
            txtUnits.setStyle("-fx-border-color: red");
            //new Alert(Alert.AlertType.ERROR, "Invalid Patient Number").show();
            return false;
        }
        return true;
    }

    private void setLabReportsID() {

        try {
            lblReportId.setText(labReportBO.generateNextId());
            //tblReport.refresh();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadPatientsIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<PatientDto> cusList = patientBO.getAll();

            for (PatientDto dto : cusList) {
                obList.add(dto.getPatient_id());
            }
            cmboPationId.setItems(obList);
            //cmbPatientId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDoctorIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<DoctorDto> cusList = doctorBO.getAll();

            for (DoctorDto dto : cusList) {
                obList.add(dto.getId());
            }
            cmbDoctor.setItems(obList);
            //cmbPatientId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbPatientIdOnAction(ActionEvent event) {
        try {
            String name = new PatientDAOImpl().getPatientName(cmboPationId.getValue());
            lblPatientName.setText(name);

            String gender = new PatientDAOImpl().getPatientGender(cmboPationId.getValue());
            lblGender.setText(gender);

            String age = new PatientDAOImpl().getPatientAge(cmboPationId.getValue());
            lblAge.setText(age);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (validateLabReport()) {
            String id = lblReportId.getText();
            String PatientId = cmboPationId.getValue();
            String Date = datefelid.getValue().toString();
            String DId = cmbDoctor.getValue();
            String DName = lblDoctorName.getText();
            String Age = lblAge.getText();
            String Gender = lblGender.getText();
            String PName = lblPatientName.getText();
            String TestName = txtReportName.getText();
            String TestResult = txtReportResult.getText();
            String Units = txtUnits.getText();
            String Others = txtOthers.getText();
            try {
                boolean isUpdated = labReportBO.update(new LabReportDto(id, PatientId, Date, DId, DName, Age, Gender, PName, TestName, TestResult, Units, Others));
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Patient updated").show();
                    clearFields();
                    //loadAllPatients();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void cmbDoctorIdOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {
            String name = doctorBO.getname(cmbDoctor.getValue());
            lblDoctorName.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchIdOnAction(ActionEvent actionEvent) {
        String id = txtSearchId.getText();

        var model = labReportBO;
        try {
            LabReportDto dto = model.searchLabReport(id);

            if (dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Report not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void fillFields(LabReportDto dto) throws SQLException {
        txtPatientName.setText(dto.getPatient_name());
        try {
            txtEmail.setText(labReportBO.getEmail(dto.getLab_reportid()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colReportId.setCellValueFactory(new PropertyValueFactory<>("lab_reportid"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        colDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctor_name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDoctorName.setCellValueFactory(new PropertyValueFactory<>("doctor_name"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));


    }


    void ReportbtnOnActhion() throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/Reports/LabReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JRDesignQuery jrDesignQuery = new JRDesignQuery();
        jrDesignQuery.setText("SELECT * FROM labreport WHERE lab_reportId = " + "\"" + txtSearchId.getText() + "\"");
        load.setQuery(jrDesignQuery);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);

        // Optionally, export to PDF after viewing the report
        exportReportToPDF(jasperPrint, "output.pdf");
    }

    public void btnPrintReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        ReportbtnOnActhion();
    }

    private void exportReportToPDF(JasperPrint jasperPrint, String outputFileName) throws JRException {
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
        System.out.println("Report exported to PDF: " + outputFileName);
    }


    @FXML
    void btnEmailOnAction(ActionEvent event) {
        String email = txtEmail.getText();
        String title = "Lab Report";
        sendMail("Thank you for choosing our service !", "Thank you for choosing our service !.", email);
    }

    private boolean sendMail(String title, String message, String gmail) {

        System.out.println(title + " " + message + " " + gmail);
        try {
            new SendEmail().sendMail(title, message, gmail);
            return true;
        } catch (IOException | MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        }
    }
}