import axios from "axios";
import IUser from "@/Types/User";
import ILoginData from "@/Types/LoginData";


const BASE_URL = "http://localhost:8080/";

class AuthService{

 async registerUser(user: IUser){

    try {
        const response = await axios.post(`${BASE_URL}signup`,user);

        return response.status;
        
    } catch (error) {
        console.error("User could not be registered", error);
    }

 }


 async loginUser(loginData: ILoginData){

    try{

        console.log(loginData);
        

        const response = await axios.post(`${BASE_URL}login`,loginData);

        return response.data;

    }catch(error){

        console.error("Couldn't log in: ",error);
    }

}


     async login (loginData: ILoginData): Promise<string> {

        try {

            const response = await axios.post(`${BASE_URL}login`,loginData);

            return response.data.token;
            
        } catch (error) {
            console.error("Login failed: ", error);
            throw new Error("Failed to login please try again");
        }
    }

    

 }

export default new AuthService;

