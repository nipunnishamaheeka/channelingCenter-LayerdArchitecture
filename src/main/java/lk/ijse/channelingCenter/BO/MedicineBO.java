package lk.ijse.channelingCenter.BO;

import lk.ijse.channelingCenter.dto.MedicineDto;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public interface MedicineBO {
    String getAllCount() throws SQLException, ClassNotFoundException;
    boolean save(final MedicineDto dto) throws SQLException, ClassNotFoundException;
    boolean update(final MedicineDto dto) throws SQLException, ClassNotFoundException;
    MedicineDto searchMedicine(String Medi_code) throws SQLException, ClassNotFoundException;
    boolean delete(String medi_code) throws SQLException, ClassNotFoundException;
    MedicineDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException;
    String generateNextId() throws SQLException, ClassNotFoundException;
    String splitId(String current) throws SQLException, ClassNotFoundException;
    List<MedicineDto> getAll() throws SQLException, ClassNotFoundException;
    boolean updateMedicineQty(List<CartTm> tmlist) throws SQLException, ClassNotFoundException;
    boolean updateQty(CartTm tm) throws SQLException, ClassNotFoundException;
    int getQty(String code) throws SQLException, ClassNotFoundException;
}
