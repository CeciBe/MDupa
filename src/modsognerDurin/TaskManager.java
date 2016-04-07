/** 2015-05-31
 * Cecilia Bergman  cili.bergman@gmail.com
 * cebe3789
 */
package modsognerDurin;

import java.util.*;

//Associationsklass

public class TaskManager {
    private Date date;
    private String desc;
    private int duration;
    private Map<Customer, Integer> taskPercentage;


    public TaskManager() {
        taskPercentage = new HashMap<>();
    }

    public void addCustomers(Customer customers, int assignTime) {
        taskPercentage.put(customers, assignTime);
    }

    public int howMuchCovered(){
        int percentage = 0;
        for(int cus : getTaskPercentage().values()){
            percentage += cus;
        }
        return percentage;
    }

    public boolean customersCoverEnough(){
        return howMuchCovered() == 100;
    }

    public void setDesc(String value) {
        if (value.trim().isEmpty()) throw new IllegalArgumentException("Du måste ange en beskrivning!");
        desc = value;
    }

    protected String getDesc() {
        return desc;
    }

    public void setDate(Date value) {
        date = value;
    }

    protected Date getDate() {
        return date;
    }

    public void setDurationInHours(int value) {
        if (value < 1) throw new NumberFormatException("Måste vara ett positivt numeriskt värde.");
        duration = value;
    }

    protected int getDuration() {
        return duration;
    }

    protected Map<Customer, Integer> getTaskPercentage() {
        return taskPercentage;
    }

}

