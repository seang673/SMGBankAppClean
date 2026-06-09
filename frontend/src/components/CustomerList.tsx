import {useEffect, useState} from 'react';
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
        <table>
          <thead>
            <tr>
              <th>Customer #</th>
              <th>Name</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {customers.map((customer: any) => (
              <tr key={customer.id}>
                <td>{customer.customerNumber}</td>
                <td>{customer.name}</td>
                <td>
                  <button onClick={() => deleteCustomer(customer.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
