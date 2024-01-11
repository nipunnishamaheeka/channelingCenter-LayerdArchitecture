package lk.ijse.channelingCenter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class AppoinmentDto implements Serializable {
    private String appoinment_id;
    private String date;
    private String patinet_id;
    private String patinetName;
    private String age;
    private String id;
    private String doctor_name;
    private String status;


}
