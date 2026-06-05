import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        gap: "20px",
        padding: "40px",
      }}
    >
      <button
        onClick={() => navigate("/customers")}
        style={buttonStyle}
      >
        Customers
      </button>

      <button
        onClick={() => navigate("/accounts")}
        style={buttonStyle}
      >
        Accounts
      </button>

      <button
        onClick={() => navigate("/create-customer")}
        style={buttonStyle}
      >
        Create Customer
      </button>

      <button
        onClick={() => navigate("/create-account")}
        style={buttonStyle}
      >
        Create Account
      </button>

      <button
        onClick={() => navigate("/transactions")}
        style={buttonStyle}
      >
        Transactions
      </button>
    </div>
  );
}

const buttonStyle: React.CSSProperties = {
  padding: "14px 22px",
  fontSize: "16px",
  borderRadius: "8px",
  border: "1px solid #ccc",
  cursor: "pointer",
  backgroundColor: "#f5f5f5",
  transition: "0.2s",
};
