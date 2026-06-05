export async function getCustomers() {
  const res = await fetch('/api/customers');
  return res.json();
}

export async function createCustomer(name: string) {
  const res = await fetch('/api/customers', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name})
  });
  return res.json();
}

export async function deleteCustomer(customerId: string) {
    const res = await fetch(`/api/customers/${customerId}`, {
        method: 'DELETE',
    });
    return res.json();
}


