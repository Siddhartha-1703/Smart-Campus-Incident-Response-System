# Smart Campus Incident & Response System

A Java-based campus incident reporting and response management system that allows students to report campus issues, track incidents using unique Incident IDs, and enables administrators to manage and update incident status.

## 📌 Project Overview

The Smart Campus Incident & Response System provides a centralized platform for reporting and managing campus incidents.

Students can report incidents by entering the category, location, and description. The system automatically generates a unique Incident ID beginning with `VMEG`, which can be used to track the incident.

Administrators can view active incidents, inspect incident details, and update their status until the issue is resolved.

## 🎯 Objectives

- Provide a simple platform for students to report campus incidents.
- Automatically generate unique Incident IDs.
- Allow students to track reported incidents.
- Provide administrators with an incident management dashboard.
- Maintain incident status history.
- Store incident information securely in a MySQL database.
- Demonstrate Java Object-Oriented Programming concepts.

## 🚀 Features

### Student Module
- Student registration
- Student login
- Report new incident
- Automatic VMEG Incident ID generation
- Track incident using Incident ID
- View previously reported incidents
- Logout

### Admin Module
- Secure admin login
- View incidents requiring action
- View complete incident details
- Update incident status
- Refresh incident list
- Logout

### Status Management

Incident status follows the workflow:

`Pending → Assigned → In Progress → Resolved`

Resolved incidents remain stored in the database for future reference but are removed from the active admin dashboard.

## 🧩 Modules

1. **User Module**
   - Registration
   - Login
   - Incident reporting
   - Incident tracking

2. **Admin Module**
   - Admin authentication
   - Incident monitoring
   - Incident management

3. **Status Module**
   - Status updates
   - Status history
   - Incident lifecycle management

4. **Database Module**
   - MySQL database
   - JDBC connectivity
   - CRUD operations
   - Transaction management

## 🛠️ Technologies Used

- Java
- Java Swing
- JDBC
- MySQL
- Git
- GitHub
- MySQL Connector/J

## 🗄️ Database Design

The system uses four main tables:

### users
Stores student registration and login information.

### admins
Stores administrator login credentials.

### incidents
Stores reported campus incidents and their current status.

### status_history
Stores the history of status changes for each incident.

### Incident ID

Each incident receives a unique ID in the format:

`VMEG + Timestamp`

Example:

`VMEG20261015224836142`

## 🔄 System Workflow

```text
Student Registration/Login
          ↓
    Student Dashboard
          ↓
    Report Incident
          ↓
 Generate VMEG Incident ID
          ↓
       MySQL Database
          ↓
     Admin Dashboard
          ↓
    View Incident Details
          ↓
      Update Status
          ↓
Pending → Assigned → In Progress → Resolved
          ↓
     Status History
          ↓
      Student Tracking