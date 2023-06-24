import {Pensum} from "@/interfaces/Pensum";
import {AppDispatch, AppThunk} from "@/state/store";
import {createPensumFailure, createPensumStart, createPensumSuccess} from "@/state/slices/pensumSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createPensum = (pensum: Pensum): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createPensumStart());
            const createdPensum = await apiService.post<Pensum>(routes.pensum.add, [pensum]);
            dispatch(createPensumSuccess(createdPensum));
        } catch (error) {
            dispatch(createPensumFailure(error.message));
        }
    };
};