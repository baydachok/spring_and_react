import * as React from "react";
import {Container} from "react-bootstrap";
import {GET_IMAGES} from "../../constants/URLS";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import styles from "./Cart.module.css";
import {OrderModal} from "./order/OrderModal";
import {useDispatch, useSelector} from "react-redux";
import {removeOrderRecord, setQuantity} from "../../redux/slices/cartSlice";
import {EmptyCart} from "./EmptyCart";

export const Cart = () => {
    const dispatch = useDispatch();
    const orderRecords = useSelector((state) => state.cart.orderRecords);
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);


    return (
        <Container>
            <table className="table table-hover table-cart">
                {orderRecords.length !== 0 &&
                    <thead>
                    <tr>
                        <th>Товар</th>
                        <th>Цена</th>
                        <th>Количество</th>
                    </tr>
                    </thead>
                }
                <tbody className="cart-items">
                {orderRecords.map((value) => {
                    return (
                        <tr className="cart-row" key={value.productId}>
                            <td className="cart-item cart-column">
                                <img className="cart-item-image" src={GET_IMAGES + value.imageSrc}
                                     width="50" height="50"
                                     alt="Изображение товара"/>
                                <span className="cart-item-title ms-3">{value.name}</span>
                            </td>
                            <td className="cart-item cart-column">
                                <span className="cart-price cart-column">{value.price} ₽</span>
                            </td>
                            <td className="cart-item cart-column">
                                <input className="cart-quantity-input" type="number" value={value.quantity}
                                       onChange={(e) => {
                                           e.preventDefault();
                                           dispatch(setQuantity({...value, quantity: e.target.value}));
                                       }}
                                       style={{width: "50px"}}/>
                                <button className="btn btn-danger ms-1" type="button" onClick={(e) => {
                                    e.preventDefault();
                                    dispatch(removeOrderRecord(value.productId));
                                }}>Удалить
                                </button>
                            </td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
            {orderRecords.length === 0 && <EmptyCart/>}
            {orderRecords.length !== 0 &&
                <>
                    <div className={styles.cartTotal}>
                        <strong className="cart-total-title">Общая сумма: </strong>
                        <span className="cart-total-price"
                              style={{textAlign: "right"}}>₽ {orderRecords.reduce((partialSum, value) => {
                            return partialSum + value.price * value.quantity
                        }, 0).toFixed(2)}</span>
                    </div>
                    <div className={styles.btnPurchase}>
                        <button onClick={handleOpen} type="button" style={{marginTop: "10%"}}
                                className={"btn btn-dark " + styles.btnPurchase}>
                            <ShoppingCartIcon style={{marginRight: "6%"}}/><span>Оформить заказ</span></button>
                    </div>
                </>
            }
            <OrderModal open={open} handleClose={handleClose}/>

        </Container>
    )
}