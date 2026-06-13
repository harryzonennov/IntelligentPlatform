# Business: Employee reference to Logon User Process

This article describes the reference and synchronization between Employee and Logon User.

---

## 1. Reference relationship from Employee to Logon User

### Background fields
The reference to a Logon User from an Employee is modeled by the reference type `EmpLogonUserReference`, which is a subnode of Employee. This means one Employee can be bound to multiple Logon User instances.

However, there is currently no UI function that allows end users to bind a Logon User to an Employee.

---

## 2. To-Do and testing considerations for Employee and Logon User

1) Remove all synchronization functions between Employee and Logon User, as they cause confusion and make the functionality harder to understand.

2) Continue using the subnode `EmpLogonUserReference` to maintain the relationship in the backend, preserving a one-to-many relationship from Logon User to Employee.

3) In the Employee UI, implement an embedded list to manage the reference from an Employee to its Logon User. Alternatively, consider adding a “main” flag to designate the primary referenced Logon User. The end user should be able to navigate from the Employee list view to the corresponding Logon User’s edit view.

4) In the Employee List UI, display the main Logon User as a field.

5) In the Logon User List UI and Editor UI, maintain the reference to the Employee information as well.

---

## 3. Async functions between Employee and Logon User

There should be no synchronization functions between Employee and Logon User, as they cause confusion and complicate the workflow.