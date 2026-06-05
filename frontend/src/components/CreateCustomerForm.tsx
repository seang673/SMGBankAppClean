import { useState } from 'react';
import { createCustomer} from '../api/customerApi';

export default function CreateCustomerForm() {
  const [name, setName] = useState('');

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    try {
        await createCustomer(name);
        alert('Customer created');
    } catch (error) {
        console.error('Error creating customer:', error);
        alert('Failed to create customer');
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <p>Customer Name:</p>
      <input value={name} onChange={e => setName(e.target.value)} placeholder="Customer Name" />
      <button type="submit">Create Customer</button>
    </form>
  );

}