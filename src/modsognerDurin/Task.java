/** 2015-05-31
 * Cecilia Bergman  cili.bergman@gmail.com
 * cebe3789
 */
package modsognerDurin;

import com.intellij.util.PlusMinus;

import java.util.*;

public class Task {

    private Date date;
    private String desc;
    private int durationInHours;
    private boolean invoiced;
    private Map<Customer, Integer> taskPercentage;

    private final int COST = 9999;




    public Task (TaskManager manage){
        if (manage.getDesc() == null){
            throw new IllegalArgumentException("Du måste ange en beskrivning!");
        }
        this.date = manage.getDate();
        this.desc = manage.getDesc();
        this.durationInHours = manage.getDuration();
        this.taskPercentage = new HashMap<>(manage.getTaskPercentage());
        invoiced = false;

    }

    public Map<Customer, Integer> getTaskPercentage() {
        return taskPercentage;
    }

    public Date getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public int getDurationInHours(){
        return durationInHours;
    }

    public boolean isInvoiced() {
        return invoiced;
    }

    public void setInvoiced(boolean value) {
        invoiced = value;
    }


    public int getCostForCustomer(Customer customer) {
        int cost = 0;
        if (taskPercentage.containsKey(customer)) {
            int percentage = taskPercentage.get(customer);
            cost = (COST * durationInHours);
            cost *= (percentage/100.0);
        } else {
            cost = -1;
        }
        return cost;

    }

}




