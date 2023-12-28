package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.DAO.Impl.LoginDAOImpl;
import lk.ijse.channelingCenter.dto.LoginDto;

import java.sql.SQLException;

public interface LoginDAO extends CrudDAO<LoginDto> {
    //searchUser(LoginDto loginDto) throws SQLException, ClassNotFoundException;
    LoginDto getUserByEmail(String email) throws SQLException, ClassNotFoundException
}
