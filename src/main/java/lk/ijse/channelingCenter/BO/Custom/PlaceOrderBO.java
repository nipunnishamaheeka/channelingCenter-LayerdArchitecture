package lk.ijse.channelingCenter.BO.Custom;

import lk.ijse.channelingCenter.dto.PlaceOrderDto;

import java.sql.SQLException;

public interface PlaceOrderBO {
    boolean placeOrder(PlaceOrderDto dto) throws SQLException, ClassNotFoundException;

}
