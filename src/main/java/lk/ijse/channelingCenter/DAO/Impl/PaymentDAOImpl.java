package lk.ijse.channelingCenter.DAO.Impl;

import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.PaymentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl {
    public boolean savePayment(final PaymentDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("insert into payment values(?,?,?,?,?)",dto.getPayment_id(),
        dto.getPayment_date(),
        dto.getPayment_time(),
        dto.getAmount(),
        dto.getAppoinment_id());
//        Connection connection = DbConnection.getInstance().getConnection();

//        String sql = "insert into payment values(?,?,?,?,?)";
//        PreparedStatement pstm = connection.prepareStatement(sql);

//        pstm.setString(1, dto.getPayment_id());
//        pstm.setDate(2, dto.getPayment_date());
//        pstm.setTime(3, dto.getPayment_time());
//        pstm.setDouble(4, dto.getAmount());
//        pstm.setString(5, dto.getAppoinment_id());
//
//        boolean isSaved = pstm.executeUpdate() > 0;
//
//        return isSaved;

    }

    public boolean updatePayment(final PaymentDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE payment SET payment_id = ?,payment_date = ?,payment_time = ?,amount = ?,appointment_id = ?  WHERE  = payment_id?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getPayment_id());
        pstm.setDate(2, dto.getPayment_date());
        pstm.setTime(3, dto.getPayment_time());
        pstm.setDouble(4, dto.getAmount());
        pstm.setString(5, dto.getAppoinment_id());

        return pstm.executeUpdate() > 0;
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

    public boolean deletePayment(String payment_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM payment WHERE payment_id = ?",payment_id);
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "DELETE FROM payment WHERE payment_id = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setString(1, payment_id);
//
//        return pstm.executeUpdate() > 0;
    }

    public String autoGenaratePatientId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM payment ORDER BY payment_id DESC LIMIT 1");
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        ResultSet resultSet = connection.prepareStatement("SELECT * FROM payment ORDER BY payment_id DESC LIMIT 1").executeQuery();
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
            String[] tempArray = current.split("PA");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 >= id && id > 0) return "PA00" + id;
            else if (99 >= id && id > 9) return "PA0" + id;
            else return "PA" + id;
        }
        return "PA001";
    }


    public List<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM payment");
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "SELECT * FROM payment";
//        PreparedStatement pstm = connection.prepareStatement(sql);

        List<PaymentDto> dtoList = new ArrayList<>();

        //ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String Payment_id = resultSet.getString(1);
            Date Payment_date = resultSet.getDate(2);
            Time Payment_time = resultSet.getTime(3);
            Double Amount = resultSet.getDouble(4);
            String Appoinment_id = resultSet.getString(5);


            var dto = new PaymentDto(Payment_id, Payment_date, Payment_time, Amount, Appoinment_id);
            dtoList.add(dto);
        }
        return dtoList;
    }

}
