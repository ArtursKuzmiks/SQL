package SpringLab.Data;

import SpringLab.SqLiteInterface;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Artur Kuzmik on 18.27.5
 */

@Component
public class SqLite_DB implements SqLiteInterface {

    private Connection con;


    SqLite_DB() {

        try {

            File sqlDBFile = new File(new File(".").getAbsolutePath() +
                    "/src/main/resources/md2_db.db");
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            final String db_url = "jdbc:sqlite:" + sqlDBFile.getAbsolutePath();
            con = DriverManager.getConnection(db_url);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<Customer> getData() {

        final String sqlQuery = "SELECT Name,Surname,orderDate,cost,payd FROM md_2DB";

        ArrayList<Customer> customers = new ArrayList<>();

        try {

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            if (rs.isBeforeFirst()) {

                while (rs.next()) {
                    String name = rs.getString("Name");
                    String surname = rs.getString("Surname");
                    Date date = Date.valueOf(rs.getString("orderDate"));
                    float cost = rs.getFloat("cost");
                    float paid = rs.getFloat("payd");

                    customers.add(new Customer(name, surname, date, cost, paid));

                }
            } else {
                System.out.println("SQL DB is empty");
                rs.close();
                stmt.close();
                return null;
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    @Override
    public void insertData(int id, Customer customer) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        final String INSERT_NEW = "INSERT INTO md_2DB (id,Name,Surname,orderDate," +
                "cost,payd) VALUES (?,?,?,?,?,?)";

        try {

            PreparedStatement pst = con.prepareStatement(INSERT_NEW);

            pst.setString(1, String.valueOf(id));
            pst.setString(2, customer.getName());
            pst.setString(3, customer.getSurname());
            pst.setString(4, sim.format(customer.getDate()));
            pst.setString(5, String.valueOf(customer.getCost()));
            pst.setString(6, String.valueOf(customer.getPaid()));

            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dataFix(int id, Customer customer) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        final String REPLACE = "REPLACE INTO md_2DB (id,Name,Surname,orderDate," +
                "cost,payd) VALUES(?,?,?,?,?,?) ";

        try {
            PreparedStatement pst = con.prepareStatement(REPLACE);

            pst.setString(1, String.valueOf(id));
            pst.setString(2, customer.getName());
            pst.setString(3, customer.getSurname());
            pst.setString(4, sim.format(customer.getDate()));
            pst.setString(5, String.valueOf(customer.getCost()));
            pst.setString(6, String.valueOf(customer.getPaid()));

            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(String deleteSurname, String deleteName) {

        final String DELETE_ROW = "DELETE FROM md_2DB WHERE NAME = ? AND Surname = ?";

        try {
            PreparedStatement pst = con.prepareStatement(DELETE_ROW);

            pst.setString(1, deleteName);
            pst.setString(2, deleteSurname);

            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
