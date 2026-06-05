import React, { useState } from "react";
import { createCustomer } from "../api/customerApi";

export default function CreateCustomerForm() {
  const [name, setName] = useState("");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
      await createCustomer(name);
      alert("Customer created successfully");
      setName("");
    } catch (error) {
      console.error("Error creating customer:", error);
      alert("Failed to create customer");
    }
  }

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>Create New Customer</h2>

      <form onSubmit={handleSubmit} style={styles.form}>
        <label style={styles.label}>Full Name</label>
        <input
          style={styles.input}
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="Enter customer name"
        />

        <button type="submit" style={styles.button}>
          Create Customer
        </button>
      </form>
    </div>
  );
}

const styles: Record<string, React.CSSProperties> = {
  container: {
    maxWidth: "400px",
    margin: "30px auto",
    padding: "20px",
    borderRadius: "10px",
    background: "#f8f9fa",
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
  },
  title: {
    textAlign: "center",
    marginBottom: "20px",
    color: "#333",
  },
  form: {
    display: "flex",
    flexDirection: "column",
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
    background: "#007bff",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontSize: "16px",
    fontWeight: "bold",
  },
};
