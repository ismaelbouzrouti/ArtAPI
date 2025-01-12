import axios from "axios";
import TokenService from "./TokenService";

const BASE_URL = "http://localhost:8080/";

class ReservationService{


    async getReservations(){

        const token = TokenService.getTokenFromLocalStorage();
        const userId = TokenService.getUserIdFromToken();

        const response = await axios.get(`${BASE_URL}reservation/${userId}`,{
            headers:{
                Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
            }
        });
        
        return response.data;
    }


    async getAllReservations(){

        const token = TokenService.getTokenFromLocalStorage();
        const response = await axios.get(`${BASE_URL}reservations`,{
            headers:{
                Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
            }
        });
        
        return response.data;
    }



}

export default new ReservationService();