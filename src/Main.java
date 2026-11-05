import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<JobApplication> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int nextId = 1;

        while (true) {
            System.out.println("\n1. Add application\n2. View all\n0. Exit");
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
                for (JobApplication a : list) System.out.println(a);
            } else if (choice == 0) {
                break;
            }
        }
    }
}