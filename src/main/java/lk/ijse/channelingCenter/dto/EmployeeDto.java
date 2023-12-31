package lk.ijse.channelingCenter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDto implements Serializable {
    private String emp_id;
    private String emp_name;
    private String emp_address;
    private String mobile_number;
    private String job_role;
    private String qualification;
    private String salary;
}
