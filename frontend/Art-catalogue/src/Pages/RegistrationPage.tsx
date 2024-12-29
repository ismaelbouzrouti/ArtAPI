import React, { useState } from "react";
import IUser from "@/Types/User";
import UserService from "@/Services/UserService";

const RegistrationPage: React.FC = () => {
  const [formData, setFormData] = useState<IUser>({
    userName: "",
    email: "",
    password: "",
    firstName: "",
    lastName: "",
  });
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  // Handle changes for formData fields
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  // Handle changes for confirmPassword
  const handleConfirmPasswordChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setConfirmPassword(e.target.value);
  };

  // Handle form submission
  const handleSubmit = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault();
    setError(null);
    setSuccessMessage(null);

    // Check if password and confirmPassword match
    if (formData.password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    console.log(formData);
    

   const status =  await UserService.registerUser(formData);

      if (status == 200) {
        setSuccessMessage("Registration successful! You can now log in.");
        setFormData({
          userName: "",
          email: "",
          password: "",
          firstName: "",
          lastName: "",
        });
        setConfirmPassword("");
      } else {
        setError("Failed to register. Please try again.");
      }
  };

  return (
    <div className="container mx-auto max-w-md mt-8">
      <h1 className="text-2xl font-bold text-center mb-6">Register</h1>
      {error && <p className="text-red-500">{error}</p>}
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
            required
            className="block w-full border-gray-300 rounded-md"
            placeholder="Username"
          />
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
            required
            className="block w-full border-gray-300 rounded-md"
            placeholder="Email"
          />
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
            required
            className="block w-full border-gray-300 rounded-md"
            placeholder="First Name"
          />
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
            required
            className="block w-full border-gray-300 rounded-md"
            placeholder="Last Name"
          />
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
            required
            className="block w-full border-gray-300 rounded-md"
            placeholder="Confirm Password"
          />
        </div>
        <button
          type="submit"
          className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600"
        >
          Register
        </button>
      </form>
    </div>
  );
};

export default RegistrationPage;
