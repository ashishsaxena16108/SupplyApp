# 🚀 Supply Chain Management System

## 📌 Project Overview
The goal of this project is to create a unified system that streamlines the entire **supply chain process**—from **order placement to delivery**—by integrating:

1. **Order Management System (OMS)**
2. **Transportation Management System (TMS)**
3. **Distribution Management System (DMS)**

Each module operates independently but communicates via **APIs**, ensuring **seamless data flow** and improved operational efficiency.

---

## 📦 Modules and Their Functions

### ✅ **Order Management System (OMS)**
- Captures and validates customer orders from multiple channels (online, in-store, mobile apps).
- Performs real-time inventory checks to confirm product availability.

### 🚛 **Transportation Management System (TMS)**
- **Route Planning & Optimization**: Determines the most efficient delivery routes based on distance, traffic, and time.
- **Carrier Management**: Interfaces with third-party logistics providers and carriers.
- **Real-Time Tracking**: Monitors shipments using **GPS and IoT devices**.

### 🏬 **Distribution Management System (DMS)**
- **Warehouse Management**: Handles storage, picking, packing, and shipping operations.
- **Inventory Control**: Tracks stock levels across multiple warehouses.
- **Returns Management**: Streamlines product returns and restocking.

---

## 🛠️ System Architecture

### 📡 **Architecture Overview**
- **Modular Design**: Each module (OMS, TMS, DMS) operates independently but communicates through **APIs**.
- **Scalable Infrastructure**: Utilizes **cloud services** for high availability and load handling.
- **Real-Time Updates**: Orders update dynamically as they progress through different stations.

### 🔀 **Path Calculation using Dijkstra's Algorithm**
1. **Find the nearest warehouse** to the order's location.
2. **Identify intermediate stations** required for the delivery.
3. **Calculate the shortest path** to the receiver's address.
4. **Update the order status** as it moves through the stations.

---

## 📆 Project Timeline (TAT: 20 Days)

### **Days 1-5:**
- Research **OMS, TMS, DMS** requirements.
- Finalize system specifications.

### **Days 6-10:**
- Develop detailed **design documents**.
- Create **architectural diagrams** & **data flow diagrams**.

### **Days 11-15:**
- Design **backend structures** (database schemas, API endpoints).
- Develop **frontend wireframes** and UI mockups.

### **Days 16-20:**
- Refine system design.
- Perform final **testing & documentation**.

---

## ⚙️ Technologies Used
- **Backend**: Java, Spring Boot
- **Database**: MongoDB
- **Messaging**: Apache Kafka
- **Frontend**: React.js (optional)
- **Cloud**: AWS / Azure (optional)

---

## 🏆 Key Features
✅ Real-time inventory checks  
✅ Route optimization using **Dijkstra’s Algorithm**  
✅ API-based modular communication  
✅ Real-time order tracking  
✅ Scalable cloud infrastructure  

---

## 💡 Future Enhancements
🔹 **Machine Learning for demand forecasting**  
🔹 **AI-based route optimization**  
🔹 **Integration with IoT for warehouse automation**  

---

## 🏗️ How to Run the Project
```bash
# Clone the repository
git clone https://github.com/your-repo/scm-system.git
cd scm-system

# Start the backend (Spring Boot)
mvn spring-boot:run

# Start Kafka (if applicable)
docker-compose up

# Start the frontend (if applicable)
npm start
```

---

## 📬 Contact & Support
For any queries, feel free to reach out at **your-email@example.com** or create an issue in the repository.

🚀 **Happy Coding!**

