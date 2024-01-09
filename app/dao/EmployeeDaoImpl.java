package com.app.dao;

import com.app.entities.Employee;
import com.app.entities.EmploymentType;

import org.hibernate.*;
import static com.app.utils.HibernateUtils.getFactory;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

	@Override
	public String addEmpDetails(Employee emp) {
		// emp : TRANSIENT (exists ONLY in heap)
		String mesg = "adding emp details failed !!!!";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession(); 
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			// save emp details
			Serializable empId = session.save(emp);
			// emp : PERSISTENT (exists in L1 cache BUT not yet in DB)
			tx.commit();/*
						 * hibernate perform auto dirty chking session.flush() --> dirty chking -->
						 * compares satte of L1 cache with DB new entity : insert session.close() --L1
						 * cache destroyed n cn rets to the pool session is closed
						 */
			mesg = "Added new emp with ID=" + empId;
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		// emp : DETACHED(not in L1 cache , exists in db)
		return mesg;
	}

	@Override
	public Employee getEmpDetailsById(Integer empId) {
		Employee emp = null;// emp : does not exist in heap !
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			emp = session.get(Employee.class, empId);// select
			emp = session.get(Employee.class, empId);// lifted from cache
			emp = session.get(Employee.class, empId);// lifted from cache
			// in case of valid id -- emp : PERSISTENT (exists in cache n db)
			tx.commit(); //if not done--> EEROR: Connection leak issue
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return emp;// emp : DETACHED from L1 cache
	}

	@Override
	public List<Employee> getAllEmps() {
		List<Employee> empList = null;
		String jpql = "select e from Employee e";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			empList = session.createQuery(jpql, Employee.class).getResultList();
			// empList : list of PERSISTENT entities.
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return empList;// empList : list of DETACHED entities.
	}

	@Override
	public List<Employee> getEmsByDateAndSalary(LocalDate begin, LocalDate end1, double minSalary) {
		List<Employee> emps = null;
		String jpql = "select e from Employee e where e.joinDate between :start and :end and e.salary > :minSal";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			emps = session.createQuery(jpql, Employee.class)
					.setParameter("start", begin)
					.setParameter("end", end1)
					.setParameter("minSal", minSalary).getResultList();
			//emps: List of persistent entities
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return emps; //emps: list of detached entities
	}

	@Override
	public List<String> getLastNamesByEmpType(EmploymentType empType1) {
		List<String> lastNames = null;
		String jpql = "select e.lastName from Employee e where e.empType=:type";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			lastNames = session.createQuery(jpql, String.class).setParameter("type", empType1).getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return lastNames;
	}

	@Override
	public List<Employee> testConstructorExpression(EmploymentType empType) {
		List<Employee> emps = null;
		String jpql = "select new com.app.entities.Employee(firstName,lastName,salary) from Employee e where e.empType=:type";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			emps = session.createQuery(jpql, Employee.class)
					.setParameter("type", empType)
					.getResultList();
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return emps;
	}

	@Override
	public String updateEmpSalary(String email1, double increment) {
		String mesg = "Salary updation failed !!!!!";
		Employee emp = null;
		String jpql = "select e from Employee e where e.email=:em";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			emp = session.createQuery(jpql, Employee.class).setParameter("em", email1).getSingleResult();
			// throws NoResultException: in case of no result found !
			
			// emp : PERSISTENT(exists in L1 cache n db)
			emp.setSalary(emp.getSalary() + increment);// modifying state of the persistent entity
			// session.evict(emp);//emp : DETACHED
			tx.commit();// session.flush--> dirty chking -->entity exists but with updated state --
			mesg = "emp's salary updated ....";
			// DML : update --session.close()
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		// emp : DETACHED from L1 cache
		emp.setSalary(emp.getSalary() + increment);// modifying detached entity state
		return mesg;
	}

	@Override
	public String updateBulkSalary(LocalDate joinDate, double increment) {
		String mesg = "Bulk updation failed";
		String jpql = "update Employee e set e.salary=e.salary+:incr where e.joinDate < :dt";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			int updateCount = session.createQuery(jpql).setParameter("incr", increment).setParameter("dt", joinDate)
					.executeUpdate();
			tx.commit();
			mesg = "Updated salary of " + updateCount + " no of emps";
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		return mesg;
	}

	@Override
	public String deleteEmpDetails(Integer empId) {
		Employee emp=null;
		String mesg="deleting emp details failed !!!!";
		// 1. Get session from SF
		Session session = getFactory().getCurrentSession();
		// 2. Begin a Tx
		Transaction tx = session.beginTransaction();
		try {
			//get emp by it's id
			emp=session.get(Employee.class, empId);
			if(emp != null)
			{
				//emp : persistent 
				session.delete(emp);//emp : REMOVED (simply marked for removal)
				mesg="deleted emp details ...";
			}
			tx.commit();//DML : delete , L1 cache is destroyed
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		}
		//emp : transient
		return mesg;
	}//emp : marked for GC

}
