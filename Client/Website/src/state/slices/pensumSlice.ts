import InitialState from "@/interfaces/InitialState";
import {Pensum, PensumPreview} from "@/interfaces/Pensum";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: InitialState<PensumPreview> = {
    data: [],
    loading: false,
    error: null,
}

const pensumSlice = createSlice({
    name: 'pensum',
    initialState,
    reducers: {
        fetchPensumsStart(state) {
            state.loading = true;
            state.error = null;
        },
        fetchPensumsSuccess(state, action: PayloadAction<PensumPreview[]>) {
            state.data = action.payload;
            state.loading = false;
            state.error = null;
        },
        fetchPensumsFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
        createPensumStart(state) {
            state.loading = true;
            state.error = null;
        },
        createPensumSuccess(state, action: PayloadAction<PensumPreview>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createPensumFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
        updatePensumStart(state) {
            state.loading = true;
            state.error = null;
        },
        updatePensumSuccess(state, action: PayloadAction<PensumPreview>) {
            const updatedPensum = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === updatedPensum.id);
            if (index !== -1) {
                state.data[index] = updatedPensum;
            }
            state.loading = false;
            state.error = null;
        },
        updatePensumFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
        deletePensumStart(state) {
            state.loading = true;
            state.error = null;
        },
        deletePensumSuccess(state, action: PayloadAction<PensumPreview>) {
            const deletedPensum = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === deletedPensum.id);
            if (index !== -1) {
                state.data[index] = deletedPensum;
            }
            state.loading = false;
            state.error = null;
        },
        deletePensumFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
});

export const {
    createPensumStart,
    createPensumSuccess,
    createPensumFailure,
    deletePensumStart,
    deletePensumSuccess,
    deletePensumFailure,
    fetchPensumsStart,
    fetchPensumsSuccess,
    fetchPensumsFailure,
    updatePensumStart,
    updatePensumSuccess,
    updatePensumFailure,
} = pensumSlice.actions;

export default pensumSlice.reducer;