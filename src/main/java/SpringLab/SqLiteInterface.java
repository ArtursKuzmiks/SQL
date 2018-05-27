package SpringLab;

import SpringLab.Data.Customer;

import java.util.ArrayList;

/**
 * @author Artur Kuzmik on 18.27.5
 */

public interface SqLiteInterface {

    ArrayList<Customer> getData();

    void insertData(int id, Customer customer);

    void dataFix(int id, Customer customer);

    void remove(String deleteSurname, String deleteName);
}
