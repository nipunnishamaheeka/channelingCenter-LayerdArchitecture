package lk.ijse.channelingCenter.DAO.Impl;

import lk.ijse.channelingCenter.DAO.CompleteOrderDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompleteOrderDAOImpl implements CompleteOrderDAO {
    public boolean saveOrder(String id, List<CartTm> list) throws SQLException, ClassNotFoundException {
        for (CartTm tm : list) {
            boolean isSaved = saveOrder(id, tm);
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }
@Override
    public boolean saveOrder(String id, CartTm tm) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("insert into completeorders values(?,?,?)",tm.getM_Code(),id,tm.getQty());

    }
@Override
    public String getAllCompleteOrdersCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM completeorders");
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        return null;
    }
}
