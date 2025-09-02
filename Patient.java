package HospitalManagementSystem;
import java.sql.*;
import java.util.*;

public class Patient {
	private Connection con;
	private Scanner sc;
	
	public Patient(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}
	
	public void addPatient() {
		sc.nextLine();
		System.out.println("Enter patients' name: ");
		String name = sc.nextLine();
		System.out.println("Enter patients' age: ");
		int age = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter patients' gender: ");
		String gender = sc.nextLine();
		
		try {
			String query = "insert into patients (name, age, gender) values (?, ?, ?);";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);
			
			int rowsAffected = ps.executeUpdate();
			if(rowsAffected > 0) {
				System.out.println("Data inserted successfully...");
				System.out.println(rowsAffected + " row(s) affected...");
			}else {
				System.out.println("Insertion failed....");
			}
		}catch(Exception e) {
			
		}
		
	}
	
	public void viewPatients() {
		String query = "select * from patients;";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+--------+----------------------+---------+----------------+");
			System.out.println("|   ID   |          Name        |   Age   |      Gender    |");
			System.out.println("+--------+----------------------+---------+----------------+");
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				
				System.out.printf("|%-8s|%-22s|%-9s|%-16s|\n", id, name, age, gender);
				System.out.println("+--------+----------------------+---------+----------------+");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean getPatientByID(int id) {
		String query = "select * from patients where id = ?;";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}else {
				return false; 
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
}
