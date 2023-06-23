import InitialState from "@/interfaces/InitialState";
import Subject from "@/interfaces/Subject";
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
        createSubjectStart(state) {
            state.loading = true;
            state.error = null;
        },
        createSubjectSuccess(state, action: PayloadAction<Subject>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createSubjectFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
});

export const {
    createSubjectStart,
    createSubjectSuccess,
    createSubjectFailure,
} = subjectSlice.actions;

export default subjectSlice.reducer;