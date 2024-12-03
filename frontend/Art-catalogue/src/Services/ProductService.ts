import axios from 'axios';
import IProduct from '../Types/Product';

const BASE_URL = "http://localhost:8080/products";

class ProductService{

   async getProducts() {

        try {
        const response = await axios.get(BASE_URL);
        return response.data;
        
        } catch (error) {
            console.error("error fetching products: ",error);
        }
    }

    async getProdyctsByCategory(category: string){

       try {
        const response = await axios.get(`${BASE_URL}/category?category=${category}`);
        return response.data;
        
       } catch (error) {
        console.error("error fetching products by category: ",error);
       }


    }



   async createProduct(product: IProduct){

        try {
            const response = await axios.post(BASE_URL,product);
            return response.status;
        } catch (error) {
            console.error("failed to create product", error);
        }
        
    }

    async getProductById(productId: number){

       try {

        const response = await axios.get(`${BASE_URL}/${productId}`);

        return response.data;
        
       } catch (error) {
        console.error("error fetching product by id: ", error);
       }
    }


    async editProduct(productId: number, product: IProduct){

       try {
           const response = await axios.put(`${BASE_URL}/${productId}`,product);

           return response.status;
           
       } catch (error) {
        console.error("failed to update product", error);
       }
    }

   async deleteProduct(productId: number){

        try {
          const response = await axios.delete(`${BASE_URL}/${productId}`);
          
          return response.status;
           
        } catch (error) {
            console.error("could not delete product ", error);
        }
    }

}



export default new ProductService();