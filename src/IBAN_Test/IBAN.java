package IBAN_Test;

import java.math.BigInteger;

public class IBAN {
    private String IBAN;

    private IBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public int GetControlNumber() {
        String IBANarr = IBAN.substring(4) + IBAN.substring(0, 2) + "00";
        StringBuilder IBANtoNumber = new StringBuilder();
        for (char i : IBANarr.toCharArray()) {
            int NumericValue = Character.getNumericValue(i);
            if (NumericValue >= 10 && NumericValue <= 35) {
                IBANtoNumber.append(NumericValue);
            } else {
                IBANtoNumber.append(i);
            }
        }
        BigInteger hh = new BigInteger(IBANtoNumber.toString());
        BigInteger gg = hh.mod(new BigInteger("97"));
        return 98 - gg.intValue();
    }

    public static void main(String[] args) {
        IBAN i1 = new IBAN("GB82WEST12345698765432");
        IBAN i2 = new IBAN("ES9121000418450200051332");
        System.out.println(i1.GetControlNumber() + " " + i2.GetControlNumber());
    }
}
