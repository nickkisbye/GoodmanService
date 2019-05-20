package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Calculator;
import org.springframework.stereotype.Service;

@Service
public class CalcService {

    public int iCalc(Calculator calc) {
        int result = 0;

        switch(calc.getNewIsolering()) {
            case 150:
                result = 149 * calc.getmTwo();
                break;
            case 200:
                result = 169 * calc.getmTwo();
                break;
            case 300:
                result = 199 * calc.getmTwo();
                break;
            case 400:
                result = 219 * calc.getmTwo();
                break;
        }
        return result;
    }

}
