import React, { useEffect, useState } from "react";
import IProduct from "@/Types/Product";
import ProductService from "@/Services/ProductService";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"


interface ProductFormProps {
  isEdit?: boolean;
  initialProduct?: IProduct | null;
}

const ProductForm: React.FC<ProductFormProps> = ({ isEdit = false, initialProduct = null }) => {
  const [formData, setFormData] = useState<IProduct>(
    initialProduct || {
      name: "",
      description: "",
      category: "",
      quantity: 0,
      pricePerDay: 0,
    }
  );

  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  useEffect(() => {
    if (isEdit && initialProduct) {
      setFormData(initialProduct);
    }
  }, [isEdit, initialProduct]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsSubmitting(true);
    setError(null);
    setSuccessMessage(null);

    // Validate quantity and pricePerDay
  if (isNaN(formData.quantity) || formData.quantity == 0) {
    setError("Quantity must be a positive integer and bigger than 0.");
    setIsSubmitting(false);
    return;
  }

  if (isNaN(formData.pricePerDay) || formData.pricePerDay == 0) {
    setError("Price per Day must be a positive number with up to two decimal places and bigger than 0.");
    setIsSubmitting(false);
    return;
  }

  if (formData.category == "") {
    setError("You must select a category.");
    setIsSubmitting(false);
    return;
  }





    try {
      if (isEdit && initialProduct?.id) {
        if(await ProductService.editProduct(initialProduct.id, formData) == 200)setSuccessMessage("Product updated successfully!");
        else setError("product could not be edited");

        
      } else {
        if( await ProductService.createProduct(formData) == 200)setSuccessMessage("Product created successfully!");
        else setError("product could not be created");
        setFormData({
          name: "",
          description: "",
          category: "",
          quantity: 0,
          pricePerDay: 0,
        });
      }
    } catch (err) {
      setError("Failed to submit the form. Please try again.");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="container mx-auto max-w-md mt-8">
      <h1 className="text-2xl font-bold text-center mb-6">
        {isEdit ? "Edit Product" : "Create a New Product"}
      </h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        {error && <p className="text-red-500">{error}</p>}
        {successMessage && <p className="text-green-500">{successMessage}</p>}

        <div>
          <label htmlFor="name" className="block text-sm font-medium text-gray-700">
            Name
          </label>
          <Input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
            placeholder="Product Name"
          />
        </div>

        <div>
          <label htmlFor="description" className="block text-sm font-medium text-gray-700">
            Description
          </label>
          <Textarea
            id="description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            placeholder="Product Description"
          />
        </div>

        <div>
          <label htmlFor="category" className="block text-sm font-medium text-gray-700">
            Category
          </label>
          <Select
    value={formData.category}
    onValueChange={(value) => setFormData({ ...formData, category: value })}
    required
  >
    <SelectTrigger id="category" className="w-full">
      {formData.category || "Select a category"}
    </SelectTrigger>
    <SelectContent>
      <SelectItem value="CABLE">CABLE</SelectItem>
      <SelectItem value="LIGHTING">LIGHTING</SelectItem>
      <SelectItem value="PANELS">PANELS</SelectItem>
    </SelectContent>
  </Select>
        </div>

        <div>
          <label htmlFor="quantity" className="block text-sm font-medium text-gray-700">
            Quantity
          </label>
          <Input
            type="text"
            id="quantity"
            name="quantity"
            value={formData.quantity}
            onChange={handleChange}
            required
            placeholder="Product Quantity"
            min={1}
          />
        </div>

        <div>
          <label htmlFor="pricePerDay" className="block text-sm font-medium text-gray-700">
            Price per Day
          </label>
          <Input
            type="text"
            id="pricePerDay"
            name="pricePerDay"
            value={formData.pricePerDay}
            onChange={handleChange}
            required
            placeholder="Price per Day"
          />
        </div>

        <Button type="submit" disabled={isSubmitting} className="w-full">
          {isSubmitting ? "Submitting..." : isEdit ? "Update Product" : "Create Product"}
        </Button>
      </form>
    </div>
  );
};

export default ProductForm;
