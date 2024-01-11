package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.CompleteOrderBO;
import lk.ijse.channelingCenter.DAO.Custom.CompleteOrderDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompleteOrderBOImpl implements CompleteOrderBO {
    CompleteOrderDAO completeOrderDAO = (CompleteOrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.COMPLETEORDER);
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

    public String getAllOrders() throws SQLException{
       return null;
    }

    @Override
    public List<AppoinmentDto> getAll() throws SQLException{
        return null;
    }

    @Override
    public boolean save(AppoinmentDto dto) throws SQLException{
        return false;
    }

    @Override
    public boolean update(AppoinmentDto dto) throws SQLException{
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException{
        return false;
    }

    @Override
    public AppoinmentDto getDetailByContact(String contact) throws SQLException{
        return null;
    }

    @Override
    public String generateNextId() throws SQLException{

        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1");
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            System.out.println(current);
            return splitId(current);
        }

        return splitId(null);
    }

    @Override
    public String splitId(String current) {
        if (current != null) {
            String[] tempArray = current.split("OR");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 >= id && id > 0) return "OR00" + id;
            else if (99 >= id && id > 9) return "OR0" + id;
            else return "OR" + id;
        }
        return "OR001";
    }
}
