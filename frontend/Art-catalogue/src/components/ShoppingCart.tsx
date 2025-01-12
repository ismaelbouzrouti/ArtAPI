import React, { useState, useEffect } from "react";
import IShoppingCart from "@/Types/ShoppingCart";
import ShoppingCartService from "@/Services/ShoppingCartService";
import { Navigate } from "react-router-dom";

interface ShoppingCartProps {
  initialCartData: IShoppingCart;
}

const ShoppingCart: React.FC<ShoppingCartProps> = ({ initialCartData }) => {
  const [cartData, setCartData] = useState<IShoppingCart>(initialCartData);
  const [selectedDate, setSelectedDate] = useState<string>("");
  const [checkoutMessage, setCheckoutMessage] = useState<string | null>(null);
  const [redirectToLogin, setRedirectToLogin] = useState<boolean>(false);
  useEffect(() => {
    // Set the initial return date from the backend
    if (cartData.returnDate) {
      setSelectedDate(cartData.returnDate);
    }
  }, [cartData]);

  const refreshCartData = async () => {
    try {
      const updatedCartData = await ShoppingCartService.getShoppingCart();
      setCartData(updatedCartData);

      // Update the selected date if the cart data changes
      if (updatedCartData.returnDate) {
        setSelectedDate(updatedCartData.returnDate);
      }
    } catch (error) {
      console.error("Failed to fetch updated cart data:", error);
    }
  };

  const handleDateChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
    const newDate = event.target.value; // 'YYYY-MM-DD' string
    setSelectedDate(newDate);

    try {
      const status = await ShoppingCartService.changeReturnDate(newDate);
      if (status === 200) {
        await refreshCartData();
      } else {
        console.error("Failed to update return date on the server");
      }
    } catch (error) {
      console.error("Error while updating return date:", error);
    }
  };

  const handleIncrement = async (id: number) => {
    try {
      const status = await ShoppingCartService.incrementItem(id);
      if (status === 200 || status === 201) {
        await refreshCartData();
      } else {
        console.error("Failed to increment item on the server");
      }
    } catch (error) {
      console.error("Failed to increment item:", error);
    }
  };

  const handleDecrement = async (id: number) => {
    try {
      const status = await ShoppingCartService.decrementItem(id);
      if (status === 200 || status === 201) {
        await refreshCartData();
      } else {
        console.error("Failed to decrement item on the server");
      }
    } catch (error) {
      console.error("Failed to decrement item:", error);
    }
  };

  const handleRemove = async (itemId: number) => {
    try {
      const status = await ShoppingCartService.removeItem(itemId);
      if (status === 200) {
        await refreshCartData();
      } else {
        console.error(`Failed to remove item with ID ${itemId}. Status: ${status}`);
      }
    } catch (error) {
      console.error("An error occurred while removing the item:", error);
    }
  };

  const handleCheckout = async () => {
    try {
      const status = await ShoppingCartService.checkOut();
      if (status === 200) {
        setCheckoutMessage("Checkout successful!");
          // Delay redirection for showing the success message
          setTimeout(() => {
            setRedirectToLogin(true);
          }, 1000);
        await refreshCartData();
      } else {
        setCheckoutMessage("Checkout failed. Please try again.");
      }
    } catch (error) {
      console.error("Checkout failed:", error);
      setCheckoutMessage("An error occurred during checkout.");
    }
  };

  if (redirectToLogin) {
    return <Navigate to="/thank-you" />;
  }

  if (!cartData.cartItemDTOList || cartData.cartItemDTOList.length === 0) {
    return (
      <div className="text-center mt-10 text-gray-500">
        <p className="text-xl">Your shopping cart is empty.</p>
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto mt-10 p-6 bg-white shadow-lg rounded-lg">
      <h2 className="text-2xl font-semibold mb-4 text-gray-800">Your Shopping Cart</h2>
      <ul className="divide-y divide-gray-200">
        {cartData.cartItemDTOList.map((item) => (
          <li key={item.id} className="py-4 flex justify-between items-center">
            <div>
              <h3 className="text-lg font-medium text-gray-800">{item.name}</h3>
              <p className="text-sm text-gray-500">Category: {item.category}</p>
              <p className="text-sm text-gray-500">Price: ${item.price.toFixed(2)}</p>
              <p className="text-sm text-gray-500">Quantity: {item.quantity}</p>
            </div>
            <div className="flex space-x-2">
              <button
                className="bg-blue-500 text-white px-3 py-1 rounded-md hover:bg-blue-600 transition"
                onClick={() => handleIncrement(item.id!)}
              >
                +
              </button>
              <button
                className="bg-blue-500 text-white px-3 py-1 rounded-md hover:bg-blue-600 transition"
                onClick={() => handleDecrement(item.id!)}
              >
                -
              </button>
              <button
                className="bg-red-500 text-white px-3 py-1 rounded-md hover:bg-red-600 transition"
                onClick={() => handleRemove(item.id!)}
              >
                Remove
              </button>
            </div>
          </li>
        ))}
      </ul>

      <div className="mt-6">
        <label htmlFor="return-date" className="block text-gray-700 font-medium mb-2">
          Select Return Date:
        </label>
        <input
          id="return-date"
          type="date"
          className="border rounded-md p-2"
          value={selectedDate}
          onChange={handleDateChange}
        />
        <p className="text-gray-500 mt-2">Current Return Date: {selectedDate}</p>
      </div>

      <div className="mt-6 flex justify-between items-center">
        <h3 className="text-xl font-semibold text-gray-800">
          Total: ${cartData.totalPrice.toFixed(2)}
        </h3>
        <button onClick={handleCheckout} className="bg-green-500 text-white px-6 py-2 rounded-md hover:bg-green-600 transition">
          Checkout
        </button>
      </div>
      {checkoutMessage && (
        <div className="mt-4 text-center">
          <p className={`text-lg ${checkoutMessage.includes("successful") ? "text-green-500" : "text-red-500"}`}>
            {checkoutMessage}
          </p>
        </div>)}
    </div>
  );
};

export default ShoppingCart;
