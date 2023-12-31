package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.CompleteOrderBO;
import lk.ijse.channelingCenter.DAO.CompleteOrderDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompleteOrderBOImpl implements CompleteOrderBO {
    CompleteOrderDAO completeOrderDAO = new CompleteOrderBOImpl();
    public boolean saveOrder(String id, List<CartTm> list) throws SQLException, ClassNotFoundException {
       return completeOrderDAO.
    }

    private boolean saveOrder(String id, CartTm tm) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("insert into completeorders values(?,?,?)",tm.getM_Code(),id,tm.getQty());

    }

    public String getAllOrders() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM completeorders");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
