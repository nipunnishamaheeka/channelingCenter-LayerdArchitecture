package lk.ijse.channelingCenter.dto;

import lk.ijse.channelingCenter.dto.tm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.monitor.StringMonitor;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceOrderDto implements Serializable {
    private String appoinment_id;
    private Date date;
    private Time time;
    private double amount;
    private List<CartTm> tmlist;
}
