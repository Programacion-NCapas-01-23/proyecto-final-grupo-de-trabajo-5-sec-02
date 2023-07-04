import InitialState, {ErrorResponse} from "@/interfaces/InitialState";
import {Subject} from "@/interfaces/Subject";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: InitialState<Subject> = {
    data: [],
    loading: false,
    error: null,
}

const subjectSlice = createSlice({
    name: 'subject',
    initialState,
    reducers: {
        fetchSubjectsStart(state) {
            state.loading = true;
            state.error = null;
        },
        fetchSubjectsSuccess(state, action: PayloadAction<Subject[]>) {
            state.data = action.payload;
            state.loading = false;
            state.error = null;
        },
        fetchSubjectsFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
        createSubjectStart(state) {
            state.loading = true;
            state.error = null;
        },
        createSubjectSuccess(state, action: PayloadAction<Subject>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createSubjectFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
        updateSubjectStart(state) {
            state.loading = true;
            state.error = null;
        },
        updateSubjectSuccess(state, action: PayloadAction<Subject>) {
            const updatedSubject = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === updatedSubject.id);
            if (index !== -1) {
                state.data[index] = updatedSubject;
            }
            state.loading = false;
            state.error = null;
        },
        updateSubjectFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
        deleteSubjectStart(state) {
            state.loading = true;
            state.error = null;
        },
        deleteSubjectSuccess(state, action: PayloadAction<Subject>) {
            const deletedSubject = action.payload;
            const index = state.data.findIndex((faculty) => faculty.id === deletedSubject.id);
            if (index !== -1) {
                state.data[index] = deletedSubject;
            }
            state.loading = false;
            state.error = null;
        },
        deleteSubjectFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
});

export const {
    createSubjectStart,
    createSubjectSuccess,
    createSubjectFailure,
    deleteSubjectStart,
    deleteSubjectSuccess,
    deleteSubjectFailure,
    fetchSubjectsStart,
    fetchSubjectsSuccess,
    fetchSubjectsFailure,
    updateSubjectStart,
    updateSubjectSuccess,
    updateSubjectFailure,
} = subjectSlice.actions;

export default subjectSlice.reducer;