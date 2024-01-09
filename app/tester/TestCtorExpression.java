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

public class TestCtorExpression {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); 
				Scanner sc = new Scanner(System.in)) {
			// create dao instance
			EmployeeDao dao = new EmployeeDaoImpl();
			System.out.println("Enter emp type");
			dao.
			testConstructorExpression
			(EmploymentType.valueOf(sc.next().toUpperCase()))
			.forEach(e -> System.out.println(e.getFirstName()+" "
			+e.getLastName()+" "+e.getSalary()));
			
			/*
			 Q: When can a lambda expression be substituted by method reference? 
			 A: Id and only if, in the body of the lambda expression, if you are calling existing method 
			 	using the lambda argument.
			 	//e->e.getSalary()
			 	empList.forEach(Employee::getSalary());
			 */

		} // JVM : sf.close() ---> DBCP : cleaned up !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
