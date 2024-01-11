package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.AppoinmentBO;
import lk.ijse.channelingCenter.DAO.Custom.AppoinmentDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.dto.AppoinmentDto;
import lk.ijse.channelingCenter.entity.Appoinment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppoinmentBOImpl implements AppoinmentBO {
    AppoinmentDAO appoinmentDAO = (AppoinmentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.APPOINMENT);


    @Override
    public String getToday() throws SQLException, ClassNotFoundException {
        return appoinmentDAO.getTodayCount();
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

        List<Appoinment> pendingAppoinemts = appoinmentDAO.getPendingAppoinemts();
        List<AppoinmentDto> list = new ArrayList<>();
        for (Appoinment appoinment : pendingAppoinemts) {
            list.add(new AppoinmentDto(appoinment.getAppoinment_id(), appoinment.getDate(), appoinment.getPatinet_id(), appoinment.getPatinetName(), appoinment.getAge(), appoinment.getId(), appoinment.getDoctor_name(), appoinment.getStatus()));

        }
        return list;

    }

    @Override
    public boolean save(final AppoinmentDto dto) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.save(new Appoinment(dto.getAppoinment_id(), dto.getDate(), dto.getPatinet_id(), dto.getPatinetName(), dto.getAge(), dto.getId(), dto.getDoctor_name(), dto.getStatus()));
    }

    @Override
    public boolean update(final AppoinmentDto dto) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.update(new Appoinment(dto.getAppoinment_id(), dto.getDate(), dto.getPatinet_id(), dto.getPatinetName(), dto.getAge(), dto.getId(), dto.getDoctor_name(), dto.getStatus()));
    }

    @Override
    public List<AppoinmentDto> getAll() throws SQLException, ClassNotFoundException {
        List<Appoinment> pendingAppoinemts = appoinmentDAO.getAll();
        List<AppoinmentDto> list = new ArrayList<>();
        for (Appoinment appoinment : pendingAppoinemts) {
            list.add(new AppoinmentDto(appoinment.getAppoinment_id(), appoinment.getDate(), appoinment.getPatinet_id(), appoinment.getPatinetName(), appoinment.getAge(), appoinment.getId(), appoinment.getDoctor_name(), appoinment.getStatus()));

        }
        return list;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.delete(id);

    }

    @Override
    public AppoinmentDto getDetailByContact(String contact) throws SQLException{
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
        Appoinment appoinment = appoinmentDAO.searchAppoinmentID(Aid);
        if (appoinment != null) {
            return new AppoinmentDto(
                    appoinment.getAppoinment_id(),
                    appoinment.getDate(),
                    appoinment.getPatinet_id(),
                    appoinment.getPatinetName(),
                    appoinment.getAge(),
                    appoinment.getId(),
                    appoinment.getDoctor_name(),
                    appoinment.getStatus());
        }
        return null;
    }

    @Override
    public boolean updateAppoinmentStatus(String appoinmentId) throws SQLException, ClassNotFoundException {
        return appoinmentDAO.updateAppoinmentStatus(appoinmentId);

    }
}
