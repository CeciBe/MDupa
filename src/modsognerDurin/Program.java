/** 2015-05-31
 * Cecilia Bergman  cili.bergman@gmail.com
 * cebe3789
 */
package modsognerDurin;


import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class Program {

    private Date date = new Date();
    private List<Customer> customerList = new ArrayList<>();
    private List<Task> taskList = new ArrayList<>();


    private void runProgram() {

        initialize();

        while (true) {
            try {
                System.out.print("\nMODSOGNER OCH DURIN U.P.A" +
                        "\n---------------------------------" +
                        "\nVälj ett av alternativen i menyn:\n"
                        + "---------------------------------\n"
                        + "1: Lägg till kund \n"
                        + "2: Ta bort kund \n"
                        + "3: Lägg till telefonnummer \n"
                        + "4: Lägg till uppdrag \n"
                        + "5: Lista alla uppdrag \n"
                        + "6: Lista alla uppdrag för en viss kund \n"
                        + "7: Lista alla kunder \n"
                        + "8: Skriv ut fakturor \n"
                        + "9: Hitta bästa kunden \n"
                        + "10: Hitta kunder med samma telefonnummer \n"
                        + "11: Avsluta \n");


                int command = readInteger("\nAnge kommando 1-11: ");
                switch (command) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        removeCustomer();
                        break;
                    case 3:
                        addPhoneNumber();
                        break;
                    case 4:
                        addTask();
                        break;
                    case 5:
                        listAllTasks();
                        break;
                    case 6:
                        listAllTasksForCustomer();
                        break;
                    case 7:
                        listAllCustomers();
                        break;
                    case 8:
                        printInvoices();
                        break;
                    case 9:
                        findBestCustomer();
                        break;
                    case 10:
                        findCustomersWithSameNumber();
                        break;
                    case 11:
                        endProgram();
                        break;
                    default:
                        System.out.println("Ogiltigt kommando, försök igen!" + "\n\n");
                }


            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt tecken, försök igen!");
            }
        }
    }


    private void initialize() {

        Customer customer1 = new Customer("Oden", "Lidskjalf, Valhall");
        customer1.addNumbers("01-23000", "Valhall växel");
        customer1.addNumbers("070-987654", "Arbetsmobil");

        Customer customer2 = new Customer("Frigg", "Fensalarna");
        customer2.addNumbers("01-456789", "Fensalarna");
        customer2.addNumbers("01-23000", "Valhall växel");

        Customer customer3 = new Customer("Heimdal", "Bifrost");

        Customer customer4 = new Customer("Skuld", "Urdarbrunnen");
        customer4.addNumbers("03-563673", "Hemtelefon");

        Customer customer5 = new Customer("Urd", "Urdarbrunnen");
        customer5.addNumbers("070-123456", "Privat mobil");
        customer5.addNumbers("03-563673", "Hemtelefon");

        Customer customer6 = new Customer("Verdandi", "Urdabrunnen");
        customer6.addNumbers("03-563673", "Hemtelefon");

        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
        customerList.add(customer4);
        customerList.add(customer5);
        customerList.add(customer6);


        TaskManager manage = new TaskManager();
        manage.setDesc("Muddring av brunn");
        manage.setDate(getNow());
        manage.setDurationInHours(5);
        manage.addCustomers(getCustomerByName("Oden"), 30);
        manage.addCustomers(getCustomerByName("Urd"), 60);
        manage.addCustomers(getCustomerByName("Verdandi"), 10);
        taskList.add(new Task(manage));

        manage = new TaskManager();
        manage.setDesc("Ommålning av Bifrost");
        manage.setDate(getNow());
        manage.setDurationInHours(80);
        manage.addCustomers(getCustomerByName("Heimdal"), 100);
        Task task = new Task(manage);
        task.setInvoiced(true);
        int cost = task.getCostForCustomer(customer3);
        customer3.invoiceAmount(cost);
        taskList.add(task);

        manage = new TaskManager();
        manage.setDesc("Uppgrävning av Yggdrasil");
        manage.setDate(getNow());
        manage.setDurationInHours(57);
        manage.addCustomers(getCustomerByName("Oden"), 10);
        manage.addCustomers(getCustomerByName("Heimdal"), 90);
        taskList.add(new Task(manage));


    }


    private static Date getNow() {
        return new Date(System.currentTimeMillis());
    }


    private static int readInteger(String msg) {
        while (true) {
            String scan = readString(msg);
            try {
                return Integer.parseInt(scan);

            } catch (NumberFormatException e) {
                System.out.println("Fel - måste vara ett heltal, minst 1!");
            }
        }
    }


    private static String readString(String msg) {
        System.out.print(msg);
        Scanner scan = new Scanner(System.in);

        return scan.nextLine();

    }


    private Customer getCustomerByName(String name) {
        try {
            for (Customer c : customerList) {
                if (name.equalsIgnoreCase(c.getName())) {
                    return c;
                }
            }
        } catch (NullPointerException npe) {
            System.out.println(npe.getMessage());
        }
        return null;

    }


    private ArrayList<Task> getUnInvoicedTasks() {
        ArrayList<Task> unInvoicedTasks = new ArrayList<>();
        System.out.printf("%d uppdrag totalt\n", taskList.size());
        for (Task task : taskList) {
            if (!task.isInvoiced()) {
                unInvoicedTasks.add(task);
            }
        }
        System.out.printf("%d av vilka är obetalda\n", unInvoicedTasks.size());
        return unInvoicedTasks;
    }


    private boolean customerHasUnInvoicedTasks(Customer customer) {
        ArrayList<Task> unInvoicedTasks = getUnInvoicedTasks();
        for (Task task : unInvoicedTasks) {
            if (task.getTaskPercentage().containsKey(customer)) {
                return true;
            }
        }
        return false;
    }


    public void addCustomer() {
        System.out.println("\nRegistrera kund");
        System.out.println("     ---");
        try {
            String name = readString("Vad heter den nya kunden: ");
            Customer cus = getCustomerByName(name);
            if (cus != null) {
                System.out.println("Denna kund existerar redan");
            } else {
                String address = readString("Adress: ");
                Customer newCustomer = new Customer(name, address);
                customerList.add(newCustomer);
                System.out.print("\n" + name + " har lagts till\n");

            }
        } catch(IllegalArgumentException iae){
            System.out.println("Du måste fylla i namn och adress på den nya kunden!");
        }
    }


    public void removeCustomer() {
        System.out.println("\nTa bort kund");
        System.out.println("     ---");

        if (customerList.isEmpty()) {
            System.out.println("Det finns inga kunder!");
        } else {
            String name = readString("Vilken kund ska tas bort: ");
            Customer tmpC = getCustomerByName(name);
            if (tmpC == null) {
                System.out.println("Denna kund existerar inte\n");
            } else {
                String str;
                if (customerHasUnInvoicedTasks(tmpC)) {
                    str = "\nDenna kund har obetalda uppdrag och kan därför inte tas bort";
                } else {
                    customerList.remove(tmpC);
                    str = "\n" + name + " har tagits bort!";
                }

                System.out.println(str);
            }
        }
    }


    public void addPhoneNumber() {
        System.out.println("\nLägg till telefonnummer");
        System.out.println("     ---");

        if (customerList.isEmpty()) {
            System.out.println("Det finns inga kunder!");
        } else {
            String name = readString("Till vilken kund hör telefonnumret: ");
            Customer cus = getCustomerByName(name);
            if (cus == null) {
                System.out.println("Denna kund existerar inte\n");
            } else {
                String number = readString("Nummer: ");
                String description = readString("Beskrivning: ");
                try {
                    PhoneNumber newNumber = new PhoneNumber(number, description);
                    cus.numbers.add(newNumber);
                    System.out.println("Numret har lagts till!");
                } catch (IllegalArgumentException iae) {
                    System.out.printf("Numret gick inte att lägga till: %s\n", iae.getMessage());
                }
            }
        }
    }


    public void addTask() {
        System.out.println("Lägg till uppdrag");
        System.out.println("     ---");

        if (customerList.isEmpty()) {
            System.out.println("Det finns inga kunder!");
        } else {
            TaskManager task = new TaskManager();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Datum: " + dateFormat.format(date));
            String desc = readString("Beskrivning: ");
            int durationInHours = readInteger("Antal timmar: ");

            try {
                task.setDate(date);
                task.setDesc(desc);
                task.setDurationInHours(durationInHours);

            } catch (IllegalArgumentException iae) {
                System.out.println("Ogiltigt kommando: " + iae.getMessage());
            }
            do {
                String name = readString("Vilken kund utförs uppdraget för: ");
                Customer oldCus = getCustomerByName(name);
                int percentage;

                if (oldCus == null) {
                    System.out.println("Det finns ingen kund med det namnet.");
                } else {
                    try {
                        int percentageLeft = 100 - task.howMuchCovered();
                        percentage = readInteger("Hur många procent av uppdraget ska " + name + " betala för: ");
                        if(percentage > percentageLeft || percentage <= 0){
                            System.out.println("Felaktigt procental! Det får minst vara 1% och max " + percentageLeft + "%");
                            continue;
                        } else if (task.getTaskPercentage().containsKey(oldCus)){
                            System.out.println("Kunden " + oldCus.getName() + " är redan tillagd i uppdraget. " +
                                    "Lägg till annan kund med minst 1% och max " + percentageLeft + "%!");
                            continue;
                        }
                        task.addCustomers(oldCus, percentage);
                        if (!task.customersCoverEnough()) {
                            System.out.println("Jobbet är ej täckt av kunder, du måste lägga till en kund!");
                        }else {
                            System.out.println("Uppdraget tillagt!");
                        }
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Ogiltigt kommando: " + iae.getMessage());
                    }
                }
            }
            while (!task.customersCoverEnough());

            try {
                Task newTask = new Task(task);
                taskList.add(newTask);

            } catch (Exception e) {
                System.out.printf("Uppdraget kunde inte läggas till: %s\n", e.getMessage());
            }
        }
    }


    public void listAllTasks() {
        System.out.println("Lista alla uppdrag");
        System.out.println("     ---");

        String output = "";
        if (taskList.isEmpty()) {
            System.out.println("Det finns inga uppdrag!");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Task task : taskList) {
                output += String.format("\n %s", task.getDesc());
                output += "\n  " + (task.isInvoiced() ? "Fakturerad" : "Inte fakturerad");
                output += String.format("\n  %s", sdf.format(task.getDate()));
                output += String.format("\n  %s timmar", task.getDurationInHours());
                output += "\n Sammanslagna kunder:";
                Map<Customer, Integer> percentages = task.getTaskPercentage();
                for (Customer customer : percentages.keySet()) {
                    output += String.format("\n Kund: %s", customer.getName());
                    output += String.format("\n Betalar: %d (%d %%)", task.getCostForCustomer(customer), percentages.get(customer));
                    output += "\n    ---";
                }
                output += "\n===";

            }
            System.out.println(output);
        }
    }


    public void listAllTasksForCustomer() {
        System.out.println("Lista alla uppdrag för en viss kund");
        System.out.println("     ---");

        if (customerList.isEmpty()) {
            System.out.println("Det finns inga kunder!");
        } else {
            String name = readString("För kund: ");
            Customer cus = getCustomerByName(name);

            if (cus == null) {
                System.out.println("Denna kund existerar inte!");
            } else {
                String output = "";
                if (taskList.isEmpty()) {
                    System.out.println("Det finns inga uppdrag!");
                } else if (taskList.size() >= 1) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    for (Task task : taskList) {
                        if (task.getTaskPercentage().containsKey(cus)) {
                            output += String.format("\n %s", task.getDesc());
                            output += "\n  " + (task.isInvoiced() ? "Fakturerad" : "Inte fakturerad");
                            output += String.format("\n  %s", sdf.format(task.getDate()));
                            output += String.format("\n  %s timmar", task.getDurationInHours());
                            output += "\n Sammanslagna kunder:";
                            Map<Customer, Integer> percentages = task.getTaskPercentage();
                            for (Customer customer : percentages.keySet()) {
                                output += String.format("\n Kund: %s", customer.getName());
                                output += String.format("\n Betalar: %d (%d %%)", task.getCostForCustomer(customer), percentages.get(customer));
                                output += "\n    ---";
                            }
                            output += "\n===";

                        }
                    }

                    System.out.println(output);
                }
            }
        }
    }




    public void listAllCustomers() {
        System.out.println("Lista alla kunder");
        System.out.println("     ---");
        if (customerList.isEmpty()) {
            System.out.println("Det finns inga kunder!");
        } else {

            Collections.sort(customerList);

            for (Customer customer : customerList) {
                String name, address;
                ArrayList<PhoneNumber> numbers;
                name = customer.getName();
                address = customer.getAddress();
                numbers = (ArrayList<PhoneNumber>) customer.getNumbers();
                String message = String.format("%s\n  %s", name, address);
                if (!numbers.isEmpty()) {
                    message += "\n  Nummer:";
                    for (PhoneNumber number : numbers) {
                        message += String.format("\n  %s\n  %s\n  ---", number.getPhoneDesc(), number.getNumber());
                    }

                }
                message += "\n===";
                System.out.println(message);
            }
        }
    }


    public void printInvoices() {
        System.out.println("Skriv ut fakturor");
        System.out.println("     ---");

        ArrayList<Task> unInvoicedTasks = getUnInvoicedTasks();
        if (unInvoicedTasks.isEmpty()) {
            System.out.println("Det finns inga obetalda uppdrag.");
        } else {
            String output = "";
            for (Customer customer : customerList) {
                ArrayList<Task> customerTasks = new ArrayList<>();
                for (Task task : unInvoicedTasks) {
                    if (task.getTaskPercentage().keySet().contains(customer)) {
                        customerTasks.add(task);
                    }
                }
                if (!customerTasks.isEmpty()) {
                    output += String.format("\n%s", customer.getName());
                    int totalCost = 0;
                    for (Task task : customerTasks) {
                        String desc = task.getDesc();
                        int cost = task.getCostForCustomer(customer);
                        totalCost += cost;
                        output += String.format("\n Uppdragsbeskrivning: %s", desc);
                        output += String.format("\n Betalning: %d", cost);
                        output += " \n---";
                        customer.invoiceAmount(cost);
                    }
                    if (customerTasks.size() > 1) {
                        output += String.format("\n Totalt: %d", totalCost);
                    }
                } else continue;
                output += "\n===";
            }
            System.out.println(output);
            for (Task task : unInvoicedTasks) {
                task.setInvoiced(true);
            }
        }
    }


    public void findBestCustomer() {
        System.out.println("Hitta bästa kunden");
        System.out.println("     ---");
        if (customerList.isEmpty()) {
            System.out.println("Det finns inga kunder!");
        } else {
            customerList.sort((c1, c2) -> c2.getAmountInvoiced() - c1.getAmountInvoiced());
            ArrayList<Customer> bestCustomers = new ArrayList<>();
            for (Customer customer : customerList) {
                bestCustomers.add(customer);

            }
            for (int i = 0; i < 3 && i < bestCustomers.size(); i++) {
                Customer bestCustomer = bestCustomers.get(i);
                if (bestCustomer.getAmountInvoiced() > 0) {
                    System.out.printf("%d: %s - %d fakturerad\n", i + 1, bestCustomer.getName(), bestCustomer.getAmountInvoiced());
                }
                else if (i == 0){
                    System.out.println("Inga kunder har fakturerats ännu!");
                    return;
                }

            }
        }
    }


    public void findCustomersWithSameNumber() {
        System.out.println("Hitta kunder med samma telefonnummer");
        System.out.println("     ---");

        if (customerList.isEmpty()) {
            System.out.println("Det finns inga kunder!");
        } else {
            HashMap<String, ArrayList<Customer>> sameNumbers = new HashMap<>();
            for (Customer customer : customerList) {
                for (PhoneNumber nr : customer.getNumbers()) {
                    String num = nr.getNumber();
                    if (!sameNumbers.containsKey(num)) {
                        sameNumbers.put(num, new ArrayList<>());
                    }
                    sameNumbers.get(num).add(customer);
                }
            }
            Set<String> numbers = sameNumbers.keySet();
            for (String nr : numbers) {
                if (sameNumbers.get(nr).size() > 1) {
                    sameNumbers.get(nr);
                    System.out.printf("Nummret \"%s\" tillhör:", nr);

                    ArrayList<Customer> customersWithNumber = sameNumbers.get(nr);
                    for (Customer customer : customersWithNumber) {
                        System.out.printf("\n%s, %s", customer.getName(), customer.getAddress());
                    }

                    System.out.print("\n    ---");
                    System.out.println();

                } else if (numbers.isEmpty() || sameNumbers.isEmpty() || numbers.size() < 2) {
                    System.out.println("Det finns inga kunder med samma nummer!");
                }
            }
        }
    }



    public void endProgram() {
        System.out.println("\nProgramet är nu avslutat!");
        System.exit(0);
    }



    public static void main(String[] args) {
        Program prog = new Program();
        prog.runProgram();
    }
}