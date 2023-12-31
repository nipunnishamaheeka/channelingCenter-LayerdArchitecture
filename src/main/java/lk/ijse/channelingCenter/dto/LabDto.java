package lk.ijse.channelingCenter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class LabDto implements Serializable {
    private  String lab_id;
    private  String emp_id;
    private  String l_report;
}
