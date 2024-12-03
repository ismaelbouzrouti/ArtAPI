import React, { useEffect, useState } from "react";
import ProductService from "../Services/ProductService";
import IProduct from "../Types/Product";
import ProductFilter from "../components/ProductFilter";
import ProductList from "../components/ProductList";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";
import Navbar from "@/components/Navbar";

const ProductCatalogue: React.FC = () => {
  const [products, setProducts] = useState<IProduct[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [categories] = useState<string[]>(["CABLE", "LIGHTING", "PANELS"]);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        let fetchedProducts: IProduct[] = [];
        if (!selectedCategory) {
          fetchedProducts = await ProductService.getProducts();
        } else {
          fetchedProducts = await ProductService.getProdyctsByCategory(
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

      <footer className="mt-8 flex justify-center">
        <Link to="/create-product">
          <Button
            className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 text-lg font-semibold shadow-lg rounded-lg"
          >
            Create Product
          </Button>
        </Link>
      </footer>
    </div>
  );
};

export default ProductCatalogue;
