import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import {Faculty} from "@/interfaces/Faculty";

interface FacultyState {
    faculties: Faculty[];
    loading: boolean;
    error: string | null;
}

const initialState: FacultyState = {
    faculties: [],
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
            state.faculties = action.payload;
            state.loading = false;
            state.error = null;
        },
        fetchFacultiesFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
        createFacultyStart(state) {
            state.loading = true;
            state.error = null;
        },
        createFacultySuccess(state, action: PayloadAction<Faculty>) {
            state.faculties.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createFacultyFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
        updateFacultyStart(state) {
            state.loading = true;
            state.error = null;
        },
        updateFacultySuccess(state, action: PayloadAction<Faculty>) {
            const updatedFaculty = action.payload;
            const index = state.faculties.findIndex((faculty) => faculty.id === updatedFaculty.id);
            if (index !== -1) {
                state.faculties[index] = updatedFaculty;
            }
            state.loading = false;
            state.error = null;
        },
        updateFacultyFailure(state, action: PayloadAction<string>) {
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
} = facultySlice.actions;

export default facultySlice.reducer;
