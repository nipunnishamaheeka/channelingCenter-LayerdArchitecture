package lk.ijse.channelingCenter.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTm {
    private String payment_id;
    private Date payment_date;
    private Time payment_time;
    private double amount;
    private String appoinment_id;
    private Button deleteButton;
}