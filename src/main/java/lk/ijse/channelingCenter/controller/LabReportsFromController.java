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
import lk.ijse.channelingCenter.DAO.Impl.DoctorDAOImpl;
import lk.ijse.channelingCenter.DAO.Impl.LabReportDAOImpl;
import lk.ijse.channelingCenter.DAO.Impl.PatientDAOImpl;
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
    LabReportDAOImpl labReportDAOImpl = new LabReportDAOImpl();

    public void initialize() throws SQLException {
        setLabReportsID();
        loadPatientsIds();
        loadDoctorIds();
        setCellValueFactory();
        loadAllReports();
    }

    private void loadAllReports() throws SQLException {
        try {
            List<LabReportDto> dtoList = labReportDAOImpl.getAll();

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
            boolean isDeleted = labReportDAOImpl.delete(code);
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

                LabReportDAOImpl labReportDAOImpl = new LabReportDAOImpl();
                boolean isSaved = labReportDAOImpl.save(new LabReportDto(id, PatientId, Date, DId, DName, Age, Gender, PName, TestName, TestResult, Units, Others));
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
            lblReportId.setText(new LabReportDAOImpl().generateNextId());
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
            List<PatientDto> cusList = new PatientDAOImpl().getAll();

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
            List<DoctorDto> cusList = new DoctorDAOImpl().getAll();

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
        } catch (ClassNotFoundException e) {
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
                boolean isUpdated = labReportDAOImpl.update(new LabReportDto(id, PatientId, Date, DId, DName, Age, Gender, PName, TestName, TestResult, Units, Others));
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
            String name = new DoctorDAOImpl().getname(cmbDoctor.getValue());
            lblDoctorName.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchIdOnAction(ActionEvent actionEvent) {
        String id = txtSearchId.getText();

        var model = new LabReportDAOImpl();
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
            txtEmail.setText(labReportDAOImpl.getEmail(dto.getLab_reportid()));
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

//    @FXML
//    void btnEmailOnAction(ActionEvent event) {
//        String email = txtEmail.getText();
//        String title = "Lab Report";
//
//        // Get the selected item directly from the table
//        LabReportTm selectedReport = (LabReportTm) tblReport.getSelectionModel().getSelectedItem();
//
//        // Check if an item is selected
//        if (selectedReport != null) {
//            // Get report details from the selected item
//            String labReportId = selectedReport.getLab_reportid();
//            String date = selectedReport.getDate();
//            String patientName = selectedReport.getPatient_name();
//            String doctorName = selectedReport.getDoctor_name();
//            String age = selectedReport.getAge();
//            String gender = selectedReport.getGender();
//
//            // Generate HTML content for the email
//            String htmlContent = generateHtmlContentForReport(email, labReportId, date, patientName, doctorName, age, gender);
//
//            // Send email with HTML content
//            if (sendMail(title, htmlContent, email)) {
//                System.out.println("Email sent successfully.");
//            } else {
//                System.out.println("Failed to send email.");
//            }
//        } else {
//            // Handle the case where no item is selected in the table
//            System.out.println("Please select a row from the table.");
//        }
//    }
//
//    private String generateHtmlContentForReport(String email, String labReportId, String date, String patientName,
//                                                String doctorName, String age, String gender) {
//        return "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "    <style>\n" +
//                "        body {\n" +
//                "            font-family: Arial, sans-serif;\n" +
//                "            background-color: #f4f4f4;\n" +
//                "            text-align: center;\n" +
//                "        }\n" +
//                "        .container {\n" +
//                "            background-color: #ffffff;\n" +
//                "            padding: 20px;\n" +
//                "            border-radius: 5px;\n" +
//                "            box-shadow: 0 0 10px rgba(0,0,0,0.2);\n" +
//                "            text-align: center;\n" +
//                "        }\n" +
//                "    </style>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <div class=\"container\">\n" +
//                "        <h1>Lab Report Details for " + patientName + "</h1>\n" +
//                "        <p>Lab Report ID: " + labReportId + "</p>\n" +
//                "        <p>Date: " + date + "</p>\n" +
//                "        <p>Patient Name: " + patientName + "</p>\n" +
//                "        <p>Doctor Name: " + doctorName + "</p>\n" +
//                "        <p>Age: " + age + "</p>\n" +
//                "        <p>Gender: " + gender + "</p>\n" +
//                "        <p>Thank you for choosing our service!</p>\n" +
//                "    </div>\n" +
//                "</body>\n" +
//                "</html>\n";
//    }

    private boolean sendMail(String title,String message,String gmail){

        System.out.println(title+" "+message+" "+gmail);
        try {
            new SendEmail().sendMail(title,message,gmail);
            return true;
        } catch (IOException | MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
            return false;
        }
    }
//void ReportbtnOnActhion() throws JRException, SQLException {
//    InputStream resourceAsStream = getClass().getResourceAsStream("/Reports/LabReport.jrxml");
//    JasperDesign load = JRXmlLoader.load(resourceAsStream);
//    JRDesignQuery jrDesignQuery = new JRDesignQuery();
//    jrDesignQuery.setText("SELECT * FROM labreport WHERE lab_reportId = " + "\"" + txtSearchId.getText() + "\"");
//    load.setQuery(jrDesignQuery);
//
//    JasperReport jasperReport = JasperCompileManager.compileReport(load);
//    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
//
//    // Optionally, export to PDF after viewing the report
//    String outputFileName = "output.pdf";
//    exportReportToPDF(jasperPrint, outputFileName);
//
//    // Send email with the attached PDF
//    String email = txtEmail.getText();
//    String title = "Lab Report";
//    String message = "Thank you for choosing our service!";
//
//    if (sendMailWithAttachment(title, message, email, outputFileName)) {
//        System.out.println("Email sent successfully.");
//    } else {
//        System.out.println("Failed to send email.");
//    }
//
//    JasperViewer.viewReport(jasperPrint, false);
//}
//
//    public void btnPrintReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
//        ReportbtnOnActhion();
//    }
//
//    private void exportReportToPDF(JasperPrint jasperPrint, String outputFileName) throws JRException {
//        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
//        System.out.println("Report exported to PDF: " + outputFileName);
//    }
//
//    private boolean sendMailWithAttachment(String subject, String body, String toEmail, String attachmentPath) {
//        try {
//            // Setup mail server properties
//            java.util.Properties properties = new java.util.Properties();
//            properties.put("mail.smtp.host", "your_smtp_server");
//            properties.put("mail.smtp.port", "your_smtp_port");
//            properties.put("mail.smtp.auth", "true");
//            properties.put("mail.smtp.starttls.enable", "true");
//
//            // Get the Session object
//            Session session = Session.getInstance(properties, new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication("your_email@gmail.com", "your_email_ password");
//                }
//            });
//
//            // Create a default MimeMessage object
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field
//            message.setFrom(new InternetAddress("your_email@gmail.com"));
//
//            // Set To: header field
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//
//            // Set Subject: header field
//            message.setSubject(subject);
//
//            // Create the message part
//            BodyPart messageBodyPart = new MimeBodyPart();
//
//            // Set the actual message
//            messageBodyPart.setText(body);
//
//            // Create a multipart message
//            Multipart multipart = new MimeMultipart();
//
//            // Attach the message part
//            multipart.addBodyPart(messageBodyPart);
//
//            // Attach the file as dataHandler
//            messageBodyPart = new MimeBodyPart();
//            DataSource source = (DataSource) new FileDataSource(attachmentPath);
//            messageBodyPart.setDataHandler(new DataHandler((javax.activation.DataSource) source));
//            messageBodyPart.setFileName("LabReport.pdf");  // Set the filename for the attachment
//            multipart.addBodyPart(messageBodyPart);
//
//            // Set the complete message parts
//            message.setContent(multipart);
//
//            // Send the message
//            Transport.send(message);
//
//            return true;
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    void ReportbtnOnActhion() throws JRException, SQLException {
//        InputStream resourceAsStream = getClass().getResourceAsStream("/Reports/LabReport.jrxml");
//        JasperDesign load = JRXmlLoader.load(resourceAsStream);
//        JRDesignQuery jrDesignQuery = new JRDesignQuery();
//        jrDesignQuery.setText("SELECT * FROM labreport WHERE lab_reportId = " + "\"" + txtSearchId.getText() + "\"");
//        load.setQuery(jrDesignQuery);
//
//        JasperReport jasperReport = JasperCompileManager.compileReport(load);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
//
//        // Optionally, export to PDF after viewing the report
//        String outputFileName = "output.pdf";
//        exportReportToPDF(jasperPrint, outputFileName);
//
//        // Send email with the attached PDF
//        String email = txtEmail.getText();
//        String title = "Lab Report";
//        String message = "Thank you for choosing our service!";
//
//        if (sendMailWithAttachment(title, message, email, outputFileName,  message)) {
//            System.out.println("Email sent successfully.");
//        } else {
//            System.out.println("Failed to send email.");
//        }
//
//    }
//
//    private void exportReportToPDF(JasperPrint jasperPrint, String outputFileName) throws JRException {
//        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFileName);
//        System.out.println("Report exported to PDF: " + outputFileName);
//    }
//
//    @FXML
//    void btnEmailOnAction(ActionEvent event) {
//        try {
//            ReportbtnOnActhion();
//        } catch (JRException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean sendMailWithAttachment(String subject, String body, String toEmail, String attachmentPath, MimeMessage message) {
//        try {
//            // ... your existing code ...
//
//            // Create the message part for attachment
//            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
//            FileDataSource source = new FileDataSource(attachmentPath);
//            attachmentBodyPart.setDataHandler(new DataHandler(source));
//            attachmentBodyPart.setFileName("LabReport.pdf");  // Set the filename for the attachment
//
//            // Create a multipart message for the email body and attachment
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(attachmentBodyPart);
//
//            // Set the complete message parts
//            message.setContent(multipart);
//
//            // Send the message
//            Transport.send(message);
//
//            return true;
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


}