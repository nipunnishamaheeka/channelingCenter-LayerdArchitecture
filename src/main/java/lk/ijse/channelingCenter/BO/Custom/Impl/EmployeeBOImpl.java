package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.EmployeeBO;
import lk.ijse.channelingCenter.DAO.Custom.EmployeeDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.EmployeeDto;
import lk.ijse.channelingCenter.entity.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean save(final EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(dto.getEmp_id(), dto.getEmp_name(), dto.getEmp_address(), dto.getMobile_number(), dto.getJob_role(), dto.getQualification(), dto.getSalary()));

    }

    @Override
    public boolean update(final EmployeeDto dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getEmp_id(), dto.getEmp_name(), dto.getEmp_address(), dto.getMobile_number(), dto.getJob_role(), dto.getQualification(), dto.getSalary()));
    }

    @Override
    public EmployeeDto searchEmployee(String Emp_id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.searchEmployee(Emp_id);
        if (employee != null) {
            return new EmployeeDto(
                    employee.getEmp_id(),
                    employee.getEmp_name(),
                    employee.getEmp_address(),
                    employee.getMobile_number(),
                    employee.getJob_role(),
                    employee.getQualification(),
                    employee.getSalary());
        }
        return null;
    }

    @Override
    public boolean delete(String emp_id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(emp_id);

    }

    @Override
    public EmployeeDto getDetailByContact(String contact) throws SQLException{
        return null;
    }

    @Override
    public List<EmployeeDto> getAll() throws SQLException, ClassNotFoundException {
        List<Employee> employeeList = employeeDAO.getAll();
        ArrayList<EmployeeDto> list = new ArrayList<>();
        for (Employee employee : employeeList) {
            list.add(new EmployeeDto(employee.getEmp_id(), employee.getEmp_name(), employee.getEmp_address(), employee.getMobile_number(), employee.getJob_role(), employee.getQualification(), employee.getSalary()));
        }
        return list;
    }

    @Override
    public String generateNextId() throws SQLException{
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
    public String splitId(String current) throws SQLException{
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
