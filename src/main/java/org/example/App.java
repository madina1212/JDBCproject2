package org.example;

import cenfig.Hibernatiprojec;
import org.example.model.Employee;
import org.example.model.Job;
import org.example.service.EmployeeService;
import org.example.service.serviceimpl.EmployeeServiceImpl;
import org.example.service.serviceimpl.JobServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        JobServiceImpl jobService = new JobServiceImpl();
        Job job = new Job("Instructor", "JavaScript", "Fronted developer", 2);
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        Employee employee = new Employee("Madina", "Halikova", 16, "m@gmail.com", 1);


//        jobService.createJobTable();
//        jobService.addJob(job);
//        System.out.println(jobService.getJobById(2L));
//        System.out.println(jobService.sortByExperience("desc"));
//        jobService.deleteDescriptionColumn();
//        System.out.println(jobService.getJobByEmployeeId(2L));
//        employeeService.createEmployee();
//        employeeService.addEmployee(employee);
//        employeeService.dropTable();
//        employeeService.cleanTable();
//        employeeService.updateEmployee(2L, employee);
//        employeeService.getAllEmployees();
//        System.out.println(employeeService.findByEmail("a@gmail.com"));
//        System.out.println(employeeService.getEmployeeById(2L));
//          System.out.println(employeeService.getEmployeeByPosition("Mentor"));




    }
}
