package lk.ijse.dep13.backendexpensemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeExpense {
    private int id;
    private String date;
    private String time;
    private String type;
    private String amount;
    private String description;
}
