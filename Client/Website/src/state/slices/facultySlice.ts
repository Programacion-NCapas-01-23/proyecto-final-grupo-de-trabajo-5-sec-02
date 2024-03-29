import {createSlice, PayloadAction} from '@reduxjs/toolkit';
import  {Faculty} from "@/interfaces/Faculty";
import InitialState, {ErrorResponse} from "@/interfaces/InitialState";

const initialState: InitialState<Faculty> = {
    data: [],
    loading: false,
    error: null,
};

const facultySlice = createSlice({
    name: 'faculty',
    initialState,
    reducers: {
        fetchFacultiesStart(state) {
            state.loading = true;
            state.error = null;
        },
        fetchFacultiesSuccess(state, action: PayloadAction<Faculty[]>) {
            state.data = action.payload;
            state.loading = false;
            state.error = null;
        },
        fetchFacultiesFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
        createFacultyStart(state) {
            state.loading = true;
            state.error = null;
        },
        createFacultySuccess(state, action: PayloadAction<Faculty>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createFacultyFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
        updateFacultyStart(state) {
            state.loading = true;
            state.error = null;
        },
        updateFacultySuccess(state, action: PayloadAction<Faculty>) {
            const updatedFaculty = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === updatedFaculty.id);
            if (index !== -1) {
                state.data[index] = updatedFaculty;
            }
            state.loading = false;
            state.error = null;
        },
        updateFacultyFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
        deleteFacultyStart(state) {
            state.loading = true;
            state.error = null;
        },
        deleteFacultySuccess(state, action: PayloadAction<Faculty>) {
            const deletedFaculty = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === deletedFaculty.id);
            if (index !== -1) {
                state.data[index] = deletedFaculty;
            }
            state.loading = false;
            state.error = null;
        },
        deleteFacultyFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
    },
});

export const {
    fetchFacultiesStart,
    fetchFacultiesSuccess,
    fetchFacultiesFailure,
    createFacultyStart,
    createFacultySuccess,
    createFacultyFailure,
    updateFacultyStart,
    updateFacultySuccess,
    updateFacultyFailure,
    deleteFacultyStart,
    deleteFacultyFailure,
    deleteFacultySuccess,
} = facultySlice.actions;

export default facultySlice.reducer;
