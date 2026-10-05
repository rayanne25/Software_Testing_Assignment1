package com.auca;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementSystem {

    public static class Employee {
        private String id;
        private String name;
        private double baseSalary;

        public Employee(String id, String name, double baseSalary) {
            this.id = id;
            this.name = name;
            this.baseSalary = baseSalary;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public double getBaseSalary() { return baseSalary; }
    }

    private List<Employee> employees = new ArrayList<>();

    // FR-01: Employee Management
    public boolean addEmployee(String id, String name, double baseSalary) {
        if (id == null || id.isEmpty() || baseSalary < 0) {
            return false;
        }
        employees.add(new Employee(id, name, baseSalary));
        return true;
    }

    // FR-02: Payment Processing
    public double processPayment(String employeeId, double bonus) {
        if (bonus < 0) {
            return -1.0;
        }

        Employee emp = null;
        for (Employee e : employees) {
            if (e.getId().equals(employeeId)) {
                emp = e;
                break;
            }
        }

        if (emp == null) {
            return -1.0;
        }

        double grossSalary = emp.getBaseSalary() + bonus;
        double taxRate;

        // Fault injected intentionally (>= instead of >)
        if (grossSalary >= 5000) { 
            taxRate = 0.20;
        } else {
            taxRate = 0.10;
        }

        return grossSalary * (1 - taxRate);
    }

    // FR-03: Reporting
    public String generateSummaryReport() {
        double totalExpense = 0;
        for (Employee e : employees) {
            totalExpense += e.getBaseSalary();
        }
        return "Total Employees: " + employees.size() + ", Total Base Expense: $" + totalExpense;
    }

    // ENTRY POINT NEEDED BY ECLIPSE
    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();

        // Add test employee
        ems.addEmployee("EMP001", "Steeve Rayanne", 4000);

        // Execute test case
        double actualNetPay = ems.processPayment("EMP001", 1000);
        double expectedNetPay = 4500.0;

        System.out.println("=== TEST EXECUTION RESULTS ===");
        System.out.println("Expected Net Pay: $" + expectedNetPay);
        System.out.println("Actual Net Pay:   $" + actualNetPay);

        if (actualNetPay == expectedNetPay) {
            System.out.println("TEST RESULT: PASS");
        } else {
            System.out.println("TEST RESULT: FAIL");
        }

        System.out.println("\n=== SUMMARY REPORT ===");
        System.out.println(ems.generateSummaryReport());
    }
}
