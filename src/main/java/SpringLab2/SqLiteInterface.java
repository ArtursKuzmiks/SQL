package SpringLab2;

import SpringLab2.Data.Customer;

import java.util.ArrayList;

/**
 * @author Artur Kuzmik on 18.27.5
 */

public interface SqLiteInterface {

    ArrayList<Customer> getData();

    void insertData(Integer id, String name, String surname, String date, float cost, float paid);

    void dataFix(Integer id, String name, String surname, String date, float cost, float paid);

    void remove(String deleteSurname, String deleteName);
}
