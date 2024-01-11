package lk.ijse.channelingCenter.BO.Custom.Impl;

import lk.ijse.channelingCenter.BO.Custom.MedicineBO;
import lk.ijse.channelingCenter.DAO.Custom.MedicineDAO;
import lk.ijse.channelingCenter.DAO.DAOFactory;
import lk.ijse.channelingCenter.dto.MedicineDto;
import lk.ijse.channelingCenter.dto.tm.CartTm;
import lk.ijse.channelingCenter.entity.Medicine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineBOImpl implements MedicineBO {
    MedicineDAO medicineDAO = (MedicineDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.MEDICINE);

    @Override
    public String getAllCount() throws SQLException, ClassNotFoundException {
        return medicineDAO.getAllCount();
    }

    @Override
    public boolean save(final MedicineDto dto) throws SQLException, ClassNotFoundException {
        return medicineDAO.save(new Medicine(dto.getMedi_code(),dto.getMedicine_name(),dto.getDescription(),dto.getQty(),dto.getUnit_price()));

    }

    @Override
    public boolean update(final MedicineDto dto) throws SQLException, ClassNotFoundException {
        return medicineDAO.update(new Medicine(dto.getMedi_code(),dto.getMedicine_name(),dto.getDescription(),dto.getQty(),dto.getUnit_price()));
    }

    @Override
    public MedicineDto searchMedicine(String Medi_code) throws SQLException, ClassNotFoundException {
        Medicine medicine = medicineDAO.searchMedicine(Medi_code);
return new MedicineDto(medicine.getMedi_code(),medicine.getMedicine_name(),medicine.getDescription(),medicine.getQty(),medicine.getUnit_price());
    }

    @Override
    public boolean delete(String medi_code) throws SQLException, ClassNotFoundException {
        return medicineDAO.delete(medi_code);
    }

    @Override
    public MedicineDto getDetailByContact(String contact) throws SQLException{
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
        List<Medicine> medicineList = medicineDAO.getAll();
        ArrayList<MedicineDto> list = new ArrayList<>();
        for(Medicine medicine : medicineList){
            list.add(new MedicineDto(medicine.getMedi_code(),medicine.getMedicine_name(),medicine.getDescription(),medicine.getQty(),medicine.getUnit_price()));
        }
        return list;
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
