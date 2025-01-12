import axios from "axios";
import ICartItem from "@/Types/CartItem";
import TokenService from "./TokenService";

const BASE_URL = "http://localhost:8080/";


class ShoppingCartService{


    async getShoppingCart(){
        const userId = TokenService.getUserIdFromToken();
        const token = TokenService.getTokenFromLocalStorage();

        try {

            const response = await axios.get(`${BASE_URL}shopping-cart/${userId}`,{

                headers:{
                    Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                },
            });
        
            
            return response.data;
            
        } catch (error) {
            console.error("failed to fetch shoppingCart: ", error);
        }

    }

    async addItem(item: ICartItem){
        const userId = TokenService.getUserIdFromToken();
        const token = TokenService.getTokenFromLocalStorage();

        try {
            const response = await axios.post(`${BASE_URL}shoppingCart/${userId}/addItem`, item ,{
                
                headers:{
                    Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                }
            });
            
            
            
            return response.status;
            
        } catch (error) {
            console.error("could not add item to shoppingCart: ", error)
        }

    }

    async removeItem(itemId: number){
        const token = TokenService.getTokenFromLocalStorage();

        try {
            const response = await axios.delete(`${BASE_URL}shopping-cart/remove/${itemId}`,{
                headers:{
                    Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                }
            });

            return response.status;
            
        } catch (error) {
            console.error("item could not be removed",error);
        }
    }


    async incrementItem(itemId: number){
        const token = TokenService.getTokenFromLocalStorage();

        try {

            const response = await axios.put(`${BASE_URL}shopping-cart/increment/${itemId}`,{} ,{
                
                headers:{
                    Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                }
            });
                    
            return response.status;
            
        } catch (error) {
            console.error(" an error occured while incrementing ", error);
        }


    }



    async decrementItem(itemId: number){
        const token = TokenService.getTokenFromLocalStorage();

        try {

            const response = await axios.put(`${BASE_URL}shopping-cart/decrement/${itemId}`,{} ,{
                
                headers:{
                    Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                }
            });
                    
            return response.status;
            
        } catch (error) {
            console.error(" an error occured while incrementing ", error);
        }
    }

    async changeReturnDate(returnDate: string){
        const token = TokenService.getTokenFromLocalStorage();
        const userId = TokenService.getUserIdFromToken();

        try {

            const response = await axios.put(`${BASE_URL}shopping-cart/return-date/${userId}`,returnDate ,{
                
                headers:{
                    Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                }
            });

            return response.status;
            
        } catch (error) {
            console.error("couldnt change the return date: ", error);
        }
    }


    async checkOut(){
        const token = TokenService.getTokenFromLocalStorage();
        const userId = TokenService.getUserIdFromToken();

        try {

            const response = await axios.delete(`${BASE_URL}shopping-cart/checkout/${userId}`,{
                
                headers:{
                    Authorization: `Bearer ${token}`,
                            "Content-Type": "application/json",
                }
            });

            return response.status;
            
        } catch (error) {
            console.error("couldnt checkout: ", error);
        }
    }







}

export default new ShoppingCartService();