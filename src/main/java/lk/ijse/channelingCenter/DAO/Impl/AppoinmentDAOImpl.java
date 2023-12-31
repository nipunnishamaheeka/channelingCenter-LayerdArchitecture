package lk.ijse.channelingCenter.DAO.Impl;

import lk.ijse.channelingCenter.DAO.AppoinmentDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppoinmentDAOImpl implements AppoinmentDAO {
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
@Override
    public String getAllCount() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.crudUtil("SELECT COUNT(*) FROM appoinment");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
@Override
    public String getPendingOrders() throws SQLException, ClassNotFoundException {

        ResultSet rst = CrudUtil.crudUtil("SELECT COUNT(*) FROM appoinment where status = 'pending'");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
@Override
    public List<AppoinmentDto> getPendingAppoinemts() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM appoinment where status = 'pending'");
        List<AppoinmentDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String age = resultSet.getString(4);
            String id = resultSet.getString(5);
            String doctor_name = resultSet.getString(6);
            String patientName = resultSet.getString(7);
            String status = resultSet.getString(8);

            var dto = new AppoinmentDto(appoinment_id, date, patient_id, patientName, age, id, doctor_name, status);
            dtoList.add(dto);
        }
        return dtoList;
    }

@Override
    public boolean save(final AppoinmentDto dto) throws SQLException, ClassNotFoundException {
         return CrudUtil.crudUtil("insert into appoinment values(?,?,?,?,?,?,?,?)",dto.getAppoinment_id(),dto.getDate(),dto.getPatinet_id(), dto.getAge(), dto.getId(), dto.getDoctor_name(), dto.getPatinetName(), dto.getStatus());
    }
    @Override
    public boolean update(final AppoinmentDto dto) throws SQLException {
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
@Override
    public List<AppoinmentDto> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM appoinment");
        List<AppoinmentDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String age = resultSet.getString(4);
            String id = resultSet.getString(5);
            String doctor_name = resultSet.getString(6);
            String patientName = resultSet.getString(7);
            String status = resultSet.getString(8);

            var dto = new AppoinmentDto(appoinment_id, date, patient_id, patientName, age, id, doctor_name, status);
            dtoList.add(dto);
        }
        return dtoList;
    }
@Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM appoinment WHERE appoinment_id = ?", id);

    }

    @Override
    public AppoinmentDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.crudUtil("SELECT appoinment_id FROM appoinment ORDER BY appoinment_id DESC LIMIT 1");
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            return splitId(current);
        }
        return splitId(null);
    }
@Override
    public String splitId(String current) {

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
@Override
    public AppoinmentDto searchAppoinmentID(String Aid) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM appoinment WHERE appoinment_id = ?", Aid);
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


            dto = new AppoinmentDto(appoinment_id, date, patient_id, patientName, age, id, doctor_name, status);
        }

        return dto;
    }
@Override
    public boolean updateAppoinmentStatus(String appoinmentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE appoinment SET status = 'complete' WHERE appoinment_id = ?", appoinmentId);

    }
}