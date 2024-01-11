package lk.ijse.channelingCenter.DAO.Custom.Impl;

import lk.ijse.channelingCenter.DAO.Custom.DoctorDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.entity.Doctor;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {
    @Override

    public String getCount() throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM doctor");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }

    @Override
    public boolean save(Doctor dto) throws SQLException{
        return CrudUtil.crudUtil("insert into doctor values(?,?,?,?,?,?,?)", dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getNumber(),
                dto.getType(),
                dto.getDrFee());
    }

    @Override
    public boolean update(final Doctor dto) throws SQLException{
        return CrudUtil.crudUtil("UPDATE doctor SET doctor_name = ?,address = ?,email = ?,number = ?,type = ?, drFee =? WHERE  id=?", dto.getName(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getNumber(),
                dto.getType(),
                dto.getDrFee(),
                dto.getId());
    }

    @Override
    public boolean delete(String doc_id) throws SQLException{
        return CrudUtil.crudUtil("DELETE FROM doctor WHERE id = ?", doc_id);
    }

    @Override
    public List<Doctor> getAll() throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM doctor");

        List<Doctor> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);


            String address = resultSet.getString(3);
            String email = resultSet.getString(4);
            String number = resultSet.getString(5);
            String type = resultSet.getString(6);
            double fee = resultSet.getDouble(7);

            var dto = new Doctor(id, name, address, email, number, type, fee);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public String generateNextId() throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM doctor ORDER BY id DESC LIMIT 1");
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
            String[] tempArray = current.split("D");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 >= id && id > 0) return "D00" + id;
            else if (99 >= id && id > 9) return "D0" + id;
            else return "D" + id;
        }
        return "D001";
    }

    @Override
    public String getname(String id) throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT doctor_name FROM doctor WHERE id=?", id);
        if (resultSet.next()) return resultSet.getString(1);
        return null;
    }

    @Override
    public Doctor getDetailByContact(String number) throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM doctor WHERE number = ?", number);
        Doctor dto = null;

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String email = resultSet.getString(4);
            String mobileNumber = resultSet.getString(5);
            String type = resultSet.getString(6);
            double fee = resultSet.getDouble(7);

            dto = new Doctor(id, name, address, email, mobileNumber, type, fee);
        }

        return dto;
    }

    @Override
    public double getfee(String id) throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT drFee FROM doctor WHERE id=?",id);
        if (resultSet.next()) return resultSet.getDouble(1);
        return 0;
    }

    //
    public Doctor searchDoctor(String Doc_id) throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM doctor WHERE id = ?");

        Doctor dto = null;

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String email = resultSet.getString(4);
            String number = resultSet.getString(5);
            String type = resultSet.getString(6);
            double fee = resultSet.getDouble(7);

            dto = new Doctor(id, name, address, email, number, type,fee);
        }

        return dto;
    }
}
