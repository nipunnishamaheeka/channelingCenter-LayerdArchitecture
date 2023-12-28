package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.DAO.CrudDAO;
import lk.ijse.channelingCenter.dto.AppoinmentDto;

import java.sql.SQLException;
import java.util.List;

public interface AppoinmentDAO extends CrudDAO<AppoinmentDto> {
    String getPendingOrders() throws SQLException, ClassNotFoundException;
    List<AppoinmentDto> getPendingAppoinemts() throws SQLException, ClassNotFoundException;
    AppoinmentDto searchAppoinmentID(String Aid) throws SQLException, ClassNotFoundException;
    boolean updateAppoinmentStatus(String appoinmentId) throws SQLException, ClassNotFoundException;
    String getAllCount() throws SQLException, ClassNotFoundException;

}
