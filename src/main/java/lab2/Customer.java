package lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
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
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    Customer(String name, String surname, Date date,
             float cost, float paid){
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.cost = cost;
        this.paid = paid;

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

    private void printCustomCustomer(ArrayList<Customer> cust, int index) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        System.out.printf("%-15s\t%-15s\t%-15s\t%.2f\t%.2f\n",
                cust.get(index).name, cust.get(index).surname,
                sim.format(cust.get(index).date), cust.get(index).cost, cust.get(index).paid);
    }

    void sortDateSurname(ArrayList<Customer> cust) {

        cust.sort(Comparator.comparing(Customer::getDate).thenComparing(Customer::getSurname));

    }

    void insertData(ArrayList<Customer> cust) throws IOException {

        SqLite_DB sqlite = new SqLite_DB();
        Date data = null;

        try {

            System.out.println("Pievienot pasutijumu");

            System.out.print("Vards: ");
            String name = format(br.readLine());

            System.out.print("Uzvards: ");
            String surname = format(br.readLine());

            System.out.print("Date(yyyy-MM-dd):");
            String date = null;

            try {

                boolean end = false;
                while (!end) {
                    data = new SimpleDateFormat("yyyy-MM-dd").parse(br.readLine());
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
            float cost = Float.parseFloat(br.readLine());

            System.out.print("Payd: ");
            float paid = Float.parseFloat(br.readLine());


            sqlite.insertData(cust.size() + 1, name, surname, date, cost, paid);
            cust.add(new Customer(name, surname, data, cost, paid));


        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }
    }

    void dataFix(ArrayList<Customer> cust) throws IOException {

        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        SqLite_DB sqlite = new SqLite_DB();

        boolean found = false;
        String surname;
        System.out.print("Ievadiet pasutitaja Uzvardu: ");
        surname = br.readLine();

        int i = 0;
        while (!found && i < cust.size()) {
            if (surname.equalsIgnoreCase(cust.get(i).getSurname()))
                found = true;
            else i++;

        }

        if (found && !isOne(cust, i)) {
            System.out.println("Pietrukst datu!");
            System.out.print("Ievadiet pasutitaja Vardu: ");
            String name = br.readLine();

            i = 0;
            found = false;

            while (!found && i < cust.size()) {
                if (surname.equalsIgnoreCase(cust.get(i).getSurname()) &&
                        name.equalsIgnoreCase(cust.get(i).getName()))
                    found = true;
                else i++;
            }

        }

        if (found) {
            System.out.println("Pasutitajs:");
            System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                    "Name", "Surname", "Date", "Cost", "Paid");
            System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                    "-------", "--------", "----------", "-----", "-----");
            printCustomCustomer(cust, i);
            System.out.println();

            try {
                System.out.println("Izveleties ko Jus gribat koriget");
                System.out.println("Vards                         :" + ANSI_RED + "1" + ANSI_RESET);
                System.out.println("Uzvards                       :" + ANSI_RED + "2" + ANSI_RESET);
                System.out.println("Pasutijuma datums             :" + ANSI_RED + "3" + ANSI_RESET);
                System.out.println("Pasutijuma cenu               :" + ANSI_RED + "4" + ANSI_RESET);
                System.out.println("Samaksats                     :" + ANSI_RED + "5" + ANSI_RESET);
                System.out.println("Pabeigt korekciju             :" + ANSI_RED + "0" + ANSI_RESET);
                loop:
                for (; ; ) {
                    System.out.print("Izvele: ");
                    int menu = Integer.parseInt(br.readLine());

                    switch (menu) {
                        case 1:
                            System.out.print("Ievadiet Vardu: ");
                            cust.get(i).setName(format(br.readLine()));
                            break;
                        case 2:
                            System.out.print("Ievadiet Uzvardu: ");
                            cust.get(i).setSurname(format(br.readLine()));
                            break;
                        case 3:
                            System.out.print("Ievadiet datumu" + ANSI_RED + "(yyyy-MM-dd)" + ANSI_RESET + ": ");

                            try {
                                Date newdate = new SimpleDateFormat("yyyy-MM-dd").parse(br.readLine());

                                if (newdate.compareTo(new Date()) < 0)
                                    System.out.println("Mums nav laika masinas :)");
                                else
                                    cust.get(i).setDate(newdate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            System.out.print("Ievadiet cenu: ");
                            cust.get(i).setCost(Float.parseFloat(br.readLine()));
                            break;
                        case 5:
                            System.out.print("Ievadiet samaksatu cenas dalu: ");
                            int n = Integer.parseInt(br.readLine());
                            if (n > cust.get(i).getCost())
                                System.out.println("Samaksats ir vairak neka cena, error");
                            else
                                cust.get(i).setPaid(Float.parseFloat(br.readLine()));
                            break;
                        case 0:
                            break loop;
                        default:
                            System.out.println("Nav tadu variantu, meiginet vel reiz: ");
                    }
                }
                SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

                sqlite.insertData(i + 1, cust.get(i).getName(), cust.get(i).getSurname(),
                        sim.format(cust.get(i).getDate()), cust.get(i).getCost(), cust.get(i).getPaid());

            } catch (IllegalArgumentException e) {
                System.out.println("input error");
            }

        } else {
            System.out.println("Pasutitaju ar tadu Uzvardu nav");
        }
    }

    void deleteCustomer(ArrayList<Customer> cust) throws IOException {
        SqLite_DB sqlite = new SqLite_DB();
        int i = 0;
        boolean found = false;

        System.out.print("Ievadiet pasutitaja Uzvardu: ");
        String surname = format(br.readLine());
        System.out.print("Izvadiet pasutitaja Vardu: ");
        String name = format(br.readLine());

        while (!found && i < cust.size()) {
            if (surname.equalsIgnoreCase(cust.get(i).getSurname()) &&
                    name.equalsIgnoreCase(cust.get(i).getName())) {
                cust.remove(i);
                found = true;
            } else i++;

        }

        if (found)
            sqlite.deleteRow(surname, name);
        else
            System.out.println("Tadu pasutitaju nav");
    }

    private static String format(String a) {
        String temp = a.trim();
        return temp.substring(0, 1).toUpperCase() + temp.substring(1);

    }

    private boolean isOne(ArrayList<Customer> cust, int index) {
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

            sqlite.insertData(i + 1, cust.get(i).getName(), cust.get(i).getSurname(),
                    sim.format(cust.get(i).getDate()), cust.get(i).getCost(), cust.get(i).getPaid());
        }
    }*/

    void allDebtors(ArrayList<Customer> cust) {

        System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "Name", "Surname", "Date", "Cost", "Paid");
        System.out.printf("%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "-------", "--------", "----------", "-----", "-----");
        for (int i = 0; i < cust.size(); i++) {
            if (cust.get(i).getCost() > cust.get(i).getPaid())
                printCustomCustomer(cust, i);
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

