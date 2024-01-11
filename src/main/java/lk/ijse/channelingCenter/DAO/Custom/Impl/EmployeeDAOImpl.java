package lk.ijse.channelingCenter.DAO.Custom.Impl;

import lk.ijse.channelingCenter.DAO.Custom.EmployeeDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.entity.Employee;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(final Employee dto) throws SQLException{
        return CrudUtil.crudUtil("insert into employee values(?,?,?,?,?,?,?)",
                dto.getEmp_id(),
                dto.getEmp_name(),
                dto.getEmp_address(),
                dto.getMobile_number(),
                dto.getJob_role(),
                dto.getQualification(),
                dto.getSalary());

    }

    @Override
    public boolean update(final Employee dto) throws SQLException{
        return CrudUtil.crudUtil("UPDATE employee SET emp_name = ?, emp_address = ?, mobile_number = ?, job_role = ?, qualification = ?, salary = ? WHERE emp_id = ?", dto.getEmp_name(),
                dto.getEmp_address(),
                dto.getMobile_number(),
                dto.getJob_role(),
                dto.getQualification(),
                dto.getSalary(),
                dto.getEmp_id());
    }

    @Override
    public Employee searchEmployee(String Emp_id) throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM employee WHERE emp_id = ?", Emp_id);
        Employee dto = null;

        if (resultSet.next()) {

            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String emp_address = resultSet.getString(3);
            String number = resultSet.getString(4);
            String job_role = resultSet.getString(5);
            String qualification = resultSet.getString(6);
            String salary = resultSet.getString(7);


            dto = new Employee(emp_id, emp_name, emp_address, number, job_role, qualification, salary);
        }

        return dto;
    }

    @Override
    public boolean delete(String emp_id) throws SQLException{
        return CrudUtil.crudUtil("DELETE FROM employee WHERE emp_id = ?", emp_id);

    }



    @Override
    public Employee getDetailByContact(String contact) throws SQLException{
        return null;
    }

    @Override
    public List<Employee> getAll() throws SQLException{
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM employee");
        List<Employee> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String emp_address = resultSet.getString(3);
            String number = resultSet.getString(4);
            String job_role = resultSet.getString(5);
            String qualification = resultSet.getString(6);
            String salary = resultSet.getString(7);


            var dto = new Employee(emp_id, emp_name, emp_address, number, job_role, qualification, salary);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public String generateNextId() throws SQLException {
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
    public String splitId(String current) {
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

