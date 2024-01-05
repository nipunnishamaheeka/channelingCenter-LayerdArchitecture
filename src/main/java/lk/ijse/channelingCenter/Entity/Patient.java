package lk.ijse.channelingCenter.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
