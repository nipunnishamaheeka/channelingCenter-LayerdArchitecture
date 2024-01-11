package lk.ijse.channelingCenter.BO.Custom;

import lk.ijse.channelingCenter.BO.SuperBO;
import lk.ijse.channelingCenter.dto.LoginDto;

import java.sql.SQLException;

public interface LoginBo extends SuperBO {
    boolean save(LoginDto loginDto) throws SQLException, ClassNotFoundException;
    boolean searchUser(LoginDto loginDto) throws SQLException, ClassNotFoundException;
    LoginDto getUserByEmail(String email) throws SQLException, ClassNotFoundException;
    boolean update(LoginDto loginDto) throws SQLException, ClassNotFoundException;
}
