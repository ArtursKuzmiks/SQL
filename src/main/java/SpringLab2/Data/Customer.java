package SpringLab2.Data;

import SpringLab2.CompanyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * @author Artur Kuzmik on 18.27.5
 */

@Component
public class Customer implements CompanyInterface {

    private SqLite_DB sqLite_db;

    private String name;
    private String surname;
    private Date date;
    private float cost;
    private float paid;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    Customer(){
    }

    public Customer(String name, String surname, Date date,
                    float cost, float paid) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.cost = cost;
        this.paid = paid;

    }

    @Autowired
    public Customer(SqLite_DB sqLite_db) {
        this.sqLite_db = sqLite_db;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    private void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDate() {
        return date;
    }

    private void setDate(Date date) {
        this.date = date;
    }

    public float getCost() {
        return cost;
    }

    private void setCost(float cost) {
        this.cost = cost;
    }

    public float getPaid() {
        return paid;
    }

    private void setPaid(float paid) {
        this.paid = paid;
    }

    @Override
    public void printCustomers(ArrayList<Customer> customers) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        title();
        for (Customer aCustomer : customers)
            System.out.printf("%-15s\t%-15s\t%-15s\t%.2f\t%.2f\n",
                    aCustomer.name, aCustomer.surname, sim.format(aCustomer.date), aCustomer.cost, aCustomer.paid);
    }

    @Override
    public void sortDateSurname(ArrayList<Customer> customers) {
        customers.sort(Comparator.comparing(Customer::getDate).thenComparing(Customer::getSurname));

    }

    @Override
    public void insertData(ArrayList<Customer> customers) throws IOException {

        Date data = null;

        try {

            System.out.println("Pievienot pasutijumu");

            System.out.print("Vards: ");
            String name = format(reader.readLine());

            System.out.print("Uzvards: ");
            String surname = format(reader.readLine());

            System.out.print("Date(yyyy-MM-dd):");
            String date = null;

            try {

                boolean end = false;
                while (!end) {
                    data = new SimpleDateFormat("yyyy-MM-dd").parse(reader.readLine());
                    if ((data != null ? data.compareTo(new Date()) : 0) < 0) {
                        System.out.println("Mums nav laika masinas :)");
                        System.out.print("Date(yyyy-MM-dd):");
                    } else {
                        date = new SimpleDateFormat("yyyy-MM-dd").format(data);
                        end = true;
                    }
                }

            } catch (ParseException e) {
                System.out.println("Nepareizas datuma formats");
            }

            System.out.print("Cost: ");
            float cost = Float.parseFloat(reader.readLine());

            System.out.print("Payd: ");
            float paid = Float.parseFloat(reader.readLine());


            sqLite_db.insertData(customers.size() + 1, name, surname, date, cost, paid);
            customers.add(new Customer(name, surname, data, cost, paid));


        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }

    }

    @Override
    public void dataFix(ArrayList<Customer> customers) throws IOException {

        int i = foundCustomer(customers);
        if (i >= 0) {
            System.out.println("Pasutitajs:");
            title();
            printCustomCustomer(customers, i);
            System.out.println();

            try {
                System.out.println("Izveleties ko Jus gribat koriget");
                System.out.println("Vards                         : 1");
                System.out.println("Uzvards                       : 2");
                System.out.println("Pasutijuma datums             : 3");
                System.out.println("Pasutijuma cenu               : 4");
                System.out.println("Samaksats                     : 5");
                System.out.println("Pabeigt korekciju             : 0");
                loop:
                for (; ; ) {
                    System.out.print("Izvele: ");
                    int menu = Integer.parseInt(reader.readLine());

                    switch (menu) {
                        case 1:
                            System.out.print("Ievadiet Vardu: ");
                            customers.get(i).setName(format(reader.readLine()));
                            break;
                        case 2:
                            System.out.print("Ievadiet Uzvardu: ");
                            customers.get(i).setSurname(format(reader.readLine()));
                            break;
                        case 3:
                            System.out.print("Ievadiet datumu (yyyy-MM-dd): ");

                            try {
                                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(reader.readLine());

                                if (newDate.compareTo(new Date()) < 0)
                                    System.out.println("Mums nav laika masinas :)");
                                else
                                    customers.get(i).setDate(newDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            System.out.print("Ievadiet cenu: ");
                            customers.get(i).setCost(Float.parseFloat(reader.readLine()));
                            break;
                        case 5:
                            System.out.print("Ievadiet samaksatu cenas dalu: ");
                            float n = Float.parseFloat(reader.readLine());
                            if (n > customers.get(i).getCost())
                                System.out.println("Samaksats ir vairak neka cena, error");
                            else
                                customers.get(i).setPaid(n);
                            break;
                        case 0:
                            break loop;
                        default:
                            System.out.println("Nav tadu variantu, meiginet vel reiz: ");
                    }
                }
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

                sqLite_db.dataFix(i + 1, customers.get(i).getName(), customers.get(i).getSurname(),
                        sim.format(customers.get(i).getDate()), customers.get(i).getCost(), customers.get(i).getPaid());

            } catch (IllegalArgumentException e) {
                System.out.println("input error");
            }

        } else {
            System.out.println("Pasutitaju ar tadu Uzvardu nav");
        }

    }

    @Override
    public void deleteCustomer(ArrayList<Customer> customers) {

        int i = 0;
        boolean found = false;
        try {
            System.out.print("Ievadiet pasutitaja Uzvardu: ");
            String surname = format(reader.readLine());
            System.out.print("Izvadiet pasutitaja Vardu: ");
            String name = format(reader.readLine());

            while (!found && i < customers.size()) {
                if (surname.equalsIgnoreCase(customers.get(i).getSurname()) &&
                        name.equalsIgnoreCase(customers.get(i).getName())) {
                    customers.remove(i);
                    found = true;
                } else i++;

            }

            if (found)
                sqLite_db.remove(surname, name);
            else
                System.out.println("Tadu pasutitaju nav");

        } catch (IOException e) {
            System.out.println("Input error");
        }

    }

    @Override
    public void allDebtors(ArrayList<Customer> customers) {
        title();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCost() > customers.get(i).getPaid())
                printCustomCustomer(customers, i);
        }

    }

    @Override
    public void allCost(ArrayList<Customer> customers) {

        float amount = 0;

        for (Customer aCust : customers) amount += aCust.getCost();

        System.out.printf("Visu pasutijumu cenu summa: %.2f\n", amount);

    }
}
