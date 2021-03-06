package lab2;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

class SqLite_DB {

    private Connection con;
    private Statement stmt;

    SqLite_DB() {

        try {
            File sqlDBFile = new File("md2_db.db");
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
            final String db_url = "jdbc:sqlite:" + sqlDBFile.getAbsolutePath();
            con = DriverManager.getConnection(db_url);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    ArrayList<Customer> getdata() {

        final String sqlQuery = "SELECT Name,Surname,orderDate,cost,payd FROM md_2DB";

        ArrayList<Customer> customers = new ArrayList<>();

        try {

            stmt = con.createStatement();
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

    void insertData(Integer id, String name, String surname, String date, float cost, float paid) {

        final String INSERT_NEW = "INSERT INTO md_2DB (id,Name,Surname,orderDate," +
                "cost,payd) VALUES ('" + id + "','" + name + "','" + surname + "','" + date + "'," +
                "'" + cost + "','" + paid + "')";

        try {

            stmt = con.createStatement();
            stmt.execute(INSERT_NEW);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    void dataFix(Integer id, String name, String surname, String date, float cost, float paid) {

        final String INSERT_NEW = "REPLACE INTO md_2DB (id,Name,Surname,orderDate," +
                "cost,payd) VALUES ('" + id + "','" + name + "','" + surname + "','" + date + "'," +
                "'" + cost + "','" + paid + "')";

        try {

            stmt = con.createStatement();
            stmt.execute(INSERT_NEW);
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /*public ArrayList<Customer> sortDataSurname(ArrayList<Customer> cust) {
        int count=0;
        final String sortSQL = "SELECT * FROM md_2DB ORDER by orderDate,Surname ASC ";

        try {

            PreparedStatement prst = con.prepareStatement(sortSQL);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                cust.set(count,new Customer().intCustomer(rs.getString("Name"), rs.getString("Surname"),
                        Date.valueOf(rs.getString("orderDate")), rs.getFloat("cost"), rs.getFloat("payd")));
                count++;
            }
            rs.close();
            prst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cust;
    }*/

    void deleteRow(String deleteSurname, String deleteName) {

        final String DELETE_ROW = "DELETE FROM md_2DB WHERE NAME = ? AND Surname = ?";

        try {
            PreparedStatement prst = con.prepareStatement(DELETE_ROW);
            prst.setString(1, deleteName);
            prst.setString(2, deleteSurname);
            prst.executeUpdate();
            prst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*public int getSize() {

        final String sqlsize = "SELECT COUNT (*) AS ROWCOUNT FROM md_2DB";
        int count = 0;

        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlsize);
            rs.next();
            count = rs.getInt("ROWCOUNT");
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }*/

}
