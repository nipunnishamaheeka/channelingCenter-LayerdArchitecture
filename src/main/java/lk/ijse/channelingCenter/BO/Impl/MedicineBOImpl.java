package lk.ijse.channelingCenter.BO.Impl;

import lk.ijse.channelingCenter.BO.MedicineBO;
import lk.ijse.channelingCenter.DAO.Impl.MedicineDAOImpl;
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

public class MedicineBOImpl implements MedicineBO {
    MedicineDAO medicineDAO = new MedicineDAOImpl();

    @Override
    public String getAllCount() throws SQLException, ClassNotFoundException {
        return medicineDAO.getAllCount();
    }

    @Override
    public boolean save(final MedicineDto dto) throws SQLException, ClassNotFoundException {
        return medicineDAO.save(dto);

    }

    @Override
    public boolean update(final MedicineDto dto) throws SQLException, ClassNotFoundException {
        return medicineDAO.update(dto);
    }

    @Override
    public MedicineDto searchMedicine(String Medi_code) throws SQLException, ClassNotFoundException {
        return medicineDAO.searchMedicine(Medi_code);
    }

    @Override
    public boolean delete(String medi_code) throws SQLException, ClassNotFoundException {
        return medicineDAO.delete(medi_code);
    }

    @Override
    public MedicineDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        return medicineDAO.generateNextId();
    }

    @Override
    public String splitId(String current) {

        return medicineDAO.splitId(current);
    }

    @Override
    public List<MedicineDto> getAll() throws SQLException, ClassNotFoundException {
        return medicineDAO.getAll();
    }

    @Override
    public boolean updateMedicineQty(List<CartTm> tmlist) throws SQLException, ClassNotFoundException {
        return medicineDAO.updateMedicineQty(tmlist);
    }

    @Override
    public boolean updateQty(CartTm tm) throws SQLException, ClassNotFoundException {
        return medicineDAO.updateQty(tm);

    }

    @Override
    public int getQty(String code) throws SQLException, ClassNotFoundException {
        return medicineDAO.getQty(code);
    }
}
