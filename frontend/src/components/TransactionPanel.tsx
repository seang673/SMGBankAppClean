import { useState } from "react";

export default function TransactionForm() {
  const [accountId, setAccountId] = useState("");
  const [amount, setAmount] = useState<number>(0);
  const [type, setType] = useState<"deposit" | "withdraw">("deposit");

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();

    if (!accountId || amount <= 0) {
      alert("Please enter a valid account ID and amount.");
      return;
    }

    try {
      const res = await fetch(`/api/accounts/${accountId}/${type}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: new URLSearchParams({ amount: amount.toString() })
      });

      if (!res.ok) {
        const errorText = await res.text();
        alert(`Failed: ${errorText}`);
        return;
      }

      const data = await res.json();
      alert(`Success! New balance: $${data.balance}`);
    } catch (err) {
      alert("An error occurred while processing the transaction.");
    }
  }

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "12px",
        maxWidth: "350px"
      }}
    >
      <h2>Account Transaction</h2>

      <input
        type="text"
        placeholder="Account ID"
        value={accountId}
        onChange={(e) => setAccountId(e.target.value)}
      />

      <input
        type="number"
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(Number(e.target.value))}
      />

      <div>
        <label>
          <input
            type="radio"
            value="deposit"
            checked={type === "deposit"}
            onChange={() => setType("deposit")}
          />
          Deposit
        </label>

        <label style={{ marginLeft: "15px" }}>
          <input
            type="radio"
            value="withdraw"
            checked={type === "withdraw"}
            onChange={() => setType("withdraw")}
          />
          Withdraw
        </label>
      </div>

      <button type="submit">Submit</button>
    </form>
  );
}
