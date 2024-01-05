package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.CompleteOrderBO;
import lk.ijse.channelingCenter.DAO.CompleteOrderDAO;
import lk.ijse.channelingCenter.DAO.Impl.CompleteOrderDAOImpl;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompleteOrderBOImpl implements CompleteOrderBO {
    CompleteOrderDAO completeOrderDAO = new CompleteOrderDAOImpl();
    public boolean saveOrder(String id, List<CartTm> list) throws SQLException, ClassNotFoundException {
     for (CartTm cartTm:list){
         boolean saveOrder = saveOrder(id, cartTm);
     if (!saveOrder){
         return false;
     }
     }return true;
    }

    private boolean saveOrder(String id, CartTm tm) throws SQLException, ClassNotFoundException {
        return completeOrderDAO.saveOrder(id,tm);

    }

    public String getAllOrders() throws SQLException, ClassNotFoundException {
       return null;
    }

    @Override
    public List<AppoinmentDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(AppoinmentDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(AppoinmentDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public AppoinmentDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String splitId(String current) {
        return null;
    }
}
