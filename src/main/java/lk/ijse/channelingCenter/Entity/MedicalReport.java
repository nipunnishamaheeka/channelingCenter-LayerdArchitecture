package lk.ijse.channelingCenter.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalReport {
    private String doc_id;
    private String patient_id;
    private String patient_name;
    private String date;
}
