---

## 📘 **README.md**

### **Financial Ledger Application**

This Java console application allows users to manage and review their financial transactions using a CSV file (`transactions.csv`). Users can record deposits and payments, view transaction history, and generate detailed reports based on specific time periods or vendors.

---

### **🧩 Features**

* **Add Deposits and Payments:**
  Easily record new transactions directly from the command line.

* **Ledger Menu:**
  View all transactions, deposits, or payments separately.

* **Report Generation:**
  Generate and view reports for:

  * Month-to-Date
  * Previous Month
  * Year-to-Date
  * Previous Year
  * Search by Vendor

* **Persistent Storage:**
  All transactions are saved to `transactions.csv` for future access.

---

### **📂 File Structure**

```
FinancialLedger/
│
├── Transaction.java          # Represents a single transaction (date, time, vendor, etc.)
├── FinancialLedger.java      # Main program file (your provided code)
└── transactions.csv          # Stores all transactions persistently
```

---

### **🧠 How It Works**

1. **Home Menu:**
   Choose to:

   * `D` → Add Deposit
   * `P` → Make Payment
   * `L` → Open Ledger
   * `X` → Exit Program

2. **Ledger Menu:**
   From here, you can view:

   * `A` → All Entries
   * `D` → Deposits Only
   * `P` → Payments Only
   * `R` → Reports Menu
   * `H` → Return to Home
   * `X` → Exit Program

3. **Reports Menu:**
   Select a report type (e.g., Month-to-Date, Previous Year, etc.) or search by vendor name.

4. **Transactions CSV:**
   Each transaction is stored in `transactions.csv` in this format:

   ```
   Date|Time|Description|Vendor|Amount
   2025-10-17|14:30|Coffee|Starbucks|-4.75
   ```

---

### **🛠️ Requirements**

**Java Version:** Java 8 or later

---

### **📈 Example Usage**

```
--- Welcome to the Financial Ledger App ---

D) Add Deposit
P) Make Payment (Debit)
L) Ledger
X) Exit
Enter Choice: D
Enter description:
Paycheck
Enter Vendor:
ACME Corp
Enter amount:
2500.00
```

The deposit is then saved to `transactions.csv`.

---

### **🧾 Notes**

* Negative amounts represent **payments (money out)**.
* Positive amounts represent **deposits (money in)**.
* The program automatically timestamps each transaction.

---

---

## 🪄 **Short Summary (1 paragraph)**

This Java console-based "Financial Ledger Application" lets users track their deposits and payments, store them in a CSV file, and generate various financial reports (monthly, yearly, or by vendor). It offers a text-based menu system for easy navigation, automatic timestamping of transactions, and persistent data storage, making it a simple functional financial tracking tool built using core Java features like file I/O, collections, and date/time APIs.

---
