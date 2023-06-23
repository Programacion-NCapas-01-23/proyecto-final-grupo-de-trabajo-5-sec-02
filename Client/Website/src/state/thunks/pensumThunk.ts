import Pensum from "@/interfaces/Pensum";
import {AppThunk, RootState} from "@/state/store";
import {Action, ThunkDispatch} from "@reduxjs/toolkit";
import {createPensumFailure, createPensumStart, createPensumSuccess} from "@/state/slices/pensumSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createPensum = (pensum: Pensum): AppThunk => {
    return async (dispatch: ThunkDispatch<RootState, null, Action<string>>) => {
        try {
            dispatch(createPensumStart());
            const pensums = await apiService.post<Pensum>(routes.pensum.add, [pensum]);
            dispatch(createPensumSuccess(pensums));
        } catch (error) {
            dispatch(createPensumFailure(error.message));
        }
    };
};