import React, { useEffect, useState } from "react";
import ProductService from "../Services/ProductService";
import IProduct from "../Types/Product";
import ProductFilter from "../components/ProductFilter";
import ProductList from "../components/ProductList";
import { Navigate } from 'react-router-dom';
import Navbar from "@/components/Navbar";

import TokenService from "@/Services/TokenService";


const ProductCatalogue: React.FC = () => {
  const [products, setProducts] = useState<IProduct[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [categories] = useState<string[]>(["CABLE", "LIGHTING", "PANELS"]);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);

  if(localStorage.getItem('token') === null || TokenService.isTokenExpired() == true){

  return <Navigate to='/login'  />

}

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        let fetchedProducts: IProduct[] = [];
        if (!selectedCategory) {
          fetchedProducts = await ProductService.getProducts();
        } else {
          fetchedProducts = await ProductService.getProductsByCategory(
            selectedCategory
          );
        }
        setProducts(fetchedProducts);
      } catch (error) {
        setError(
          selectedCategory
            ? `Failed to load products for the category: ${selectedCategory}`
            : "Failed to load the products. Try again later."
        );
      }
    };
    fetchProducts();
  }, [selectedCategory]);

  const handleCategoryChange = (newCategory: string | null) => {
    setSelectedCategory(newCategory);
  };

  if (error) {
    return <p>{error}</p>;
  }

  if (products.length === 0) {
    return <p>Products are loading...</p>;
  }

  return (
    <div className="product-catalog">
      <Navbar/>
      <h1 className="text-3xl font-bold mb-4">Product Catalogue</h1>
      {/* Product Filter */}
      <ProductFilter
        categories={categories}
        selectedCategory={selectedCategory}
        onCategoryChange={handleCategoryChange}
      />
      {/* Product List */}
      <ProductList products={products} />
    </div>
  );
};

export default ProductCatalogue;
