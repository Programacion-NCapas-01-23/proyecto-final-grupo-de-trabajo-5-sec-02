import ClassTime from "@/interfaces/ClassTime";
import {AppThunk, RootState} from "@/state/store";
import {Action, ThunkDispatch} from "@reduxjs/toolkit";
import {createClassTimeFailure, createClassTimeStart, createClassTimeSuccess} from "@/state/slices/classTimeSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createClassTime = (classTime: ClassTime): AppThunk => {
    return async (dispatch: ThunkDispatch<RootState, null, Action<string>>) => {
        try {
            dispatch(createClassTimeStart());
            const classTimes = await apiService.post<ClassTime>(routes.classTime.add, [classTime]);
            dispatch(createClassTimeSuccess(classTimes));
        } catch (error) {
            dispatch(createClassTimeFailure(error.message));
        }
    };
};