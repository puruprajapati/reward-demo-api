# Reward Demo API

A simple **Spring Boot REST API** that demonstrates a **customer rewards program**.

Customers earn reward points based on their transactions:

- ⭐ **2 points** for every dollar spent **over $100** in a transaction  
- ⭐ **1 point** for every dollar spent **between $50 and $100**

**Example:**  
A $120 purchase → `2 x 20 + 1 x 50 = 90 points`

The project is seeded with sample customers (**John Doe**, **Niru Lama**, **Nori Yangchen**) and sample transactions at application startup.

---

## 🚀 Features
- 📊 Reward calculation for all customers across a **3-month period**  
- 🗓️ Aggregated reward points per **month** and **total**  
- ⚖️ Global exception handling with consistent **JSON error responses**  
- 🗄️ **In-memory H2 database** for quick demo/testing  
- ✅ Unit test coverage for key scenarios (e.g., **empty customers**)  

---

## 🛠️ Tech Stack
- ☕ Java 17  
- 🚀 Spring Boot 3.x  
- 🌐 Spring Web (REST APIs)  
- 🗂️ Spring Data JPA  
- 🗄️ H2 Database (in-memory for demo)  
- 🧪 JUnit 5 + Mockito (unit testing)  

---

## 📦 Project Setup

### 1. Clone the repository
```bash
git clone https://github.com/your-username/reward-demo-api.git
cd reward-demo-api
