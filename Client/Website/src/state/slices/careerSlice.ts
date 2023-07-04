import InitialState, {ErrorResponse} from "@/interfaces/InitialState";
import {CareerPreview} from "@/interfaces/Career";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: InitialState<CareerPreview> = {
    data: [],
    loading: false,
    error: null,
}

const careerSlice = createSlice({
    name: 'career',
    initialState,
    reducers: {
        fetchCareersStart(state) {
            state.loading = true;
            state.error = null;
        },
        fetchCareersSuccess(state, action: PayloadAction<CareerPreview[]>) {
            state.data = action.payload;
            state.loading = false;
            state.error = null;
        },
        fetchCareersFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
        createCareerStart(state) {
            state.loading = true;
            state.error = null;
        },
        createCareerSuccess(state, action: PayloadAction<CareerPreview>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createCareerFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },

        updateCareerStart(state) {
            state.loading = true;
            state.error = null;
        },
        updateCareerSuccess(state, action: PayloadAction<CareerPreview>) {
            const updatedCareer = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === updatedCareer.id);
            if (index !== -1) {
                state.data[index] = updatedCareer;
            }
            state.loading = false;
            state.error = null;
        },
        updateCareerFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
        deleteCareerStart(state) {
            state.loading = true;
            state.error = null;
        },
        deleteCareerSuccess(state, action: PayloadAction<CareerPreview>) {
            const deletedCareer = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === deletedCareer.id);
            if (index !== -1) {
                state.data[index] = deletedCareer;
            }
            state.loading = false;
            state.error = null;
        },
        deleteCareerFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
    },
});

export const {
    createCareerStart,
    createCareerSuccess,
    createCareerFailure,
    deleteCareerStart,
    deleteCareerSuccess,
    deleteCareerFailure,
    fetchCareersStart,
    fetchCareersSuccess,
    fetchCareersFailure,
    updateCareerStart,
    updateCareerSuccess,
    updateCareerFailure,
} = careerSlice.actions;

export default careerSlice.reducer;