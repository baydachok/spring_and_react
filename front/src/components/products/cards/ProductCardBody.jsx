import React from 'react';
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import classes from "./Card.module.css";
import {Link} from "react-router-dom";
import {useSelector} from "react-redux";
import Markdown from "react-markdown";

const ProductCardBody = ({deletionCallback, title, description, id}) => {
    const role = useSelector((state) => state.auth.role);

    return (
        <Card.Body className={classes.cardBody + " d-flex flex-column justify-content-end"}>
            <Card.Title>{title}</Card.Title>
            <Card.Text as="div" className={classes.readMore}>
                <div className={classes.readMoreDescription}>
                    <Markdown>
                        {description}
                    </Markdown>
                </div>
                <Link to={"/catalog/" + id} className={classes.readMoreLink}>

                </Link>
            </Card.Text>
            {role === "[MANAGER]" ? <Button variant="danger" className={classes.cardButton} onClick={() => {
                deletionCallback(id);
            }}>Удалить товар</Button> : <></>}
        </Card.Body>
    );
};

export default ProductCardBody;