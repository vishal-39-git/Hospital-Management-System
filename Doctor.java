package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctor() {
        String query = "Select * from doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();

            System.out.println("doctors:");
            System.out.println("+------------+---------------+------------------------+");
            System.out.println("| doctor Id | Name          | specialization          |");
            System.out.println("+------------+---------------+------------------------+");

            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String specialization = resultset.getString("specialization");

                System.out.printf("|%-12d|%-15s|%-18s\n", id, name, specialization);
                System.out.println("+------------+---------------+------------------------+");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultset = preparedStatement.executeQuery();

            if (resultset.next()) {
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}