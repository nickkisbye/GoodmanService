package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Case;
import org.springframework.stereotype.Service;

@Service
public class Validation {

    public String validateCase(Case obj) {
        if(obj.getDescription().length() <= 3 || obj.getDescription() == null) {
            return "BESKRIVELSE ER FORKORT, PRØV IGEN.";
        } else if(obj.getPrice() <= 0 || obj.getPrice() == null) {
            return "PRIS ER FOR LAV, PRØV IGEN.";
        } else if(obj.getStartDate().length() <= 1 || obj.getStartDate() == null) {
            return "START DATO ER UGYLDIGT, PRØV IGEN.";
        } else if(obj.getStartTime().length() <= 1 || obj.getStartTime() == null) {
            return "START TIDPUNKT ER UGYLDIGT, PRØV IGEN.";
        } else if(obj.getEndDate().length() <= 1 || obj.getEndDate() == null) {
            return "SLUT DATO ER UGYLDIGT, PRØV IGEN.";
        } else if(obj.getEndTime().length() <= 1 || obj.getEndTime() == null) {
            return "SLUT TIDPUNKT ER UGYLDIGT, PRØV IGEN.";
        } else {
            return "1";
        }
    }

    public boolean isNumeric(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
