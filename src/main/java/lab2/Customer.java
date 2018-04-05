package lab2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


class Customer {

    private String name;
    private String surname;
    private Date date;
    private float cost;
    private float paid;

    Customer intCustomer(String name, String surname, Date date,
                         float cost, float paid) {
        Customer cust = new Customer();

        cust.name = name;
        cust.surname = surname;
        cust.date = date;
        cust.cost = cost;
        cust.paid = paid;

        return cust;

    }

    void printCustomers(ArrayList<Customer> cust) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "Name", "Surname", "Date", "Cost", "Paid");

        System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "-------", "--------", "----------", "-----", "-----");

        for (Customer aCust : cust)
            System.out.printf("%-15s\t%-15s\t%-15s\t%.2f\t%.2f\n",
                    aCust.name, aCust.surname, sim.format(aCust.date), aCust.cost, aCust.paid);
    }

    void printCustomCustomer(ArrayList<Customer> cust, int index) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        System.out.printf("%-15s\t%-15s\t%-15s\t%.2f\t%.2f\n",
                cust.get(index).name, cust.get(index).surname,
                sim.format(cust.get(index).date), cust.get(index).cost, cust.get(index).paid);
    }

    void sortDateSurname(ArrayList<Customer> cust) {

        cust.sort(Comparator.comparing(Customer::getDate).thenComparing(Customer::getSurname));

    }

    boolean isOne(ArrayList<Customer> cust, int index) {
        int equals = 0;
        for (Customer aCust : cust) {
            if (cust.get(index).getSurname().equalsIgnoreCase(aCust.getSurname()))
                equals++;
        }

        return equals == 1;
    }

    /*private void refactorySQLbase(ArrayList<Customer> cust) {
        SqLite_DB sqlite = new SqLite_DB();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < cust.size(); i++) {

            sqlite.insertdata(i + 1, cust.get(i).getName(), cust.get(i).getSurname(),
                    sim.format(cust.get(i).getDate()), cust.get(i).getCost(), cust.get(i).getPaid());
        }
    }*/

    void allDeptors(ArrayList<Customer> cust) {
        Customer customer = new Customer();

        System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "Name", "Surname", "Date", "Cost", "Paid");
        System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "-------", "--------", "----------", "-----", "-----");
        for (int i = 0; i < cust.size(); i++) {
            if (cust.get(i).getCost() > cust.get(i).getPaid())
                customer.printCustomCustomer(cust, i);
        }
    }

    void allCost(ArrayList<Customer> cust) {
        float summ = 0;

        for (Customer aCust : cust) summ += aCust.getCost();

        System.out.printf("Visu pasutijumu cenu summa: %.2f\n", summ);
    }

    String getName() {
        return name;
    }

    String getSurname() {
        return surname;
    }

    Date getDate() {
        return date;
    }

    float getCost() {
        return cost;
    }

    float getPaid() {
        return paid;
    }

    void setName(String name) {
        this.name = name;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }

    void setDate(Date date) {
        this.date = date;
    }

    void setCost(float cost) {
        this.cost = cost;
    }

    void setPaid(float paid) {
        this.paid = paid;
    }

}

