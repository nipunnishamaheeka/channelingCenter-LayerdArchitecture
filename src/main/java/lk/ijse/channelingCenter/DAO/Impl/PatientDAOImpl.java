package lk.ijse.channelingCenter.DAO.Impl;


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

public class PatientDAOImpl implements PatientDAO {
@Override
    public String getCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM patient");

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
@Override
    public boolean save(final PatientDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO patient VALUES(?,?,?,?,?,?,?,?)", dto.getPatient_id(),
                dto.getPatient_name(),
                dto.getMobile_number(),
                dto.getAddress(),
                dto.getSex(),
                dto.getEmail(),
                dto.getAge(),
                dto.getBlood());

    }
@Override
    public boolean update(final PatientDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE patient SET patient_name = ?,mobile_number = ?,address = ?,sex = ?,email = ?,age = ?,blood =? WHERE   patient_id=?", dto.getPatient_name(),
                dto.getMobile_number(),
                dto.getAddress(),
                dto.getSex(),
                dto.getEmail(),
                dto.getBlood(),
                dto.getAge(),
                dto.getPatient_id());
    }
@Override
    public PatientDto search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE patient_id = ?");

        PatientDto dto = null;

        if (resultSet.next()) {
            String Patient_id = resultSet.getString(1);
            String Patient_name = resultSet.getString(2);
            String Mobile_number = resultSet.getString(3);
            String Address = resultSet.getString(4);
            String Sex = resultSet.getString(5);
            String Email = resultSet.getString(6);
            String Age = resultSet.getString(7);
            String Blood = resultSet.getString(8);

            dto = new PatientDto(Patient_id, Patient_name, Mobile_number, Address, Sex, Email, Age, Blood);
        }

        return dto;
    }
@Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM patient WHERE patient_id = ?", id);
    }
@Override
    public List<PatientDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient");

        List<PatientDto> dtoList = new ArrayList<>();
        while (resultSet.next()) {
            String Patient_id = resultSet.getString(1);
            String Patient_name = resultSet.getString(2);
            String Mobile_number = resultSet.getString(3);
            String Address = resultSet.getString(4);
            String Sex = resultSet.getString(5);
            String Email = resultSet.getString(6);
            String Age = resultSet.getString(7);
            String Blood = resultSet.getString(8);


            var dto = new PatientDto(Patient_id, Patient_name, Mobile_number, Address, Sex, Email, Age, Blood);
            dtoList.add(dto);
        }
        return dtoList;
    }
@Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient ORDER BY patient_id DESC LIMIT 1");
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            System.out.println(current);
            return splitId(current);
        }

        return splitId(null);
    }
@Override
    public String splitId(String current) {

        if (current != null) {
            String[] tempArray = current.split("P");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 >= id && id > 0) return "P00" + id;
            else if (99 >= id && id > 9) return "P0" + id;
            else return "P" + id;
        }
        return "P001";
    }

@Override
    public String getPatientName(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE patient_id = ?", value);
        if (resultSet.next()) {
            return resultSet.getString(2);
        }
        return null;

    }
@Override
    public String getPatientAge(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE patient_id = ?", value);
        if (resultSet.next()) {
            return resultSet.getString(7);
        }
        return null;

    }

    public String getBloodGroup(String value) throws SQLException {
        DbConnection dbConnection = DbConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        String sql = "SELECT * FROM patient WHERE blood = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, value);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(2);
        }
        return null;

    }
@Override
    public String getPatientGender(String value) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE patient_id = ?", value);

        if (resultSet.next()) {
            return resultSet.getString(5);
        }
        return null;

    }
@Override
    public PatientDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE mobile_number = ?",contact);
        PatientDto dto = null;

        if (resultSet.next()) {
            String Patient_id = resultSet.getString(1);
            String Patient_name = resultSet.getString(2);
            String Mobile_number = resultSet.getString(3);
            String Address = resultSet.getString(4);
            String Sex = resultSet.getString(5);
            String Email = resultSet.getString(6);
            String Age = resultSet.getString(7);
            String Blood = resultSet.getString(8);

            dto = new PatientDto(Patient_id, Patient_name, Mobile_number, Address, Sex, Email, Age, Blood);
        }

        return dto;
    }
}