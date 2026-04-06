# KharchaBahi 💰
### *Kharcha = Expense | Bahi = Ledger*

A personal finance companion app built with Jetpack Compose and modern Android architecture. KharchaBahi helps users track daily income and expenses, understand spending patterns, and stay within monthly budgets.

---
## Screenshots

<p float="left">
  <img src="https://github.com/user-attachments/assets/f8135490-7396-479b-bb4c-d6ee2e9587e8" width="200" />
  <img src="https://github.com/user-attachments/assets/4ca727c9-6312-4994-aa2e-3b5308e5cf3b" width="200" />
  <img src="https://github.com/user-attachments/assets/47075bd5-00b9-4ed2-b458-ee4bb532b0a4" width="200" />
  <img src="https://github.com/user-attachments/assets/7814dc03-731c-49e8-a31b-f98bdac5b8df" width="200" />
</p>
---

## Features

### 📋 Transaction Tracking
- Add, edit, and delete income/expense transactions
- Transactions grouped by date for easy reading
- Category-based organization with emoji icons
- Search and filter transactions by category or note
- Income shown in blue, expenses in red

### 📊 Stats Screen
- Toggle between Income and Expense views
- Category breakdown showing amount and percentage
- Understand where money is being spent

### 🎯 Budget Tracker (Goal Feature)
- Set a monthly spending budget
- Real-time progress bar showing spent vs budget
- Remaining balance displayed clearly
- Budget persists across sessions via SharedPreferences
- Warning color when approaching limit

### 🏠 Transaction Dashboard
- Total income, expenses, and balance at a glance
- Date-wise grouped transaction list
- Daily income and expense summary per date group

---

## Tech Stack

| Tool | Purpose |
|------|---------|
| Jetpack Compose | UI |
| MVVM | Architecture |
| Room (SQLite) | Local database |
| StateFlow | State management |
| SharedPreferences | Budget persistence |
| Navigation Compose | Screen navigation |
| Coroutines | Async operations |
| Material 3 | Design system |

---

## Architecture

```
UI (Composables)
    ↓ collectAsState()
ViewModel (TransactionViewModel)
    ↓ StateFlow
Repository (TransactionRepository)
    ↓ suspend fun
DAO (TransactionDao)
    ↓
Room Database (TransactionDatabase)
```

---

## Screens

```
MainScreen (Bottom Navigation)
├── TransactionContent  → Transaction list + search + summary
├── StatsScreen         → Category breakdown by income/expense
└── BudgetScreen        → Monthly budget tracker with progress bar

AddEditScreen           → Add new or edit existing transaction
```

---

## Data Model

```kotlin
Transaction(
    id: Int,           // auto-generated
    time: Long,        // timestamp (formatted for display)
    amount: Double,    // transaction amount
    type: String,      // "Income" or "Expense"
    category: String,  // e.g. "💰 Salary", "🍔 Food"
    note: String       // optional description
)
```

---

## Getting Started

1. Clone the repo
```bash
git clone https://github.com/CSwithChetan123/KharchaBahi.git
```
2. Open in Android Studio
3. Run on emulator or physical device (API 26+)

---

## Assumptions & Design Decisions

- **No login required** — local storage only, privacy first
- **Room over Firebase** — offline first, no internet dependency
- **SharedPreferences for budget** — single value, no need for full database
- **Timestamp as Long** — enables easy sorting and flexible date formatting

---

## Assignment Mapping (Zorvyn FinTech)

| Requirement | Implementation |
|-------------|---------------|
| Home Dashboard | Income/Expense/Total summary row |
| Transaction Tracking | Add/Edit/Delete with Room |
| Filter/Search | Real-time search by category and note |
| Goal Feature | Monthly budget tracker with progress bar |
| Insights Screen | Stats screen with category breakdown |
| Local Data | Room database (SQLite) |
| State Management | MVVM + StateFlow |

---

## Author

**Chetan Thapa**
GitHub: [@CSwithChetan123](https://github.com/CSwithChetan123)
