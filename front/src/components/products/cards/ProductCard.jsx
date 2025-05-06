import React from 'react';
import Card from "react-bootstrap/Card";
import ProductCardBody from "./ProductCardBody";
import classes from "./Card.module.css";
import {GET_IMAGES} from "../../../constants/URLS";

const ProductCard = ({deletionCallback, name, description, imageSrc, productId, quantityInStock}) => {
    return (
        <Card className={classes.card}  >
            <Card.Img variant="top" src={GET_IMAGES + imageSrc}/>
            <ProductCardBody
                deletionCallback={deletionCallback}
                title={name}
                description={description}
                id={productId}
                quantityInStock={quantityInStock}
                imageSrc={imageSrc}
            />
        </Card>
    );
};

export default ProductCard;
