import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<JobApplication> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int nextId = 1;

        while (true) {
            System.out.println("\n1. Add application\n2. View all\n3. Update status\n4. Delete application\n0. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                System.out.print("Company: ");
                String company = sc.nextLine();
                System.out.print("Role: ");
                String role = sc.nextLine();
                list.add(new JobApplication(nextId++, company, role,
                        LocalDate.now(), "Applied", LocalDate.now().plusDays(7)));
                System.out.println("Added!");

            } else if (choice == 2) {
                if (list.isEmpty()) System.out.println("No applications yet.");
                for (JobApplication a : list) System.out.println(a);

            } else if (choice == 3) {
                System.out.print("Enter application ID: ");
                int id = Integer.parseInt(sc.nextLine());
                boolean found = false;
                for (JobApplication a : list) {
                    if (a.getId() == id) {
                        System.out.print("New status (Applied/Interview/Rejected/Offer): ");
                        a.setStatus(sc.nextLine());
                        System.out.println("Status updated!");
                        found = true;
                        break;
                    }
                }
                if (!found) System.out.println("ID not found.");

            } else if (choice == 4) {
                System.out.print("Enter application ID to delete: ");
                int id = Integer.parseInt(sc.nextLine());
                boolean removed = list.removeIf(a -> a.getId() == id);
                System.out.println(removed ? "Deleted!" : "ID not found.");

            } else if (choice == 0) {
                break;
            }
        }
    }
}