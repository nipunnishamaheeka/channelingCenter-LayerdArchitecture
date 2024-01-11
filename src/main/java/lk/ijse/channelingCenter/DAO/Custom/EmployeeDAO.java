package lk.ijse.channelingCenter.DAO.Custom;

import lk.ijse.channelingCenter.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee>{

    Employee searchEmployee(String Emp_id) throws SQLException, ClassNotFoundException;
}
