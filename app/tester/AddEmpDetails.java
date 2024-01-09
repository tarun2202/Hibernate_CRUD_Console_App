package com.app.tester;

import static com.app.utils.HibernateUtils.getFactory;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import com.app.dao.EmployeeDao;
import com.app.dao.EmployeeDaoImpl;
import com.app.entities.Employee;
import com.app.entities.EmploymentType;

public class AddEmpDetails {

	public static void main(String[] args) {
		try (SessionFactory sf = getFactory(); //it will 
				Scanner sc=new Scanner(System.in)) {
			//create dao instance
			EmployeeDao dao=new EmployeeDaoImpl();
			System.out.println("Enter emp details : firstName,  lastName,  email,  password,  joinDate, empType,  salary");
			Employee newEmp=new Employee(sc.next(),sc.next(),
					sc.next(), sc.next(), LocalDate.parse(sc.next()),
					EmploymentType.valueOf(sc.next().toUpperCase()),sc.nextDouble());
			System.out.println(dao.addEmpDetails(newEmp)); //transient
		} //JVM : sf.close() ---> DBCP : cleaned up !
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
