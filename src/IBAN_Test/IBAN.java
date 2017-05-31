package IBAN_Test;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class IBAN {
    private Map<String, String> CharacterIntMap = new HashMap<String, String>() {
        {
            put("A", "10");
            put("B", "11");
            put("C", "12");
            put("D", "13");
            put("E", "14");
            put("F", "15");
            put("G", "16");
            put("H", "17");
            put("I", "18");
            put("J", "19");
            put("K", "20");
            put("L", "21");
            put("M", "22");
            put("N", "23");
            put("O", "24");
            put("P", "25");
            put("Q", "26");
            put("R", "27");
            put("S", "28");
            put("T", "29");
            put("U", "30");
            put("V", "31");
            put("W", "32");
            put("X", "33");
            put("Y", "34");
            put("Z", "35");
        }
    };
    private String IBAN;

    private IBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public int GetControlNumber() {
//        String IBANarr = IBAN.substring(4) + IBAN.substring(0, 4);
//        String IBANarr = IBAN.substring(4);
        String IBANarr = IBAN.substring(4) + IBAN.substring(0, 2) + "00";
        String IBANtoNumber = "";
        for (int i = 0; i < IBANarr.length(); i++) {
            IBANtoNumber += CharacterIntMap.getOrDefault(IBANarr.substring(i, i + 1), IBANarr.substring(i, i + 1));
        }
        BigInteger hh = new BigInteger(IBANtoNumber);
        BigInteger gg = hh.mod(new BigInteger("97"));
        return (int) 98 - gg.intValue();
    }

    public static void main(String[] args) {
        IBAN i1 = new IBAN("GB82WEST12345698765432");
        IBAN i2 = new IBAN("ES9121000418450200051332");
        System.out.println(i1.GetControlNumber() + " " + i2.GetControlNumber());
    }
}
