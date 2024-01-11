package lk.ijse.channelingCenter.BO.Custom;

import lk.ijse.channelingCenter.BO.SuperBO;
import lk.ijse.channelingCenter.dto.PaymentDto;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean save(final PaymentDto dto) throws SQLException, ClassNotFoundException;
    boolean update(final PaymentDto dto) throws SQLException, ClassNotFoundException;
    PaymentDto searchPayment(String Doc_id) throws SQLException;
    boolean delete(String payment_id) throws SQLException, ClassNotFoundException;
    PaymentDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException;
    String generateNextId() throws SQLException, ClassNotFoundException;
    String splitId(String current)throws SQLException, ClassNotFoundException;
    List<PaymentDto> getAll() throws SQLException, ClassNotFoundException;
}
