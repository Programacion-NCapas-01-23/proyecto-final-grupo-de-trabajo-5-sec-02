import {Schedule, SchedulePreview} from "@/interfaces/Schedule";
import {AppDispatch, AppThunk, RootState} from "@/state/store";
import {createScheduleFailure, createScheduleStart, createScheduleSuccess} from "@/state/slices/scheduleSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createSchedule = (schedule: SchedulePreview): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createScheduleStart());
            const createdSchedule = await apiService.post<SchedulePreview>(routes.schedule.add, [schedule]);
            dispatch(createScheduleSuccess(createdSchedule));
        } catch (error) {
            dispatch(createScheduleFailure(error.message));
        }
    };
};