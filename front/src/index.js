import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.bundle'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Root from "./routes/root/Root";
import ErrorPage from "./routes/error/ErrorPage";
import Catalog from "./routes/catalog/Catalog";
import Orders from "./routes/orders/Orders";
import Item from "./routes/catalog/item/Item";
import Support from "./routes/support/Support";
import {Provider} from "react-redux";
import configureStore from "./redux/store";
import {PersistGate} from "redux-persist/integration/react";
import ProductCreationForm from "./routes/add-product/ProductCreationForm";
import {Cart} from "./routes/cart/Cart";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Root/>,
        errorElement: <ErrorPage/>,
        children: [
            {
                path: "cart",
                element: <Cart/>
            },
            {
                path: "catalog",
                element: <Catalog/>,
                children: [
                    {
                        path: ":itemId",
                        element: <Item/>,
                    }
                ]
            },
            {
                path: "orders",
                element: <Orders/>
            },
            {
                path: "support",
                element: <Support/>
            },
            {
                path: "add-product",
                element: <ProductCreationForm/>
            }
        ]
    },
]);

const {store, persistor} = configureStore();


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <PersistGate loading={null} persistor={persistor}>
                <RouterProvider router={router}/>
            </PersistGate>
        </Provider>
    </React.StrictMode>
);
