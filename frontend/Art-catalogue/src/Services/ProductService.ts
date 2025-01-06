import axios from 'axios';
import IProduct from '../Types/Product';
import TokenService from './TokenService';

const BASE_URL = "http://localhost:8080/products";

class ProductService {


    async getProducts() {
        

        const token = TokenService.getTokenFromLocalStorage();
        try {
            const response = await axios.get(BASE_URL, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });
            return response.data;
        } catch (error) {
            console.error("Error fetching products: ", error);
        }
    }

    async getProductsByCategory(category: string) {

        const token = TokenService.getTokenFromLocalStorage();
        try {
            const response = await axios.get(`${BASE_URL}/category?category=${category}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });
            return response.data;
        } catch (error) {
            console.error("Error fetching products by category: ", error);
        }
    }

    async createProduct(product: IProduct) {

        const token = TokenService.getTokenFromLocalStorage();
        const isAdmin = TokenService.isAdmin();

        if (isAdmin) {
            try {
                const response = await axios.post(BASE_URL, product, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });
                return response.status;
            } catch (error) {
                console.error("Failed to create product", error);
            }
        } else {
            throw new Error("You are not an admin!");
        }
    }

    async getProductById(productId: number) {

        const token = TokenService.getTokenFromLocalStorage();
        try {
            const response = await axios.get(`${BASE_URL}/${productId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
            });
            return response.data;
        } catch (error) {
            console.error("Error fetching product by ID: ", error);
        }
    }

    async editProduct(productId: number, product: IProduct) {

        const token = TokenService.getTokenFromLocalStorage();
        const isAdmin = TokenService.isAdmin();

        if (isAdmin) {
            try {
                const response = await axios.put(`${BASE_URL}/${productId}`, product, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });
                return response.status;
            } catch (error) {
                console.error("Failed to update product", error);
            }
        } else {
            throw new Error("You are not an admin!");
        }
    }

    async deleteProduct(productId: number) {

        const token = TokenService.getTokenFromLocalStorage();
        const isAdmin = TokenService.isAdmin();

        if (isAdmin) {
            try {
                const response = await axios.delete(`${BASE_URL}/${productId}`, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        "Content-Type": "application/json",
                    },
                });
                return response.status;
            } catch (error) {
                console.error("Could not delete product", error);
            }
        } else {
            throw new Error("You are not an admin!");
        }
    }
}

export default new ProductService();
