import {configureStore} from '@reduxjs/toolkit'
import authReducer from './slices/roleSlice'
import cartReducer from './slices/cartSlice'
import {persistStore, persistReducer} from 'redux-persist'
import storage from 'redux-persist/lib/storage'

const persistedAuthReducer = persistReducer({key: 'auth', storage}, authReducer)
const persistedCartReducer = persistReducer({key: "cart", storage}, cartReducer)

export default () => {
    let store = configureStore({
        middleware: (getDefaultMiddleware) => getDefaultMiddleware({
            serializableCheck: false
        }),
        reducer: {
            auth: persistedAuthReducer,
            cart: persistedCartReducer,
        },
    })
    let persistor = persistStore(store)
    return {store, persistor}
}
