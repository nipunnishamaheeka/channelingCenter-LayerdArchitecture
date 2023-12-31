package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.AppoinmentBO;
import lk.ijse.channelingCenter.DAO.AppoinmentDAO;
import lk.ijse.channelingCenter.DAO.Impl.AppoinmentDAOImpl;
import lk.ijse.channelingCenter.dto.AppoinmentDto;

import java.sql.SQLException;
import java.util.List;

public class AppoinmentBOImpl implements AppoinmentBO {
    AppoinmentDAO appoinmentDAO = new AppoinmentDAOImpl();


    @Override
    public String getToday() throws SQLException, ClassNotFoundException {
        return appoinmentDAO;
    }

    @Override
    public String getAllCount() throws SQLException, ClassNotFoundException {
        return appoinmentDAO.getAllCount();
    }

    @Override
    public String getPendingOrders() throws SQLException, ClassNotFoundException {
        return appoinmentDAO.getPendingOrders();
    }

    @Override
    public List<AppoinmentDto> getPendingAppoinemts() throws SQLException, ClassNotFoundException {

        return appoinmentDAO.getPendingAppoinemts();
    }

    @Override
    public boolean save(final AppoinmentDto dto) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.save(dto);
    }

    @Override
    public boolean update(final AppoinmentDto dto) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.update(dto);
    }

    @Override
    public List<AppoinmentDto> getAll() throws SQLException, ClassNotFoundException {
        return appoinmentDAO.getAll();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.delete(id);

    }

    @Override
    public AppoinmentDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return appoinmentDAO.generateNextId();
    }

    @Override
    public String splitId(String current) {
        return appoinmentDAO.splitId(current);
    }

    @Override
    public AppoinmentDto searchAppoinmentID(String Aid) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.searchAppoinmentID(Aid);
    }

    @Override
    public boolean updateAppoinmentStatus(String appoinmentId) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.updateAppoinmentStatus(appoinmentId);

    }
}
