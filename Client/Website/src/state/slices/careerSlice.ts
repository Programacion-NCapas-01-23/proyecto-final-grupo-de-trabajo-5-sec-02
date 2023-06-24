import InitialState from "@/interfaces/InitialState";
import {Career} from "@/interfaces/Career";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";

const initialState: InitialState<Career> = {
    data: [],
    loading: false,
    error: null,
}

const careerSlice = createSlice({
    name: 'career',
    initialState,
    reducers: {
        createCareerStart(state) {
            state.loading = true;
            state.error = null;
        },
        createCareerSuccess(state, action: PayloadAction<Career>) {
            state.data.push(action.payload);
            state.loading = false;
            state.error = null;
        },
        createCareerFailure(state, action: PayloadAction<string>) {
            state.loading = false;
            state.error = action.payload;
        },
    },
});

export const {
    createCareerStart,
    createCareerSuccess,
    createCareerFailure,
} = careerSlice.actions;

export default careerSlice.reducer;