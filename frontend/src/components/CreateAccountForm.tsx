import { useState } from "react";
import { createAccount } from "../api/accountApi";

export default function CreateAccountForm() {
  const [customerId, setCustomerId] = useState("");
  const [balance, setBalance] = useState(0);
  const [type, setType] = useState("checking");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      await createAccount(customerId, balance, type);
      alert("Account created successfully");
      setCustomerId("");
      setBalance(0);
      setType("checking");
    } catch (error) {
      console.error("Error creating account:", error);
      alert("Failed to create account");
    }
  }

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>Open New Account</h2>

      <form onSubmit={handleSubmit} style={styles.form}>
        <label style={styles.label}>Customer ID</label>
        <input
          style={styles.input}
          value={customerId}
          onChange={(e) => setCustomerId(e.target.value)}
          placeholder="Enter customer ID"
        />

        <label style={styles.label}>Initial Balance</label>
        <input
          type="number"
          style={styles.input}
          value={balance}
          onChange={(e) => setBalance(Number(e.target.value))}
        />

        <label style={styles.label}>Account Type</label>
        <select
          style={styles.input}
          value={type}
          onChange={(e) => setType(e.target.value)}
        >
          <option value="checking">Checking</option>
          <option value="savings">Savings</option>
        </select>

        <button type="submit" style={styles.button}>
          Create Account
        </button>
      </form>
    </div>
  );
}

const styles = {
  container: {
    maxWidth: "400px",
    margin: "30px auto",
    padding: "20px",
    borderRadius: "10px",
    background: "#f8f9fa",
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
  },
  title: {
    textAlign: "center" as const,
    marginBottom: "20px",
    color: "#333",
  },
  form: {
    display: "flex",
    flexDirection: "column" as const,
    gap: "15px",
  },
  label: {
    fontWeight: "bold",
    color: "#444",
  },
  input: {
    padding: "10px",
    borderRadius: "6px",
    border: "1px solid #ccc",
    fontSize: "16px",
  },
  button: {
    padding: "12px",
    background: "#28a745",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontSize: "16px",
    fontWeight: "bold",
  },
};
