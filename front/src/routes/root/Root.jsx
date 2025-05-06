import './Root.css';
import Navigation from "../../components/navigation/Navigation";
import React from "react";
import {Outlet} from "react-router-dom";
import {AuthModal} from "../../components/auth/AuthModal";
import {useSelector} from "react-redux";
import axios from "axios";

function Root() {
    (function() {
        const token = useSelector((state) => state.auth.token);
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        } else {
            axios.defaults.headers.common['Authorization'] = null;
        }
    })();

    return (
        <div className="root-page">
            <Navigation/>
            <AuthModal/>
            <Outlet />
        </div>
    );
}

export default Root;
