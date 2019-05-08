package dk.goodmanservice.goodmanservice.Service;

import org.springframework.stereotype.Service;

@Service
public class Validation {

    public boolean isNumeric(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
