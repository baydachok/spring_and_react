import {createSlice} from "@reduxjs/toolkit";

export const authSlice = createSlice({
    name: 'auth',
    initialState: {
        token: "",
        role: ""
    },
    reducers: {
        setToken: (state, action) => {
            state.token = action.payload
        },
        setRole: (state, action) => {
            state.role = action.payload
        }
    }
})

export const { setToken, setRole } = authSlice.actions

export default authSlice.reducer
