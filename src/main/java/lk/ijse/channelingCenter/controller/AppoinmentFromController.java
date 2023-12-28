package lk.ijse.channelingCenter.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import lk.ijse.channelingCenter.model.AppoinmentListModel;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lk.ijse.channelingCenter.dto.tm.AppoinmentTm;
import lk.ijse.channelingCenter.DAO.Impl.AppoinmentDAOImpl;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AppoinmentFromController {
    @Getter

    public AnchorPane appoinmentPane;
    public TableView tblAppointment;
    public TableColumn colId;
    public TableColumn colDate;
    public TableColumn colPatientName;

    public TableColumn colDoctor;

    public TableColumn colPatientId;
    public TableColumn colAge;
    public TableColumn colDoctorId;
    public TableColumn colUpdate;
    public TableColumn colDelete;
    public TableColumn colStatus;

    AppoinmentDAOImpl appoinmentmodel = new AppoinmentDAOImpl();
    @SneakyThrows
    public void initialize() {
        setCellValueFactory();
        loadAllAppoinments();
//        loadData();

    }

    public void btnaddAppoinmentOnAction(ActionEvent mouseEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/addAppoinmentFrom.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("addAppoinmentFrom");
        stage.centerOnScreen();
        stage.show();

    }
    private void setFontAwesomeIcons() {
        tblAppointment.getItems().forEach(item -> {
            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        });
    }

    private void loadAllAppoinments() throws SQLException, ClassNotFoundException {
        try {
            List<AppoinmentDto> dtoList = appoinmentmodel.getAll();

            ObservableList<AppoinmentTm> obList = FXCollections.observableArrayList();

            for (AppoinmentDto dto : dtoList) {
                Button deleteButton = new Button("");
                FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setCursor(Cursor.HAND);

                deleteButton.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes) {
                        int selectedIndex = tblAppointment.getSelectionModel().getSelectedIndex();
                        String code = dto.getAppoinment_id(); // Use the code directly from the DTO

                        try {
                            deleteItem(code);   // Delete item from the database
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }

                        obList.remove(selectedIndex);   // Delete item from the JFX-Table
                    }
                });

                var tm = new AppoinmentTm(
                        dto.getAppoinment_id(),
                        dto.getDate(),
                        dto.getPatinet_id(),
                        dto.getAge(),
                        dto.getId(),
                        dto.getDoctor_name(),
                        dto.getPatinetName(),
                        dto.getStatus(),
                        deleteButton

                );
                obList.add(tm);
            }

            tblAppointment.setItems(obList);
            setFontAwesomeIcons();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String code) throws ClassNotFoundException {
        try {
            boolean isDeleted = appoinmentmodel.delete(code);
            if (isDeleted) {
                //new Alert(Alert.AlertType.CONFIRMATION, "Medicine item deleted!").show();
            }
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("appoinment_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patient_id"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        System.out.println("badu hri ");
        colDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor_name"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        System.out.println("awa");
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    public void btnrefershonAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        loadAllAppoinments();
    }
}
