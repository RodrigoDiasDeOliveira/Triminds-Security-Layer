
import "./index.css";
import ReactDOM from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import Roles from "./pages/Roles";
import Policies from "./pages/Policies";
import Audit from "./pages/Audit";

const qc = new QueryClient();

const App = () => (
  <QueryClientProvider client={qc}>
    <BrowserRouter>
      <nav style={{ padding: 16, borderBottom: "1px solid #ddd" }}>
        <Link to="/">Dashboard</Link> {" | "}
        <Link to="/roles">Roles</Link> {" | "}
        <Link to="/policies">Policies</Link> {" | "}
        <Link to="/audit">Audit</Link>
      </nav>
      <main style={{ padding: 24 }}>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/roles" element={<Roles />} />
          <Route path="/policies" element={<Policies />} />
          <Route path="/audit" element={<Audit />} />
        </Routes>
      </main>
    </BrowserRouter>
  </QueryClientProvider>
);

ReactDOM.createRoot(document.getElementById("root")!).render(<App />);
