import { useState } from "react";
import { Link } from "react-router-dom";

export default function SignupPage() {
  const [formData, setFormData] = useState({ username: "", email: "", password: "" });
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    try {
      const res = await fetch("/api/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formData),
      });

      if (res.ok) {
        setIsSubmitted(true);
      } else {
        const data = await res.json();
        setError(data.message || "Signup failed. Please try again.");
      }
    } catch (err) {
      setError("Server error. Please try again later.");
    }
  };

  if (isSubmitted) {
    return (
      <div className="auth-container">
        <h2>Account Created!</h2>
        <p>verification link sent to <strong>{formData.email}</strong>, please check your spam/junk folder.</p>
        <p>click the link in your email to verify your account before logging in.</p>
        <Link to="/login" className="btn-primary">verified? go to Login</Link>
      </div>
    );
  }

  return (
    <div className="auth-container">
      <h2>Create an Account</h2>
      {error && <p className="error-text">{error}</p>}
      <form onSubmit={handleSubmit}>
        <input 
          type="text" 
          placeholder="username" 
          onChange={(e) => setFormData({...formData, username: e.target.value})}
          required 
        />
        <input 
          type="email" 
          placeholder="Email" 
          onChange={(e) => setFormData({...formData, email: e.target.value})}
          required 
        />
        <input 
          type="password" 
          placeholder="Password" 
          onChange={(e) => setFormData({...formData, password: e.target.value})}
          required 
        />
        <button type="submit">Sign Up</button>
      </form>
      <p>Already have an account? <Link to="/login">Login here</Link></p>
    </div>
  );
}