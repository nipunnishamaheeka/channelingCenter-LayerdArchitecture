package lk.ijse.channelingCenter.BO;

import lk.ijse.channelingCenter.BO.Custom.Impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }
    public static BOFactory getInstance(){
        return (boFactory == null)? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes{
        APPOINMENT,DOCTOR,EMPLOYEE,LABREPORT,MEDICALREPORT,MEDICINE,PATIENT,PAYMENT,COMPLETEORDERS,LOGIN
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case APPOINMENT:return new AppoinmentBOImpl();
            case COMPLETEORDERS:return new CompleteOrderBOImpl();
            case DOCTOR:return new DoctorBOImpl();
            case EMPLOYEE:return new EmployeeBOImpl();
            case LABREPORT:return new LabReportBOImpl();
            case LOGIN:return new LoginBoImpl();
            case MEDICALREPORT:return new MedicineBOImpl();
            case MEDICINE:return new MedicineBOImpl();
            case PATIENT:return new PatientBOImpl();
            case PAYMENT: return new PaymentBOImpl();

            default:return null;
        }


    }
}
