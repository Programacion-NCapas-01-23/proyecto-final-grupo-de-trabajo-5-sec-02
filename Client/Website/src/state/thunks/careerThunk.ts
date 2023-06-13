import Career from "@/interfaces/Career";
import {AppThunk} from "@/state/store";
import {createCareerFailure, createCareerStart, createCareerSuccess} from "@/state/slices/careerSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createCareer = (career: Career): AppThunk =>
    async (dispatch) => {
        try {
            dispatch(createCareerStart());
            const careers = await apiService.post<Career>(routes.careers.newCareer, career);
            dispatch(createCareerSuccess(careers));
        } catch (error) {
            dispatch(createCareerFailure(error.message));
        }
    };
