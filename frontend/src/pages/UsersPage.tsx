import { useEffect, useState } from "react";
import { useAuth } from "../features/auth/useAuth";
import { UserCard } from "../Components/UserCard";

interface UserListItem {
  username: string;
}

export default function UsersPage() {
  const [users, setUsers] = useState<UserListItem[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const { user } = useAuth();

  const isAdmin = user?.role === "ROLE_ADMIN";

  const fetchUsers = () => {
    fetch(`/api/users?page=${currentPage}&size=20`)
      .then((res) => res.json())
      .then((data) => {
        setUsers(data.content);
        setTotalPages(data.totalPages);
      });
  };

  useEffect(() => {
    fetchUsers();
  }, [currentPage]);

  const handleDelete = async (username: string) => {
    if (!window.confirm(`Are you sure you want to delete ${username}?`)) return;

    const res = await fetch(`/api/users/${username}`, {
      method: "DELETE",
    });

    if (res.ok) {
      // Refresh the list or filter local state
      setUsers(users.filter(u => u.username !== username));
    }
  };

  return (
    <div className="users-page">
      <h1>Community Members</h1>
      
      <div className="user-list">
        {users.map((u) => (
          <UserCard 
            key={u.username} 
            username={u.username} 
            isAdmin={isAdmin}
            onDelete={handleDelete}
          />
        ))}
      </div>

      {totalPages > 1 && (
        <div className="pagination">
          <button disabled={currentPage === 0} onClick={() => setCurrentPage(p => p - 1)}>Prev</button>
          <span>{currentPage + 1} / {totalPages}</span>
          <button disabled={currentPage >= totalPages - 1} onClick={() => setCurrentPage(p => p + 1)}>Next</button>
        </div>
      )}
    </div>
  );
}