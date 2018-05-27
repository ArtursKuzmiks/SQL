package SpringLab.Data;

import SpringLab.CompanyInterface;
import SpringLab.SqLiteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Artur Kuzmik on 18.27.5
 */

@Component
public class Application {

    private SqLiteInterface sqLite_db;
    private CompanyInterface customer;

    @Autowired
    public Application(SqLiteInterface sqLite, CompanyInterface customer) {
        this.sqLite_db = sqLite;
        this.customer = customer;
    }

    public void run() throws IOException {


        ArrayList<Customer> list = sqLite_db.getData();

        int menu;

        System.out.println("Arturs Kuzmiks 12.gr. 111REB779");

        System.out.println("Menu");
        System.out.println("Apskatit tabulu                                   : 1");
        System.out.println("Pievienot pasutijumu                              : 2");
        System.out.println("Izslegt pasutitaju                                : 3");
        System.out.println("Koriget pasutitaja datus                          : 4");
        System.out.println("Sakartot info faila pec datuma un pirceja uzvarda : 5");
        System.out.println("Izvadit pircejus kuri nepilnigi samaksaja         : 6");
        System.out.println("Visu pasutijumu cenu summa                        : 7");
        System.out.println("Pabeigt darbu                                     : 0");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            loop:
            for (; ; ) {

                System.out.print("\u001B[36mIevaddati: \u001B[0m");
                menu = Integer.parseInt(br.readLine());

                switch (menu) {
                    case 1:

                        customer.printCustomers(list);
                        break;
                    case 2:
                        customer.insertData(list);
                        customer.printCustomers(list);
                        break;
                    case 3:
                        customer.deleteCustomer(list);
                        customer.printCustomers(list);
                        break;
                    case 4:
                        customer.dataFix(list);
                        break;
                    case 5:
                        customer.sortDateSurname(list);
                        customer.printCustomers(list);
                        break;
                    case 6:
                        customer.allDebtors(list);
                        break;
                    case 7:
                        customer.allCost(list);
                        break;
                    case 0:
                        System.out.println("Darbs ir pabeigts");
                        break loop;
                    default:
                        System.out.print("Nav tadu variantu, meiginet vel reiz:");
                        customer.printCustomers(list);
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }
    }
}
