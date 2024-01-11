package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.PatientBO;
import lk.ijse.channelingCenter.DAO.Custom.PatientDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.dto.PatientDto;
import lk.ijse.channelingCenter.entity.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);

    @Override
    public String getCount() throws SQLException, ClassNotFoundException {
        return patientDAO.getCount();
    }

    @Override
    public boolean save(final PatientDto dto) throws SQLException, ClassNotFoundException {
        return patientDAO.save(new Patient(dto.getPatient_id(),dto.getPatient_name(),dto.getMobile_number(),dto.getAddress(),dto.getSex(),dto.getEmail(),dto.getAge(),dto.getBlood()));

    }

    @Override
    public boolean update(final PatientDto dto) throws SQLException, ClassNotFoundException {
        return patientDAO.update(new Patient(dto.getPatient_id(),dto.getPatient_name(),dto.getMobile_number(),dto.getAddress(),dto.getSex(),dto.getEmail(),dto.getAge(),dto.getBlood()));
    }

    @Override
    public PatientDto search(String id) throws SQLException, ClassNotFoundException {
        Patient patient = patientDAO.search(id);
        if (patient != null) {
            return new PatientDto(
                    patient.getPatient_id(),
                    patient.getPatient_name(),
                    patient.getMobile_number(),
                    patient.getAddress(),
                    patient.getSex(),
                    patient.getEmail(),
                    patient.getAge(),
                    patient.getBlood());
        }
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return patientDAO.delete(id);
    }

    @Override
    public List<PatientDto> getAll() throws SQLException, ClassNotFoundException {
        List<Patient> patientList = patientDAO.getAll();
        ArrayList<PatientDto> list = new ArrayList<>();
        for(Patient patient : patientList){
            list.add(new PatientDto(patient.getPatient_id(),patient.getPatient_name(),patient.getMobile_number(),patient.getAddress(),patient.getSex(),patient.getEmail(),patient.getAge(),patient.getBlood()));
        }
        return list;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return patientDAO.generateNextId();
    }

    @Override
    public String splitId(String current) throws SQLException{
        return patientDAO.splitId(current);
    }

    @Override
    public String getPatientName(String value) throws SQLException, ClassNotFoundException {
        return patientDAO.getPatientName(value);

    }

    @Override
    public String getPatientAge(String value) throws SQLException, ClassNotFoundException {
        return patientDAO.getPatientAge(value);

    }

    public String getBloodGroup(String value) throws SQLException {
        return null;

    }

    @Override
    public String getPatientGender(String value) throws SQLException, ClassNotFoundException {

        return patientDAO.getPatientGender(value);

    }
//check krnna
    @Override
    public PatientDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        Patient patient = patientDAO.getDetailByContact(contact);
        return new PatientDto(patient.getPatient_id(),patient.getPatient_name(),patient.getMobile_number(),patient.getAddress(),patient.getSex(),patient.getEmail(),patient.getAge(),patient.getBlood());

    }
}
