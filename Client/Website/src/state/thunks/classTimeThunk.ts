import ClassTime from "@/interfaces/ClassTime";
import {AppDispatch, AppThunk} from "@/state/store";
import {createClassTimeFailure, createClassTimeStart, createClassTimeSuccess} from "@/state/slices/classTimeSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createClassTime = (classTime: ClassTime): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createClassTimeStart());
            const createdClassTime = await apiService.post<ClassTime>(routes.faculties.add, [classTime]);
            dispatch(createClassTimeSuccess(createdClassTime));
        } catch (error) {
            dispatch(createClassTimeFailure(error.message));
        }
    };
};