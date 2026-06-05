const API = import.meta.env.VITE_API_URL;

export async function getAccounts() {
  const res = await fetch('/api/accounts');
  return res.json();
}

export async function createAccount(customerId: string, balance: number, type: string) {
  const res = await fetch('{API}/api/accounts', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ customerId, balance, type })
  });
  return res.json();
}

export async function withdraw(accountId: string, amount: number) {
  const res = await fetch(`${API}/api/accounts/${accountId}/withdraw`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ amount })
  });
  return res.json();
}

export async function deposit(accountId: string, amount: number) {
  const res = await fetch(`/api/accounts/${accountId}/deposit`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ amount })
  });
  return res.json();
}

export async function deleteAccount(accountId: string) {
  const res = await fetch(`/api/accounts/${accountId}`, {
    method: 'DELETE'
  });
  return res.json();
}
