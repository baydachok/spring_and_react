import Form from "react-bootstrap/Form";
import Button from "@mui/material/Button";
import * as React from "react";
import axios from "axios";
import {AUTH_LOGIN} from "../../../constants/URLS";
import {setRole, setToken} from "../../../redux/slices/roleSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";

export const Login = ({setOpen}) => {

    const [username, setUsername] = React.useState("");
    const [password, setPassword] = React.useState("");
    const navigate = useNavigate();
    const dispatch = useDispatch();


    const handleLoginSubmit = (e) => {
        e.preventDefault();
        axios.post(AUTH_LOGIN, {
            username: username,
            password: password
        })
            .then((response) => {
                if (response.status.valueOf() === 200) {
                    dispatch(setToken(response.data.accessToken));
                    dispatch(setRole(response.data.role));
                    axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.accessToken}`;
                    setOpen(false);
                    navigate("/catalog");
                }
            })
            .catch((error) => {
                console.log(error);
            });
    }

    return (
        <Form onSubmit={handleLoginSubmit}>
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
                Отправить
            </Button>
        </Form>
    )
}