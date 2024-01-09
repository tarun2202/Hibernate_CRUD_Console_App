package com.app.dao;

import java.time.LocalDate;
import java.util.List;

import com.app.entities.Employee;
import com.app.entities.EmploymentType;

public interface EmployeeDao {
//add a method to save emp details
	String addEmpDetails(Employee emp);

	// get emp details by id
	Employee getEmpDetailsById(Integer empId);

	// get all emp details
	List<Employee> getAllEmps();

	// get emps by join date n salary
	List<Employee> getEmsByDateAndSalary(LocalDate begin, LocalDate end, double minSalary);

	// get emp last names by emp type
	List<String> getLastNamesByEmpType(EmploymentType empType);

	// test ctor expression
	List<Employee> testConstructorExpression(EmploymentType empType);

	// update salary
	String updateEmpSalary(String email, double increment);
	//bulk updation of salary
	String updateBulkSalary(LocalDate joinDate,double increment);
	//delete emp details
	String deleteEmpDetails(Integer empId);
}
