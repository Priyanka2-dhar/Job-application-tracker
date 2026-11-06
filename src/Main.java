import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static final String FILE_NAME = "applications.txt";

    static void saveToFile(ArrayList<JobApplication> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (JobApplication a : list) {
                pw.println(a.getId() + "|" + a.getCompany() + "|" + a.getRole() + "|"
                        + a.getAppliedDate() + "|" + a.getStatus() + "|" + a.getFollowUpDate());
            }
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    static ArrayList<JobApplication> loadFromFile() {
        ArrayList<JobApplication> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;
        try (Scanner fs = new Scanner(file)) {
            while (fs.hasNextLine()) {
                String line = fs.nextLine();
                if (line.isBlank()) continue;
                String[] p = line.split("\\|");
                list.add(new JobApplication(Integer.parseInt(p[0]), p[1], p[2],
                        LocalDate.parse(p[3]), p[4], LocalDate.parse(p[5])));
            }
        } catch (IOException e) {
            System.out.println("Could not load: " + e.getMessage());
        }
        return list;
    }

    // Keeps asking until the user types a valid number
    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Keeps asking until the user types something (and removes "|" so the file format stays safe)
    static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = sc.nextLine().trim().replace("|", "-");
            if (!text.isEmpty()) return text;
            System.out.println("This cannot be empty.");
        }
    }

    // Only accepts one of the four allowed statuses
    static String readStatus(Scanner sc) {
        String[] valid = {"Applied", "Interview", "Rejected", "Offer"};
        while (true) {
            System.out.print("New status (Applied/Interview/Rejected/Offer): ");
            String input = sc.nextLine().trim();
            for (String s : valid) {
                if (s.equalsIgnoreCase(input)) return s;
            }
            System.out.println("Invalid status. Please choose one of the four options.");
        }
    }

    public static void main(String[] args) {
        ArrayList<JobApplication> list = loadFromFile();
        Scanner sc = new Scanner(System.in);
        int nextId = 1;
        for (JobApplication a : list) {
            if (a.getId() >= nextId) nextId = a.getId() + 1;
        }

        while (true) {
            System.out.println("\n1. Add application\n2. View all\n3. Update status\n4. Delete application\n5. Follow up today\n0. Exit");
            int choice = readInt(sc, "Choose: ");

            if (choice == 1) {
                String company = readNonEmpty(sc, "Company: ");
                String role = readNonEmpty(sc, "Role: ");
                list.add(new JobApplication(nextId++, company, role,
                        LocalDate.now(), "Applied", LocalDate.now().plusDays(7)));
                saveToFile(list);
                System.out.println("Added!");

            } else if (choice == 2) {
                if (list.isEmpty()) System.out.println("No applications yet.");
                for (JobApplication a : list) System.out.println(a);

            } else if (choice == 3) {
                int id = readInt(sc, "Enter application ID: ");
                boolean found = false;
                for (JobApplication a : list) {
                    if (a.getId() == id) {
                        a.setStatus(readStatus(sc));
                        saveToFile(list);
                        System.out.println("Status updated!");
                        found = true;
                        break;
                    }
                }
                if (!found) System.out.println("ID not found.");

            } else if (choice == 4) {
                int id = readInt(sc, "Enter application ID to delete: ");
                boolean removed = list.removeIf(a -> a.getId() == id);
                if (removed) saveToFile(list);
                System.out.println(removed ? "Deleted!" : "ID not found.");

            } else if (choice == 5) {
                LocalDate today = LocalDate.now();
                boolean any = false;
                for (JobApplication a : list) {
                    boolean isOpen = a.getStatus().equalsIgnoreCase("Applied")
                            || a.getStatus().equalsIgnoreCase("Interview");
                    boolean isDue = !a.getFollowUpDate().isAfter(today);
                    if (isOpen && isDue) {
                        System.out.println(a);
                        any = true;
                    }
                }
                if (!any) System.out.println("Nothing to follow up today!");

            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Please choose a number from the menu.");
            }
        }
    }
}