package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Model.Expense;
import dk.goodmanservice.goodmanservice.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Validation {

    /**
     Validation validere på de værdier som bliver skrevet ind i forme og giver en fejl besked vores noget er skrevet forkert ind.
     returnere " 1 " tilbage hvis der er intet galt i de indsatte værdier.
     */

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
        }
        return "1";
    }

    public String validateExpense(Expense obj) {
        if(obj.getPrice() == null || obj.getPrice() < 1) {
            return "PRIS ER FOR LAV, PRØV IGEN.";
        } else if(obj.getDescription() == null || obj.getDescription().length() <= 1) {
            return "BESKRIVELSE ER FOR KORT, PRØV IGEN.";
        }
        return "1";
    }

    public String validateUser(User obj) {
        if(obj.getFirstName() == null || obj.getLastName() == null || obj.getFirstName().length() < 2 || obj.getLastName().length() < 2) {
            return "Fornavn eller efternavn må ikke være under 2 karaktere";
        } else if(obj.getEmail() == null || obj.getEmail().length() < 2  || !obj.getEmail().contains("@")) {
            return "Ugyldig Email.";
        } else if(obj.getAddress() == null || obj.getAddress().length() < 2) {
            return "Ugyldig Adresse";
        } else if(obj.getCity() == null || obj.getCity().length() < 2) {
            return "Ugyldigt Bynavn";
        } else if(obj.getZip() == null || obj.getZip() < 1000 || obj.getZip() > 10000) {
            return "Ugyldigt Postnummer";
        } else if(obj.getPhoneNumber() == null || obj.getPhoneNumber().length() != 8) {
            return "Ugyldigt Telefon Nr.";
        } else if(obj.getPassword() == null || obj.getPassword().length() < 8) {
            return "Kodeordet skal være mindst 8 karaktere langt.";
        }
        return "1";
    }

    public String validateImage(MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) {
            return "DU HAR IKKE VALGT NOGET BILLEDE.";
        } else {
            return "1";
        }
    }
}
