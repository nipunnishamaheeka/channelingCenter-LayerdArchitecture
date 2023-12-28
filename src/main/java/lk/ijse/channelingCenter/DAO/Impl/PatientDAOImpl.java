package lk.ijse.channelingCenter.DAO.Impl;


import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.PatientDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl {
    public static String getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM patient");
//        Connection connection = DbConnection.getInstance().getConnection();
//        String sql = "SELECT COUNT(*) FROM patient";
//        try (PreparedStatement pstm = connection.prepareStatement(sql);
//             ResultSet resultSet = pstm.executeQuery()) {
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean savePatient(final PatientDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO patient VALUES(?,?,?,?,?,?,?,?)", dto.getPatient_id(),
                dto.getPatient_name(),
                dto.getMobile_number(),
                dto.getAddress(),
                dto.getSex(),
                dto.getEmail(),
                dto.getAge(),
                dto.getBlood());
//        Connection connection = DbConnection.getInstance().getConnection();
//        String sql = "INSERT INTO patient VALUES(?,?,?,?,?,?,?,?)";
//        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
//            pstm.setString(1, dto.getPatient_id());
//            pstm.setString(2, dto.getPatient_name());
//            pstm.setString(3, dto.getMobile_number());
//            pstm.setString(4, dto.getAddress());
//            pstm.setString(5, dto.getSex());
//            pstm.setString(6, dto.getEmail());
//            pstm.setString(7, dto.getAge());
//            pstm.setString(8, dto.getBlood());
//
//            return pstm.executeUpdate() > 0;

    }

    public boolean updatePatient(final PatientDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE patient SET patient_name = ?,mobile_number = ?,address = ?,sex = ?,email = ?,age = ?,blood =? WHERE   patient_id=?", dto.getPatient_name(),
                dto.getMobile_number(),
                dto.getAddress(),
                dto.getSex(),
                dto.getEmail(),
                dto.getBlood(),
                dto.getAge(),
                dto.getPatient_id());
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "UPDATE patient SET patient_name = ?,mobile_number = ?,address = ?,sex = ?,email = ?,age = ?,blood =? WHERE   patient_id=?";
//        PreparedStatement pstm = connection.prepareStatement(sql);

//        pstm.setString(1, dto.getPatient_name());
//        pstm.setString(2, dto.getMobile_number());
//        pstm.setString(3, dto.getAddress());
//        pstm.setString(4, dto.getSex());
//        pstm.setString(5, dto.getEmail());
//        pstm.setString(6, dto.getBlood());
//        pstm.setString(7, dto.getAge());
//        pstm.setString(8, dto.getPatient_id());
//
//        return pstm.executeUpdate() > 0;
    }

    public PatientDto searchPatient(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE patient_id = ?");
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "SELECT * FROM patient WHERE patient_id = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setString(1, id);

        // ResultSet resultSet = pstm.executeQuery();

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

    public boolean deletePatient(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM patient WHERE patient_id = ?", id);
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "DELETE FROM patient WHERE patient_id = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setString(1, id);

//        return pstm.executeUpdate() > 0;
    }

    public List<PatientDto> getAllPatient() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient");
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "SELECT * FROM patient";
//        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PatientDto> dtoList = new ArrayList<>();

//        ResultSet resultSet = pstm.executeQuery();

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

    public String autoGenaratePatientId() throws SQLException, ClassNotFoundException {
//        Connection connection = DbConnection.getInstance().getConnection();
//
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient ORDER BY patient_id DESC LIMIT 1");
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            System.out.println(current);
            return splitId(current);
        }

        return splitId(null);
    }

    private String splitId(String current) {

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


    public String getPatientName(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE patient_id = ?", value);
//        DbConnection dbConnection = DbConnection.getInstance();
//        Connection connection = dbConnection.getConnection();
//        String sql = "SELECT * FROM patient WHERE patient_id = ?";

//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1, value);
//        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(2);
        }
        return null;

    }

    public String getPatientAge(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE patient_id = ?", value);
//        DbConnection dbConnection = DbConnection.getInstance();
//        Connection connection = dbConnection.getConnection();
//        String sql = "SELECT * FROM patient WHERE patient_id = ?";

//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1, value);
//        ResultSet resultSet = preparedStatement.executeQuery();

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

    public String getPatientGender(String value) throws SQLException, ClassNotFoundException {
//        DbConnection dbConnection = DbConnection.getInstance();
//        Connection connection = dbConnection.getConnection();
//        String sql = "SELECT * FROM patient WHERE patient_id = ?";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE patient_id = ?", value);
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1, value);
//        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(5);
        }
        return null;

    }

    public PatientDto searchNumber(String number) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM patient WHERE mobile_number = ?",number);
        Connection connection = DbConnection.getInstance().getConnection();

//        String sql = "SELECT * FROM patient WHERE mobile_number = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setString(1, number);
//
//        ResultSet resultSet = pstm.executeQuery();

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