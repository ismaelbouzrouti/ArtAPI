import React from "react";

const ProductFilter:React.FC<{


    categories: string[];
    selectedCategory: string | null;
    onCategoryChange: (selectedCategory: string | null) => void;
}> = ({categories, selectedCategory, onCategoryChange}) => {

const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const value = event.target.value;
    onCategoryChange(value || null);
}

return(

    <div className="mb-6">
    <label
      htmlFor="category-filter"
      className="block text-gray-700 text-lg font-medium mb-2"
    >
      Filter by Category:
    </label>
    <select
      id="category-filter"
      value={selectedCategory || ''}
      onChange={handleChange}
      className="block w-full p-2 border rounded-lg text-gray-700 bg-white focus:outline-none focus:ring-2 focus:ring-blue-500"
    >
      <option value="">All Categories</option>
      {categories.map((category) => (
        <option key={category} value={category}>
          {category}
        </option>
      ))}
    </select>
  </div>
);



};

export default ProductFilter;