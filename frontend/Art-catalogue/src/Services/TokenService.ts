import {jwtDecode} from "jwt-decode";
import ICustomJwtPayload from "@/Types/CustomJwtPayload";


class TokenService {

   static getTokenFromLocalStorage = () => {
        try {
          const token = localStorage.getItem('token'); // Retrieve the token
          if (token) {
            return token; // Return the token if it exists
          } else {
            console.warn('Token not found in localStorage.');
            return null; // Return null if the token is not found
          }
        } catch (error) {
          console.error('Error retrieving token from localStorage:', error);
          return null; // Return null if an error occurs
        }
      };

    static decodeToken():ICustomJwtPayload{

        const token = this.getTokenFromLocalStorage();

        if(!token){

            throw new Error("Token is undefined or invalid.");
        }
        else{

            try {
                return jwtDecode(token);
            } catch (error) {
                throw new Error("Failed to decode token: " + error);
            }
        }

    }

   static isAdmin():boolean{

        const decodedToken = this.decodeToken();

        if(!decodedToken || decodedToken.isAdmin === undefined){

            throw new Error("'isAdmin' claim is not present in the token.");

        }

        return decodedToken.isAdmin;


    }

    static getUserIdFromToken():string{

        const decodedToken = this.decodeToken();

        if(!decodedToken || decodedToken.subject === undefined){

            throw new Error("'subject' claim is not present in the token.");

        }

        return decodedToken.subject;


    }

    static isTokenExpired():boolean{

        const decodedToken = this.decodeToken();

        console.log("exp: " + decodedToken.exp);
        

        if(!decodedToken || decodedToken.exp === null || decodedToken.exp * 1000 < Date.now()){

            console.log("decoded token:" + decodedToken);

            console.log("expiration: " + decodedToken.exp * 1000);

            console.log("date now: " + Date.now());
            
            
            
            return true

        }else {
            console.log("yesssiiiir");

            
            return false;
        }
        
        

    }





}

export default TokenService;