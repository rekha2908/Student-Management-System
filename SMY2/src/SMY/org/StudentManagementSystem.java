package SMY.org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class StudentManagementSystem {
	
	static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
	static final String USER = "root";
	static final String PASS = "Sivatharni@13";
	

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("create table students(id int, name varchar(30), age int)");
			System.out.println("Table created successfully");
			conn.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Scanner scanner = new Scanner(System.in)) {
			
			
			while (true) {
				System.out.println("\n--- Student Management System ----");
				System.out.println("1. Add Student");
				System.out.println("2. View Students");
				System.out.println("3. Update Student");
				System.out.println("4. Delete Student");
				System.out.println("5. Exit");
				System.out.println("Choose an option: ");
				int choice = scanner.nextInt();
				scanner.nextLine();
				
				switch (choice) {
				case 1:
					//System.out.println("Enter Student ID: ");
					//int id = scanner.nextInt();
					System.out.println("Enter Student Name: ");
					String name = scanner.nextLine();
					System.out.println("Enter Age: ");
					int age = scanner.nextInt();
					addStudent(conn, name, age);
					break;
				case 2:
					viewStudents(conn);
					break;
				case 3:
					System.out.println("Enter Students ID to Update: ");
					int id = scanner.nextInt();
					scanner.nextLine();
					System.out.println("Enter New Name: ");
					String newName = scanner.nextLine();
					System.out.println("Enter New Age: ");
					int newAge = scanner.nextInt();
					updateStudent(conn, id, newName, newAge);
					break;
				case 4:
					System.out.println("Enter Students ID to Delete: ");
					int deleteId = scanner.nextInt();
					deleteStudent(conn, deleteId);
					break;
				case 5:
					System.out.println("Exiting...Thank You!");
					System.exit(0);
					break;
					default:
						System.out.println("Invalid Option! Please try again.");
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
				

	}
	public static void addStudent(Connection conn, String name, int age)
	throws SQLException{
		String query = "INSERT INTO students (name, age) VALUES (?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1,  name);
			pstmt.setInt(2, age);
			pstmt.executeUpdate();
			System.out.println("Student Added Successfully!");
		}
		
	}
	
	public static void viewStudents(Connection conn)
			throws SQLException{
		String query = "SELECT * FROM students";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)){
			System.out.println("ID | Name | Age");
			System.out.println("-----------------");
			while(rs.next()) {
				System.out.printf("%d | %s | %d%n", rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
			}
		}
	}
	public static void updateStudent(Connection conn, int id, String name, int age )
	throws SQLException {
		String query = "UPDATE students SET name = ?, age = ? WHERE id = ?";
		try(PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1,  name);
			pstmt.setInt(2, age);
			pstmt.setInt(3, id);
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Student Updated Successfully");
							}
			else {
				System.out.println("Student Not Found!");
			}
		}
	}
	public static void deleteStudent(Connection conn, int id)
			throws SQLException {
				String query = "DELETE FROM students WHERE id = ?";
				try(PreparedStatement pstmt = conn.prepareStatement(query)) {
					pstmt.setInt(1, id);
					
					int rowsDeleted = pstmt.executeUpdate();
					if (rowsDeleted > 0) {
						System.out.println("Student Deleted Successfully");
									}
					else {
						System.out.println("Student Not Found!");
					}
				}
	}
}
	


