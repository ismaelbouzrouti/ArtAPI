import React, { useState } from "react";
import ILoginData from "@/Types/LoginData";
import AuthService from "@/Services/AuthService";
import { Navigate, Link } from "react-router-dom";

const LoginPage: React.FC = () => {
  const [formData, setFormData] = useState<ILoginData>({ username: "", password: "" });
  const [error, setError] = useState<string | null>(null);
  const [redirectToLogin, setRedirectToLogin] = useState<boolean>(false);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [errors, setErrors] = useState<{ [key: string]: string }>({});

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const validateForm = (): boolean => {
    const newErrors: { [key: string]: string } = {};

    // Username validation: non-blank, min 3 characters, max 20 characters
    if (!formData.username.trim()) {
      newErrors.username = "Username must not be blank.";
    } else if (formData.username.length < 3 || formData.username.length > 20) {
      newErrors.username = "Username must be between 3 and 20 characters.";
    }

    // Password validation: non-blank, min 8 characters, max 50 characters
    if (!formData.password.trim()) {
      newErrors.password = "Password must not be blank.";
    } else if (formData.password.length < 8 || formData.password.length > 50) {
      newErrors.password = "Password must be between 8 and 50 characters.";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault();
    setError(null);

    if (!validateForm()) {
      return; // Exit early if validation fails
    }

    try {
      const token = await AuthService.login(formData);

      if (token) {
        localStorage.setItem("token", token);
        setSuccessMessage(`Login successful, Welcome ${formData.username}!`);

        // Delay redirection for showing the success message
        setTimeout(() => {
          setRedirectToLogin(true);
        }, 1000);
      } else {
        setError("Invalid username or password.");
      }
    } catch (err) {
      setError("An error occurred. Please try again later.");
    }
  };

  if (redirectToLogin) {
    return <Navigate to="/" />;
  }

  return (
    <div className="container mx-auto max-w-md mt-8">
      <h1 className="text-2xl font-bold text-center mb-6">Login</h1>
      {error && <p className="text-red-500">{error}</p>}
      {successMessage && <p className="text-green-500">{successMessage}</p>}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="username" className="block text-sm font-medium text-gray-700">
            Username
          </label>
          <input
            type="text"
            id="username"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
            className="block w-full border-gray-300 rounded-md"
            placeholder="Username"
          />
          {errors.username && <p className="text-red-500 text-sm">{errors.username}</p>}
        </div>
        <div>
          <label htmlFor="password" className="block text-sm font-medium text-gray-700">
            Password
          </label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            className="block w-full border-gray-300 rounded-md"
            placeholder="Password"
          />
          {errors.password && <p className="text-red-500 text-sm">{errors.password}</p>}
        </div>
        <button
          type="submit"
          className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600"
        >
          Login
        </button>
      </form>
      <br />
      <Link className="hover:bg-sky-700" to="/register">
        <u>No account yet? Register here!</u>
      </Link>
    </div>
  );
};

export default LoginPage;
