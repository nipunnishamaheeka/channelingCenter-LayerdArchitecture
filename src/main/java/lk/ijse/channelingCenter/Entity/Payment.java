package lk.ijse.channelingCenter.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
        private String payment_id;
        private Date payment_date;
        private Time payment_time;
        private double amount;
        private String appoinment_id;
}
