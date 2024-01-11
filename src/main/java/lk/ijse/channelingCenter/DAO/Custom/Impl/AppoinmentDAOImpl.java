package lk.ijse.channelingCenter.DAO.Custom.Impl;

import lk.ijse.channelingCenter.DAO.Custom.AppoinmentDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.entity.Appoinment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppoinmentDAOImpl implements AppoinmentDAO {
@Override
    public String getTodayCount() throws SQLException{
        String today = java.time.LocalDate.now().toString();
        System.out.println(today);

        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM appoinment WHERE date = ?", today);

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public String getAllCount() throws SQLException{
        ResultSet rst = CrudUtil.crudUtil("SELECT COUNT(*) FROM appoinment");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public String getPendingOrders() throws SQLException{

        ResultSet rst = CrudUtil.crudUtil("SELECT COUNT(*) FROM appoinment where status = 'pending'");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Appoinment> getPendingAppoinemts() throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM appoinment where status = 'pending'");
        List<Appoinment> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String age = resultSet.getString(4);
            String id = resultSet.getString(5);
            String doctor_name = resultSet.getString(6);
            String patientName = resultSet.getString(7);
            String status = resultSet.getString(8);

            var dto = new Appoinment(appoinment_id, date, patient_id, patientName, age, id, doctor_name, status);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public boolean save(final Appoinment dto) throws SQLException{
        return CrudUtil.crudUtil("insert into appoinment values(?,?,?,?,?,?,?,?)", dto.getAppoinment_id(), dto.getDate(), dto.getPatinet_id(), dto.getAge(), dto.getId(), dto.getDoctor_name(), dto.getPatinetName(), dto.getStatus());
    }

    @Override
    public boolean update(final Appoinment dto) throws SQLException{
        return CrudUtil.crudUtil("UPDATE appoinment SET patient_id = ?,date = ?,time = ?,id = ?,fee_status = ?,age = ?,status =? WHERE  = appoinment_id?",dto.getAppoinment_id(), dto.getDate(), dto.getPatinet_id(), dto.getAge(), dto.getId(), dto.getDoctor_name(), dto.getPatinetName(), dto.getStatus());

    }

    @Override
    public List<Appoinment> getAll() throws SQLException{

        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM appoinment");
        List<Appoinment> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String age = resultSet.getString(4);
            String id = resultSet.getString(5);
            String doctor_name = resultSet.getString(6);
            String patientName = resultSet.getString(7);
            String status = resultSet.getString(8);

            var dto = new Appoinment(appoinment_id, date, patient_id, patientName, age, id, doctor_name, status);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public boolean delete(String id) throws SQLException{
        return CrudUtil.crudUtil("DELETE FROM appoinment WHERE appoinment_id = ?", id);

    }

    @Override
    public Appoinment getDetailByContact(String contact) throws SQLException{
        return null;
    }


    @Override
    public String generateNextId() throws SQLException{

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
    public Appoinment searchAppoinmentID(String Aid) throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM appoinment WHERE appoinment_id = ?", Aid);
        Appoinment dto = null;

        if (resultSet.next()) {
            String appoinment_id = resultSet.getString(1);
            String date = resultSet.getString(2);
            String patient_id = resultSet.getString(3);
            String age = resultSet.getString(4);
            String patientName = resultSet.getString(7);
            String id = resultSet.getString(5);
            String doctor_name = resultSet.getString(6);
            String status = resultSet.getString(8);


            dto = new Appoinment(appoinment_id, date, patient_id, patientName, age, id, doctor_name, status);
        }

        return dto;
    }

    @Override
    public boolean updateAppoinmentStatus(String appoinmentId) throws SQLException{
        return CrudUtil.crudUtil("UPDATE appoinment SET status = 'complete' WHERE appoinment_id = ?", appoinmentId);

    }
}