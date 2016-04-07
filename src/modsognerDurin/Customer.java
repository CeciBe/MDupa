/** 2015-05-31
 * Cecilia Bergman  cili.bergman@gmail.com
 * cebe3789
 */
package modsognerDurin;

import java.util.*;

public class Customer implements Comparable<Customer> {

    private String name;
    private String address;
    public List<PhoneNumber> numbers = new ArrayList<>();
    private int amountInvoiced;



    public Customer(String name, String address) {
        if (name.trim().isEmpty()) throw new IllegalArgumentException("Du måste skriva ett namn!");
        if (address.trim().isEmpty()) throw new IllegalArgumentException("Du måste skriva en adress!");
        this.name = name;
        this.address = address;
        numbers = new ArrayList<>();
        this.amountInvoiced = 0;

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void invoiceAmount(int amount) {
        amountInvoiced += amount;
    }

    public int getAmountInvoiced() {
        return amountInvoiced;
    }

    public List<PhoneNumber> getNumbers() {
        return numbers;
    }

    public void addNumbers(String number, String description) {
        numbers.add(new PhoneNumber(number, description));
    }

    public int compareTo(Customer other) {
        return name.compareToIgnoreCase(other.name);

    }

}







