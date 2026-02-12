interface UserCardProps {
  username: string;
  isAdmin: boolean;
  onDelete: (username: string) => void;
}

export function UserCard({ username, isAdmin, onDelete }: UserCardProps) {
  return (
    <div className="user-card">
      <div className="user-info">
        <span className="user-icon">👤</span>
        <span className="username">{username}</span>
      </div>
      
      {isAdmin && (
        <button 
          className="btn-delete-user" 
          onClick={() => onDelete(username)}
          title="Delete User"
        >
          Delete
        </button>
      )}
    </div>
  );
}