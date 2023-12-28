package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.dto.MedicineDto;
import lk.ijse.channelingCenter.dto.tm.CartTm;

import java.sql.SQLException;
import java.util.List;

public interface MedicineDAO extends CrudDAO<MedicineDto>{
    String getAllCount() throws SQLException, ClassNotFoundException;
    MedicineDto searchMedicine(String Medi_code) throws SQLException, ClassNotFoundException;
    boolean updateMedicineQty(List<CartTm> tmlist) throws SQLException, ClassNotFoundException;
    boolean updateQty(CartTm tm) throws SQLException, ClassNotFoundException;
    int getQty(String code) throws SQLException, ClassNotFoundException;
}
