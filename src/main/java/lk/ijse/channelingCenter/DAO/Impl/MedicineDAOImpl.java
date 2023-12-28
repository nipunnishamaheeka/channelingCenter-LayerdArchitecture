package lk.ijse.channelingCenter.DAO.Impl;

import lk.ijse.channelingCenter.DAO.MedicineDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.MedicineDto;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAOImpl implements MedicineDAO {

    @Override
    public String getAllCount() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM medicine");

            if (resultSet.next()) {
                return resultSet.getString(1);
            }

        return null;
    }
@Override
    public boolean save(final MedicineDto dto) throws SQLException, ClassNotFoundException {

        return CrudUtil.crudUtil("insert into medicine values(?,?,?,?,?)",dto.getMedi_code(),
        dto.getMedicine_name(),
        dto.getDescription(),
        dto.getQty(),
        dto.getUnit_price());

    }
@Override
    public boolean update(final MedicineDto dto) throws SQLException, ClassNotFoundException {
return CrudUtil.crudUtil("UPDATE medicine SET medicine_name = ?,description = ?,qty = ?, unit_price =? WHERE medi_code = ?",
        dto.getMedicine_name(),
        dto.getDescription(),
        dto.getQty(),
        dto.getUnit_price(),
    dto.getMedi_code());
    }
@Override
    public MedicineDto searchMedicine(String Medi_code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM medicine WHERE medi_code = ?");
        MedicineDto dto = null;

        if (resultSet.next()) {
            String medi_code = resultSet.getString(1);
            String medicine_name = resultSet.getString(2);
            String description = resultSet.getString(3);
            String qty = resultSet.getString(4);
            String unit_price = resultSet.getString(5);


            dto = new MedicineDto(medi_code, medicine_name, description, qty, unit_price);
        }

        return dto;
    }
@Override
    public boolean delete(String medi_code) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM medicine WHERE medi_code = ?",medi_code);
    }

    @Override
    public MedicineDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }
@Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM medicine ORDER BY medi_code DESC LIMIT 1");
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            System.out.println(current);
            return splitId(current);
        }

        return splitId(null);
    }
@Override
    public String splitId(String current) {

        if (current != null) {
            String[] tempArray = current.split("M");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 >= id && id > 0) return "M00" + id;
            else if (99 >= id && id > 9) return "M0" + id;
            else return "M" + id;
        }
        return "M001";
    }
@Override
    public List<MedicineDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM medicine");
        List<MedicineDto> dtoList = new ArrayList<>();
        while (resultSet.next()) {
            String medi_code = resultSet.getString(1);
            String medicine_name = resultSet.getString(2);
            String description = resultSet.getString(3);
            String qty = resultSet.getString(4);
            String unit_price = resultSet.getString(5);

            var dto = new MedicineDto(medi_code, medicine_name, description, qty, unit_price);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public String getMedicineDescription(String value) throws SQLException {
        DbConnection dbConnection = DbConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        String sql = "SELECT * FROM patient WHERE patient_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, value);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return resultSet.getString(3);
        } return null;

    }

    public String getMedicinePrice(String value) throws SQLException {
        DbConnection dbConnection = DbConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        String sql = "SELECT * FROM patient WHERE patient_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, value);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return resultSet.getString(5);
        } return null;

    }
@Override
    public boolean updateMedicineQty(List<CartTm> tmlist) throws SQLException, ClassNotFoundException {
        for(CartTm tm: tmlist){
            if (!updateQty(tm)){
                return false;
            }
        }
        return true;
    }
@Override
    public boolean updateQty(CartTm tm) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE medicine SET qty=qty-? WHERE  medi_code=?",tm.getQty(),tm.getM_Code());

    }

    public boolean saveToTable(String code, String description, String qty, double unitPrice, String total) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        System.out.println(code);
        System.out.println(description);
        System.out.println(qty);
        System.out.println(unitPrice);
        System.out.println(total);
        String sql = "INSERT INTO completeorders VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, code);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, qty);
        preparedStatement.setString(4, String.valueOf(unitPrice));
        double totals = Double.parseDouble(qty)*Double.parseDouble(String.valueOf(unitPrice));
        preparedStatement.setDouble(5, totals);
        return preparedStatement.executeUpdate()>0;
    }
@Override
    public int getQty(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT qty FROM medicine WHERE medi_code = ?",code);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }return  0;
    }
}

