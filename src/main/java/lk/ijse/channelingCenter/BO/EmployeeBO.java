package lk.ijse.channelingCenter.BO;

import lk.ijse.channelingCenter.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO {
    boolean save(final EmployeeDto dto) throws SQLException, ClassNotFoundException;
    boolean update(final EmployeeDto dto) throws SQLException, ClassNotFoundException;
    EmployeeDto searchEmployee(String Emp_id) throws SQLException, ClassNotFoundException;
    boolean delete(String emp_id) throws SQLException, ClassNotFoundException;
    EmployeeDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException;
    List<EmployeeDto> getAll() throws SQLException, ClassNotFoundException;
    String generateNextId() throws SQLException, ClassNotFoundException;
    String splitId(String current) throws SQLException, ClassNotFoundException;
}
