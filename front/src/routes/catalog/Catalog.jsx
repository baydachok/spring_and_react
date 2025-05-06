import React, {useEffect, useState} from 'react';
import ProductsContainer from "../../components/products/ProductsContainer";
import {Outlet} from "react-router-dom";
import axios from "axios";
import {GET_PRODUCTS} from "../../constants/URLS"

const Catalog = () => {

    const [products, setProducts] = useState([]);

    useEffect(() => {
        axios.get(GET_PRODUCTS)
            .then(response => {
                setProducts(response.data.data);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    return (
        <>
            <Outlet/>
            <ProductsContainer products={products} setProducts={setProducts}/>
        </>
    );
};

export default Catalog;
