import styles from "./SingleProduct.module.css";
import {Link, useParams} from "react-router-dom";
import {Container, Row} from "react-bootstrap";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {GET_PRODUCT, PATCH_PRODUCT} from "../../../constants/URLS";

const ProductUpdateForm = () => {

    const { itemId } = useParams();
    const [product, setProduct] = useState({name: "", description: "", price: 10, imageSrc: ""});

    useEffect(() => {
        axios.get(GET_PRODUCT + itemId)
            .then(response => {
                setProduct(response.data);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.patch(PATCH_PRODUCT + itemId, product)
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    return (
        <Container className={styles.productContainer}>
            <Link to={"/catalog"} className={styles.close} />
            <h2>Обновить товар</h2>
            <Row className={"mt-4"}>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="formProductName">
                        <Form.Label>Название товара</Form.Label>
                        <Form.Control value={product.name} type="text" placeholder="Введите название"
                                      onChange={(e) => setProduct({...product, name: e.target.value})}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formProductDescription">
                        <Form.Label>Описание товара</Form.Label>
                        <Form.Control value={product.description} as="textarea" rows={3} placeholder="Введите описание"
                                      onChange={(e) => setProduct({...product, description: e.target.value})}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formProductPrice">
                        <Form.Label>Цена товара</Form.Label>
                        <Form.Control value={product.price} type="number" placeholder="Введите цену"
                                      onChange={(e) => setProduct({...product, price: e.target.value})}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formProductImageSrc">
                        <Form.Label>Ссылка на изображение</Form.Label>
                        <Form.Control value={product.imageSrc} type="url" placeholder="Добавьте ссылку на изображение товара"
                                      onChange={(e) => setProduct({...product, imageSrc: e.target.value})}/>
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Обновить данные
                    </Button>
                </Form>
            </Row>
        </Container>
    )
}

export default ProductUpdateForm;
