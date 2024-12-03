import React, { useEffect, useState } from "react";
import ProductForm from "@/components/ProductForm";
import ProductService from "@/Services/ProductService";
import { useParams } from "react-router-dom";
import IProduct from "@/Types/Product";
import Navbar from "@/components/Navbar";

const EditProduct: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<IProduct | null>(null);

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
