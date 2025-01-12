import React, { useEffect, useState } from "react";
import ShoppingCartService from "@/Services/ShoppingCartService";
import ShoppingCart from "@/components/ShoppingCart";
import IShoppingCart from "@/Types/ShoppingCart";
import Navbar from "@/components/Navbar";
import TokenService from "@/Services/TokenService";
import { Navigate } from "react-router-dom";

const MyShoppingCart: React.FC = () => {
  const [cartData, setCartData] = useState<IShoppingCart | null>(null);

  if(localStorage.getItem('token') === null || TokenService.isTokenExpired() == true){

    return <Navigate to='/login'  />
  
  }

  useEffect(() => {
    const fetchShoppingCart = async () => {
      try {
        const fetchedShoppingCart: IShoppingCart = await ShoppingCartService.getShoppingCart();
        setCartData(fetchedShoppingCart);
      } catch (error) {
        console.error("Error fetching cart items:", error);
      }
    };

    fetchShoppingCart();
  }, []);

  if (!cartData) {
    return <p>Loading shopping cart...</p>; // Render a loading state while data is being fetched.
  }

  return(
    <>
    <Navbar />
    <ShoppingCart initialCartData={cartData} />
  </>

  );

 
};

export default MyShoppingCart;
