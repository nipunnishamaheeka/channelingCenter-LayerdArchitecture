package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.PatientBO;
import lk.ijse.channelingCenter.DAO.Impl.PatientDAOImpl;
import lk.ijse.channelingCenter.DAO.PatientDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.PatientDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public String getCount() throws SQLException, ClassNotFoundException {
        return patientDAO.getCount();
    }

    @Override
    public boolean save(final PatientDto dto) throws SQLException, ClassNotFoundException {
        return patientDAO.save(dto);

    }

    @Override
    public boolean update(final PatientDto dto) throws SQLException, ClassNotFoundException {
        return patientDAO.update(dto);
    }

    @Override
    public PatientDto search(String id) throws SQLException, ClassNotFoundException {
        return patientDAO.search(id);
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return patientDAO.delete(id);
    }

    @Override
    public List<PatientDto> getAll() throws SQLException, ClassNotFoundException {
        return patientDAO.getAll();
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return patientDAO.generateNextId();
    }

    @Override
    public String splitId(String current) throws SQLException, ClassNotFoundException {
        return patientDAO.splitId(current);
    }

    @Override
    public String getPatientName(String value) throws SQLException, ClassNotFoundException {
        return patientDAO.getPatientName(value);

    }

    @Override
    public String getPatientAge(String value) throws SQLException, ClassNotFoundException {
        return patientDAO.getPatientAge(value);

    }

    public String getBloodGroup(String value) throws SQLException {
        return null;

    }

    @Override
    public String getPatientGender(String value) throws SQLException, ClassNotFoundException {

        return patientDAO.getPatientGender(value);

    }

    @Override
    public PatientDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return patientDAO.getDetailByContact(contact);
    }
}
