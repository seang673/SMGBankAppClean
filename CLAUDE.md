# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Structure

This is a monorepo with two independent sub-projects:

- `backend/` — Spring Boot 4 REST API (Java 21, Maven, MongoDB)
- `frontend/` — React 18 SPA (TypeScript, Vite, React Router)

## Commands

### Backend (run from `backend/`)
```bash
./mvnw spring-boot:run          # Start the API server (port 8080)
./mvnw test                     # Run all tests
./mvnw test -Dtest=AccountServiceTest   # Run a single test class
./mvnw package                  # Build the JAR
```

### Frontend (run from `frontend/`)
```bash
npm run dev     # Start dev server (port 5173)
npm run build   # TypeScript check + Vite production build
npm run lint    # ESLint (0 warnings allowed)
```

## Architecture

### Data Flow
The frontend calls the backend via REST. The API base URL is configured through `VITE_API_URL` in `frontend/.env`. In production the backend is hosted on Render; the frontend on Vercel.

### Backend Layers

**Controllers** (`controller/`) map HTTP routes to service calls — no business logic lives here.

**Services** (`service/`) hold all business logic:
- `CustomerService` — CRUD plus deposit/withdraw routed to the customer's first account
- `AccountService` — CRUD plus deposit/withdraw operating directly on an account by ID
- `CounterService` — atomically auto-increments sequential integer IDs using MongoDB's `findAndModify` on a `counters` collection; sequence names are `"customerNumber"` and `"accountNumber"`

**Models** (`model/`):
- `Customer` stores embedded `List<Account>` but accounts are also stored independently in the `accounts` collection. Has both a MongoDB string `custId` and a sequential integer `custNumber`.
- `Account` is abstract; has both a MongoDB string `accId` and a sequential integer `accountNumber`. Implements abstract `getType()` returning `"checking"` or `"savings"`. `CheckingsAccount` allows overdraft down to -$500; `SavingsAccount` requires a minimum $100 balance after withdrawal.

**Repositories** (`repository/`) are Spring Data MongoDB interfaces — no implementation needed.

**Security** (`security/`):
- All routes are open (`permitAll`) — Spring Security is present but not enforcing auth
- CORS allows `http://localhost:5173` and `https://smg-bank-app-clean.vercel.app`

### API Routes
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/customers` | All customers |
| POST | `/api/customers` | Create customer |
| PUT | `/api/customers/{id}` | Rename customer |
| DELETE | `/api/customers/{id}` | Delete customer |
| POST | `/api/customers/{id}/deposit` | Deposit to customer's first account |
| POST | `/api/customers/{id}/withdraw` | Withdraw from customer's first account |
| GET | `/api/accounts` | All accounts |
| POST | `/api/accounts` | Create account (`type`: `"checking"` or `"savings"`) |
| POST | `/api/accounts/{id}/deposit` | Deposit by account ID |
| POST | `/api/accounts/{id}/withdraw` | Withdraw by account ID |
| DELETE | `/api/accounts/{id}` | Delete account |

### Frontend

React Router is configured in `App.tsx`. API calls are centralized in `src/api/customerApi.ts` and `src/api/accountApi.ts` — they read `VITE_API_URL` and call fetch directly (no HTTP client library).

**Component notes:**
- `CustomerList` — displays `customerNumber` (sequential int) in the ID column, not the raw MongoDB `custId`. Deletes by `customer.id` (Spring serializes `custId` as `id` via the default `id` alias — use `customer.custId` if that alias is not present).
- `AccountList` — displays `account.accId` for the ID column and `account.customerId` for the owner column. The Account JSON uses `accId` (matching the Java field name) not `id`. Deletes by `account.accId`.
- `CreateAccountForm` — fetches all customers on mount and presents them in a `<select>` dropdown showing `#customerNumber — name`; submits the MongoDB `custId` as the `customerId` payload field. Never accept a free-text customer ID input — users will enter the sequential number rather than the hex string and the backend lookup will fail.

### Sequential ID Strategy
MongoDB generates hex string `_id` values (`custId` on Customer, `accId` on Account). To provide human-readable sequential integers, `CounterService` uses MongoDB's atomic `findAndModify` on a `counters` collection:
- `"customerNumber"` → `Customer.customerNumber` (set in `CustomerService.createCustomer`)
- `"accountNumber"` → `Account.accountNumber` (set in `AccountService.createAccount`)

Use the integer fields for display. Use the MongoDB string IDs (`custId`, `accId`) for all API calls and inter-service lookups.

**Startup migration:** `MigrationService` runs at `ApplicationReadyEvent` and backfills any existing documents where `customerNumber == 0` or `accountNumber == 0`, assigning each the next available sequence value. The check against `0` makes the migration idempotent — re-running the app will skip already-assigned records.

### MongoDB Configuration
Connection is loaded from a `.env` file in `backend/` via the `spring-dotenv` library (not committed). The `MONGO_URI` env var should point to a MongoDB Atlas cluster. Collections used: `customers`, `accounts`, `counters`.
