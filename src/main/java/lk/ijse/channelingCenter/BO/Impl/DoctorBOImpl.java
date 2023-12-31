package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.DoctorBO;
import lk.ijse.channelingCenter.DAO.DoctorDAO;
import lk.ijse.channelingCenter.DAO.Impl.DoctorDAOImpl;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.DoctorDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorBOImpl implements DoctorBO {
    DoctorDAO doctorDAO = new DoctorDAOImpl();

    @Override

    public String getCount() throws SQLException, ClassNotFoundException {
        return doctorDAO.getCount();
    }

    @Override
    public boolean save(DoctorDto dto) throws SQLException, ClassNotFoundException {
        return doctorDAO.save(dto);
    }

    @Override
    public boolean update(final DoctorDto dto) throws SQLException, ClassNotFoundException {
        return doctorDAO.update(dto);
    }

    @Override
    public boolean delete(String doc_id) throws SQLException, ClassNotFoundException {
        return doctorDAO.delete(doc_id);
    }

    @Override
    public List<DoctorDto> getAll() throws SQLException, ClassNotFoundException {
        return doctorDAO.getAll();
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return doctorDAO.generateNextId();
    }

    @Override
    public String splitId(String current) {
        return doctorDAO.splitId(current);
    }

    @Override
    public String getname(String id) throws SQLException, ClassNotFoundException {
        return doctorDAO.getname(id);
    }

    @Override
    public DoctorDto getDetailByContact(String number) throws SQLException, ClassNotFoundException {
        return doctorDAO.getDetailByContact(number);
    }

    @Override
    public double getfee(String id) throws SQLException, ClassNotFoundException {
        return doctorDAO.getfee(id);
    }

    public DoctorDto searchDoctor(String Doc_id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
