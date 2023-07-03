import InitialState from "@/interfaces/InitialState";
import {Cycle} from "@/interfaces/Cycle";
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
        fetchCyclesStart(state) {
            state.loading = true;
            state.error = null;
        },
        fetchCyclesSuccess(state, action: PayloadAction<Cycle[]>) {
            state.data = action.payload;
            state.loading = false;
            state.error = null;
        },
        fetchCyclesFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
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
        updateCycleStart(state) {
            state.loading = true;
            state.error = null;
        },
        updateCycleSuccess(state, action: PayloadAction<Cycle>) {
            const updatedCycle = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === updatedCycle.id);
            if (index !== -1) {
                state.data[index] = updatedCycle;
            }
            state.loading = false;
            state.error = null;
        },
        updateCycleFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
        deleteCycleStart(state) {
            state.loading = true;
            state.error = null;
        },
        deleteCycleSuccess(state, action: PayloadAction<Cycle>) {
            const deletedCycle = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === deletedCycle.id);
            if (index !== -1) {
                state.data[index] = deletedCycle;
            }
            state.loading = false;
            state.error = null;
        },
        deleteCycleFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
    },
});

export const {
    createCycleStart,
    createCycleSuccess,
    createCycleFailure,
    deleteCycleStart,
    deleteCycleSuccess,
    deleteCycleFailure,
    fetchCyclesStart,
    fetchCyclesSuccess,
    fetchCyclesFailure,
    updateCycleStart,
    updateCycleSuccess,
    updateCycleFailure,
} = cycleSlice.actions;

export default cycleSlice.reducer;