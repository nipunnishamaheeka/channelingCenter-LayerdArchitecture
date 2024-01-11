package lk.ijse.channelingCenter.DAO;

import lk.ijse.channelingCenter.DAO.Custom.Impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }
    public static DAOFactory getInstance(){
        return (daoFactory == null)? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOTypes{
        APPOINMENT,DOCTOR,EMPLOYEE,LABREPORT,LOGIN,MEDICALREPORT,MEDICINE,PATIENT,PAYMENT,COMPLETEORDER
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case APPOINMENT:return new AppoinmentDAOImpl();
            case DOCTOR:return new DoctorDAOImpl();
            case EMPLOYEE:return new EmployeeDAOImpl();
            case LABREPORT:return new LabReportDAOImpl();
            case LOGIN:return new LoginDAOImpl();
            case MEDICALREPORT:return new MedicineDAOImpl();
            case MEDICINE:return new MedicineDAOImpl();
            case PATIENT:return new PatientDAOImpl();
            case PAYMENT: return new PaymentDAOImpl();
            case COMPLETEORDER:return new CompleteOrderDAOImpl();
            default:return null;
        }


    }
}
