package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.LabReportBO;
import lk.ijse.channelingCenter.DAO.Impl.LabReportDAOImpl;
import lk.ijse.channelingCenter.DAO.LabReportDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.LabReportDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabReportBOImpl implements LabReportBO {
LabReportDAO labReportDAO = new LabReportDAOImpl();
    @Override
    public boolean save(final LabReportDto dto) throws SQLException, ClassNotFoundException{
        return labReportDAO.save(dto);

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
return labReportDAO.searchLabReport(id);
    }

    @Override
    public String getEmail(String id) throws SQLException, ClassNotFoundException {
return labReportDAO.getEmail(id);
    }

    @Override
    public List<LabReportDto> getAll() throws SQLException, ClassNotFoundException {
return labReportDAO.getAll();

    }

    @Override
    public boolean update(LabReportDto ReportDto) throws SQLException, ClassNotFoundException {
    return labReportDAO.update(ReportDto);
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return labReportDAO.delete(code);

    }

    @Override
    public LabReportDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
    return labReportDAO.getDetailByContact(contact);
    }
}
