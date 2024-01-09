# Hibernate_CRUD_Console_App
Performing the CRUD operation: The code demonstrates the usage of Hibernate's session and transactions to interact with the database, handling entity states (TRANSIENT, PERSISTENT, and DETACHED), and executing HQL (Hibernate Query Language) queries for various operations.

This Hibernate code is part of an implementation for a data access object (DAO) handling operations related to the Employee entity. Here's a short description of the key functionalities:

addEmpDetails: Adds employee details to the database by saving the provided Employee object.

getEmpDetailsById: Retrieves an Employee object by its ID from the database.

getAllEmps: Retrieves a list of all employees from the database.

getEmsByDateAndSalary: Retrieves a list of employees based on their join date, end date, and minimum salary.

getLastNamesByEmpType: Retrieves a list of last names of employees based on their employment type.

testConstructorExpression: Retrieves a list of Employee objects using a constructor expression based on employment type.

updateEmpSalary: Updates the salary of an employee identified by their email.

updateBulkSalary: Performs a bulk update of salaries for employees who joined before a specified date.

deleteEmpDetails: Deletes employee details based on the provided employee ID.
