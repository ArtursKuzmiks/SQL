package lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        SqLite_DB sqlite = new SqLite_DB();
        Customer customer = new Customer();
        ArrayList<Customer> cust = sqlite.getdata();

        int menu;

        System.out.println("Arturs Kuzmiks 12.gr. 111REB779");

        System.out.println("Menu");
        System.out.println("Apskatit tabulu                                   :" + ANSI_RED + "1" + ANSI_RESET);
        System.out.println("Pievienot pasutijumu                              :" + ANSI_RED + "2" + ANSI_RESET);
        System.out.println("Izslegt pasutitaju                                :" + ANSI_RED + "3" + ANSI_RESET);
        System.out.println("Koriget pasutitaja datus                          :" + ANSI_RED + "4" + ANSI_RESET);
        System.out.println("Sakartot info faila pec datuma un pirceja uzvarda :" + ANSI_RED + "5" + ANSI_RESET);
        System.out.println("Izvadit pircejus kuri nepilnigi samaksaja         :" + ANSI_RED + "6" + ANSI_RESET);
        System.out.println("Visu pasutijumu cenu summa                        :" + ANSI_RED + "7" + ANSI_RESET);
        System.out.println("Pabeigt darbu                                     :" + ANSI_RED + "0" + ANSI_RESET);

        try {

            loop:
            for (; ; ) {

                System.out.print("\u001B[36mIevaddati: \u001B[0m");
                menu = Integer.parseInt(br.readLine());

                switch (menu) {
                    case 1:

                        customer.printCustomers(cust);
                        break;
                    case 2:
                        insertdata(cust);
                        customer.printCustomers(cust);
                        break;
                    case 3:
                        deleteCustomer(cust);
                        customer.printCustomers(cust);
                        break;
                    case 4:
                        dataFix(cust);
                        break;
                    case 5:
                        customer.sortDateSurname(cust);
                        customer.printCustomers(cust);
                        break;
                    case 6:
                        assert cust != null;
                        customer.allDeptors(cust);
                        break;
                    case 7:
                        assert cust != null;
                        customer.allCost(cust);
                        break;
                    case 0:
                        System.out.println("Darbs ir pabeigts");
                        break loop;
                    default:
                        System.out.print("Nav tadu variantu, meiginet vel reiz:");
                        customer.printCustomers(cust);
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }
    }

    private static void insertdata(ArrayList<Customer> cust) throws IOException {

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


            sqlite.insertdata(cust.size() + 1, name, surname, date, cost, paid);
            cust.add(new Customer().intCustomer(name, surname, data, cost, paid));


        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }
    }

    private static void dataFix(ArrayList<Customer> cust) throws IOException {

        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        SqLite_DB sqlite = new SqLite_DB();
        Customer customer = new Customer();

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

        if (found && !customer.isOne(cust, i)) {
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
            customer.printCustomCustomer(cust, i);
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

                sqlite.insertdata(i + 1, cust.get(i).getName(), cust.get(i).getSurname(),
                        sim.format(cust.get(i).getDate()), cust.get(i).getCost(), cust.get(i).getPaid());

            } catch (IllegalArgumentException e) {
                System.out.println("input error");
            }

        } else {
            System.out.println("Pasutitaju ar tadu Uzvardu nav");
        }
    }

    private static void deleteCustomer(ArrayList<Customer> cust) throws IOException {
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

}
