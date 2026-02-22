# Warehouse Management System

Backend warehouse management system built with Spring Boot.

## 📦 Overview

This project represents the core logic of a Warehouse Management System (WMS).

The system supports:

- Receiving goods via invoices
- Batch tracking (with expiry date)
- Stock stored in warehouse locations
- Moving stock between locations
- Movement history tracking
- Stock validation (cannot move more than available)

The architecture separates:

- Product (what the item is)
- Batch (specific delivery with expiry)
- Stock (current quantity in a location)
- MovementHistory (audit log of stock movements)

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven

---

## 🗄 Database Model (Core Entities)

### Product
Represents a unique product by name.

### Invoice / InvoiceItem
Represents goods received from supplier.

### Batch
Represents a specific product delivery with:
- Batch number
- Expiry date
- Quantity

### Location
Warehouse location (example: A1-01-A1)

### Stock
Represents:
- How much of a specific batch is stored
- In a specific location

### MovementHistory
Stores all stock movements:
- From location
- To location
- Quantity
- Timestamp


## 📬 Example API Usage
Create Invoice

POST /api/create

    {
        "invoiceNumber": "INV-1001",
        "invoiceDate": "2026-01-10T00:00:00.000Z",
        "invoiceItems": [
            {
                "name": "Milk 2%",
                "quantity": 20,
                "price": 2.50,
                "batchNumber": "M2-001",
                "expiryDate": "2027-02-01T00:00:00.000Z"
            }
        ]
    }

## 📊 Planned Features

FEFO (First Expiry First Out)

Stock dispatch logic

Authentication & user tracking

Unit & integration tests

JavaFX UI