import ProductCard from "./cards/ProductCard";
import React from "react";
import {Col, Container, Row} from "react-bootstrap";
import axios from "axios";
import {DELETE_PRODUCT} from "../../constants/URLS";
import {ProductSearchingForm} from "./ProductSearchingForm";

const ProductsContainer = ({products, setProducts}) => {

    const ROW_SIZE = 4;

    const getProductCol = (i) => {
        return (
            <Col className="col-3 d-flex mt-2" key={i}>
                <ProductCard deletionCallback={deletionCallback} {...products[i]}/>
            </Col>
        )
    }

    const getProductRow = (rowIndex) => {
        const cols = [];
        const max = Math.min(ROW_SIZE * (rowIndex + 1), products.length);
        for (let i = ROW_SIZE * rowIndex; i < max; i++) {
            cols.push(getProductCol(i));
        }

        return (
            <Row className="justify-content-center" key={rowIndex}>
                {cols}
            </Row>
        )
    }

    const getProductRows = () => {
        const max = (products.length - 1) / ROW_SIZE + 1;
        const rows = [];
        for (let i = 0; i < max; i++) {
            rows.push(getProductRow(i));
        }

        return rows;
    }

    const deletionCallback = (id) => {
        axios.delete(`${DELETE_PRODUCT + id}`)
            .then(() => {
                console.log(`Deleted product with ID ${id}`);
                setProducts(products.filter((product) => product.productId !== id))
            })
            .catch(error => {
                console.error(error);
            });
    }

    return (
        <Container>
            <ProductSearchingForm setProducts={setProducts}/>
            {getProductRows()}
        </Container>
    );
};

export default ProductsContainer;
