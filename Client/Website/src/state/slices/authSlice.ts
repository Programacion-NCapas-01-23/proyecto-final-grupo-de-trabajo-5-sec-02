import {Admin, Login} from "@/interfaces/Admin";
import InitialState from "@/interfaces/InitialState";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

type Auth = Admin | Login;

const initialState: InitialState<Auth> = {
    data: [],
    loading: false,
    error: null,
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
            state.data.push(action.payload);
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
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
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