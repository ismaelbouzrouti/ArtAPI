import React from "react";
import Navbar from "@/components/Navbar";
import ProductForm from "@/components/ProductForm";
import { Navigate } from "react-router-dom";
import TokenService from "@/Services/TokenService";

const CreateProduct: React.FC = () => {


  if(localStorage.getItem('token') === null || TokenService.isTokenExpired() == true || TokenService.isAdmin() == false){

    return <Navigate to='/login'  />
  
  }

  
  return (

  <div>
    <Navbar/>
    <ProductForm />
  </div>



  );
};

export default CreateProduct;
