package lk.ijse.channelingCenter.DAO.Impl;

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

public class MedicineDAOImpl {
    public static String getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM medicine");
//        Connection connection = DbConnection.getInstance().getConnection();
//        String sql = "SELECT COUNT(*) FROM medicine";
//        try (PreparedStatement pstm = connection.prepareStatement(sql);
//             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        return null;
    }

    public boolean saveMedicine(final MedicineDto dto) throws SQLException, ClassNotFoundException {

        return CrudUtil.crudUtil("insert into medicine values(?,?,?,?,?)",dto.getMedi_code(),
        dto.getMedicine_name(),
        dto.getDescription(),
        dto.getQty(),
        dto.getUnit_price());
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "insert into medicine values(?,?,?,?,?)";
//        PreparedStatement pstm = connection.prepareStatement(sql);

//        pstm.setString(1, dto.getMedi_code());
//        pstm.setString(2, dto.getMedicine_name());
//        pstm.setString(3, dto.getDescription());
//        pstm.setString(4, dto.getQty());
//        pstm.setString(5, dto.getUnit_price());
//
//        boolean isSaved = pstm.executeUpdate() > 0;
//
//        return isSaved;

    }

    public boolean updateMedicine(final MedicineDto dto) throws SQLException, ClassNotFoundException {
return CrudUtil.crudUtil("UPDATE medicine SET medicine_name = ?,description = ?,qty = ?, unit_price =? WHERE medi_code = ?",dto.getMedi_code(),
        dto.getMedicine_name(),
        dto.getDescription(),
        dto.getQty(),
        dto.getUnit_price());

//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "UPDATE medicine SET medicine_name = ?,description = ?,qty = ?, unit_price =? WHERE medi_code = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);

//        pstm.setString(1, dto.getMedi_code());
//        pstm.setString(2, dto.getMedicine_name());
//        pstm.setString(3, dto.getDescription());
//        pstm.setString(4, dto.getQty());
//        pstm.setString(5, dto.getUnit_price());
//
//
//        return pstm.executeUpdate() > 0;
    }

    public MedicineDto searchMedicine(String Medi_code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM medicine WHERE medi_code = ?");
//        Connection connection = DbConnection.getInstance().getConnection();
//        String sql = "SELECT * FROM medicine WHERE medi_code = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setString(1, Medi_code);

//        ResultSet resultSet = pstm.executeQuery();

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

    public boolean deleteMedicine(String medi_code) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM medicine WHERE medi_code = ?",medi_code);
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "DELETE FROM medicine WHERE medi_code = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setString(1, medi_code);
//
//        return pstm.executeUpdate() > 0;
    }

    public String autoGenarateMedicineId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM medicine ORDER BY medi_code DESC LIMIT 1");
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        ResultSet resultSet = connection.prepareStatement("SELECT * FROM medicine ORDER BY medi_code DESC LIMIT 1").executeQuery();
        String current = null;
        while (resultSet.next()) {
            current = resultSet.getString(1);
            System.out.println(current);
            return splitId(current);
        }

        return splitId(null);
    }

    private String splitId(String current) {

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

    public List<MedicineDto> getAllMedicine() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM medicine");
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "SELECT * FROM medicine";
//        PreparedStatement pstm = connection.prepareStatement(sql);

        List<MedicineDto> dtoList = new ArrayList<>();
        //ResultSet resultSet = pstm.executeQuery();
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

    public boolean updateMedicineQty(List<CartTm> tmlist) throws SQLException, ClassNotFoundException {
        for(CartTm tm: tmlist){
            if (!updateQty(tm)){
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(CartTm tm) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE medicine SET qty=qty-? WHERE  medi_code=?",tm.getQty(),tm.getM_Code());
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "UPDATE medicine SET qty=qty-? WHERE  medi_code=?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setInt(1, (int) tm.getQty());
//        pstm.setString(2, tm.getM_Code());
//
//        return pstm.executeUpdate()>0;
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

    public int getQty(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT qty FROM medicine WHERE medi_code = ?",code);
//        Connection connection = DbConnection.getInstance().getConnection();
//
//        String sql = "SELECT qty FROM medicine WHERE medi_code = ?";
//        PreparedStatement pstm = connection.prepareStatement(sql);
//        pstm.setString(1, code);
//        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(1);
        }return  0;
    }
}

