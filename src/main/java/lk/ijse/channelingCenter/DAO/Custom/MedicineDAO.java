package lk.ijse.channelingCenter.DAO.Custom;
import lk.ijse.channelingCenter.dto.tm.CartTm;
import lk.ijse.channelingCenter.entity.Medicine;

import java.sql.SQLException;
import java.util.List;

public interface MedicineDAO extends CrudDAO<Medicine>{
    String getAllCount() throws SQLException, ClassNotFoundException;
    Medicine searchMedicine(String Medi_code) throws SQLException, ClassNotFoundException;
    boolean updateMedicineQty(List<CartTm> tmlist) throws SQLException, ClassNotFoundException;
    boolean updateQty(CartTm tm) throws SQLException, ClassNotFoundException;
    int getQty(String code) throws SQLException, ClassNotFoundException;
}
