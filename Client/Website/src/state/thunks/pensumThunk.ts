import {AppThunk} from "@/state/store";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";
import {createPensumFailure, createPensumStart, createPensumSuccess} from "@/state/slices/pensumSlice";
import Pensum from "@/interfaces/Pensum";

export const createPensum = (pensum: Pensum): AppThunk =>
    async (dispatch) => {
        try {
            dispatch(createPensumStart());
            const pensums = await apiService.post<Pensum>(routes.pensums.newPensum, pensum);
            dispatch(createPensumSuccess(pensums));
        } catch (error) {
            dispatch(createPensumFailure(error.message));
        }
    };
