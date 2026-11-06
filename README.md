# Job Application Tracker

A simple console application in Java to keep track of job applications during a job hunt.

## Features
- Add a new application (company, role, date, status)
- View all applications
- Update the status (Applied / Interview / Rejected / Offer)
- Delete an application
- "Follow up today" list for applications that need a follow-up
- Saves data to a local file so nothing is lost when the program closes

## Tech Used
- Java
- Collections (ArrayList), File Handling, `java.time.LocalDate`
- IntelliJ IDEA, Git and GitHub

## How to Run
1. Clone this repository
2. Open it in IntelliJ IDEA
3. Run `Main.java`

## Sample Output
```
1 | Infosys | Software Tester | 2026-11-05 | Applied | follow up: 2026-11-12
```

## Future Plans
- Connect to a database using JDBC
- Build a web version with JSP and Servlets