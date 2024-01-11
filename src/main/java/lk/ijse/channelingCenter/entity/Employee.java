package lk.ijse.channelingCenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
        private String emp_id;
        private String emp_name;
        private String emp_address;
        private String mobile_number;
        private String job_role;
        private String qualification;
        private String salary;
}
