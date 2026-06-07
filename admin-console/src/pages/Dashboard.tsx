function Dashboard() {
  return (
    <section>
      <h2>Visão geral</h2>
      <p>Resumo de usuários, autenticações e riscos em um único painel.</p>
      <div className="cards">
        <div className="card">Usuários ativos</div>
        <div className="card">Eventos de risco</div>
        <div className="card">Status do gateway</div>
      </div>
    </section>
  );
}

export default Dashboard;
