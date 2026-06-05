import {useEffect, useState} from 'react';
import { getAccounts, deleteAccount} from '../api/accountApi';

export default function AccountList() {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    getAccounts().then(data => {
      setAccounts(data);
      setLoading(false);
    });
    }, []);

    return (
        <div>
            <h2>Accounts</h2>
            {loading ? <p>Loading...</p> : (
                <ul>
                    {accounts.map((account: any) => (
                        <li key={account.id}>
                            {account.type} Account: ${account.balance} (ID: {account.id}) (Customer: {account.customerId})
                            <button onClick={() => deleteAccount(account.id)}>Delete</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
