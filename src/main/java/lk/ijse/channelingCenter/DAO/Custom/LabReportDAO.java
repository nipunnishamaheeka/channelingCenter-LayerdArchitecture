package lk.ijse.channelingCenter.DAO.Custom;


import lk.ijse.channelingCenter.entity.LabReport;

import java.sql.SQLException;

public interface LabReportDAO extends CrudDAO<LabReport>{
    String getEmail(String id) throws SQLException, ClassNotFoundException;
    LabReport searchLabReport(String id) throws SQLException, ClassNotFoundException;
}
