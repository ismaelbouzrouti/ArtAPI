import ReservedItemDTO from "./ReservedItem";

export interface ReservationDTO {
  reservedItems: ReservedItemDTO[]; 
  quantity: number;                
  totalPrice: number;              
  returnDate: string;              
  dateOfReservation: string;       
}
