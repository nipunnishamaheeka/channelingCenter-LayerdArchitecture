package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.PaymentBO;
import lk.ijse.channelingCenter.DAO.Impl.PaymentDAOImpl;
import lk.ijse.channelingCenter.DAO.PaymentDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.PaymentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public boolean save(final PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(dto);

    }

    @Override
    public boolean update(final PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(dto);
    }

    public PaymentDto searchPayment(String Doc_id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String payment_id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(payment_id);
    }

    @Override
    public PaymentDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNextId();
    }

    public String splitId(String current) {
        return paymentDAO.splitId(current);
    }

    @Override
    public List<PaymentDto> getAll() throws SQLException, ClassNotFoundException {
        return paymentDAO.getAll();
    }
}
