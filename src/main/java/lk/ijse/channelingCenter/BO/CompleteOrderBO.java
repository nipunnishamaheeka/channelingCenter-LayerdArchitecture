package lk.ijse.channelingCenter.BO;

import lk.ijse.channelingCenter.dto.AppoinmentDto;

import java.sql.SQLException;
import java.util.List;

public interface CompleteOrderBO {
    List<AppoinmentDto> getAll() throws SQLException, ClassNotFoundException;
    boolean save(AppoinmentDto dto) throws SQLException, ClassNotFoundException;
    boolean update(AppoinmentDto dto) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    AppoinmentDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException;
    String generateNextId() throws SQLException, ClassNotFoundException;
    //void search(String id) throws SQLException, ClassNotFoundException;
    String splitId(String current);
}
