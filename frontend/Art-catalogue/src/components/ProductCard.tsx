import React from "react";
import IProduct from "../Types/Product";
import { Button } from "./ui/button";
import { Link } from "react-router-dom";

const ProductCard: React.FC<{product: IProduct}> = ({product}) => {

return (

    <div className="border border-gray-200 rounded-lg shadow-md p-4 bg-white">
      <h2 className="text-xl font-semibold text-gray-800">{product.name}</h2>
      <p className="text-sm text-gray-600 mb-2">{product.description || 'No description available.'}</p>
      <p className="text-sm font-medium text-gray-700">
        <span className="font-semibold">Category:</span> {product.category || 'Uncategorized'}
      </p>
      <p className="text-sm font-medium text-gray-700">
        <span className="font-semibold">Quantity:</span> {product.quantity}
      </p>
      <p className="text-sm font-medium text-gray-700">
        <span className="font-semibold">Price per Day:</span> ${product.pricePerDay}
      </p>
      <Button><Link to ={`products/${product.id}`}>View details</Link></Button>
    </div>

);


};

export default ProductCard;