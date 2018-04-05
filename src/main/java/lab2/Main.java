package lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {



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

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            loop:
            for (; ; ) {

                System.out.print("\u001B[36mIevaddati: \u001B[0m");
                menu = Integer.parseInt(br.readLine());

                switch (menu) {
                    case 1:

                        customer.printCustomers(cust);
                        break;
                    case 2:
                        customer.insertData(cust);
                        customer.printCustomers(cust);
                        break;
                    case 3:
                        customer.deleteCustomer(cust);
                        customer.printCustomers(cust);
                        break;
                    case 4:
                        customer.dataFix(cust);
                        break;
                    case 5:
                        customer.sortDateSurname(cust);
                        customer.printCustomers(cust);
                        break;
                    case 6:
                        customer.allDebtors(cust);
                        break;
                    case 7:
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

}
