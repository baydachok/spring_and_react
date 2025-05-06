import Form from "react-bootstrap/Form";
import Button from "@mui/material/Button";
import * as React from "react";
import axios from "axios";
import {AUTH_REGISTRATION} from "../../../constants/URLS";
import {Snackbar} from "@mui/material";

export const Registration = () => {
    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [open, setOpen] = React.useState(false);

    const handleClose = () => {
        setOpen(false);
    };
    const handleRegistrationSubmit = (e) => {
        e.preventDefault();
        axios.post(AUTH_REGISTRATION, {
            username: username,
            password: password
        })
            .then((response) => {
                if (response.status.valueOf() === 200) {
                    setOpen(true);
                }
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const action = (
        <React.Fragment>
        </React.Fragment>
    );

    return (
        <Form onSubmit={handleRegistrationSubmit}>
            <Form.Group className="mb-3" controlId="formLogin">
                <Form.Label>Логин</Form.Label>
                <Form.Control value={username}
                              onChange={(e) => setUsername(e.target.value)}
                              type="text"
                              placeholder="Введите логин"/>
            </Form.Group>
            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>Пароль</Form.Label>
                <Form.Control value={password}
                              onChange={(e) => setPassword(e.target.value)}
                              type="password"
                              placeholder="Пароль"/>
            </Form.Group>
            <Button variant="contained" type="submit">
                Зарегистрироваться
            </Button>
            <Snackbar
                open={open}
                autoHideDuration={1000}
                onClose={handleClose}
                message="Регистрация прошла успешно"
                action={action}
            />
        </Form>
    )
}
