import ICartItem from "./CartItem"

export default interface IShoppingCart{

    id?: number,
    quantity: number,
    totalPrice: number,
    userId: number
    cartItemDTOList: Array<ICartItem>
    returnDate: string
}