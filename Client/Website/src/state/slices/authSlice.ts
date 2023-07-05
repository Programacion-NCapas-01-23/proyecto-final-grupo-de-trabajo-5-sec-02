import {Admin} from "@/interfaces/Admin";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import InitialState, {ErrorResponse} from "@/interfaces/InitialState";

const initialState: InitialState<Admin> = {
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
        registerFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
})

export const {
    registerStart,
    registerSuccess,
    registerFailure,
} = authSlice.actions

export default authSlice.reducer