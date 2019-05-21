package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Model.Expense;
import org.springframework.stereotype.Service;

@Service
public class Validation {

    public String validateAppointment(Appointment obj) {
        if(obj.getDescription() == null || obj.getDescription().length() <= 3) {
            return "BESKRIVELSE ER FOR KORT, PRØV IGEN.";
        } else if(obj.getDate() == null || obj.getDate().length() <= 1) {
            return "DATO ER UGYLDIG, PRØV IGEN.";
        } else if(obj.getTime() == null || obj.getTime().length() <= 1) {
            return "TIDSPUNKTET ER UGYLDIGT, PRØV IGEN.";
        }
        return "1";
    }

    public String validateCase(Case obj) {
        if(obj.getDescription() == null || obj.getDescription().length() <= 3) {
            return "BESKRIVELSE ER FOR KORT, PRØV IGEN.";
        } else if(obj.getPrice() == null || obj.getPrice() < 1) {
            return "PRIS ER FOR LAV, PRØV IGEN.";
        } else if(obj.getStartDate() == null || obj.getStartDate().length() <= 1) {
            return "START DATO ER UGYLDIGT, PRØV IGEN.";
        } else if(obj.getStartTime() == null || obj.getStartTime().length() <= 1) {
            return "START TIDPUNKT ER UGYLDIGT, PRØV IGEN.";
        } else if(obj.getEndDate() == null || obj.getEndDate().length() <= 1) {
            return "SLUT DATO ER UGYLDIGT, PRØV IGEN.";
        } else if(obj.getEndTime() == null || obj.getEndTime().length() <= 1) {
            return "SLUT TIDPUNKT ER UGYLDIGT, PRØV IGEN.";
        } else {
            return "1";
        }
    }

    public String validateExpense(Expense obj) {
        if(obj.getPrice() == null || obj.getPrice() < 1) {
            return "PRIS ER FOR LAV, PRØV IGEN.";
        } else if(obj.getDescription() == null || obj.getDescription().length() <= 1) {
            return "BESKRIVELSE ER FOR KORT, PRØV IGEN.";
        }
        return "1";
    }
}
