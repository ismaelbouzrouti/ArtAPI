import React from "react";
import IProduct from "../Types/Product";
import ProductCard from "./ProductCard";


const ProductList:React.FC<{products: IProduct[]}> = ({products}) => {

    return(

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>



    );



}

export default ProductList;