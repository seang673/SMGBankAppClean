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

      {loading ? (
        <p>Loading...</p>
      ) : (
        <table style={{ borderCollapse: "collapse", width: "100%" }}>
          <thead>
            <tr>
              <th style={thStyle}>ID</th>
              <th style={thStyle}>Type</th>
              <th style={thStyle}>Balance</th>
              <th style={thStyle}>Customer ID</th>
              <th style={thStyle}>Actions</th>
            </tr>
          </thead>

          <tbody>
            {accounts.map((account: any) => (
              <tr key={account.id}>
                <td style={tdStyle}>{account.id}</td>
                <td style={tdStyle}>{account.type}</td>
                <td style={tdStyle}>${account.balance}</td>
                <td style={tdStyle}>{account.customerNumber}</td>
                <td style={tdStyle}>
                  <button onClick={() => deleteAccount(account.accId)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

const thStyle: React.CSSProperties = {
  border: "1px solid #ccc",
  padding: "8px",
  background: "#f5f5f5",
  textAlign: "left",
};

const tdStyle: React.CSSProperties = {
  border: "1px solid #ccc",
  padding: "8px",
};
