package lk.ijse.channelingCenter.DAO.Custom;
import lk.ijse.channelingCenter.entity.Patient;

import java.sql.SQLException;

public interface PatientDAO extends CrudDAO<Patient> {
    String getPatientGender(String value) throws SQLException, ClassNotFoundException;
    String getPatientName(String value) throws SQLException, ClassNotFoundException;
    String getPatientAge(String value) throws SQLException, ClassNotFoundException;
    String getCount() throws SQLException, ClassNotFoundException;
    Patient search(String id) throws SQLException, ClassNotFoundException;
}
