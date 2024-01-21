package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password= "Vishal@123";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);

            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1 . Add patient ");
                System.out.println("2 . view patients ");
                System.out.println("3 . view Doctors ");
                System.out.println("4 . Book Appointment ");
                System.out.println("5 . View Appointment");
                System.out.println("6 . Exit ");
                System.out.println("Enter your choice");

                int choice = scanner.nextInt();

                switch(choice){
                    case 1:
                        patient.addpatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewpatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctor();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        viewAppointments(connection, scanner);
                        System.out.println();
                    break;
                    case 6:
                        return;
                    default:
                        System.out.println("Enter valid choice!!!");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection,Scanner scanner){
        System.out.println("Enter Patient id:");
        int patientId = scanner.nextInt();

        System.out.println("Enter Doctor id:");
        int doctorId = scanner.nextInt();

        System.out.println("Enter appointment date (YYYY-MM-DD)");
        String appointmentDate = scanner.next();

        if(patient.getPatientById(patientId) && (doctor.getDoctorById(doctorId))){
            if(checkDoctorAvilability(doctorId,appointmentDate,connection)){
                String appointmentquery = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";

                        try{
                            PreparedStatement preparedStatement = connection.prepareStatement(appointmentquery);
                            preparedStatement.setInt(1,patientId);
                            preparedStatement.setInt(2,doctorId);
                            preparedStatement.setString(3,appointmentDate);

                            int rowsAffected = preparedStatement.executeUpdate();
                            if(rowsAffected>0){
                                System.out.println("Appointment Booked!");
                            }else{
                                System.out.println("Failed to book appointment");
                            }
                        }catch(SQLException e){
                            e.printStackTrace();
                        }
            }

        }else{
            System.out.println("Either doctor or patient doesn't exit!!!");
        }
    }
    public static boolean checkDoctorAvilability(int doctorId,String appointmentDate,Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int count = resultSet.getInt(1);

                if(count == 0){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static void viewAppointments(Connection connection, Scanner scanner) {
        String query = "SELECT * FROM appointments";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Appointments:");
            System.out.println("+-----------------+------------+------------+-------------------|");
            System.out.println("| Appointment Id  | Patient Id | Doctor Id  | Appointment Date  |");
            System.out.println("+-----------------+------------+------------+-------------------|");

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("id");
                int patientId = resultSet.getInt("patient_id");
                int doctorId = resultSet.getInt("doctor_id");
                String appointmentDate = resultSet.getString("appointment_date");

                System.out.printf("|%-17d|%-12d|%-12d|%-19s|\n", appointmentId, patientId, doctorId, appointmentDate);
                System.out.println("+-----------------+------------+------------+-------------------|");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
