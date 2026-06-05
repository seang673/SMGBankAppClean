import { useState } from 'react';
import { createAccount } from '../api/accountApi';

export default function CreateAccountForm() {
  const [customerId, setCustomerId] = useState('');
  const [balance, setBalance] = useState(0);
  const [type, setType] = useState('checking');

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    await createAccount(customerId, balance, type);
    alert('Account created');
  }

  return (
    <form onSubmit={handleSubmit}>
      <label>Customer ID:</label>
      <input value={customerId} onChange={e => setCustomerId(e.target.value)} placeholder="Customer ID" />
      <label>Initial Balance:</label>
      <input type="number" value={balance} onChange={e => setBalance(Number(e.target.value))} />
      <label>Account Type:</label>
      <select value={type} onChange={e => setType(e.target.value)}>
        <option value="checking">Checking</option>
        <option value="savings">Savings</option>
      </select>
      <button type="submit">Create Account</button>
    </form>
  );
}
