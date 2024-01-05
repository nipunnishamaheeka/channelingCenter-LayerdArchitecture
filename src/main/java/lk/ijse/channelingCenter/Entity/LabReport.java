package lk.ijse.channelingCenter.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LabReport {

        private String lab_reportid;
        private String patient_id;
        private String date;
        private String doctor_id;
        private String doctor_name;
        private String age;
        private String gender;
        private String patient_name;
        private String test_name;
        private String test_result;
        private String units;
        private String others;
}
