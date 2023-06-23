import InitialState from "@/interfaces/InitialState";
import Cycle from "@/interfaces/Cycle";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: InitialState<Cycle> = {
    data: [],
    loading: false,
    error: null,
}

const cycleSlice = createSlice({
    name: 'cycle',
    initialState,
    reducers: {
        createCycleStart(state) {
            state.loading = true;
            state.error = null;
        },
        createCycleSuccess(state, action: PayloadAction<Cycle>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createCycleFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
});

export const {
    createCycleStart,
    createCycleSuccess,
    createCycleFailure,
} = cycleSlice.actions;

export default cycleSlice.reducer;