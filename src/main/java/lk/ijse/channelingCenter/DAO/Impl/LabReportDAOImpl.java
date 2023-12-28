package lk.ijse.channelingCenter.DAO.Impl;

import lk.ijse.channelingCenter.DAO.LabReportDAO;
import lk.ijse.channelingCenter.Util.CrudUtil;
import lk.ijse.channelingCenter.dto.LabReportDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabReportDAOImpl implements LabReportDAO {
    @Override
    public boolean save(final LabReportDto dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("insert into LabReport values(?,?,?,?,?,?,?,?,?,?,?,?)",dto.getLab_reportid(),
        dto.getPatient_id(),
        dto.getDate(),
        dto.getDoctor_id(),
        dto.getDoctor_name(),
        dto.getAge(),
        dto.getGender(),
        dto.getPatient_name(),
        dto.getTest_name(),
        dto.getTest_result(),
        dto.getUnits(),
        dto.getOthers());

    }

@Override
    public String generateNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM labreport ORDER BY lab_reportId DESC LIMIT 1");
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
            String[] tempArray = current.split("LR");
            int id = Integer.parseInt(tempArray[1]);
            id++;
            if (9 >= id && id > 0) return "LR00" + id;
            else if (99 >= id && id > 9) return "LR0" + id;
            else return "LR" + id;
        }
        return "LR001";
    }

    @Override
    public LabReportDto searchLabReport(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM labreport WHERE lab_reportId = ?");
        LabReportDto dto = null;

        if (resultSet.next()) {

            String lab_reportId = resultSet.getString(1);
            String patient_id = resultSet.getString(2);
            String date = resultSet.getString(3);
            String doctor_id = resultSet.getString(4);
            String doctor_name = resultSet.getString(5);
            String age = resultSet.getString(6);
            String gender = resultSet.getString(7);
            String patient_name = resultSet.getString(8);
            String test_name = resultSet.getString(9);
            String test_result = resultSet.getString(10);
            String units = resultSet.getString(11);
            String others = resultSet.getString(12);


            dto = new LabReportDto(lab_reportId,patient_id,doctor_name,date,doctor_id,age,gender,patient_name,test_name,test_result,units,others);
        }

        return dto;
    }

    @Override
    public String getEmail(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT p.email +" +
                "from patient p +" +
                "join labreport l on p.patient_id = l.patient_id +" +
                "where l.lab_reportId = ?",id);

        String email = null;

        if (resultSet.next()) {
            email = resultSet.getString(1);
        }

        return email;
    }
@Override
    public List<LabReportDto> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =  CrudUtil.crudUtil("SELECT * FROM labreport");
        List<LabReportDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            String lab_reportId = resultSet.getString(1);
            String patient_id = resultSet.getString(2);
            String date = resultSet.getString(3);
            String doctor_id = resultSet.getString(4);
            String doctor_name = resultSet.getString(5);
            String age = resultSet.getString(6);
            String gender = resultSet.getString(7);
            String patient_name = resultSet.getString(8);
            String test_name = resultSet.getString(9);
            String test_result = resultSet.getString(10);
            String units = resultSet.getString(11);
            String others = resultSet.getString(12);


            var dto = new LabReportDto(lab_reportId,patient_id,date,doctor_id,doctor_name,age,gender,patient_name,test_name,test_result,units,others);
            dtoList.add(dto);
        }
        return dtoList;

    }
@Override
    public boolean update(LabReportDto ReportDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE labreport SET patient_id = ?,date = ?,doctor_id = ?,doctor_name = ?,age = ?, gender = ? patient_id = ?,test_name = ?, test_result = ?, units = ? , others = ? WHERE  = lab_reportId = ?",ReportDto.getLab_reportid(),
        ReportDto.getPatient_id(),
        ReportDto.getDate(),
        ReportDto.getDoctor_id(),
        ReportDto.getDoctor_name(),
        ReportDto.getAge(),
        ReportDto.getGender(),
        ReportDto.getPatient_name(),
        ReportDto.getTest_name(),
        ReportDto.getTest_result(),
        ReportDto.getUnits(),
        ReportDto.getOthers());
    }
@Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM labreport WHERE lab_reportId = ?");

    }

    @Override
    public LabReportDto getDetailByContact(String contact) throws SQLException, ClassNotFoundException {
        return null;
    }
}
