package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.PaymentBO;
import lk.ijse.channelingCenter.DAO.Custom.PaymentDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.dto.PaymentDto;
import lk.ijse.channelingCenter.entity.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public boolean save(final PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(dto.getPayment_id(),dto.getPayment_date(),dto.getPayment_time(),dto.getAmount(),dto.getAppoinment_id()));

    }

    @Override
    public boolean update(final PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(dto.getPayment_id(),dto.getPayment_date(),dto.getPayment_time(),dto.getAmount(),dto.getAppoinment_id()));
    }

    public PaymentDto searchPayment(String Doc_id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String payment_id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(payment_id);
    }

    @Override
    public PaymentDto getDetailByContact(String contact) throws SQLException{
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
        List<Payment> paymentList = paymentDAO.getAll();
        ArrayList<PaymentDto> list = new ArrayList<>();
        for(Payment payment :paymentList){
            list.add(new PaymentDto(payment.getPayment_id(),payment.getPayment_date(),payment.getPayment_time(),payment.getAmount(),payment.getAppoinment_id()));
        }
        return list;
    }
}
