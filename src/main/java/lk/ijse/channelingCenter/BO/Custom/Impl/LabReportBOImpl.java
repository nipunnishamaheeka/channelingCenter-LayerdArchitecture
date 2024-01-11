package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.LabReportBO;
import lk.ijse.channelingCenter.DAO.Custom.LabReportDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.dto.LabReportDto;
import lk.ijse.channelingCenter.entity.LabReport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabReportBOImpl implements LabReportBO {
    LabReportDAO labReportDAO = (LabReportDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.LABREPORT);

    @Override
    public boolean save(final LabReportDto dto) throws SQLException, ClassNotFoundException {
        return labReportDAO.save(new LabReport(dto.getLab_reportid(), dto.getPatient_id(), dto.getDate(), dto.getDoctor_id(), dto.getDoctor_name(), dto.getAge(), dto.getGender(), dto.getPatient_name(), dto.getTest_name(), dto.getTest_result(), dto.getUnits(), dto.getOthers()));

    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return labReportDAO.generateNextId();
    }

    @Override
    public String splitId(String current) {
        return labReportDAO.splitId(current);
    }

    @Override
    public LabReportDto searchLabReport(String id) throws SQLException, ClassNotFoundException {
        LabReport labReport = labReportDAO.searchLabReport(id);
        if (labReport != null) {
            return new LabReportDto(
                    labReport.getLab_reportid(),
                    labReport.getPatient_id(),
                    labReport.getDate(),
                    labReport.getDoctor_id(),
                    labReport.getDoctor_name(),
                    labReport.getAge(),
                    labReport.getGender(),
                    labReport.getPatient_name(),
                    labReport.getTest_name(),
                    labReport.getTest_result(),
                    labReport.getUnits(),
                    labReport.getOthers());
        }
        return null;
    }

    @Override
    public String getEmail(String id) throws SQLException, ClassNotFoundException {
        return labReportDAO.getEmail(id);
    }

    @Override
    public List<LabReportDto> getAll() throws SQLException, ClassNotFoundException {
        List<LabReport> pendingLabReports = labReportDAO.getAll();
        List<LabReportDto> list = new ArrayList<>();
        for (LabReport labReport : pendingLabReports) {
            list.add(new LabReportDto(labReport.getLab_reportid(), labReport.getPatient_id(), labReport.getDate(), labReport.getDoctor_id(), labReport.getDoctor_name(), labReport.getAge(), labReport.getGender(), labReport.getPatient_name(), labReport.getTest_name(), labReport.getTest_result(), labReport.getUnits(), labReport.getOthers()));

        }
        return list;

    }

    @Override
    public boolean update(LabReportDto dto) throws SQLException, ClassNotFoundException {
        return labReportDAO.update(new LabReport(dto.getLab_reportid(), dto.getPatient_id(), dto.getDate(), dto.getDoctor_id(), dto.getDoctor_name(), dto.getAge(), dto.getGender(), dto.getPatient_name(), dto.getTest_name(), dto.getTest_result(), dto.getUnits(), dto.getOthers()));
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return labReportDAO.delete(code);

    }

    @Override
    public LabReportDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {

        LabReport labReport = labReportDAO.getDetailByContact(contact);
        return new LabReportDto(labReport.getLab_reportid(), labReport.getPatient_id(), labReport.getDate(), labReport.getDoctor_id(), labReport.getDoctor_name(), labReport.getAge(), labReport.getGender(), labReport.getPatient_name(), labReport.getTest_name(), labReport.getTest_result(), labReport.getUnits(), labReport.getOthers());

    }
}
