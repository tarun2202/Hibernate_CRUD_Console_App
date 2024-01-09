package com.app.tester;

import static com.app.utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import com.app.dao.EmployeeDao;
import com.app.dao.EmployeeDaoImpl;
import com.app.entities.Employee;
import com.app.entities.EmploymentType;

public class GetAllEmployees {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory()) {
			// create dao instance
			EmployeeDao dao = new EmployeeDaoImpl();
			System.out.println("All emps : ");
			dao.getAllEmps().forEach(System.out::println);
			
		} // JVM : sf.close() ---> DBCP : cleaned up !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
