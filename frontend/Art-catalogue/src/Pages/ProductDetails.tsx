import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import IProduct from "@/Types/Product";
import ProductService from "@/Services/ProductService";
import { Card, CardHeader, CardTitle, CardDescription, CardContent, CardFooter } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import EditButton from "@/components/EditButton";
import Navbar from "@/components/Navbar";

const ProductDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<IProduct | null>(null);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const [message, setMessage] = useState<{type: "success" | "error"; text: string} | null>(null);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const fetchedProduct = await ProductService.getProductById(Number(id));
        setProduct(fetchedProduct);
      } catch (err) {
        setError("Failed to load product details. Please try again later.");
      }
    };

    fetchProduct();
  }, [id]);

  const handleDelete = async () => {

    if(product && product.id != null){

      if(await ProductService.deleteProduct(product.id) == 200) {
        setMessage({type:"success",text: "Product was deleted successfully"});
        setTimeout(() => navigate("/products"),2000);
        
        }else setMessage({type:"error",text:"Product could not be deleted"});      
        
    }
        

  }

  if (error) {
    return <p className="text-red-500">{error}</p>;
  }

  if (!product) {
    return <p className="text-gray-700">Loading product details...</p>;
  }

  return (
    <div className="container mx-auto mt-8 max-w-3xl">
        <Navbar/>
      <Card className="shadow-lg">
        <CardHeader>
          <CardTitle className="text-2xl font-bold">{product.name}</CardTitle>
          <CardDescription>{product.category || "Uncategorized"}</CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <p className="text-gray-700">{product.description || "No description available."}</p>
          <div className="text-gray-700">
            <p>
              <strong>Quantity:</strong> {product.quantity}
            </p>
            <p>
              <strong>Price per Day:</strong> ${product.pricePerDay}
            </p>
          </div>
        </CardContent>
        <CardFooter>
        <div className="mt-4 flex space-x-2">
        <EditButton productId={product.id!} />
        <Button className="text-sm">
          Rent Now
        </Button>
        <Button className="bg-red-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
        onClick={handleDelete}
        >
          Delete product
        </Button>
      </div>
        </CardFooter>
      </Card>

      {message && (

        <p className={`mt-4 text-center ${
            message.type === "success" ? "text-green-500" : "text-red-500"
          }`}>

            {message.text}

        </p>
      )}
    </div>
  );
};

export default ProductDetails;
