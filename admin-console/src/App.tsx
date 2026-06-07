import { Link, Route, Routes } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Policies from './pages/Policies';
import Users from './pages/Users';

function App() {
  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="brand">Triminds Security</div>
        <nav>
          <Link to="/">Dashboard</Link>
          <Link to="/users">Usuários</Link>
          <Link to="/policies">Políticas</Link>
        </nav>
      </aside>
      <main className="content">
        <header>
          <h1>Admin Console</h1>
          <p>Gestão centralizada de identidade, políticas e riscos.</p>
        </header>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/users" element={<Users />} />
          <Route path="/policies" element={<Policies />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
