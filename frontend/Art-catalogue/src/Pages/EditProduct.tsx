import React, { useEffect, useState } from "react";
import ProductForm from "@/components/ProductForm";
import ProductService from "@/Services/ProductService";
import { useParams } from "react-router-dom";
import IProduct from "@/Types/Product";
import Navbar from "@/components/Navbar";
import { Navigate } from "react-router-dom";
import TokenService from "@/Services/TokenService";

const EditProduct: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<IProduct | null>(null);

  if(localStorage.getItem('token') === null ||TokenService.isTokenExpired() == true || TokenService.isAdmin() == false){

    return <Navigate to='/login'  />
  
  }


  useEffect(() => {
    const fetchProduct = async () => {
      if (id) {
        const fetchedProduct = await ProductService.getProductById(Number(id));
        setProduct(fetchedProduct);
      }
    };

    fetchProduct();
  }, [id]);

  if (!product) {
    return <p>Loading...</p>;
  }

  return (
  <div>
  <Navbar/>
  <ProductForm isEdit={true} initialProduct={product} />
  </div>

  )
};

export default EditProduct;
