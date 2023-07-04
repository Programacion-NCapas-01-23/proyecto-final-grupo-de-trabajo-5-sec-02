import ClassTime from "@/interfaces/ClassTime";
import {AppDispatch, AppThunk} from "@/state/store";
import {createClassTimeFailure, createClassTimeStart, createClassTimeSuccess} from "@/state/slices/classTimeSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";
import {ErrorResponse} from "@/interfaces/InitialState";

export const createClassTime = (classTime: ClassTime): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createClassTimeStart());
            const createdClassTime = await apiService.post<ClassTime>(routes.classTime.add, [classTime]);
            dispatch(createClassTimeSuccess(createdClassTime));
        } catch (error: any) {
            // @ts-ignore
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(createClassTimeFailure(receivedError));
        }
    };
};