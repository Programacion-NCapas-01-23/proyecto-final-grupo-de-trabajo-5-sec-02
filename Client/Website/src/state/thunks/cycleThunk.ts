import Cycle from "@/interfaces/Cycle";
import {AppThunk, RootState} from "@/state/store";
import {Action, ThunkDispatch} from "@reduxjs/toolkit";
import {createCycleFailure, createCycleStart, createCycleSuccess} from "@/state/slices/cycleSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createCycle = (cycle: Cycle): AppThunk => {
    return async (dispatch: ThunkDispatch<RootState, null, Action<string>>) => {
        try {
            dispatch(createCycleStart());
            const cycles = await apiService.post<Cycle>(routes.cycle.add, [cycle]);
            dispatch(createCycleSuccess(cycles));
        } catch (error) {
            dispatch(createCycleFailure(error.message));
        }
    };
};