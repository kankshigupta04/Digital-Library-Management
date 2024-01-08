package DigitalLibraryManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Customers {
    private Connection connection;
    private Scanner scanner;

    public Customers(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addCustomer(){
        //System.out.println("enter customer id: ");
        //int id = scanner.nextInt();
        System.out.println("enter customer name");
        String name = scanner.next();
        try{
            String query = "INSERT INTO customers(Cus_name) VALUES(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("customer added successfully");
            }else{
                System.out.println("failed to add customer!");
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
        //System.out.println("displaying customer id");
        String display = "SELECT customer_id FROM customers WHERE Cus_name = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(display);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while((resultSet.next())){
                int id = resultSet.getInt("customer_id");
                System.out.printf("Your customer id is: %d%n",id);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }



    public void viewCustomer(){
        String query = "select * from customers";
        try{
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("CUSTOMERS:");
            System.out.println("+-------------+---------------+");
            System.out.println("| Customer id | Customer name |");
            System.out.println("+-------------+---------------+");
            while ((resultSet.next())){
                int id = resultSet.getInt("customer_id");
                String name = resultSet.getString("Cus_name");
                System.out.printf("|%-13s|%-15s|\n",id,name);
                System.out.println("+-------------+---------------+");

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getCustomer(int id){
        String query = "SELECT * FROM customers WHERE customer_id=?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return  false;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }


}
