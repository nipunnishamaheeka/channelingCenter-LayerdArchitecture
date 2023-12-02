package lk.ijse.channelingCenter.model;

import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppoinmentModel {
    @SneakyThrows
    public static String getToday() {
        String today = java.time.LocalDate.now().toString();
        System.out.println(today);
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM appoinment WHERE date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, today);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static String getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM appoinment";
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String getPendingOrders() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM appoinment where status = 'pending'";
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public List<AppoinmentDto> getPendingAppoinemts() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appoinment where status = 'pending'";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<AppoinmentDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String age = resultSet.getString(4);
            String id = resultSet.getString(5);
            String doctor_name = resultSet.getString(6);
            String patientName = resultSet.getString(7);
            String status = resultSet.getString(8);

            var dto = new AppoinmentDto(appoinment_id, date, patient_id,patientName,age,id,doctor_name,status);
            dtoList.add(dto);
        }
        return dtoList;
    }


    public boolean saveAppoinment(final AppoinmentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into appoinment values(?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getAppoinment_id());
        pstm.setString(2, dto.getDate());
        pstm.setString(3, dto.getPatinet_id());
        pstm.setString(5, dto.getAge());
        pstm.setString(6, dto.getId());
        pstm.setString(7, dto.getDoctor_name());
        pstm.setString(4, dto.getPatinetName());
        pstm.setString(8, dto.getStatus());



        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public boolean updateAppoinment(final AppoinmentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE appoinment SET patient_id = ?,date = ?,time = ?,id = ?,fee_status = ?,age = ?,status =? WHERE  = appoinment_id?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getAppoinment_id());
        pstm.setString(2, dto.getDate());
        pstm.setString(3, dto.getPatinet_id());
        pstm.setString(4, dto.getPatinetName());
        pstm.setString(5, dto.getAge());
        pstm.setString(6, dto.getId());
        pstm.setString(7, dto.getDoctor_name());
        pstm.setString(8, dto.getStatus());

        return pstm.executeUpdate() > 0;
    }

   /* public static AppoinmentDto searchAppoinment(String Appoinment_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appoinment WHERE appoinment_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Appoinment_id);

        ResultSet resultSet = pstm.executeQuery();

        AppoinmentDto dto = null;

        if (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String time = resultSet.getString(2);
            String date = resultSet.getString(3);
            String patient_name = resultSet.getString(4);
            String dr_name = resultSet.getString(5);
            String fee_status = resultSet.getString(6);
            String age = resultSet.getString(7);

            dto = new AppoinmentDto(appoinment_id, time, date,patient_name, dr_name,fee_status,age);
        }

        return dto;
    }*/

    public List<AppoinmentDto> getAllAppoinment() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appoinment";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<AppoinmentDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String age = resultSet.getString(4);
            String id = resultSet.getString(5);
            String doctor_name = resultSet.getString(6);
            String patientName = resultSet.getString(7);
            String status = resultSet.getString(8);

            var dto = new AppoinmentDto(appoinment_id, date, patient_id,patientName,age,id,doctor_name,status);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteAppoinment(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM appoinment WHERE appoinment_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public List<AppoinmentDto> LoadAllAppoinment() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appoinment";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AppoinmentDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(new AppoinmentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        return dtoList;
    }
    public String autoGenarateId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT appoinment_id FROM appoinment ORDER BY appoinment_id DESC LIMIT 1").executeQuery();
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            return splitId(current);
        }
        return splitId(null);
    }

    private String splitId(String current) {

        if (current != null) {
            String[] tempArray = current.split("A");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 > id && id > 0) return "A00" + id;
            else if (99 > id && id > 9) return "A0" + id;
            else return "A" + id;
        }
        return "A001";
    }
    public AppoinmentDto searchAppoinmentID(String Aid) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM appoinment WHERE appoinment_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Aid);

        ResultSet resultSet = pstm.executeQuery();

        AppoinmentDto dto = null;

        if (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String age = resultSet.getString(4);
            String patientName = resultSet.getString(7);
            String id = resultSet.getString(5);
            String doctor_name = resultSet.getString(6);
            String status = resultSet.getString(8);


            dto = new AppoinmentDto(appoinment_id, date, patient_id,patientName,age,id,doctor_name,status);
        }

        return dto;
    }

    public boolean updateAppoinmentStatus(String appoinmentId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE appoinment SET status = 'complete' WHERE appoinment_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, appoinmentId);

        return pstm.executeUpdate()> 0;
    }
//    public String patientautoGenarateId() throws SQLException {
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        ResultSet resultSet = connection.prepareStatement("SELECT patient_id FROM patient ORDER BY patient_id DESC LIMIT 1").executeQuery();
//        String current = null;
//        while (resultSet.next()) {
//            current = resultSet.getString(1);
//            return patientsplitId(current);
//        }
//        return patientsplitId(null);
//    }
//
//    private String patientsplitId(String current) {
//
//        if (current != null) {
//            String[] tempArray = current.split("P");
//            int id = Integer.parseInt(tempArray[1]);
//            id++;
//            if (9 > id && id > 0) return "P00" + id;
//            else if (99 > id && id > 9) return "P0" + id;
//            else return "P" + id;
//        }
//        return "P001";
//    }

}