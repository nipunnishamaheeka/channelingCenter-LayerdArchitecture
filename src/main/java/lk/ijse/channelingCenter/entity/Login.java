package lk.ijse.channelingCenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Login {


        private String fullName;
        private String userName;
        private String password;
        private String email;
}
