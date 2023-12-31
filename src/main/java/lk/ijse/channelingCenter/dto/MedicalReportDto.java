package lk.ijse.channelingCenter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MedicalReportDto implements Serializable {
    private String doc_id;
    private String patient_id;
    private String patient_name;
    private String date;
}
