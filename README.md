# Lost & Found Management System

A Java desktop application that digitally manages lost and found items in a college environment, replacing manual record keeping. Users can report lost or found items, and the system automatically matches them using a keyword-based matching algorithm.

## Features

- **Report Lost Item:** register a lost item with name, contact, category, last seen location, date and description
- **Report Found Item:** register a found item so the owner can be reunited with it
- **Automatic Matching:** a keyword-based algorithm compares item names and descriptions of lost and found reports and links matching items
- **Check Status:** search by registered contact (case-insensitive) to view all your items; matched items show the other party's contact details
- **Reference IDs:** every report gets a unique reference ID for tracking
- **Persistent Storage:** all data is stored in a local SQLite database, created automatically on first run
- **SQL Injection Prevention:** all database operations use JDBC Prepared Statements
- **Modern UI:** custom dark-themed Swing interface with a built-in calendar date picker and color-coded status badges (LOST, FOUND, MATCHED)

## How Matching Works

When a new item is registered, the system checks all unmatched items of the opposite type (LOST vs FOUND). If any word longer than 3 characters from the new item's name or description appears in an existing item's name or description (case-insensitive), the two items are marked as MATCHED and linked to each other.

## Tech Stack

- **Language:** Java
- **UI:** Java Swing
- **Database:** SQLite
- **Connectivity:** JDBC (sqlite-jdbc)
- **Concepts:** Object-Oriented Programming, event handling, Prepared Statements

## Project Structure

| File | Purpose |
|---|---|
| `Main.java` | Entry point; initializes the database and launches the UI |
| `DataStore.java` | Database layer: item model, queries, auto-matching |
| `HomeScreen.java` | Home screen with navigation cards |
| `ReportLostScreen.java` | Form to report a lost item |
| `ReportFoundScreen.java` | Form to report a found item |
| `CheckStatusScreen.java` | Search and view item status by contact |
| `CalendarPicker.java` | Custom calendar date picker component |
| `Theme.java` | Shared colors, fonts and styled UI components |
| `lib/` | SQLite JDBC driver |

## Getting Started

### Prerequisites

- JDK 17 or higher (the code uses Java text blocks, which need Java 15+)

### Run the project

1. Clone the repository

```bash
   git clone https://github.com/YOUR-USERNAME/lost-and-found-system.git
   cd lost-and-found-system
```

2. Compile

   **Windows**
```bash
   javac -cp ".;lib/sqlite-jdbc-3_51_3_0.jar" *.java
```

   **macOS / Linux**
```bash
   javac -cp ".:lib/sqlite-jdbc-3_51_3_0.jar" *.java
```

3. Run

   **Windows**
```bash
   java -cp ".;lib/sqlite-jdbc-3_51_3_0.jar" Main
```

   **macOS / Linux**
```bash
   java -cp ".:lib/# Lost & Found Management System

A Java desktop application that digitally manages lost and found items in a college environment, replacing manual record keeping. Users can report lost or found items, and the system automatically matches them using a keyword-based matching algorithm.

## Features

- **Report Lost Item:** register a lost item with name, contact, category, last seen location, date and description
- **Report Found Item:** register a found item so the owner can be reunited with it
- **Automatic Matching:** a keyword-based algorithm compares item names and descriptions of lost and found reports and links matching items
- **Check Status:** search by registered contact (case-insensitive) to view all your items; matched items show the other party's contact details
- **Reference IDs:** every report gets a unique reference ID for tracking
- **Persistent Storage:** all data is stored in a local SQLite database, created automatically on first run
- **SQL Injection Prevention:** all database operations use JDBC Prepared Statements
- **Modern UI:** custom dark-themed Swing interface with a built-in calendar date picker and color-coded status badges (LOST, FOUND, MATCHED)

## How Matching Works

When a new item is registered, the system checks all unmatched items of the opposite type (LOST vs FOUND). If any word longer than 3 characters from the new item's name or description appears in an existing item's name or description (case-insensitive), the two items are marked as MATCHED and linked to each other.

## Tech Stack

- **Language:** Java
- **UI:** Java Swing
- **Database:** SQLite
- **Connectivity:** JDBC (sqlite-jdbc)
- **Concepts:** Object-Oriented Programming, event handling, Prepared Statements

## Project Structure

| File | Purpose |
|---|---|
| `Main.java` | Entry point; initializes the database and launches the UI |
| `DataStore.java` | Database layer: item model, queries, auto-matching |
| `HomeScreen.java` | Home screen with navigation cards |
| `ReportLostScreen.java` | Form to report a lost item |
| `ReportFoundScreen.java` | Form to report a found item |
| `CheckStatusScreen.java` | Search and view item status by contact |
| `CalendarPicker.java` | Custom calendar date picker component |
| `Theme.java` | Shared colors, fonts and styled UI components |
| `lib/` | SQLite JDBC driver |

## Getting Started

### Prerequisites

- JDK 17 or higher (the code uses Java text blocks, which need Java 15+)

### Run the project

1. Clone the repository

```bash
   git clone https://github.com/YOUR-USERNAME/lost-and-found-system.git
   cd lost-and-found-system
```

2. Compile

   **Windows**
```bash
   javac -cp ".;lib/sqlite-jdbc-3_51_3_0.jar" *.java
```

   **macOS / Linux**
```bash
   javac -cp ".:lib/sqlite-jdbc-3_51_3_0.jar" *.java
```

3. Run

   **Windows**
```bash
   java -cp ".;lib/# Lost & Found Management System

A Java desktop application that digitally manages lost and found items in a college environment, replacing manual record keeping. Users can report lost or found items, and the system automatically matches them using a keyword-based matching algorithm.

## Features

- **Report Lost Item:** register a lost item with name, contact, category, last seen location, date and description
- **Report Found Item:** register a found item so the owner can be reunited with it
- **Automatic Matching:** a keyword-based algorithm compares item names and descriptions of lost and found reports and links matching items
- **Check Status:** search by registered contact (case-insensitive) to view all your items; matched items show the other party's contact details
- **Reference IDs:** every report gets a unique reference ID for tracking
- **Persistent Storage:** all data is stored in a local SQLite database, created automatically on first run
- **SQL Injection Prevention:** all database operations use JDBC Prepared Statements
- **Modern UI:** custom dark-themed Swing interface with a built-in calendar date picker and color-coded status badges (LOST, FOUND, MATCHED)

## How Matching Works

When a new item is registered, the system checks all unmatched items of the opposite type (LOST vs FOUND). If any word longer than 3 characters from the new item's name or description appears in an existing item's name or description (case-insensitive), the two items are marked as MATCHED and linked to each other.

## Tech Stack

- **Language:** Java
- **UI:** Java Swing
- **Database:** SQLite
- **Connectivity:** JDBC (sqlite-jdbc)
- **Concepts:** Object-Oriented Programming, event handling, Prepared Statements

## Project Structure

| File | Purpose |
|---|---|
| `Main.java` | Entry point; initializes the database and launches the UI |
| `DataStore.java` | Database layer: item model, queries, auto-matching |
| `HomeScreen.java` | Home screen with navigation cards |
| `ReportLostScreen.java` | Form to report a lost item |
| `ReportFoundScreen.java` | Form to report a found item |
| `CheckStatusScreen.java` | Search and view item status by contact |
| `CalendarPicker.java` | Custom calendar date picker component |
| `Theme.java` | Shared colors, fonts and styled UI components |
| `lib/` | SQLite JDBC driver |

## Getting Started

### Prerequisites

- JDK 17 or higher (the code uses Java text blocks, which need Java 15+)

### Run the project

1. Clone the repository

```bash
   git clone https://github.com/YOUR-USERNAME/lost-and-found-system.git
   cd lost-and-found-system
```

2. Compile

   **Windows**
```bash
   javac -cp ".;lib/sqlite-jdbc-3_51_3_0.jar" *.java
```

   **macOS / Linux**
```bash
   javac -cp ".:lib/# Lost & Found Management System

A Java desktop application that digitally manages lost and found items in a college environment, replacing manual record keeping. Users can report lost or found items, and the system automatically matches them using a keyword-based matching algorithm.

## Features

- **Report Lost Item:** register a lost item with name, contact, category, last seen location, date and description
- **Report Found Item:** register a found item so the owner can be reunited with it
- **Automatic Matching:** a keyword-based algorithm compares item names and descriptions of lost and found reports and links matching items
- **Check Status:** search by registered contact (case-insensitive) to view all your items; matched items show the other party's contact details
- **Reference IDs:** every report gets a unique reference ID for tracking
- **Persistent Storage:** all data is stored in a local SQLite database, created automatically on first run
- **SQL Injection Prevention:** all database operations use JDBC Prepared Statements
- **Modern UI:** custom dark-themed Swing interface with a built-in calendar date picker and color-coded status badges (LOST, FOUND, MATCHED)

## How Matching Works

When a new item is registered, the system checks all unmatched items of the opposite type (LOST vs FOUND). If any word longer than 3 characters from the new item's name or description appears in an existing item's name or description (case-insensitive), the two items are marked as MATCHED and linked to each other.

## Tech Stack

- **Language:** Java
- **UI:** Java Swing
- **Database:** SQLite
- **Connectivity:** JDBC (sqlite-jdbc)
- **Concepts:** Object-Oriented Programming, event handling, Prepared Statements

## Project Structure

| File | Purpose |
|---|---|
| `Main.java` | Entry point; initializes the database and launches the UI |
| `DataStore.java` | Database layer: item model, queries, auto-matching |
| `HomeScreen.java` | Home screen with navigation cards |
| `ReportLostScreen.java` | Form to report a lost item |
| `ReportFoundScreen.java` | Form to report a found item |
| `CheckStatusScreen.java` | Search and view item status by contact |
| `CalendarPicker.java` | Custom calendar date picker component |
| `Theme.java` | Shared colors, fonts and styled UI components |
| `lib/` | SQLite JDBC driver |

## Getting Started

### Prerequisites

- JDK 17 or higher (the code uses Java text blocks, which need Java 15+)

### Run the project

1. Clone the repository

```bash
   git clone https://github.com/YOUR-USERNAME/lost-and-found-system.git
   cd lost-and-found-system
```

2. Compile

   **Windows**
```bash
   javac -cp ".;lib/sqlite-jdbc-3_51_3_0.jar" *.java
```

   **macOS / Linux**
```bash
   javac -cp ".:lib/# Lost & Found Management System

A Java desktop application that digitally manages lost and found items in a college environment, replacing manual record keeping. Users can report lost or found items, and the system automatically matches them using a keyword-based matching algorithm.

## Features

- **Report Lost Item:** register a lost item with name, contact, category, last seen location, date and description
- **Report Found Item:** register a found item so the owner can be reunited with it
- **Automatic Matching:** a keyword-based algorithm compares item names and descriptions of lost and found reports and links matching items
- **Check Status:** search by registered contact (case-insensitive) to view all your items; matched items show the other party's contact details
- **Reference IDs:** every report gets a unique reference ID for tracking
- **Persistent Storage:** all data is stored in a local SQLite database, created automatically on first run
- **SQL Injection Prevention:** all database operations use JDBC Prepared Statements
- **Modern UI:** custom dark-themed Swing interface with a built-in calendar date picker and color-coded status badges (LOST, FOUND, MATCHED)

## How Matching Works

When a new item is registered, the system checks all unmatched items of the opposite type (LOST vs FOUND). If any word longer than 3 characters from the new item's name or description appears in an existing item's name or description (case-insensitive), the two items are marked as MATCHED and linked to each other.

## Tech Stack

- **Language:** Java
- **UI:** Java Swing
- **Database:** SQLite
- **Connectivity:** JDBC (sqlite-jdbc)
- **Concepts:** Object-Oriented Programming, event handling, Prepared Statements

## Project Structure

| File | Purpose |
|---|---|
| `Main.java` | Entry point; initializes the database and launches the UI |
| `DataStore.java` | Database layer: item model, queries, auto-matching |
| `HomeScreen.java` | Home screen with navigation cards |
| `ReportLostScreen.java` | Form to report a lost item |
| `ReportFoundScreen.java` | Form to report a found item |
| `CheckStatusScreen.java` | Search and view item status by contact |
| `CalendarPicker.java` | Custom calendar date picker component |
| `Theme.java` | Shared colors, fonts and styled UI components |
| `lib/` | SQLite JDBC driver |

## Getting Started

### Prerequisites

- JDK 17 or higher (the code uses Java text blocks, which need Java 15+)

### Run the project

1. Clone the repository

```bash
   git clone https://github.com/Ujjawal-Patidar-24/lost-and-found-system.git
   cd lost-and-found-system
```

2. Compile

   **Windows**
```bash
   javac -cp ".;lib/sqlite-jdbc-3.51.3.0.jar" *.java
```

   **macOS / Linux**
```bash
   javac -cp ".:lib/sqlite-jdbc-3.51.3.0.jar" *.java
```

3. Run

   **Windows**
```bash
   java -cp ".;lib/sqlite-jdbc-3.51.3.0.jar" Main
```

   **macOS / Linux**
```bash
   java -cp ".:lib/sqlite-jdbc-3.51.3.0.jar" Main
```

The database file `lost_and_found.db` is created automatically in the project folder on first run.

## Author

**Ujjawal Patidar**
MCA, National Institute of Technology, Tiruchirappalli
[LinkedIn](https://www.linkedin.com/in/ujjawalpatidar)" *.java
```

3. Run

   **Windows**
```bash
   java -cp ".;lib/sqlite-jdbc-3_51_3_0.jar" Main
```

   **macOS / Linux**
```bash
   java -cp ".:lib/sqlite-jdbc-3_51_3_0.jar" Main
```

The database file `lost_and_found.db` is created automatically in the project folder on first run.

## Author

**Ujjawal Patidar**
MCA, National Institute of Technology, Tiruchirappalli
[LinkedIn](https://www.linkedin.com/in/ujjawalpatidar)" *.java
```

3. Run

   **Windows**
```bash
   java -cp ".;lib/sqlite-jdbc-3_51_3_0.jar" Main
```

   **macOS / Linux**
```bash
   java -cp ".:lib/sqlite-jdbc-3_51_3_0.jar" Main
```

The database file `lost_and_found.db` is created automatically in the project folder on first run.

## Author

**Ujjawal Patidar**
MCA, National Institute of Technology, Tiruchirappalli
[LinkedIn](https://www.linkedin.com/in/ujjawalpatidar)" Main
```

   **macOS / Linux**
```bash
   java -cp ".:lib/sqlite-jdbc-3_51_3_0.jar" Main
```

The database file `lost_and_found.db` is created automatically in the project folder on first run.

## Author

**Ujjawal Patidar**
MCA, National Institute of Technology, Tiruchirappalli
[LinkedIn](https://www.linkedin.com/in/ujjawalpatidar)r" Main
```

The database file `lost_and_found.db` is created automatically in the project folder on first run.

## Author

**Ujjawal Patidar**
MCA, National Institute of Technology, Tiruchirappalli
[LinkedIn](https://www.linkedin.com/in/ujjawalpatidar)
