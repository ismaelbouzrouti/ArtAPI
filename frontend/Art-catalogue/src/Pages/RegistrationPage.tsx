import React, { useState } from "react";
import IUser from "@/Types/User";
import AuthService from "@/Services/AuthService";
import { Navigate } from "react-router-dom";
import { Link } from "react-router-dom";

const RegistrationPage: React.FC = () => {
  const [formData, setFormData] = useState<IUser>({
    userName: "",
    email: "",
    password: "",
    firstName: "",
    lastName: "",
  });
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [errors, setErrors] = useState<{ [key: string]: string }>({});
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [redirectToLogin, setRedirectToLogin] = useState<boolean>(false);

  const validateForm = (): boolean => {
    const newErrors: { [key: string]: string } = {};
  
    // Username validation: non-blank, min 3 characters, max 20 characters
    if (!formData.userName.trim()) {
      newErrors.userName = "Username must not be blank.";
    } else if (formData.userName.length < 3 || formData.userName.length > 20) {
      newErrors.userName = "Username must be between 3 and 20 characters.";
    }
  
    // Email validation: non-blank, valid email format
    if (!formData.email.trim()) {
      newErrors.email = "Email must not be blank.";
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = "Invalid email format.";
    }
  
    // Password validation: non-blank, min 8 characters, max 50 characters
    if (!formData.password.trim()) {
      newErrors.password = "Password must not be blank.";
    } else if (formData.password.length < 8 || formData.password.length > 50) {
      newErrors.password = "Password must be between 8 and 50 characters.";
    }
  
    // Confirm password validation: must match password
    if (formData.password !== confirmPassword) {
      newErrors.confirmPassword = "Passwords do not match.";
    }
  
    // First name validation: non-blank, min 2 characters, max 30 characters
    if (!formData.firstName.trim()) {
      newErrors.firstName = "First name can't be blank.";
    } else if (formData.firstName.length < 2 || formData.firstName.length > 30) {
      newErrors.firstName = "First name must be between 2 and 30 characters.";
    }
  
    // Last name validation: non-blank, min 2 characters, max 30 characters
    if (!formData.lastName.trim()) {
      newErrors.lastName = "Last name can't be blank.";
    } else if (formData.lastName.length < 2 || formData.lastName.length > 30) {
      newErrors.lastName = "Last name must be between 2 and 30 characters.";
    }
  
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };
  

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleConfirmPasswordChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setConfirmPassword(e.target.value);
  };

  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault();

    if (!validateForm()) return;

    const status = await AuthService.registerUser(formData);
    if (status === 201) {
      setSuccessMessage("Registration successful! Redirecting to login...");
      setTimeout(() => {
        setRedirectToLogin(true);
      }, 2000);
    } else {
      setErrors({ general: "Failed to register. Please try again." });
    }
  };

  if (redirectToLogin) {
    return <Navigate to="/login" />;
  }

  return (
    <div className="container mx-auto max-w-md mt-8">
      <h1 className="text-2xl font-bold text-center mb-6">Register</h1>
      {errors.general && <p className="text-red-500">{errors.general}</p>}
      {successMessage && <p className="text-green-500">{successMessage}</p>}
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="userName" className="block text-sm font-medium text-gray-700">
            Username
          </label>
          <input
            type="text"
            id="userName"
            name="userName"
            value={formData.userName}
            onChange={handleChange}
            className="block w-full border-gray-300 rounded-md"
            placeholder="Username"
          />
          {errors.userName && <p className="text-red-500">{errors.userName}</p>}
        </div>
        <div>
          <label htmlFor="email" className="block text-sm font-medium text-gray-700">
            Email
          </label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            className="block w-full border-gray-300 rounded-md"
            placeholder="Email"
          />
          {errors.email && <p className="text-red-500">{errors.email}</p>}
        </div>
        <div>
          <label htmlFor="firstName" className="block text-sm font-medium text-gray-700">
            First Name
          </label>
          <input
            type="text"
            id="firstName"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
            className="block w-full border-gray-300 rounded-md"
            placeholder="First Name"
          />
          {errors.firstName && <p className="text-red-500">{errors.firstName}</p>}
        </div>
        <div>
          <label htmlFor="lastName" className="block text-sm font-medium text-gray-700">
            Last Name
          </label>
          <input
            type="text"
            id="lastName"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
            className="block w-full border-gray-300 rounded-md"
            placeholder="Last Name"
          />
          {errors.lastName && <p className="text-red-500">{errors.lastName}</p>}
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
            className="block w-full border-gray-300 rounded-md"
            placeholder="Password"
          />
          {errors.password && <p className="text-red-500">{errors.password}</p>}
        </div>
        <div>
          <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700">
            Confirm Password
          </label>
          <input
            type="password"
            id="confirmPassword"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
            className="block w-full border-gray-300 rounded-md"
            placeholder="Confirm Password"
          />
          {errors.confirmPassword && <p className="text-red-500">{errors.confirmPassword}</p>}
        </div>
        <button
          type="submit"
          className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600"
        >
          Register
        </button>
      </form>
      <br />
      <Link className="hover:bg-sky-700" to="/login">
        <u>Already registered? Login here!</u>
      </Link>
    </div>
  );
};

export default RegistrationPage;
