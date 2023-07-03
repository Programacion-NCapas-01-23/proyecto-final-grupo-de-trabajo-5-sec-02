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

export const fetchPensums = (): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(fetchPensumsStart());
            const careers = await apiService.get<PensumPreview[]>(routes.pensum.all);
            dispatch(fetchPensumsSuccess(careers));
        } catch (error) {
            dispatch(fetchPensumsFailure(error.message));
        }
    };
};

export const createPensum = (pensum: PensumPreview): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createPensumStart());
            const createdPensum = await apiService.post<PensumPreview>(routes.pensum.add, [pensum]);
            dispatch(createPensumSuccess(createdPensum));
        } catch (error) {
            dispatch(createPensumFailure(error.message));
        }
    };
};