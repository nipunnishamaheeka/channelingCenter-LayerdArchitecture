package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.dto.PatientDto;
import org.checkerframework.checker.units.qual.C;

import java.sql.SQLException;

public interface PatientDAO extends CrudDAO<PatientDto> {
    String getPatientGender(String value) throws SQLException, ClassNotFoundException;
    String getPatientName(String value) throws SQLException, ClassNotFoundException;
    String getPatientAge(String value) throws SQLException, ClassNotFoundException;
    String getCount() throws SQLException, ClassNotFoundException;
    PatientDto search(String id) throws SQLException, ClassNotFoundException;
}
