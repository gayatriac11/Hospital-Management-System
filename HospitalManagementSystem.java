package HospitalManagementSystem;
import java.sql.*;
import java.util.*;

public class HospitalManagementSystem {
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username = "root";
	private static final String password = "Gayu@4002";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Drivers loaded successfully...");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		Scanner sc = new Scanner(System.in);
		
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			Patient p = new Patient(con, sc);
			Doctor d = new Doctor(con);
			
			while(true) {
				System.out.println("WELCOME TO HOSPITAL MANAGEMENT SYSTEM ");
				System.out.println();
				System.out.println("1. Add Patients");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointments");
				System.out.println("5. Exit");
				System.out.println("Enter your choice: ");
				int choice = sc.nextInt();
				switch(choice) {
				case 1:
					//Add Patients
					p.addPatient();
					System.out.println();
					break;
					
				case 2:
					//View Patients
					p.viewPatients();
					System.out.println();
					break;
				case 3:
					//View Doctors
					d.viewDoctors();
					System.out.println();
					break;
				case 4:
					//Book Appointments
					bookAppointment(con, sc, p, d);
					System.out.println();
					break;
				case 5:
					System.out.println("THANK YOU FOR VISITING HOSPITAL MANAGEMENT SYSTEM");
					return;
				default: System.out.println("Enter valid choice!!!");	
						
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void bookAppointment(Connection con, Scanner sc, Patient p, Doctor d) {
		System.out.println("Enter patient id: ");
		int patientID = sc.nextInt();
		System.out.println("Enter doctor id: ");
		int doctorID = sc.nextInt();
		System.out.println("Enter appointment date (YYYY-MM-DD): ");
		String date = sc.next();
		
		if(p.getPatientByID(patientID) && d.getDoctorByID(doctorID)) {
			if(checkDoctorAvailability(doctorID, date, con)) {
				String query = "insert into appointments (patient_id, doctor_id, appointment_date) values (?, ?, ?);";
				try {
					PreparedStatement ps = con.prepareStatement(query);
					ps.setInt(1, patientID);
					ps.setInt(2, doctorID);
					ps.setString(3, date);
					
					int rowsAffected = ps.executeUpdate();
					if(rowsAffected > 0) {
						System.out.println("Appintment booked successfully!!!");
					}else {
						System.out.println("Appointment booking failed!!!");
					}
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}else {
				System.out.println("Doctor not available on this date!!!");
			}
		}else {
			System.out.println("Patient and Doctor doesn't exist!!!");
		}
	}
	
	public static boolean checkDoctorAvailability(int doctorID, String date, Connection con) {
		String query = "select count(*) from appointments where doctor_id = ? and appointment_date = ?;";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, doctorID);
			ps.setString(2, date);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				if(count == 0) {
					return true;
				}else {
					return false;
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
}
