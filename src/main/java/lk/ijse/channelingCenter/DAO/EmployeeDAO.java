package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.DAO.Impl.EmployeeDAOImpl;
import lk.ijse.channelingCenter.dto.EmployeeDto;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<EmployeeDto>{

    EmployeeDto searchEmployee(String Emp_id) throws SQLException, ClassNotFoundException;
}
