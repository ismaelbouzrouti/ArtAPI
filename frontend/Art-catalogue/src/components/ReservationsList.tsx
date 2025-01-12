import React, { useEffect, useState } from "react";
import ReservationService from "@/Services/ReservationService";
import { ReservationDTO } from "@/Types/Reservation";

const ReservationsList: React.FC = () => {
  const [reservations, setReservations] = useState<ReservationDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        setLoading(true);
        const data = await ReservationService.getReservations();
        setReservations(data);
        setError(null);
      } catch (err: any) {
        setError(err.message || "An error occurred while fetching reservations.");
      } finally {
        setLoading(false);
      }
    };

    fetchReservations();
  }, []);

  if (loading) return <p className="text-center text-lg font-semibold">Loading reservations...</p>;
  if (error) return <p className="text-center text-red-500 text-lg">{error}</p>;

  return (
    <div className="container mx-auto px-4">
      <h1 className="text-3xl font-bold text-center my-6">Reservations</h1>
      {reservations.length === 0 ? (
        <p className="text-center text-gray-500">No reservations found.</p>
      ) : (
        <div className="space-y-6">
          {reservations.map((reservation, index) => (
            <div key={index} className="bg-white shadow-md rounded-lg p-6">
              <h2 className="text-xl font-semibold mb-4">
                Reservation #{index + 1}
              </h2>
              <p className="text-gray-700 mb-2">
                <strong>Date of Reservation:</strong> {reservation.dateOfReservation}
              </p>
              <p className="text-gray-700 mb-2">
                <strong>Return Date:</strong> {reservation.returnDate}
              </p>
              <p className="text-gray-700 mb-2">
                <strong>Total Price:</strong> ${reservation.totalPrice.toFixed(2)}
              </p>
              <p className="text-gray-700 mb-4">
                <strong>Total Items:</strong> {reservation.quantity}
              </p>
              <h3 className="text-lg font-semibold mb-2">Reserved Items</h3>
              <table className="table-auto w-full border-collapse border border-gray-200">
                <thead>
                  <tr className="bg-gray-100">
                    <th className="border border-gray-200 px-4 py-2">Product Name</th>
                    <th className="border border-gray-200 px-4 py-2">Price</th>
                    <th className="border border-gray-200 px-4 py-2">Quantity</th>
                  </tr>
                </thead>
                <tbody>
                  {reservation.reservedItems.map((item, idx) => (
                    <tr key={idx} className="hover:bg-gray-50">
                      <td className="border border-gray-200 px-4 py-2">{item.productName}</td>
                      <td className="border border-gray-200 px-4 py-2">${item.price.toFixed(2)}</td>
                      <td className="border border-gray-200 px-4 py-2">{item.quantity}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ReservationsList;
