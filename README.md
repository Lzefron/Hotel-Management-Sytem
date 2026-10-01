**Topic:** Hotel Management System<br>
**Assignment:** JAVA GROUP PROJECT CSE282<br>
**Course:** CSE 282.5 - PROGRAMMING LANGUAGE II LAB (JAVA)

👥 **Group Hotel Operations Team**

| SL | Student Name | ID |
|:--:|:-------------|:--:|
| 1 | MD Mahin Sikder-L | 2024200000572 |
| 2 | Farjana Khatun Munni | 2024100000386 |
| 3 | Sayeem Al Hasan | 2024200000085 |
| 4 | Swehinu Marma Boishakhi | 2024100000140 |
| 5 | Nargis Akter | 2024100000030 |

🖱️ **Project Overview** The Hotel Management System is a Java desktop application designed to streamline daily front-desk operations in hotels, guest houses, and resorts.

The system centralizes all management tasks, allowing users to:
* Report room availability and manage room features.
* Register guests and maintain staff payroll records.
* Process reservations with automated stay calculations.
* Calculate checkout bills with discount deductions and service fees.

🖱️ **Project Objectives** Create a centralized storage system for rooms, guests, and reservations. Implement all four OOP principles (Encapsulation, Abstraction, Inheritance, Polymorphism). Provide a smooth graphical user interface using Java Swing. Design a secure and user-friendly system that validates user input and manages errors gracefully. Ensure admins have full control over employee records and system operations.

⚙️ **System Theory** In traditional hospitality setups, managing room reservations and billing manually through registers often causes double-booking and calculation errors. Hotel Management System overcomes these issues through:
* **Object-Oriented Architecture:** clear class relationships (Room, User, Customer, Admin, HotelBill).
* **Structured Data Flow:** from user input → validation → file storage → billing engine.
* **Encapsulated Modules:** each class handles a defined responsibility to reduce coupling.

| PRINCIPLES | IMPLEMENTATION |
| :--- | :--- |
| **Encapsulation** | Private fields with getters/setters in every entity (e.g., Room, User, HotelBill) |
| **Abstraction** | Abstract base classes User and HotelService enforce shared structure |
| **Inheritance** | Class hierarchies: Admin and Customer extend User, FoodDelivery and Laundry extend HotelService |
| **Polymorphism** | Overridden displayDetails() and getRole() methods across models |

🖱️ **Methodology**

**Requirement Analysis** Understand front-desk hotel workflows. Define user roles: Customer and Admin. Specify functional requirements (room booking, facility service requests, billing, record management).

**System Design**
* **Frontend:** Built using Java Swing with JTabbedPane separating the main functions.
* **Backend Services:** Controllers handle room allocations, guest queries, and reservation rules.
* **Exception Handling:** Custom exceptions (RoomNotFoundException, InvalidInputException) ensure robust error reporting.

**Object Model**

| CLASS | DESCRIPTION |
| :--- | :--- |
| **Main** | The main driver function that runs all the other classes and methods. |
| **User (abstract)** | Base class defining shared user fields. |
| **Customer** | Extends User with reservation history, active bookings, and discount details. |
| **Admin** | Inherits from User with additional privileges to manage staff and system logs. |
| **Room** | Stores details like room number, type, capacity, price, and reserved dates. |
| **Booking** | Links guests to rooms along with check-in and check-out dates. |
| **HotelService (abstract)** | Base class for add-on facilities. |
| **FoodDelivery** | Extends HotelService to handle room dining service calculations. |
| **Laundry** | Extends HotelService to handle per-item garment cleaning fees. |
| **Billable (interface)** | Interface declaring total bill calculation signatures. |
| **HotelBill** | Implements Billable to calculate final payment totals with discounts. |
| **FileManager** | Manages flat-file data persistence for application restarts. |

**Implementation** Developed in Java SE using OOP design. Uses try-catch blocks and custom exceptions for input safety. GUI forms mapped to backend services ensuring smooth communication.

**Testing & Validation** GUI event functions validated for both success and error scenarios. Admin approval workflows tested with edge cases (duplicate room IDs, invalid dates).

🧮 **Functional Modules**
* **Room Management** — register, edit, and view room inventories.
* **Guest & Employee Registration** — record new profile details.
* **Manage Reservations** — check date overlaps and book rooms.
* **Facility Requests** — attach extra food or laundry charges to active stays.
* **Billing Engine** — compute final checkout invoices with discounts.

💡 **Exception Handling** Custom exceptions ensure data validity and user feedback:
* **`RoomNotFoundException`** → when querying or booking a non-existent room.
* **`GuestNotFoundException`** → when retrieving an invalid guest record.
* **`InvalidInputException`** → when invalid pricing or wrong date formats are entered.
* Friendly user-facing messages shown via JOptionPane.

🧾 **Conclusion** The Grand Hotel Management System successfully demonstrates all essential OOP and Java programming principles while solving a real-world hospitality problem. It provides an organized, scalable, and user-centered solution for managing hotel operations efficiently.

This project serves as a model for structured software design effectively integrating object-oriented programming with GUI development and robust exception management.

⏳ **Future Enhancements**
* Integration with MySQL or PostgreSQL for persistent database storage.
* Add email and SMS notifications for reservation confirmations.
* Integrate online payment gateways for credit cards and mobile banking.
* Extend system to a web or mobile platform for cross-device accessibility.
