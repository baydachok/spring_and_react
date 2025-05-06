import styles from "./SingleProduct.module.css";
import {Col, Container, Image, Row} from "react-bootstrap";
import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {GET_IMAGES, GET_PRODUCT} from "../../../constants/URLS";
import Markdown from "react-markdown";
import {useDispatch} from "react-redux";
import {addOrderRecord} from "../../../redux/slices/cartSlice";

const SingleProduct = () => {

    const dispatch = useDispatch();
    const {itemId} = useParams();
    const [product, setProduct] = useState({
        name: "",
        description: "",
        price: 10,
        imageSrc: "",
        quantityInStock: 0,
        productId: 0,
    });


    useEffect(() => {
        axios.get(GET_PRODUCT + itemId)
            .then(response => {
                setProduct(response.data);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    const addProduct = (e) => {
        e.preventDefault();
        dispatch(addOrderRecord({
            ...product,
            quantity: 1
        }));
    }

    let description = <Markdown>{product.description}</Markdown>
    return (
        <Container className={styles.productContainer}>
            <Link to={"/catalog"} href="src/components/items#" className={styles.close}/>
            <Row>
                <Col xs="5" className={styles.productImage}>
                    <Image src={GET_IMAGES + product.imageSrc} fluid alt="Product Image"/>
                </Col>
                <Col xs="6" className={styles.productDetails}>
                    <h2>{product.name}</h2>
                    <p className={styles.price}>Цена: {product.price}<span className={styles.quantity}> (Осталось: {product.quantityInStock})</span></p>
                    {description}
                    <button onClick={addProduct}>Добавить в корзину</button>
                </Col>
            </Row>
            <Row>

            </Row>
        </Container>
    );
}

export default SingleProduct;
