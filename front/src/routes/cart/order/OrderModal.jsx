import {Modal} from "@mui/material";
import styles from "../Cart.module.css";
import Form from "react-bootstrap/Form";
import Button from "@mui/material/Button";
import * as React from "react";
import axios from "axios";
import {POST_ORDER} from "../../../constants/URLS";
import {useDispatch, useSelector} from "react-redux";
import {clearOrderRecords} from "../../../redux/slices/cartSlice";

export const OrderModal = ({open, handleClose}) => {
    const dispatch = useDispatch();
    const orderRecords = useSelector((state) => state.cart.orderRecords);
    const [address, setAddress] = React.useState("");

    const handleOrderSubmit = (e) => {
        e.preventDefault();
        axios.post(POST_ORDER, {orderRecords: orderRecords, address: address})
            .then((response) => {
                dispatch(clearOrderRecords());
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            });
        handleClose();
    }

    return (
        <Modal
            open={open}
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
        >
            <div className={styles.cartForm}>
                <Form onSubmit={handleOrderSubmit}>
                    <Form.Group className="mb-3" controlId="formOrder">
                        <Form.Label>Адрес</Form.Label>
                        <Form.Control value={address}
                                      onChange={(e) => setAddress(e.target.value)}
                                      type="text"
                                      placeholder="Введите адрес"/>
                    </Form.Group>
                    <Button variant="contained" type="submit">
                        Оформить заказ
                    </Button>
                </Form>
            </div>
        </Modal>
    )
}
