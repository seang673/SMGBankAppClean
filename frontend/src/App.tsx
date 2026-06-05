
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css'
import Dashboard from './components/Dashboard'
import CreateAccountForm from './components/CreateAccountForm'
import CreateCustomerForm from './components/CreateCustomerForm'
import CustomerList from './components/CustomerList'
import AccountList from './components/AccountList'
import TransactionPanel from './components/TransactionPanel'

function App() {

  return (
    <>
      <Router>
        <Routes>
          <Route path="/customers" element={<CustomerList />} />
          <Route path="/accounts" element={<AccountList />} />
          <Route path="/create-customer" element={<CreateCustomerForm />} />
          <Route path="/create-account" element={<CreateAccountForm />} />
          <Route path="/transactions" element={<TransactionPanel />} />
          <Route path="/" element={<Dashboard />} />
        </Routes>

      </Router>
    </>
  )
}

export default App
