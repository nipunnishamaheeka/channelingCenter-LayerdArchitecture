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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.channelingCenter.dto.PaymentDto;
import lk.ijse.channelingCenter.dto.tm.PaymentTm;
import lk.ijse.channelingCenter.DAO.Impl.PaymentDAOImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PaymentFromController {
    public AnchorPane paymentPane;
    public TableView tblPayment;
    public TableColumn colPaymentId;
    public TableColumn colAppoinmentId;
    public TableColumn colTime;
    public TableColumn colDate;
    public TableColumn colPatientName;
    public TableColumn colAmount;
    public TableColumn colAction;


    public void initialize() {
        setCellValueFactory();
        loadAllPayments();
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws IOException {
        paymentPane.getChildren().clear();
        paymentPane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/medicinePlaceOrder.fxml")));
    }

    public void btnCompleteOrdersOnAction(ActionEvent actionEvent) throws IOException {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/completeOrdersFrom.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("addAppoinmentFrom");
        stage.centerOnScreen();
        stage.show();

    }
    private void setFontAwesomeIcons() {
        tblPayment.getItems().forEach(item -> {

            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        });
    }

    public void loadAllPayments() {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try {
            List<PaymentDto> dtoList = new PaymentDAOImpl().getAll();

            for (PaymentDto dto : dtoList) {
                Button deleteButton = new Button();

                deleteButton.setCursor(Cursor.HAND);
                FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this Patient?", yes, no).showAndWait();
                    if (result.orElse(no) == yes) {
                        int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                        String code = (String) colPaymentId.getCellData(selectedIndex);
                        deletePatient(code);
                        obList.remove(selectedIndex);
                        tblPayment.refresh();
                    }
                });
                obList.add(
                        new PaymentTm(
                                dto.getPayment_id(),
                                dto.getPayment_date(),
                                dto.getPayment_time(),
                                dto.getAmount(),
                                dto.getAppoinment_id(),
                                deleteButton
                        )
                );
            }
            tblPayment.setItems(obList);
            setFontAwesomeIcons();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePatient(String code) {
        try {
            boolean isDeleted = new PaymentDAOImpl().delete(code);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully !", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again !", ButtonType.OK).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("payment_date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("payment_time"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAppoinmentId.setCellValueFactory(new PropertyValueFactory<>("appoinment_id"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }
}
