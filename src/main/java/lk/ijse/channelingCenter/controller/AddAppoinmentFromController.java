package lk.ijse.channelingCenter.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lk.ijse.channelingCenter.dto.DoctorDto;
import lk.ijse.channelingCenter.dto.PatientDto;
import lk.ijse.channelingCenter.DAO.Impl.AppoinmentDAOImpl;
import lk.ijse.channelingCenter.DAO.Impl.DoctorDAOImpl;
import lk.ijse.channelingCenter.DAO.Impl.PatientDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AddAppoinmentFromController {
    public Label lblPatientId;
    public Label lblDoctorName;
    public Label lblAge;
    public Label lblAppointmentDate;
    @FXML
    private AnchorPane addPatient;

    @FXML
    private JFXComboBox<String> cmbPatientId;

    @FXML
    private JFXComboBox<String> cmbDoctorId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblPatientName;
    @FXML
    private Label lblAppoinmentId;
    AppoinmentDAOImpl appoinmentModel = new AppoinmentDAOImpl();
    @FXML
    private TextField txtAge;

    public void initialize() throws SQLException, ClassNotFoundException {
        setAppoinmentId();
        //setPatientID();
        loadPatientsIds();
        loadDoctorIds();
        //loadAPatientIds();
        //generateRealTime();
        this.lblAppointmentDate.setText(generateRealTime());

    }

    private void setAppoinmentId() throws ClassNotFoundException {
        try {
            lblAppoinmentId.setText(new AppoinmentDAOImpl().generateNextId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  /*  private void setPatientID(){
        try{
            cmbPatientId.setId(new AppoinmentModel().autoGenarateId());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }*/
//  private void setPatientID() {
//      try {
//          lblPatientId.setId(new AppoinmentModel().patientautoGenarateId());
//      } catch (SQLException e) {
//          System.out.println(e.getMessage());
//      }
//  }

    @FXML
    void btnClerOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        // setAppoinmentId();
        // setPatientID();
        String appoinmentId = lblAppoinmentId.getText();
        String date = generateRealTime();
        String id = cmbPatientId.getValue();
        String age = lblAge.getText();
        String doctorId = cmbDoctorId.getValue();
        String doctorName = lblDoctorName.getText();
        String patinetName = lblPatientName.getText();
        String status = "pending";

        AppoinmentDto itemDto = new AppoinmentDto(appoinmentId,date,id,patinetName,age,doctorId,doctorName,status);

        try {
            AppoinmentDAOImpl appoinmentModel = new AppoinmentDAOImpl();
            boolean isSaved = appoinmentModel.save(itemDto);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Appoinment Saved!", ButtonType.OK).show();
                //clearFields();
                loadAllItems();
            } else {
                new Alert(Alert.AlertType.ERROR, "Appoinment Not Saved!", ButtonType.OK).show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllItems() throws ClassNotFoundException {
        ObservableList<AppoinmentDto> obList = FXCollections.observableArrayList();
        try {
            List<AppoinmentDto> appoinmentDtoList = appoinmentModel.getAll();
            for (AppoinmentDto dto : appoinmentDtoList) {
                obList.add(dto);
            }
            //tblAppoinment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        //lblAppoinmentId.setText("");
        lblPatientId.setText("");
        lblDoctorName.setText("");
        lblAge.setText("");
        lblPatientName.setText("");
        lblAppointmentDate.setText("");
        cmbPatientId.setValue("");
        cmbDoctorId.setValue("");
        txtAge.setText("");

    }
    private void loadPatientsIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<PatientDto> cusList = new PatientDAOImpl().getAll();

            for (PatientDto dto : cusList) {
                obList.add(dto.getPatient_id());
            }
            cmbPatientId.setItems(obList);
            //cmbPatientId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDoctorIds() throws ClassNotFoundException {

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<DoctorDto> cusList = new DoctorDAOImpl().getAll();

            for (DoctorDto dto : cusList) {
                obList.add(dto.getId());
            }

            cmbDoctorId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*private void loadAPatientIds() {

         ObservableList<String> obList = FXCollections.observableArrayList();
         try {
             List<PatientDto> cusList = new PatientModel().getAllPatient();

             for (PatientDto dto : cusList) {
                 obList.add(dto.getPatient_id());
             }

             lblPatientId.setId(obList.toString());
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }*/
    public void btnaddOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/addAppoinmentPatinetDetials.fxml"));
        Scene scene = new Scene(rootNode);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("addAppoinmentFrom");
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    public void cmbDoctorOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {
            String name = new DoctorDAOImpl().getname(cmbDoctorId.getValue());
            lblDoctorName.setText(name);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cmbPatientOnAction(ActionEvent event) {
        try {
            String name = new PatientDAOImpl().getPatientName(cmbPatientId.getValue());
            lblPatientName.setText(name);
            String age = new PatientDAOImpl().getPatientAge(cmbPatientId.getValue());
            lblAge.setText((age));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRealTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        return format;
    }

}
