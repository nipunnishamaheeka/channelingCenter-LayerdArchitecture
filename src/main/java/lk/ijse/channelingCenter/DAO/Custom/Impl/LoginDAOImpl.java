package lk.ijse.channelingCenter.DAO.Custom.Impl;

import lk.ijse.channelingCenter.DAO.Custom.LoginDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.LoginDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public List<LoginDto> getAll() throws SQLException{
        return null;
    }
@Override
    public boolean save(LoginDto loginDto) throws SQLException{
        return CrudUtil.crudUtil("INSERT INTO login VALUES (?,?,?,?)",loginDto.getFullName(),
        loginDto.getUserName(),
        loginDto.getPassword(),
        loginDto.getEmail());

    }

    public boolean searchUser(LoginDto loginDto) throws SQLException{
        return CrudUtil.crudUtil("SELECT * FROM login WHERE user_name=? AND password=?",loginDto.getUserName(),
        loginDto.getPassword());

    }
    @Override
    public LoginDto getUserByEmail(String email) throws SQLException{
        ResultSet resultSet =  CrudUtil.crudUtil("SELECT * FROM login WHERE email = ?",email);
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
    public boolean update(LoginDto loginDto) throws SQLException{

        return CrudUtil.crudUtil("UPDATE login SET password = ? WHERE email = ?",loginDto.getPassword(),loginDto.getEmail());

        }

    @Override
    public boolean delete(String id) throws SQLException{
        return false;
    }

    @Override
    public LoginDto getDetailByContact(String contact) throws SQLException{
        return null;
    }

    @Override
    public String generateNextId() throws SQLException{
        return null;
    }

    @Override
    public String splitId(String current) {
        return null;
    }
}
