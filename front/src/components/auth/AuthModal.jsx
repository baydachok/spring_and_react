import * as React from 'react';
import {useSelector} from "react-redux";
import {Modal} from "@mui/material";
import {AuthTabs} from "./AuthTabs";
import styles from "./Auth.module.css";

export function AuthModal() {
    const role = useSelector((state) => state.auth.role);
    const token = useSelector((state) => state.auth.token);

    const [open, setOpen] = React.useState(role === "" || token === "");
    const isAuthenticated = () => role !== "" && token !== "";

    const handleClose = () => {
        if (isAuthenticated()) {
            setOpen(false);
        }
    }

    return (
        <Modal
            open={open}
            backdrop="static"
            onClose={handleClose}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
        >
            <div className={styles.authModal}>
                <AuthTabs setOpen={setOpen}/>
            </div>
        </Modal>
    )
}
