package lk.ijse.channelingCenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@Data
@NoArgsConstructor
public class Doctor {
    private String id;
    private String name;
    private String address;
    private String email;
    private String number;
    private String type;
    private double drFee;
}
