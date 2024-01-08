package DigitalLibraryManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Books {
    private Connection connection;
    private Scanner scanner;

    public Books(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addBooks(){
        System.out.println("enter book name:(firstname_lastname) ");
        String name = scanner.next();
        System.out.println("enter genre of the book: ");
        String genre = scanner.next();
        System.out.println("enter rent of the book: ");
        int rent = scanner.nextInt();

        try{
            String query = "INSERT INTO books(Book_name,Category,Book_rent) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,genre);
            preparedStatement.setInt(3,rent);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("book added successfully");
            }else{
                System.out.println("failed to add book!");
            }

        }catch(SQLException e){
            e.printStackTrace();

        }
    }
    public void viewBooks(){
        String query = "select * from books";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("BOOKS:");
            System.out.println("+---------+-------------+----------+-----------+");
            System.out.println("| Book id |  Book name  | Category | Book rent |");
            System.out.println("+---------+-------------+----------+-----------+");
            while ((resultSet.next())){
                int id = resultSet.getInt("Book_id");
                String name = resultSet.getString("Book_name");
                String category = resultSet.getString("Category");
                int rent = resultSet.getInt("Book_rent");
                System.out.printf("|%-9s|%-13s|%-10s|%-11s|\n",id,name,category,rent);
                System.out.println("+---------+-------------+----------+-----------+");

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getBooks(int id){
        String query = "SELECT * FROM books WHERE book_id=?";
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
