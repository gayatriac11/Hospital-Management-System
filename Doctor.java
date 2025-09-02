package HospitalManagementSystem;
import java.sql.*;

public class Doctor {
	private Connection con;
	
	public Doctor(Connection con) {
		this.con = con;
	}
	
	public void viewDoctors() {
		String query = "select * from doctors;";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			System.out.println("Doctorss: ");
			System.out.println("+--------+----------------------+-------------------------------+");
			System.out.println("|   ID   |          Name        |         Specialization        |");
			System.out.println("+--------+----------------------+-------------------------------+");
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String specialization = rs.getString("specialization");
				
				System.out.printf("|%-8s|%-22s|%-31s|\n", id, name, specialization);
				System.out.println("+--------+----------------------+-------------------------------+");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean getDoctorByID(int id) {
		String query = "select * from doctors where id = ?;";
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
