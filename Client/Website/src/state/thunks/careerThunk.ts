import {Career, CareerPreview} from "@/interfaces/Career";
import {AppDispatch, AppThunk} from "@/state/store";
import {createCareerFailure, createCareerStart, createCareerSuccess} from "@/state/slices/careerSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createCareer = (career: CareerPreview): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createCareerStart());
            const createdCareer = await apiService.post<Career>(routes.career.add, [career]);
            dispatch(createCareerSuccess(createdCareer));
        } catch (error) {
            dispatch(createCareerFailure(error.message));
        }
    };
};
