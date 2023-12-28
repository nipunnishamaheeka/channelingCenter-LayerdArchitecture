package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.dto.LabReportDto;

import java.sql.SQLException;

public interface LabReportDAO extends CrudDAO<LabReportDto>{
    String getEmail(String id) throws SQLException, ClassNotFoundException;
    LabReportDto searchLabReport(String id) throws SQLException, ClassNotFoundException;
}
