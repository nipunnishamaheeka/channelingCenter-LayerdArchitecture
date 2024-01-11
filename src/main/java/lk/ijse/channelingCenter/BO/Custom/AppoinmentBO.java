package lk.ijse.channelingCenter.BO.Custom;

import lk.ijse.channelingCenter.BO.SuperBO;
import lk.ijse.channelingCenter.dto.AppoinmentDto;

import java.sql.SQLException;
import java.util.List;

public interface AppoinmentBO extends SuperBO {
    List<AppoinmentDto> getAll() throws SQLException, ClassNotFoundException;
    boolean save(AppoinmentDto dto) throws SQLException, ClassNotFoundException;
    boolean update(AppoinmentDto dto) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    AppoinmentDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException;
    String generateNextId() throws SQLException, ClassNotFoundException;
    //void search(String id) throws SQLException, ClassNotFoundException;
    String splitId(String current);
    AppoinmentDto searchAppoinmentID(String Aid) throws SQLException, ClassNotFoundException;
    boolean updateAppoinmentStatus(String appoinmentId) throws SQLException, ClassNotFoundException;
    List<AppoinmentDto> getPendingAppoinemts() throws SQLException, ClassNotFoundException;
    String getPendingOrders() throws SQLException, ClassNotFoundException;
    String getAllCount() throws SQLException, ClassNotFoundException;
    String getToday() throws SQLException, ClassNotFoundException;
}
