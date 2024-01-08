package DigitalLibraryManagement;

import java.sql.*;
import java.util.Scanner;

public class DigitalLibrary {
    private static final String url = "jdbc:mysql://localhost:3306/digital_library";
    private static final String username = "root";
    private static final String password  = "kankshi";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Customers customers = new Customers(connection,scanner);
            Books books = new Books(connection,scanner);
            System.out.println("DIGITAL LIBRARY MANAGEMENT");
            System.out.println("LOGIN PAGE:");
            System.out.println("1. Customer login:");
            System.out.println("2. Admin login:");
            System.out.print("Enter your choice: ");
            int x = scanner.nextInt();
            if(x==1) {
                while (true) {
                    System.out.println("DIGITAL LIBRARY MANAGEMENT SYSTEM (CUSTOMER)");
                    System.out.println("1. Add Customer");
                    System.out.println("2. View Books");
                    System.out.println("3. Rent Books");
                    System.out.println("4. Exit");
                    System.out.println("ENTER YOUR CHOICE: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            customers.addCustomer();
                            System.out.println();
                            break;
                        case 2:
                            books.viewBooks();
                            System.out.println();
                            break;
                        case 3:
                            rentBook(customers, books, connection, scanner);
                            System.out.println();
                            break;
                        case 4:
                            System.out.println("THANK YOU FOR VISITING US!!");
                            return;
                        default:
                            System.out.println("enter valid choice!!!");
                    }
                }
            } else if (x==2) {
                while (true) {
                    System.out.println("DIGITAL LIBRARY MANAGEMENT SYSTEM (ADMIN)");
                    System.out.println("1. Add Customer");
                    System.out.println("2. View Customers");
                    System.out.println("3. View Books");
                    System.out.println("4. Rent Books");
                    System.out.println("5. Add Books");
                    System.out.println("6. Exit");
                    System.out.println("ENTER YOUR CHOICE: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            customers.addCustomer();
                            System.out.println();
                            break;
                        case 2:
                            customers.viewCustomer();
                            System.out.println();
                            break;
                        case 3:
                            books.viewBooks();
                            System.out.println();
                            break;
                        case 4:
                            rentBook(customers, books, connection, scanner);
                            System.out.println();
                            break;
                        case 5:
                            books.addBooks();
                            System.out.println();
                            break;
                        case 6:
                            System.out.println("SESSION ENDED!!");
                            return;
                        default:
                            System.out.println("enter valid choice!!!");
                    }
                }


            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void rentBook(Customers customers,Books books,Connection connection,Scanner scanner){
        System.out.println("enter customer id: ");
        int customerID = scanner.nextInt();
        System.out.println("enter book id: ");
        int bookID = scanner.nextInt();
        System.out.println("enter book issue date (YYYY-MM-DD): ");
        String issuedate = scanner.next();
        System.out.println("enter book due date (YYYY-MM-DD)");
        String duedate = scanner.next();
        if(customers.getCustomer(customerID)&& books.getBooks(bookID)){
            if(check(bookID,issuedate,duedate,connection)){
                String rent = "INSERT INTO rent(customer_id,book_id,issue_date,due_date)VALUES(?,?,?,?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(rent);
                    preparedStatement.setInt(1,customerID);
                    preparedStatement.setInt(2,bookID);
                    preparedStatement.setString(3,issuedate);
                    preparedStatement.setString(4,duedate);
                    int rows = preparedStatement.executeUpdate();
                    if(rows>0) {
                        System.out.println("==============================");
                        System.out.println("YOUR BOOK HAS BEEN ISSUED!!");
                        String display = "SELECT Book_rent FROM books WHERE Book_id = ?";
                        try {
                            PreparedStatement preparedS = connection.prepareStatement(display);
                            preparedS.setInt(1, bookID);
                            ResultSet resultSet = preparedS.executeQuery();
                            while ((resultSet.next())) {
                                int B_rent = resultSet.getInt("Book_rent");
                                System.out.printf("YOUR BOOK RENT IS : Rs.%d%n", B_rent);
                                System.out.println("==============================");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                            System.out.println("book not available");
                    }

                }
                catch(SQLException e){
                    e.printStackTrace();
                }

            }else{
                System.out.println("book not available");
            }
        }else{
            System.out.println("either book or customer doesn't exist");
        }

    }

    public static boolean check(int bookID, String issuedate,String duedate, Connection connection){
        String query = "SELECT COUNT(*) FROM rent WHERE book_id = ? AND issue_date = ? AND due_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,bookID);
            preparedStatement.setString(2,issuedate);
            preparedStatement.setString(3,duedate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                return count == 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

