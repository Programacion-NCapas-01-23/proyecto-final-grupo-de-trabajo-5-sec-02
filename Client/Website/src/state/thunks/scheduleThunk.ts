import Schedule from "@/interfaces/Schedule";
import {AppThunk, RootState} from "@/state/store";
import {Action, ThunkDispatch} from "@reduxjs/toolkit";
import {createScheduleFailure, createScheduleStart, createScheduleSuccess} from "@/state/slices/scheduleSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createSchedule = (schedule: Schedule): AppThunk => {
    return async (dispatch: ThunkDispatch<RootState, null, Action<string>>) => {
        try {
            dispatch(createScheduleStart());
            const schedules = await apiService.post<Schedule>(routes.schedule.add, [schedule]);
            dispatch(createScheduleSuccess(schedules));
        } catch (error) {
            dispatch(createScheduleFailure(error.message));
        }
    };
};