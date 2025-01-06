import { JwtPayload } from "jwt-decode";


export default interface ICustomJwtPayload extends JwtPayload {
    issuer: string;        
    subject: string;         // userId
    issuedAt: number;        // Issued At: timestamp
    exp: number;      // Expiration time: timestamp
    isAdmin: boolean;   // isAdmin claim
}