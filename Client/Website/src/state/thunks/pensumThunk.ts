import {Pensum, PensumPreview} from "@/interfaces/Pensum";
import {AppDispatch, AppThunk} from "@/state/store";
import {
    createPensumFailure,
    createPensumStart,
    createPensumSuccess,
    fetchPensumsFailure,
    fetchPensumsStart,
    fetchPensumsSuccess
} from "@/state/slices/pensumSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";
import {ErrorResponse} from "@/interfaces/InitialState";

export const fetchPensums = (): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(fetchPensumsStart());
            const careers = await apiService.get<PensumPreview[]>(routes.pensum.all);
            dispatch(fetchPensumsSuccess(careers));
        } catch (error) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(fetchPensumsFailure(receivedError));
        }
    };
};

export const createPensum = (pensum: PensumPreview): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createPensumStart());
            const createdPensum = await apiService.post(routes.pensum.add, [pensum]);
            dispatch(createPensumSuccess(createdPensum));
        } catch (error) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(createPensumFailure(receivedError));
        }
    };
};