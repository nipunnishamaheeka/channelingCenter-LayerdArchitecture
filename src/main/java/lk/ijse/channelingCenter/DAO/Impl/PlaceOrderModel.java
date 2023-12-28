package lk.ijse.channelingCenter.DAO.Impl;

import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.PaymentDto;
import lk.ijse.channelingCenter.dto.PlaceOrderDto;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderModel {

//    public String autoGenaratePatientId() throws SQLException {
//            Connection connection = DbConnection.getInstance().getConnection();
//
//            ResultSet resultSet = connection.prepareStatement("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1").executeQuery();
//            String current = null;
//            while (resultSet.next()) {
//                current = resultSet.getString(1);
//                System.out.println(current);
//                return splitId(current);
//            }
//
//            return splitId(null);
//        }
//
//        private String splitId(String current) {
//
//            if (current != null) {
//                String[] tempArray = current.split("OR");
//                int id = Integer.parseInt(tempArray[1]);
//                id++;
//                if (9 >= id && id > 0) return "OR00" + id;
//                else if (99 >= id && id > 9) return "OR0" + id;
//                else return "OR" + id;
//            }
//            return "OR001";
//        }

    public boolean placeOrder(PlaceOrderDto dto) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            System.out.println("connection false ");
            System.out.println(dto.getAppoinment_id());
            boolean isAppoinmentUpdated = new AppoinmentDAOImpl().updateAppoinmentStatus(dto.getAppoinment_id());
            if (isAppoinmentUpdated) {
                System.out.println("appoinmentUpdated");
                String payId = new PaymentDAOImpl().autoGenaratePatientId();
                boolean isPaid = new PaymentDAOImpl().savePayment(new PaymentDto(payId, dto.getDate(), dto.getTime(), dto.getAmount(), dto.getAppoinment_id()));
                if (isPaid) {
                    System.out.println("paymentUpdated");
                    if (!dto.getTmlist().isEmpty()) {
                        boolean isMediUpdate = new MedicineDAOImpl().updateMedicineQty(dto.getTmlist());
                        if (isMediUpdate) {
                            boolean isOrderSave = new CompleteOrderDAOImpl().saveOrder(dto.getAppoinment_id(), dto.getTmlist());
                            if (isOrderSave) {
                                connection.commit();
                                result = true;
                            }
                        } else {
                            connection.commit();
                            result = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return result;
    }
}

