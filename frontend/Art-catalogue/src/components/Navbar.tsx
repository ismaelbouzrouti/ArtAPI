import React from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { useState } from "react";

const Navbar: React.FC = () => {
  //const [userRole, setUserRole] = useState<string | null>(null); // Placeholder for role, replace with actual logic

  return (
    <nav className="bg-white shadow-md border-b border-gray-200">
      <div className="container mx-auto px-4 flex items-center justify-between h-16">
        {/* Logo */}
        <div className="text-2xl font-bold text-blue-600">
          <Link to="/">MyProject</Link>
        </div>

        {/* Navigation Links */}
        <div className="flex space-x-4">
          <Link to="/">
            <Button variant="ghost" className="hover:text-blue-600">
              Home
            </Button>
          </Link>
          <Link to="/my-reservations">
            <Button variant="ghost" className="hover:text-blue-600">
              My Reservations
            </Button>
          </Link>
          {/* Conditional rendering for role-based access */}
          {//userRole === "ADMIN" && 
          (
            <Link to="/create-product">
              <Button variant="outline" className="hover:bg-blue-600 hover:text-white">
                Create Product
              </Button>
            </Link>
          )}
        </div>

        {/* Profile/Logout */}
        <div>
          <Button variant="ghost" className="hover:text-red-500">
            Logout
          </Button>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
