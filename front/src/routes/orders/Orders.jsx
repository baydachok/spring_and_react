import React, {useEffect, useState} from 'react';
import {Container} from "react-bootstrap";
import axios from "axios";
import {GET_ORDERS, PATCH_ORDER} from "../../constants/URLS";

const Orders = () => {

    const [orders, setOrders] = useState([]);

    useEffect(() => {
        axios.get(GET_ORDERS)
            .then(response => {
                setOrders(response.data.orders);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    const handleCancelOrder = (e) => {
        e.preventDefault();
        axios.patch(PATCH_ORDER[0] + e.target.dataset.orderId + PATCH_ORDER[1])
            .then(() => {
                let newOrders = [...orders];
                newOrders.find((value) => {
                    return value.orderId === Number(e.target.dataset.orderId)
                }).orderStatus = "CANCELED";
                setOrders(newOrders);
            })
            .catch(error => {
                console.error(error);
            });
    }

    return (
        <Container>
            <table className="table table-hover table-cart">
                <thead>
                <tr>
                    <th>Id заказа</th>
                    <th>Дата последнего обновления</th>
                    <th>Адрес</th>
                    <th>Статус</th>
                    <th>Отменить заказ</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((value) => {
                    return (
                        <tr key={value.orderId}>
                            <td>
                                <span>{value.orderId}</span>
                            </td>
                            <td>
                                <span>{value.updatedTimestamp}</span>
                            </td>
                            <td>
                                <span>{value.address}</span>
                            </td>
                            <td>
                                <span>{value.orderStatus}</span>
                            </td>
                            <td>
                                <button data-order-id={value.orderId} className="btn btn-danger ms-1" type="button"
                                    onClick={handleCancelOrder}
                                >Отменить
                                </button>
                            </td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
        </Container>
    );
};

export default Orders;
