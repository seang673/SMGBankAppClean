import React, {useEffect, useState} from 'react';
import {getCustomers, deleteCustomer} from '../api/customerApi';

export default function CustomerList() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    getCustomers().then(data => {
      setCustomers(data);
      setLoading(false);
    });
  }, []);

  return (
    <div>
      <h2>Customers</h2>

      {loading ? (
        <p>Loading...</p>
      ) : (
        <table style={{ borderCollapse: "collapse", width: "100%" }}>
          <thead>
            <tr>
              <th style={thStyle}>ID</th>
              <th style={thStyle}>Name</th>
              <th style={thStyle}>Actions</th>
            </tr>
          </thead>

          <tbody>
            {customers.map((customer: any) => (
              <tr key={customer.id}>
                <td style={tdStyle}>{customer.id}</td>
                <td style={tdStyle}>{customer.name}</td>
                <td style={tdStyle}>
                  <button onClick={() => deleteCustomer(customer.id)}>
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
