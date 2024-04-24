package org.example;

import org.example.models.Employee;
import org.example.models.Job;
import org.example.service.EmployeeService;
import org.example.service.JobService;
import org.example.service.serviceImpl.EmployeeServiceImpl;
import org.example.service.serviceImpl.JobServiceImpl;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        JobService jobService = new JobServiceImpl();
        EmployeeService employeeService = new EmployeeServiceImpl();
        Employee employee = new Employee();
        boolean exit = true;
        while (exit) {
            System.out.println("""
                      ┌──────────────────────┬─────────────────────┐
                      │         Job          │       Employee      │
                      ├──────────────────────┼─────────────────────┤
                      │ 1. createJobTable    │ 7. createEmployee   │
                      │ 2. addJob            │ 8. addEmployee      │
                      │ 3. getJobById        │ 9. dropTable        │
                      │ 4. sortByExperience  │ 10. cleanTable      │
                      │ 5. getJobByEmployeeId│ 11. updateEmployee  │
                      │ 6. deleteDescription │ 12. getAllEmployees │
                      ├──────────────────────┼─────────────────────┤
                      │ 6.Column             │ 13. findByEmail     │
                      │                      │ 14. getEmployeeById │
                      │                      │ 15. getEmployeeByPos│
                      └──────────────────────┴─────────────────────┘
                    """);
            switch (new Scanner(System.in).nextInt()) {
                case 1 -> jobService.createJobTable();
                case 2 -> jobService.addJob(new Job("Mentor,Management,Instructor", "Java,JavaScript,Backend developer", "Fronted developer", 1));
                case 3 -> System.out.println(jobService.getJobById(1L));
                case 4 -> jobService.sortByExperience("asc");
                case 5 -> System.out.println(jobService.getJobByEmployeeId(1L));
                case 6 -> jobService.deleteDescriptionColumn();
                case 7 -> employeeService.createEmployee();
                case 8 -> employeeService.addEmployee(new Employee(1L, "Yzaat", "Kadyrkulov", 18, "yzaat@gmail.com", 1));
                case 9 -> employeeService.dropTable();
                case 10 -> employeeService.cleanTable();
                case 11 -> employeeService.updateEmployee(1L, employee);
                case 12 -> employeeService.getAllEmployees();
                case 13 -> employeeService.findByEmail("yzaat@gmail.com");
                case 14 -> employeeService.getEmployeeById(1L);
                case 15 -> employeeService.getEmployeeByPosition("Mentor");
                case 16 -> exit = false;

            }
        }
    }
}