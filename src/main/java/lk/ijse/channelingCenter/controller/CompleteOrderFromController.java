package lk.ijse.channelingCenter.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.channelingCenter.BO.AppoinmentBO;
import lk.ijse.channelingCenter.BO.Impl.AppoinmentBOImpl;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lk.ijse.channelingCenter.dto.tm.CompleteOrdersTm;
import lk.ijse.channelingCenter.DAO.Impl.AppoinmentDAOImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class CompleteOrderFromController {
    @FXML
    private AnchorPane CompleteOrderPane;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAppoinment_id;

    @FXML
    private TableColumn<?, ?> colDocName;

    @FXML
    private TableColumn<?, ?> colPatientName;

    @FXML
    private TableView<CompleteOrdersTm> tblMedicine;
private AppoinmentBO appoinmentBO = new AppoinmentBOImpl();
    public void initialize() throws ClassNotFoundException {
        setValueFactories();
        loadCompleteOrders();
    }

    private void setValueFactories() {
        colAppoinment_id.setCellValueFactory(new PropertyValueFactory<>("appoinment_id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colDocName.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }
    private void loadCompleteOrders() throws ClassNotFoundException {
        try {
            List<AppoinmentDto> allAppoinment = appoinmentBO.getAll();
            ObservableList<CompleteOrdersTm> list = FXCollections.observableArrayList();
            for (AppoinmentDto dto : allAppoinment) {
                if (dto.getStatus().equals("complete")) {
                    Button btn = new Button("Generate");
                    btn.setCursor(Cursor.HAND);

                    btn.setOnAction(event -> {
                        try {
                            // New: Pass the appoinment_id to the report generation method
                            ReportbtnOnActhion(dto.getAppoinment_id());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    list.add(new CompleteOrdersTm(dto.getAppoinment_id(), dto.getPatinetName(), dto.getDoctor_name(), btn));
                }
            }
            tblMedicine.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // New: Accept appoinment_id as a parameter
    void ReportbtnOnActhion(String appoinmentId) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/Reports/CompleteOrders.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JRDesignQuery jrDesignQuery = new JRDesignQuery();
        jrDesignQuery.setText("SELECT\n" +
                "    appoinment.appoinment_id,\n" +
                "    appoinment.date,\n" +
                "    appoinment.doctor_name,\n" +
                "    appoinment.patient_name,\n" +
                "    completeorders.qty,\n" +
                "    medicine.medi_code,\n" +
                "    medicine.medicine_name,\n" +
                "    medicine.description,\n" +
                "    medicine.unit_price\n" +
                "FROM\n" +
                "    appoinment\n" +
                "        JOIN\n" +
                "    completeorders ON appoinment.appoinment_id = completeorders.appoinment_id\n" +
                "        JOIN\n" +
                "    medicine ON completeorders.medi_code = medicine.medi_code\n" +
                "WHERE\n" +
                "    appoinment.appoinment_id = '" + appoinmentId + "'");
        load.setQuery(jrDesignQuery);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

}
