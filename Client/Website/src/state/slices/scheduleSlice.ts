import InitialState from "@/interfaces/InitialState";
import {SchedulePreview} from "@/interfaces/Schedule";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: InitialState<SchedulePreview> = {
    data: [],
    loading: false,
    error: null,
}

const scheduleSlice = createSlice({
    name: 'schedule',
    initialState,
    reducers: {
        createScheduleStart(state) {
            state.loading = true;
            state.error = null;
        },
        createScheduleSuccess(state, action: PayloadAction<SchedulePreview>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createScheduleFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
    }
});

export const {
    createScheduleStart,
    createScheduleSuccess,
    createScheduleFailure,
} = scheduleSlice.actions;

export default scheduleSlice.reducer;