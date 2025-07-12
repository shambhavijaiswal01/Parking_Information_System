# 🚗 Parking Information System

A full-stack Parking Information System that allows users to search for available parking, book a slot in advance, and manage arrivals and departures. The system also supports valet operations and notifies users of booking status.

---

## 📌 Features

- 🔐 **User Authentication**: Signup/Login using mobile number and password.
- 🔍 **Search Interface**: Find available parking slots in different locations.
- 📅 **Slot Booking**: Book a parking slot 15 minutes in advance.
- 📱 **Notifications**: Slot expiration reminders and status updates.
- 🧾 **User Dashboard**: View and manage your booking history.
- 🚦 **Valet Interface**: Mark car as arrived or departed in real time.
- 🕒 **Automatic Slot Expiry**: Unattended bookings expire after 15 minutes.

---

## 🛠️ Tech Stack

### Frontend
- HTML
- CSS
- JavaScript (Vanilla)

### Backend
- Java 17
- Spring Boot 3.5.3
- REST API
- Servlet Support

### Database
- PostgreSQL

---

## 📂 Project Structure
parking-information-system/
├── backend/
│ ├── src/main/java/com/parking/backend/
│ │ ├── controller/
│ │ ├── model/
│ │ ├── repository/
│ │ ├── config/
│ │ └── ...
│ └── application.properties
├── frontend/
│ ├── static/
│ │ ├── index.html
│ │ ├── login.html
│ │ ├── signup.html
│ │ ├── booking.html
│ │ ├── valet.html
│ │ └── settings.html
│ └── script.js
└── README.md


⚙️ Getting Started
1. Clone the Repository
bash
Copy
Edit
git clone https://github.com/your-username/parking-information-system.git
cd parking-information-system
2. Set Up PostgreSQL
Create a PostgreSQL database named parkingdb (or your preferred name).

Update the database credentials in backend/src/main/resources/application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/parkingdb
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
⚠️ Make sure PostgreSQL is running locally and your username/password match the configuration.

3. Run the Backend
From the root directory, run:

bash
Copy
Edit
cd backend
./mvnw spring-boot:run
By default, the backend server will start at:

arduino
Copy
Edit
http://localhost:8081
4. Launch the Frontend
Open the following file in your browser:

arduino
Copy
Edit
frontend/static/index.html
You can also serve the frontend with a local server (like Live Server extension in VS Code) for better performance.
