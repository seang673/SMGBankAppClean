import {useEffect, useState} from 'react';
import { getCustomers} from '../api/customerApi';

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
            {loading ? <p>Loading...</p> : (
                <ul>
                    {customers.map((customer: any) => (
                        <li key={customer.id}>
                            {customer.name} (ID: {customer.id})
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
