import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Appointment {
    private String patientName;
    private String doctorName;
    private String appointmentTime;

    public Appointment(String patientName, String doctorName, String appointmentTime) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentTime = appointmentTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                '}';
    }
}

class HospitalManager {
    private List<Appointment> appointments = new ArrayList<>();

    public synchronized void bookAppointment(String patientName, String doctorName, String appointmentTime) {
        Appointment appointment = new Appointment(patientName, doctorName, appointmentTime);
        appointments.add(appointment);
        System.out.println("Appointment booked: " + appointment);
    }

    public synchronized void viewAppointments() {
        System.out.println("\n--- Appointments ---");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }
}

class UserThread extends Thread {
    private HospitalManager hospitalManager;

    public UserThread(HospitalManager hospitalManager) {
        this.hospitalManager = hospitalManager;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Book Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Exit");

            System.out.print("Enter your choice (1-3): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter patient name: ");
                    String patientName = scanner.next();
                    System.out.print("Enter doctor name: ");
                    String doctorName = scanner.next();
                    hospitalManager.bookAppointment(patientName, doctorName, "10:00 AM");
                    break;
                case 2:
                    hospitalManager.viewAppointments();
                    break;
                case 3:
                    System.out.println("Exiting Hospital Management System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }
}

public class HospitalManagementSystem {
    public static void main(String[] args) {
        HospitalManager hospitalManager = new HospitalManager();

        // Create user thread
        UserThread userThread = new UserThread(hospitalManager);

        // Start user thread
        userThread.start();

        try {
            // Wait for user thread to complete
            userThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
