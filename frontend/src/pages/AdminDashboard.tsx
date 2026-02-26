import { Link } from "react-router-dom";

export default function AdminDashboard() {
  const adminModules = [
    {
      title: "User Management",
      description: "View community members and manage account access.",
      path: "/users",
      icon: "👥",
      color: "#3498db"
    },
    {
      title: "Review Moderation",
      description: "Monitor and delete reviews that violate community guidelines.",
      path: "/reviews", // Points to global reviews page
      icon: "✍️",
      color: "#e67e22"
    },
    {
      title: "Library Catalog",
      description: "Browse and manage the global book collection.",
      path: "/books", // Points to books page
      icon: "📚",
      color: "#2ecc71"
    }
  ];

  return (
    <div className="admin-container">
      <header className="admin-header">
        <h1>Admin Control Panel</h1>
        <p>System overview and moderation tools.</p>
      </header>

      <div className="admin-grid">
        {adminModules.map((module) => (
          <Link to={module.path} key={module.title} className="admin-card-link">
            <div className="admin-card" style={{ borderTop: `4px solid ${module.color}` }}>
              <span className="module-icon">{module.icon}</span>
              <h3>{module.title}</h3>
              <p>{module.description}</p>
              <span className="btn-manage">Manage →</span>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}