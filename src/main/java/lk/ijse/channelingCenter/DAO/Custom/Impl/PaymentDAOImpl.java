package lk.ijse.channelingCenter.DAO.Custom.Impl;

import lk.ijse.channelingCenter.DAO.Custom.PaymentDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.PaymentDto;
import lk.ijse.channelingCenter.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public boolean save(final Payment dto) throws SQLException{
        return CrudUtil.crudUtil("insert into payment values(?,?,?,?,?)", dto.getPayment_id(),
                dto.getPayment_date(),
                dto.getPayment_time(),
                dto.getAmount(),
                dto.getAppoinment_id());
    }

    @Override
    public boolean update(final Payment dto) throws SQLException{
        return CrudUtil.crudUtil("UPDATE payment SET payment_id = ?,payment_date = ?,payment_time = ?,amount = ?,appointment_id = ?  WHERE  = payment_id?", dto.getPayment_id(),
                dto.getPayment_date(),
                dto.getPayment_time(),
                dto.getAmount(),
                dto.getAppoinment_id());
    }

    public PaymentDto searchPayment(String Doc_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM payment WHERE payment_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Doc_id);

        ResultSet resultSet = pstm.executeQuery();

        PaymentDto dto = null;

        if (resultSet.next()) {
            String payment_id = resultSet.getString(1);
            Date payment_date = resultSet.getDate(2);
            Time payment_time = resultSet.getTime(3);
            Double amount = resultSet.getDouble(4);
            String appoinment_id = resultSet.getString(5);


            dto = new PaymentDto(payment_id, payment_date, payment_time, amount, appoinment_id);
        }

        return dto;
    }

    @Override
    public boolean delete(String payment_id) throws SQLException{
        return CrudUtil.crudUtil("DELETE FROM payment WHERE payment_id = ?", payment_id);
    }

    @Override
    public Payment getDetailByContact(String contact) throws SQLException{
        return null;
    }

    @Override
    public String generateNextId() throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM payment ORDER BY payment_id DESC LIMIT 1");
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            System.out.println(current);
            return splitId(current);
        }

        return splitId(null);
    }

    public String splitId(String current) {

        if (current != null) {
            String[] tempArray = current.split("PA");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 >= id && id > 0) return "PA00" + id;
            else if (99 >= id && id > 9) return "PA0" + id;
            else return "PA" + id;
        }
        return "PA001";
    }

    @Override
    public List<Payment> getAll() throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM payment");
        List<Payment> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String Payment_id = resultSet.getString(1);
            Date Payment_date = resultSet.getDate(2);
            Time Payment_time = resultSet.getTime(3);
            Double Amount = resultSet.getDouble(4);
            String Appoinment_id = resultSet.getString(5);


            Payment dto = new Payment(Payment_id, Payment_date, Payment_time, Amount, Appoinment_id);
            dtoList.add(dto);
        }
        return dtoList;
    }

}
