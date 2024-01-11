package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.PlaceOrderBO;
import lk.ijse.channelingCenter.DAO.Custom.AppoinmentDAO;
import lk.ijse.channelingCenter.DAO.Custom.CompleteOrderDAO;
import lk.ijse.channelingCenter.DAO.Custom.Impl.CompleteOrderDAOImpl;
import lk.ijse.channelingCenter.DAO.Custom.MedicineDAO;
import lk.ijse.channelingCenter.DAO.Custom.PaymentDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.Util.TransactionUtil;
import lk.ijse.channelingCenter.dto.PlaceOrderDto;
import lk.ijse.channelingCenter.entity.Payment;

import java.sql.SQLException;

public class PlaceOrderBOImpl implements PlaceOrderBO {
 PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);
MedicineDAO medicineDAO = (MedicineDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.MEDICINE);
CompleteOrderDAO completeOrderDAO = (CompleteOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.COMPLETEORDER);
AppoinmentDAO appoinmentDAO = (AppoinmentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.APPOINMENT);
    @Override
    public boolean placeOrder(PlaceOrderDto dto) throws SQLException{
        Boolean result = false;

        try {

            TransactionUtil.startTransaction();
            System.out.println("connection false ");
            System.out.println(dto.getAppoinment_id());
            boolean isAppoinmentUpdated = appoinmentDAO.updateAppoinmentStatus(dto.getAppoinment_id());
            if (isAppoinmentUpdated) {
                System.out.println("appoinmentUpdated");
                String payId = paymentDAO.generateNextId();
                boolean isPaid = paymentDAO.save(new Payment(paymentDAO.generateNextId(),dto.getDate(),dto.getTime(),dto.getAmount(),dto.getAppoinment_id()));
                if (isPaid) {
                    System.out.println("paymentUpdated");
                    if (!dto.getTmlist().isEmpty()) {
                        boolean isMediUpdate = medicineDAO.updateMedicineQty(dto.getTmlist());
                        if (isMediUpdate) {
                            boolean isOrderSave = completeOrderDAO.saveOrderByList(dto.getAppoinment_id(), dto.getTmlist());
                            if (isOrderSave) {
                                TransactionUtil.endTransaction();
                                result = true;
                            }
                        } else {
                            TransactionUtil.endTransaction();
                            result = true;
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            TransactionUtil.rollBack();
        }
        return result;
    }
}

