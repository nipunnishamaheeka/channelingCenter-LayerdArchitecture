package lk.ijse.channelingCenter.BO.Custom;

import lk.ijse.channelingCenter.BO.SuperBO;
import lk.ijse.channelingCenter.dto.LabReportDto;

import java.sql.SQLException;
import java.util.List;

public interface LabReportBO extends SuperBO {
    boolean save(final LabReportDto dto) throws SQLException, ClassNotFoundException;
    String generateNextId() throws SQLException, ClassNotFoundException;
    String splitId(String current)throws SQLException, ClassNotFoundException;
    LabReportDto searchLabReport(String id) throws SQLException, ClassNotFoundException;
    String getEmail(String id) throws SQLException, ClassNotFoundException;
    List<LabReportDto> getAll() throws SQLException, ClassNotFoundException;
    boolean update(LabReportDto ReportDto) throws SQLException, ClassNotFoundException;
    boolean delete(String code) throws SQLException, ClassNotFoundException;
   LabReportDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException;
}
