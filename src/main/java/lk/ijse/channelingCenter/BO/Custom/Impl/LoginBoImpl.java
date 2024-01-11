package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.LoginBo;
import lk.ijse.channelingCenter.DAO.Custom.LoginDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.LoginDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginBoImpl implements LoginBo {
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.LOGIN);

    @Override
    public boolean save(LoginDto loginDto) throws SQLException {
        return CrudUtil.crudUtil("INSERT INTO login VALUES (?,?,?,?)", loginDto.getFullName(),
                loginDto.getUserName(),
                loginDto.getPassword(),
                loginDto.getEmail());

    }

    public boolean searchUser(LoginDto loginDto) throws SQLException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM login WHERE user_name=? AND password=?", loginDto.getUserName(),
                loginDto.getPassword());
        if (resultSet.next()) return true;
        return false;
    }

    @Override
    public LoginDto getUserByEmail(String email) throws SQLException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM login WHERE email = ?", email);
        if (resultSet.next()) {
            return new LoginDto(
                    resultSet.getString("full_name"),
                    resultSet.getString("user_name"),
                    resultSet.getString("password"),
                    resultSet.getString("email")
            );
        }
        return null;
    }

    @Override
    public boolean update(LoginDto loginDto) throws SQLException {

        return CrudUtil.crudUtil("UPDATE login SET password = ? WHERE email = ?", loginDto.getPassword(), loginDto.getEmail());

    }
}

