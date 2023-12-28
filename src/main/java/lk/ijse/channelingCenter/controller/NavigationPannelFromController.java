package lk.ijse.channelingCenter.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.channelingCenter.dto.PatientDto;
import lk.ijse.channelingCenter.DAO.Impl.PatientDAOImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class NavigationPannelFromController implements Initializable {
    public AnchorPane navigationPane;
    public Label lblDate;
    public Label lblTimeMini;
    public TextField idSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     generateRealTime();
     navigationPane.getChildren().clear();
        try {
            navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/overViewFrom.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnoverViewOnAction(MouseEvent mouseEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/overViewFrom.fxml")));
    }

    public void btnpatientOnAction(MouseEvent mouseEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/patientFrom.fxml")));

    }

    public void btnappoinmentOnAction(MouseEvent mouseEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/appoinmentFrom.fxml")));

    }

    public void btndoctorOnAction(MouseEvent mouseEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/doctorFrom.fxml")));

    }

    public void btnlabreportsOnAction(MouseEvent mouseEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/labReportsFrom.fxml")));
    }

    public void btnmedicineOnAction(MouseEvent mouseEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/medicineFrom.fxml")));

    }

    public void btnlogoutOnAction(MouseEvent mouseEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginFrom.fxml"));
        Scene scene = new Scene(rootNode);

        navigationPane.getChildren().clear();
        Stage primaryStage = (Stage) navigationPane.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("ToDo");

    }

    public void btnemployeeOnAction(MouseEvent mouseEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/employeeFrom.fxml")));

    }

    public void btnPaymentOnAction(MouseEvent mouseEvent) throws IOException {
        navigationPane.getChildren().clear();
        navigationPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/paymentFrom.fxml")));

    }
    /*-----DATE AND TIME GENERATE------*/
    public String timeNow() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss");
        //System.out.println(dateFormat.format(new Date()));
        return dateFormat.format(new Date()) ;
    }
    private void generateRealTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {

            lblTimeMini.setText(timeNow());
            // lblTime.setText(LocalDateTime.now().format(formatter));

        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void searchOnAction(ActionEvent actionEvent) {
        String id = idSearch.getText();

        var model = new PatientDAOImpl();
        try {
            PatientDto dto = model.searchPatient(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillFields(PatientDto dto) {

    }


}
