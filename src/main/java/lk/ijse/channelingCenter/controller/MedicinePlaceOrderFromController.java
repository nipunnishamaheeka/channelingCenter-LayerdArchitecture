package lk.ijse.channelingCenter.controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.channelingCenter.BO.Custom.AppoinmentBO;
import lk.ijse.channelingCenter.BO.Custom.Impl.AppoinmentBOImpl;
import lk.ijse.channelingCenter.BO.Custom.Impl.MedicineBOImpl;
import lk.ijse.channelingCenter.BO.Custom.Impl.PlaceOrderBOImpl;
import lk.ijse.channelingCenter.BO.Custom.MedicineBO;
import lk.ijse.channelingCenter.BO.Custom.PlaceOrderBO;
import lk.ijse.channelingCenter.DAO.Custom.Impl.DoctorDAOImpl;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lk.ijse.channelingCenter.dto.MedicineDto;
import lk.ijse.channelingCenter.dto.PlaceOrderDto;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicinePlaceOrderFromController {

    public AnchorPane medicinePlaceOrderPane;
    public Label lblAppointmentName;
    public TextField txtAppointmentID;
    public AnchorPane setOtherOption;
    public AnchorPane PaneOtherOption;
    public Label lblOrderId;
    public Label lblDate;
    public ComboBox cmbPatientId;
    public ComboBox<String> cmbMedicineId;
    public TextField txtQty;
    public Label lblDescription;
    public Label lblUnitPrice;
    public Label lblPatientName;
    public Pane visiblePane;
    public Label lblDoctorName;
    public Label lblTotal;
    public TableView tblMedicine;
    public TableColumn colMediCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colPrice;
    public TableColumn colTotal;
    public TableColumn colAction;
    public Pane cartPane;
    public Pane placeOrder;
    private AppoinmentBO appoinmentBO = new AppoinmentBOImpl();
    private MedicineBO medicineBO = new MedicineBOImpl();
    private PlaceOrderBO placeOrderBO = new PlaceOrderBOImpl();

    public void initialize() {
        //setOrderId();
        this.lblDate.setText(generateRealTime());
        loadMedicineIds();
        setCellValuefactory();
    }

    private String generateRealTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }


    private void setFields(AppoinmentDto dto) {
        txtAppointmentID.setText(dto.getAppoinment_id());
        // lblAppointmentName.setText(dto.getPatinetName());

    }

    private void loadMedicineIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<MedicineDto> cusList = medicineBO.getAll();

            for (MedicineDto dto : cusList) {
                obList.add(dto.getMedi_code());
            }
            cmbMedicineId.setItems(obList);
            //cmbPatientId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private final ObservableList<CartTm> obList = FXCollections.observableArrayList();


    private double value = 0;
    private double netTotal = 0;

    private void calculateNetTotal() {
        for (int i = 0; i < tblMedicine.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        netTotal += value;
        lblTotal.setText("Rs. " + netTotal + "0");
    }


    private void setCellValuefactory() {

        colMediCode.setCellValueFactory(new PropertyValueFactory<>("M_Code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Dis"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("U_price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    public void btnBackOnAction(MouseEvent event) throws IOException {
        medicinePlaceOrderPane.getChildren().clear();
        medicinePlaceOrderPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/View/paymentFrom.fxml")));
    }

    public void btnSearchAppoinmentIDOnAction(javafx.event.ActionEvent actionEvent) {
        String id = txtAppointmentID.getText();

        try {
            AppoinmentDto dto = appoinmentBO.searchAppoinmentID(id);
            if (dto != null) {
                setFields(dto);
                visiblePane.setVisible(true);
                cartPane.setVisible(true);
                lblPatientName.setText(dto.getPatinetName());
                lblDoctorName.setText(dto.getDoctor_name());
                value = new DoctorDAOImpl().getfee(dto.getId());
                calculateNetTotal();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Appoinemnt ID not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void AddbtnOnActhion(javafx.event.ActionEvent actionEvent) {
        String code = (String) cmbMedicineId.getValue();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;

        int qtyOnHand = 0;
        try {
            qtyOnHand = medicineBO.getQty(code);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (qty < qtyOnHand) {


            JFXButton btn = new JFXButton("Remove");
            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            btn.setGraphic(deleteIcon);
            btn.setCursor(Cursor.HAND);

            btn.setOnAction((e) -> {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {
                    int index = tblMedicine.getSelectionModel().getSelectedIndex();
                    obList.remove(index);
                    tblMedicine.refresh();

                    calculateNetTotal();
                }
            });

            for (int i = 0; i < tblMedicine.getItems().size(); i++) {
                if (code.equals(colMediCode.getCellData(i))) {
                    qty += (int) colQty.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(total);

                    tblMedicine.refresh();
                    calculateNetTotal();
                    return;
                }
            }

            obList.add(new CartTm(
                    code,
                    description,
                    qty,
                    unitPrice,
                    total,
                    btn
            ));

            tblMedicine.setItems(obList);
            placeOrder.setVisible(true);
            calculateNetTotal();
            //txtQty.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Low Qty Check Your Stock!").show();
        }
    }

    public void btnPlaceOrderOnAction(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String appoinmentId = txtAppointmentID.getText();
        Date date = Date.valueOf(LocalDate.now());
        Time time = Time.valueOf(LocalTime.now());

        List<CartTm> tmList = new ArrayList<>();

        for (CartTm tm : obList) {
            tmList.add(tm);
        }

        PlaceOrderDto placeOrderDto = new PlaceOrderDto(appoinmentId, date, time, netTotal, tmList);


        boolean isPlaced = placeOrderBO.placeOrder(placeOrderDto);

        if (isPlaced) {

            new Alert(Alert.AlertType.INFORMATION, "Order Placed Successfully").show();
            //completeOrder();

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Order Placed Failed").show();
        }
    }

    public void btnCompleteOrdersOnAction(javafx.event.ActionEvent actionEvent) throws IOException {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/completeOrdersFrom.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("CompleteOrder");
        stage.centerOnScreen();
        stage.show();

    }

    public void cmbMedicineIdOnAction(javafx.event.ActionEvent actionEvent) {
        try {

            MedicineDto dto = medicineBO.searchMedicine(cmbMedicineId.getValue());
            lblDescription.setText(dto.getDescription());
           // String price = new MedicineModel().getMedicinePrice(cmbMedicineId.getValue());
            lblUnitPrice.setText(dto.getUnit_price());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

