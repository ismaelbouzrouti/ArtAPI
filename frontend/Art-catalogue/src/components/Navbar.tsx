import React from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import TokenService from "@/Services/TokenService";
import { useNavigate } from "react-router-dom";
const Navbar: React.FC = () => {
  
  const isAdmin = TokenService.isAdmin();
  const navigate = useNavigate();

  const handleLogout = (): void => {
    localStorage.removeItem("token");
    navigate("/login");
  };


  return (
    <nav className="bg-white shadow-md border-b border-gray-200">
      <div className="container mx-auto px-4 flex items-center justify-between h-16">
        {/* Logo */}
        <div className="text-2xl font-bold text-blue-600">
          <Link to="/">ART</Link>
        </div>

        {/* Navigation Links */}
        <div className="flex space-x-4">
          <Link to="/">
            <Button variant="ghost" className="hover:text-blue-600">
              Home
            </Button>
          </Link>
          <Link to="/reservations">
            <Button variant="ghost" className="hover:text-blue-600">
              Reservations
            </Button>
          </Link>
          {isAdmin && 
          (
            <Link to="/create-product">
              <Button variant="ghost" className="hover:text-blue-600">
                Create Product
              </Button>
            </Link>
          )}

        {!isAdmin && 
          (
            <Link to="/shopping-cart">
            <Button variant="ghost" className="hover:text-blue-600">
              My shopping cart
            </Button>
            </Link>
          )}
        </div>

        {/* Profile/Logout */}
        <div>
          <Button variant="ghost" className="hover:text-red-500" onClick={handleLogout}>
            Logout
          </Button>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
