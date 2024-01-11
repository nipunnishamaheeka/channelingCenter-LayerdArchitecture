package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.DoctorBO;
import lk.ijse.channelingCenter.DAO.Custom.DoctorDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.dto.DoctorDto;
import lk.ijse.channelingCenter.entity.Doctor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorBOImpl implements DoctorBO {
    DoctorDAO doctorDAO = (DoctorDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.DOCTOR);

    @Override

    public String getCount() throws SQLException, ClassNotFoundException {
        return doctorDAO.getCount();
    }

    @Override
    public boolean save(DoctorDto dto) throws SQLException, ClassNotFoundException {
        return doctorDAO.save(new Doctor(dto.getId(), dto.getName(), dto.getAddress(), dto.getEmail(), dto.getNumber(), dto.getType(), dto.getDrFee()));
    }

    @Override
    public boolean update(final DoctorDto dto) throws SQLException, ClassNotFoundException {
        return doctorDAO.update(new Doctor(dto.getId(), dto.getName(), dto.getAddress(), dto.getEmail(), dto.getNumber(), dto.getType(), dto.getDrFee()));
    }

    @Override
    public boolean delete(String doc_id) throws SQLException, ClassNotFoundException {
        return doctorDAO.delete(doc_id);
    }

    @Override
    public List<DoctorDto> getAll() throws SQLException, ClassNotFoundException {
        List<Doctor> doctorList = doctorDAO.getAll();
        ArrayList<DoctorDto> list = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            list.add(new DoctorDto(doctor.getId(), doctor.getName(), doctor.getAddress(), doctor.getEmail(), doctor.getNumber(), doctor.getType(), doctor.getDrFee()));
        }
        return list;
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
        Doctor doctor = doctorDAO.getDetailByContact(number);
        return new DoctorDto(doctor.getId(), doctor.getName(), doctor.getAddress(), doctor.getEmail(), doctor.getNumber(), doctor.getType(), doctor.getDrFee()
        );

    }

    @Override
    public double getfee(String id) throws SQLException, ClassNotFoundException {
        return doctorDAO.getfee(id);
    }

    public DoctorDto searchDoctor(String Doc_id) throws SQLException{
        return null;
    }
}
