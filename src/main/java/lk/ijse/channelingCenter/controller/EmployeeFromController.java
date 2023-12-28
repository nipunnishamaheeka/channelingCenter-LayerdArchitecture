package lk.ijse.channelingCenter.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.channelingCenter.dto.EmployeeDto;
import lk.ijse.channelingCenter.dto.tm.EmployeeTm;
import lk.ijse.channelingCenter.DAO.Impl.EmployeeDAOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeFromController {
    @FXML
    private AnchorPane employeePane;
    @FXML
    private TableColumn<?, ?> colUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableView<EmployeeTm> tblEmployeeView;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJob;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private TableColumn<?, ?> colQulification;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colDelete;

      EmployeeDAOImpl employeeModel = new EmployeeDAOImpl();


    public void btnaddemployeeOnAction() throws IOException {
        employeePane.getChildren().clear();
        employeePane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/employeeDetails.fxml")));
    }
public void initialize() {
    setCellValueFactory();
    try {
        loadAllEmployees();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    setFontAwesomeIcons();
}
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("emp_name"));
        //colAddress.setCellValueFactory(new PropertyValueFactory<>("emp_address"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("mobile_number"));
        colJob.setCellValueFactory(new PropertyValueFactory<>("job_role"));
        colQulification.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

    }
    private void setFontAwesomeIcons() {
        tblEmployeeView.getItems().forEach(item -> {
            Button deleteButton = item.getDeleteButton();
            Button updateButton = item.getUpdateButton();

            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            FontAwesomeIconView updateIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);

            deleteButton.setGraphic(deleteIcon);
            updateButton.setGraphic(updateIcon);
        });
    }

    private void loadAllEmployees() throws ClassNotFoundException {


        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = employeeModel.getAll();

            for(EmployeeDto dto : dtoList) {
                Button deleteButton = new Button();
                Button updateButton = new Button();

                deleteButton.setCursor(Cursor.HAND);
                updateButton.setCursor(Cursor.HAND);
                //setFontAwesomeIcons();

                deleteButton.setOnAction((e) -> {
                    ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this employee?", yes, no).showAndWait();
                    if (result.orElse(no) == yes){
                        int selectedIndex = tblEmployeeView.getSelectionModel().getSelectedIndex();
                        System.out.println("awa");
                        String code = (String) colId.getCellData(selectedIndex);
                        System.out.println(code);
                        try {
                            deleteEmployee(code);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        obList.remove(selectedIndex);
                        tblEmployeeView.refresh();
                    }
                });

                updateButton.setOnAction((e) -> {
                    int selectedIndex = tblEmployeeView.getSelectionModel().getSelectedIndex();
                    String code = (String) colId.getCellData(selectedIndex);
                    System.out.println(code);
                    try {
                        employeePane.getChildren().clear();
                        employeePane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/employeeDetails.fxml")));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                obList.add(
                        new EmployeeTm(
                                dto.getEmp_id(),
                                dto.getEmp_name(),
                                dto.getEmp_address(),
                                dto.getMobile_number(),
                                dto.getJob_role(),
                                dto.getQualification(),
                                dto.getSalary(),
                                deleteButton,
                                updateButton
                        )
                );
            }


            tblEmployeeView.setItems(obList);
            setFontAwesomeIcons();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEmployee(String code) throws ClassNotFoundException {
        try {
            boolean b = employeeModel.delete(code);
            if (b){
//                new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void refreshTable() throws ClassNotFoundException {
        loadAllEmployees();

    }

    public void btnRefreshOnAction(MouseEvent mouseEvent) {
        try {
            loadAllEmployees();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

