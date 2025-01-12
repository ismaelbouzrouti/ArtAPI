import { Routes, Route } from 'react-router-dom';
import ProductCatalogue from './Pages/ProductCatalogue';
import ProductDetails from './Pages/ProductDetails';
import EditProduct from './Pages/EditProduct';
import CreateProduct from './Pages/CreateProduct';
import './App.css';
import LoginPage from './Pages/LoginPage';
import RegistrationPage from './Pages/RegistrationPage';
import MyShoppingCart from './Pages/MyShoppingCart';
import ReservationsPage from './Pages/ReservationsPages';
import ThankYouPage from './Pages/ThankYouPage';

function App() {
  return (
    <div className="container">
      <Routes>
        <Route path="/" element={<ProductCatalogue />} />
        <Route path="/products/:id" element={<ProductDetails />} />
        <Route path="/edit-product/:id" element={<EditProduct />} />
        <Route path="/create-product" element={<CreateProduct />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegistrationPage />} />
        <Route path="/shopping-cart" element={<MyShoppingCart />} />
        <Route path="/reservations" element={<ReservationsPage />} />
        <Route path="/thank-you" element={<ThankYouPage />} />
      </Routes>
    </div>
  );
}

export default App;
