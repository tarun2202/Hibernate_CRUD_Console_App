package com.app.tester;

import static com.app.utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import com.app.dao.EmployeeDao;
import com.app.dao.EmployeeDaoImpl;
import com.app.entities.Employee;
import com.app.entities.EmploymentType;
import static java.time.LocalDate.parse;

public class GetEmpsByDateAndSalary {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); Scanner sc = new Scanner(System.in)) {
			// create dao instance
			EmployeeDao dao = new EmployeeDaoImpl();
			System.out.println("Enter start date , end date and min sal");
			dao.getEmsByDateAndSalary(parse(sc.next()), parse(sc.next()), sc.nextDouble())
			.forEach(System.out::println);

		} // JVM : sf.close() ---> DBCP : cleaned up !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
