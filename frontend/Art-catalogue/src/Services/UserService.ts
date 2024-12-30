import axios from "axios";
import IUser from "@/Types/User";


const BASE_URL = "http://localhost:8080/";

class UserService{

 async registerUser(user: IUser){

    try {
        const response = await axios.post(`${BASE_URL}signup`,user);

        return response;
        
    } catch (error) {

       console.error(error);
    }

 }


}

export default new UserService;

