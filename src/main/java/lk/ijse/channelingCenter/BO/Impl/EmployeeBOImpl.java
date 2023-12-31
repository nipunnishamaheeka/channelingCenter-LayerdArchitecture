package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.EmployeeBO;
import lk.ijse.channelingCenter.DAO.EmployeeDAO;
import lk.ijse.channelingCenter.DAO.Impl.EmployeeDAOImpl;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO{
    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    @Override
    public boolean save(final EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(dto);

    }

    @Override
    public boolean update(final EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(dto);
    }

    @Override
    public EmployeeDto searchEmployee(String Emp_id) throws SQLException, ClassNotFoundException {
       return employeeDAO.searchEmployee(Emp_id);
    }

    @Override
    public boolean delete(String emp_id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(emp_id);

    }
    @Override
    public EmployeeDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<EmployeeDto> getAll() throws SQLException, ClassNotFoundException {
     return employeeDAO.getAll();
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT * FROM employee ORDER BY emp_id DESC LIMIT 1").executeQuery();
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            System.out.println(current);
            return splitId(current);
        }
        return splitId(current);

    }

    @Override
    public String splitId(String current) throws SQLException, ClassNotFoundException {
        if (current != null) {
            String[] tempArray = current.split("E");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 >= id && id > 0) return "E00" + id;
            else if ((99 >= id && id > 9)) return "E0" + id;
            else return "E" + id;
        }
        return "E001";
    }
}
