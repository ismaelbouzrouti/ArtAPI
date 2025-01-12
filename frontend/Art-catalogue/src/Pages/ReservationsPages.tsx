import React from "react";
import ReservationsList from "@/components/ReservationsList";
import Navbar from "@/components/Navbar";
import TokenService from "@/Services/TokenService";
import AdminReservationsList from "@/components/AdminReservationsList";
import { Navigate } from "react-router-dom";

const ReservationsPage: React.FC = () => {
    const isAdmin = TokenService.isAdmin();
    
    if(localStorage.getItem('token') === null || TokenService.isTokenExpired() == true){

        return <Navigate to='/login'  />
      
      }
    
  return (
   
    <>
    <Navbar />
    {isAdmin ? <AdminReservationsList /> : <ReservationsList />}
  </>
  );
};

export default ReservationsPage;
