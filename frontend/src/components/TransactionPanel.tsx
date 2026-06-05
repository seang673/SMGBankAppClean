import { useState } from "react";

export default function TransactionForm() {
  const [accountId, setAccountId] = useState("");
  const [amount, setAmount] = useState<number>(0);
  const [type, setType] = useState<"deposit" | "withdraw">("deposit");
  const [loading, setLoading] = useState(false);

  const API_BASE = import.meta.env.VITE_API_BASE_URL;

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    if (!accountId || amount <= 0) {
      alert("Please enter a valid account ID and amount.");
      return;
    }

    setLoading(true);

    try {
      const res = await fetch(
        `${API_BASE}/api/accounts/${accountId}/${type}`,
        {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({ amount: amount.toString() })
        }
      );

      if (!res.ok) {
        const errorText = await res.text();
        alert(`Failed: ${errorText}`);
        setLoading(false);
        return;
      }

      const data = await res.json();
      alert(`Success! New balance: $${data.balance}`);

      // Reset form
      setAccountId("");
      setAmount(0);
      setType("deposit");
    } catch (err) {
      alert("An error occurred while processing the transaction.");
    }

    setLoading(false);
  }

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>Account Transaction</h2>

      <form onSubmit={handleSubmit} style={styles.form}>
        <label style={styles.label}>Account ID</label>
        <input
          style={styles.input}
          type="text"
          value={accountId}
          onChange={(e) => setAccountId(e.target.value)}
          placeholder="Enter account ID"
          required
        />

        <label style={styles.label}>Amount</label>
        <input
          style={styles.input}
          type="number"
          min="0.01"
          step="0.01"
          value={amount}
          onChange={(e) => setAmount(Number(e.target.value))}
          placeholder="Enter amount"
          required
        />

        <label style={styles.label}>Transaction Type</label>
        <select
          style={styles.input}
          value={type}
          onChange={(e) => setType(e.target.value as "deposit" | "withdraw")}
        >
          <option value="deposit">Deposit</option>
          <option value="withdraw">Withdraw</option>
        </select>

        <button type="submit" style={styles.button} disabled={loading}>
          {loading ? "Processing..." : "Submit Transaction"}
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
    boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
  },
  title: {
    textAlign: "center" as const,
    marginBottom: "20px",
    color: "#333"
  },
  form: {
    display: "flex" as const,
    flexDirection: "column" as const,
    gap: "15px"
  },
  label: {
    fontWeight: "bold",
    color: "#444"
  },
  input: {
    padding: "10px",
    borderRadius: "6px",
    border: "1px solid #ccc",
    fontSize: "16px"
  },
  button: {
    padding: "12px",
    background: "#17a2b8",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontSize: "16px",
    fontWeight: "bold"
  }
};
