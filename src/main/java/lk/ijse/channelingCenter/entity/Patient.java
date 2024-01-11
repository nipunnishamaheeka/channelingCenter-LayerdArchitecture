package lk.ijse.channelingCenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
        private String patient_id;
        private String patient_name;
        private String mobile_number;
        private String address;
        private String sex;
        private String email;
        private String age;
        private String blood;
}
