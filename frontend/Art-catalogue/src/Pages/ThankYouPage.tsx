import TokenService from "@/Services/TokenService";
import { useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";

const ThankYouPage = () => {
  const navigate = useNavigate();

  if(localStorage.getItem('token') === null || TokenService.isTokenExpired() == true){

    return <Navigate to='/login'  />
  
  }

  useEffect(() => {
    // Redirect to reservations page after 5 seconds
    const timer = setTimeout(() => {
      navigate("/reservations");
    }, 5000);

    // Clean up the timer
    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
    <div className="bg-white shadow-md rounded-lg p-6 text-center">
      <h1 className="text-3xl font-bold text-green-600 mb-4">Thank You!</h1>
      <p className="text-lg text-gray-700">Your checkout was successful.</p>
      <p className="text-sm text-gray-500">You will be redirected to your reservations in a few seconds.</p>
    </div>
  </div>
  
  );
};

export default ThankYouPage;
