import {Form, Row} from "react-bootstrap";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import React, {useState} from "react";
import axios from "axios";
import {GET_PRODUCTS} from "../../constants/URLS";

export const ProductSearchingForm = ({setProducts}) => {

    const [searchValue, setSearchValue] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.get(GET_PRODUCTS + `?search=${searchValue}`)
            .then(response => {
                console.log(response.data.data);
                setProducts(response.data.data);
            })
            .catch(error => {
                console.error(error);
            });
    }

    return (
        <Row className="mt-2">
            <InputGroup className="mb-3">
                <Form.Control
                    placeholder="Название товара"
                    aria-label="product name"
                    aria-describedby="basic-addon2"
                    onChange={(e) => {
                        setSearchValue(e.target.value);
                    }}
                />
                <Button onClick={handleSubmit} variant="outline-secondary" id="button-addon2">
                    Поиск
                </Button>
            </InputGroup>
        </Row>
    )
}
