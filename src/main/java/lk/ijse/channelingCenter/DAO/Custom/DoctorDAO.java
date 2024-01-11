package lk.ijse.channelingCenter.DAO.Custom;



import lk.ijse.channelingCenter.entity.Doctor;

import java.sql.SQLException;

public interface DoctorDAO extends CrudDAO<Doctor> {
    double getfee(String id) throws SQLException, ClassNotFoundException;
    String getname(String id) throws SQLException, ClassNotFoundException;
    String getCount() throws SQLException, ClassNotFoundException;
}
