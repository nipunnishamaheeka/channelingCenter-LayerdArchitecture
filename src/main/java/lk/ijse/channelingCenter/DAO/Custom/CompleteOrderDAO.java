package lk.ijse.channelingCenter.DAO.Custom;

import lk.ijse.channelingCenter.DAO.SuperDAO;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public interface CompleteOrderDAO extends SuperDAO {
    boolean saveOrder(String id, CartTm tm) throws SQLException, ClassNotFoundException;
    String getAllCompleteOrdersCount() throws SQLException, ClassNotFoundException;
    boolean saveOrderByList(String id, List<CartTm> list) throws SQLException, ClassNotFoundException;
}
