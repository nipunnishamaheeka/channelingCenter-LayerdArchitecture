package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.dto.DoctorDto;

import java.sql.SQLException;

public interface DoctorDAO extends CrudDAO<DoctorDto> {
    double getfee(String id) throws SQLException, ClassNotFoundException;
    String getname(String id) throws SQLException, ClassNotFoundException;
    String getCount() throws SQLException, ClassNotFoundException;
}
