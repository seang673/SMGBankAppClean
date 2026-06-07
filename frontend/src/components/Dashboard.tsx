import React from "react";
import { useNavigate } from "react-router-dom";
import "../dashboard.css";
 
const NAV_ITEMS = [
  { label: "Customers",       path: "/customers"       },
  { label: "Accounts",        path: "/accounts"        },
  { label: "Create Customer", path: "/create-customer" },
  { label: "Create Account",  path: "/create-account"  },
  { label: "Transactions",    path: "/transactions"    },
];
 
export default function Dashboard() {
  const navigate = useNavigate();
 
  return (
    
    <div className="dashboard">
      {NAV_ITEMS.map(({ label, path }) => (
        <button
          key={path}
          className="dash-btn"
          onClick={() => navigate(path)}
        >
          {label}
        </button>
      ))}
    </div>

  );
}
 