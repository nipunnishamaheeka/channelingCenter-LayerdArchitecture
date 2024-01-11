package lk.ijse.channelingCenter.DAO.Custom.Impl;

import lk.ijse.channelingCenter.db.DbConnection;
import lk.ijse.channelingCenter.dto.LabDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LabModel {
    public boolean saveLab(final LabDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "insert into lab values(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getLab_id());
        pstm.setString(2, dto.getEmp_id());
        pstm.setString(3, dto.getL_report());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;

    }

    public boolean updateLab(final LabDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET emp_id = ?,l_report = ? WHERE  = lab_id?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getLab_id());
        pstm.setString(2, dto.getEmp_id());
        pstm.setString(3, dto.getL_report());

        return pstm.executeUpdate() > 0;
    }

    public LabDto searchLab(String Lab_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM lab WHERE lab_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, Lab_id);

        ResultSet resultSet = pstm.executeQuery();

        LabDto dto = null;

        if (resultSet.next()) {
            String lab_id = resultSet.getString(1);
            String emp_id = resultSet.getString(2);
            String l_report = resultSet.getString(3);


            dto = new LabDto(lab_id, emp_id, l_report);
        }

        return dto;
    }

    public boolean deleteLab(String lab_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM lab WHERE lab_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, lab_id);

        return pstm.executeUpdate() > 0;
    }

}
