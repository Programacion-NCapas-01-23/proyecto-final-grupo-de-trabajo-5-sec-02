import {Admin, Login} from "@/interfaces/Admin";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {DefaultState} from "@/interfaces/DefaultState";
import {any} from "prop-types";

type Auth = Admin | Login;

const initialState: DefaultState<Auth> = {
    data: any,
    loading: false,
    error: undefined,
}

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        registerStart(state) {
            state.loading = true;
            state.error = null;
        },
        registerSuccess(state, action: PayloadAction<Admin>) {
            state.data = action.payload;
            state.loading = false;
            state.error = null;
        },
        registerFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
        loginStart(state) {
            state.loading = true;
            state.error = null;
        },
        loginSuccess(state, action: PayloadAction<Login>) {
            state.data = action.payload;
            state.loading = false;
            state.error = null;
            localStorage.setItem('token', action.payload.token!);
        },
        loginFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
})

export const {
    loginStart,
    loginSuccess,
    loginFailure,
    registerStart,
    registerSuccess,
    registerFailure,
} = authSlice.actions

export default authSlice.reducer