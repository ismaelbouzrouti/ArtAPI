import React from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";

const EditButton: React.FC<{productId: number}> = ({ productId }) => {
  return (
    <Link to={`/edit-product/${productId}`}>
      <Button variant="outline" className="text-sm">
        Edit Product
      </Button>
    </Link>
  );
};

export default EditButton;
