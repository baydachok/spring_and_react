import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {useEffect, useState} from "react";
import axios from "axios";
import {GET_PRODUCT_TYPES, POST_PRODUCTS} from "../../constants/URLS";
import {Container, Row} from "react-bootstrap";

const ProductCreationForm = () => {

    const [productTypes, setProductTypes] = useState([]);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState(10);
    const [image, setImage] = useState(null);
    const [quantityInStock, setQuantityInStock] = useState(1);
    const [productTypeId, setProductTypeId] = useState(1);

    useEffect(() => {
        axios.get(GET_PRODUCT_TYPES)
            .then(response => {
                setProductTypes(response.data.data);
            })
            .catch(error => {
                console.error(error);
            });
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post(
            POST_PRODUCTS,
            {
                name: name,
                description: description,
                price: price,
                image: image,
                quantityInStock: quantityInStock,
                productTypeId: productTypeId
            },
            {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            })
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    return (
        <Container>
            <Row>
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-3" controlId="formProductName">
                        <Form.Label>Название товара</Form.Label>
                        <Form.Control value={name} type="text" placeholder="Введите название"
                                      onChange={(e) => setName(e.target.value)}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formProductDescription">
                        <Form.Label>Описание товара</Form.Label>
                        <Form.Control value={description} as="textarea" rows={3} placeholder="Введите описание"
                                      onChange={(e) => setDescription(e.target.value)}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formProductPrice">
                        <Form.Label>Цена товара</Form.Label>
                        <Form.Control value={price} type="number" placeholder="Введите цену"
                                      onChange={(e) => setPrice(e.target.value)}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formProductImageSrc">
                        <Form.Label>Ссылка на изображение</Form.Label>
                        <Form.Control type="file" accept=".png,.jpg,.jpeg"
                                      placeholder="Добавьте ссылку на изображение товара"
                                      onChange={(e) => {
                                          if (e.target.files) {
                                              setImage(e.target.files[0]);
                                          }
                                      }}/>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="formProductQuantity">
                        <Form.Label>Количество товара</Form.Label>
                        <Form.Control value={quantityInStock} type="number" placeholder="Введите количество товара"
                                      onChange={(e) => {
                                          if (e.target.value >= 1) {
                                              setQuantityInStock(e.target.value);
                                          }
                                      }}/>
                    </Form.Group>
                    <Form.Select value={productTypeId} onChange={(e) => setProductTypeId(e.target.value)}
                                 className="mb-3" aria-label="Default select example">
                        {productTypes.map(productType => {
                            return (
                                <option key={productType.productTypeId}
                                        value={productType.productTypeId}>{productType.name}</option>
                            )
                        })}
                    </Form.Select>
                    <Button variant="primary" type="submit">
                        Создать
                    </Button>
                </Form>
            </Row>
        </Container>
    )
}

export default ProductCreationForm;
