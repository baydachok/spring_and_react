import {createSlice} from "@reduxjs/toolkit";

export const cartSlice = createSlice({
    name: 'cart',
    initialState: {
        orderRecords: []
    },
    reducers: {
        addOrderRecord: (state, action) => {
            if (state.orderRecords.some(item => item.productId === action.payload.productId)) return;
            state.orderRecords.push(action.payload);
        },
        removeOrderRecord: (state, action) => {
            state.orderRecords = state.orderRecords.filter(item => item.productId !== action.payload);
        },
        setQuantity: (state, action) => {
            let orderRecord = state.orderRecords.find(item => item.productId === action.payload.productId);
            if (action.payload.quantity > 0) {
                orderRecord.quantity = action.payload.quantity;
            }
        },
        clearOrderRecords: (state) => {
            state.orderRecords = [];
        }
    }
})

export const {
    addOrderRecord,
    removeOrderRecord,
    setQuantity,
    clearOrderRecords,
} = cartSlice.actions

export default cartSlice.reducer
