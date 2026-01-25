import { Outlet } from "react-router-dom";
import Navbar from "../Components/NavBar";
import { useAuth } from "../features/auth/useAuth";

export default function MainLayout() {
    const { loading } = useAuth();

    if (loading) {
        return <div className="loading-screen">Loading...</div>
    }

    return (
        <div className="app-layout">
            <header>
                 <Navbar />
            </header>

            <main className="container">
                {/* this is where page components ie home search edit render*/}
                <Outlet />
            </main>

            <footer>
                <p>&copy; Wes books reviews</p>
            </footer>
        </div>
    );
}