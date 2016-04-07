/** 2015-05-31
 * Cecilia Bergman  cili.bergman@gmail.com
 * cebe3789
 */
package modsognerDurin;

import java.util.Arrays;
import java.util.List;


public class PhoneNumber implements Comparable<PhoneNumber>{
    private String number, phoneDesc;
    private final List<Character> CHARACTERS = Arrays.asList('0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '-', ' ');


    public PhoneNumber(String number, String phoneDesc) {
        if (number.trim().isEmpty()) throw new IllegalArgumentException("Du måste fylla i ett telefonnummer!");
        if (phoneDesc.trim().isEmpty()) throw new IllegalArgumentException("Du måste skriva en beskrivning!");
        for (char numC : number.toCharArray()) {
            if (!CHARACTERS.contains(numC)) {
                throw new IllegalArgumentException("Telefonnumret får bara innehålla siffror.");
            }
        }
        this.number = number;
        this.phoneDesc = phoneDesc;
    }

    public String getNumber() {
        return number;
    }

    public String getPhoneDesc() {
        return phoneDesc;
    }

    public int compareTo(PhoneNumber other) {
        return number.compareTo(other.number);
    }


}









