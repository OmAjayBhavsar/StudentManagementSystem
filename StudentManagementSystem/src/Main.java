import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        int[] nextID = {1};

        loadStudentsFromFile(students, nextID);

        boolean running = true;

        while (running) {
            System.out.println("\n--- Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter your choice (1-6): ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter student email: ");
                    String email = scanner.nextLine();

                    Student newStudent = new Student(nextID[0], name, email);
                    students.add(newStudent);


                    saveStudentsToFile(students);


                    System.out.print("Student added successfully with ID: " + nextID[0]);
                    nextID[0]++;
                    break;

                case 2:
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        System.out.println("+----+----------------------+------------------------------+");
                        System.out.printf("| %-2s | %-20s | %-28s |%n", "ID", "Name", "Email");
                        System.out.println("+----+----------------------+------------------------------+");

                        for (Student student : students) {
                            System.out.printf("| %-2d | %-20s | %-28s |%n",
                                    student.getId(),
                                    student.getName(),
                                    student.getEmail());
                        }

                        System.out.println("+----+----------------------+------------------------------+");
                    }
                    break;


                case 3:
                    System.out.print("Enter Student ID to search: ");
                    int searchID = scanner.nextInt();
                    scanner.nextLine();

                    boolean found = false;

                    for (Student student : students) {
                        if (student.getId() == searchID) {
                            System.out.println("Student Found: ");
                            student.displayInfo();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Student with ID " + searchID + " not found.");
                    }
                    break;

                case 4:
                    System.out.println("Enter Student ID to update");
                    int updateID = scanner.nextInt();
                    scanner.nextLine();

                    boolean updated = false;

                    for (Student student : students) {
                        if (student.getId() == updateID) {
                            System.out.print("Enter new name: ");
                            String newName = scanner.nextLine();

                            System.out.print("Enter new email: ");
                            String newEmail = scanner.nextLine();

                            student.setName(newName);
                            student.setEmail(newEmail);

                            saveStudentsToFile(students);

                            System.out.println("Student updated successfully.");
                            updated = true;
                            break;
                        }
                    }

                    if (!updated) {
                        System.out.println("Student with ID " + updateID + " not found.");
                    }
                    break;

                case 5:
                    System.out.println("Delete Students selected.");
                    int deleteID = scanner.nextInt();
                    scanner.nextLine();

                    boolean deleted = false;

                    for (int i = 0; i < students.size(); i++) {
                        if (students.get(i).getId() == deleteID) {
                            students.remove(i);

                            saveStudentsToFile(students);

                            System.out.println("Student with ID " + deleteID + " has been deleted.");
                            deleted = true;
                            break;
                        }
                    }

                    if (!deleted) {
                        System.out.println("Student with ID " + deleteID + " not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }

        scanner.close();
    }

    public static void saveStudentsToFile(ArrayList<Student> students) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("students.txt");

            writer.write("+----+----------------------+------------------------------+\n");
            writer.write(String.format("| %-2s | %-20s | %-28s |%n", "ID", "Name", "Email"));
            writer.write("+----+----------------------+------------------------------+\n");

            for (Student student : students) {
                writer.write(String.format("| %-2d | %-20s | %-28s |%n",
                        student.getId(),
                        student.getName(),
                        student.getEmail()));
            }

            writer.write("+----+----------------------+------------------------------+\n");

            writer.close();
            System.out.println("Data saved successfully in table format.");
        } catch (Exception e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }

    public static void loadStudentsFromFile(ArrayList<Student> students, int[] nextIdRef) {
        try {
            java.io.File file = new java.io.File("students.txt");
            if (!file.exists()) return;

            java.util.Scanner fileScanner = new java.util.Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    students.add(new Student(id, name, email));

                    if (id >= nextIdRef[0]) {
                        nextIdRef[0] = id + 1;
                    }
                }
            }

            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }
    }

}

