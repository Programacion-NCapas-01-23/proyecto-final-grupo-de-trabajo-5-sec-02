import InitialState, {ErrorResponse} from "@/interfaces/InitialState";
import ClassTime from "@/interfaces/ClassTime";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: InitialState<ClassTime> = {
    data: [],
    loading: false,
    error: null,
}

const classTimeSlice = createSlice({
    name: 'classTime',
    initialState,
    reducers: {
        createClassTimeStart(state) {
            state.loading = true;
            state.error = null;
        },
        createClassTimeSuccess(state, action: PayloadAction<ClassTime>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createClassTimeFailure(state, action: PayloadAction<ErrorResponse>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
});

export const {
    createClassTimeStart,
    createClassTimeSuccess,
    createClassTimeFailure,
} = classTimeSlice.actions;

export default classTimeSlice.reducer;