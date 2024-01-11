package lk.ijse.channelingCenter.BO.Custom;

import lk.ijse.channelingCenter.BO.SuperBO;
import lk.ijse.channelingCenter.dto.DoctorDto;

import java.sql.SQLException;
import java.util.List;

public interface DoctorBO extends SuperBO {
    String getCount() throws SQLException, ClassNotFoundException;

    boolean save(DoctorDto dto) throws SQLException, ClassNotFoundException;

    boolean update(final DoctorDto dto) throws SQLException, ClassNotFoundException;

    boolean delete(String doc_id) throws SQLException, ClassNotFoundException;

    List<DoctorDto> getAll() throws SQLException, ClassNotFoundException;

    String generateNextId() throws SQLException, ClassNotFoundException;

    String splitId(String current);

    String getname(String id) throws SQLException, ClassNotFoundException;

    DoctorDto getDetailByContact(String number) throws SQLException, ClassNotFoundException;

    double getfee(String id) throws SQLException, ClassNotFoundException;

    DoctorDto searchDoctor(String Doc_id) throws SQLException, ClassNotFoundException;
}
