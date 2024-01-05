package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.SQLException;

public interface CompleteOrderDAO {
    boolean saveOrder(String id, CartTm tm) throws SQLException, ClassNotFoundException;
    String getAllCompleteOrdersCount() throws SQLException, ClassNotFoundException;
}
