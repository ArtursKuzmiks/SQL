package SpringLab;


import SpringLab.Data.Customer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Artur Kuzmik on 18.27.5
 */

public interface CompanyInterface {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    void printCustomers(ArrayList<Customer> customers);

    void sortDateSurname(ArrayList<Customer> customers);

    void insertData(ArrayList<Customer> customers) throws IOException;

    void dataFix(ArrayList<Customer> customers) throws IOException;

    void deleteCustomer(ArrayList<Customer> customers);

    void allDebtors(ArrayList<Customer> customers);

    void allCost(ArrayList<Customer> customers);

    default void printCustomCustomer(ArrayList<Customer> customers, int index) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        System.out.printf("%-15s\t%-15s\t%-15s\t%.2f\t%.2f\n",
                customers.get(index).getName(), customers.get(index).getSurname(),
                sim.format(customers.get(index).getDate()),
                customers.get(index).getCost(), customers.get(index).getPaid());
    }

    default boolean isOne(ArrayList<Customer> customers, int index) {
        int equals = 0;
        for (Customer aCustomer : customers) {
            if (customers.get(index).getSurname().equalsIgnoreCase(aCustomer.getSurname()))
                equals++;
        }

        return equals == 1;
    }

    default String format(String a) {
        String temp = a.trim();
        return temp.substring(0, 1).toUpperCase() + temp.substring(1);

    }

    default void title() {
        System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "Name", "Surname", "Date", "Cost", "Paid");

        System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "-------", "--------", "----------", "-----", "-----");
    }

    default int foundCustomer(ArrayList<Customer> customers) throws IOException {
        boolean found = false;
        int i=-1;

        try {

            String surname;
            System.out.print("Ievadiet pasutitaja Uzvardu: ");
            surname = reader.readLine();

            i = 0;
            while (!found && i < customers.size()) {
                if (surname.equalsIgnoreCase(customers.get(i).getSurname()))
                    found = true;
                else i++;

            }

            if (found && !isOne(customers, i)) {
                System.out.println("Pietrukst datu!");
                System.out.print("Ievadiet pasutitaja Vardu: ");
                String name = reader.readLine();

                i = 0;
                found = false;

                while (!found && i < customers.size()) {
                    if (surname.equalsIgnoreCase(customers.get(i).getSurname()) &&
                            name.equalsIgnoreCase(customers.get(i).getName()))
                        found = true;
                    else i++;
                }

            }

        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }
        if(found)
            return i;
        else
            return -1;

    }

}
