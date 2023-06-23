import Career from "@/interfaces/Career";
import {AppThunk, RootState} from "@/state/store";
import {createCareerFailure, createCareerStart, createCareerSuccess} from "@/state/slices/careerSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";
import {Action, ThunkDispatch} from "@reduxjs/toolkit";

export const createCareer = (career: Career): AppThunk => {
    return async (dispatch: ThunkDispatch<RootState, null, Action<string>>) => {
        try {
            dispatch(createCareerStart());
            const careers = await apiService.post<Career>(routes.career.add, [career]);
            dispatch(createCareerSuccess(careers));
        } catch (error) {
            dispatch(createCareerFailure(error.message));
        }
    };
};
