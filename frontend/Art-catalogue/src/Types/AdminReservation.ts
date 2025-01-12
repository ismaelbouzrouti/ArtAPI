import ReservedItemDTO from "./ReservedItem";

export interface AdminReservationDTO {
  reservedItems: ReservedItemDTO[]; 
  quantity: number;                
  totalPrice: number;              
  returnDate: string;              
  dateOfReservation: string;
  userName: string      
}
