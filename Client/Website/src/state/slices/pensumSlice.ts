import InitialState from "@/interfaces/InitialState";
import {Pensum} from "@/interfaces/Pensum";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: InitialState<Pensum> = {
    data: [],
    loading: false,
    error: null,
}

const pensumSlice = createSlice({
    name: 'pensum',
    initialState,
    reducers: {
        createPensumStart(state) {
            state.loading = true;
            state.error = null;
        },
        createPensumSuccess(state, action: PayloadAction<Pensum>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createPensumFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
});

export const {
    createPensumStart,
    createPensumSuccess,
    createPensumFailure,
} = pensumSlice.actions;

export default pensumSlice.reducer;