package lk.ijse.channelingCenter.BO.Custom;

import lk.ijse.channelingCenter.BO.SuperBO;
import lk.ijse.channelingCenter.dto.PatientDto;

import java.sql.SQLException;
import java.util.List;

public interface PatientBO extends SuperBO {
    String getCount() throws SQLException, ClassNotFoundException;
    boolean save(final PatientDto dto) throws SQLException, ClassNotFoundException;
    boolean update(final PatientDto dto) throws SQLException, ClassNotFoundException;
    PatientDto search(String id) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    List<PatientDto> getAll() throws SQLException, ClassNotFoundException;
    String generateNextId() throws SQLException, ClassNotFoundException;
    String splitId(String current) throws SQLException, ClassNotFoundException;
    String getPatientName(String value) throws SQLException, ClassNotFoundException;
    String getPatientAge(String value) throws SQLException, ClassNotFoundException;
    String getPatientGender(String value) throws SQLException, ClassNotFoundException;
    PatientDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException;
}
