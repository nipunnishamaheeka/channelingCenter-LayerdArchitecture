package lk.ijse.channelingCenter.DAO.Custom;


import lk.ijse.channelingCenter.entity.Appoinment;

import java.sql.SQLException;
import java.util.List;

public interface AppoinmentDAO extends CrudDAO<Appoinment> {
    String getPendingOrders() throws SQLException, ClassNotFoundException;
    List<Appoinment> getPendingAppoinemts() throws SQLException, ClassNotFoundException;
    Appoinment searchAppoinmentID(String Aid) throws SQLException, ClassNotFoundException;
    boolean updateAppoinmentStatus(String appoinmentId) throws SQLException, ClassNotFoundException;
    String getAllCount() throws SQLException, ClassNotFoundException;
    String getTodayCount() throws SQLException, ClassNotFoundException;

}
