import {Career, CareerPreview} from "@/interfaces/Career";
import {AppDispatch, AppThunk} from "@/state/store";
import {
    createCareerFailure,
    createCareerStart,
    createCareerSuccess,
    fetchCareersFailure,
    fetchCareersStart,
    fetchCareersSuccess, updateCareerFailure, updateCareerStart, updateCareerSuccess
} from "@/state/slices/careerSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";
import {Faculty} from "@/interfaces/Faculty";
import {updateFacultyFailure, updateFacultyStart, updateFacultySuccess} from "@/state/slices/facultySlice";
import {ErrorResponse} from "@/interfaces/InitialState";

export const fetchCareers = (): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(fetchCareersStart());
            const careers = await apiService.get<Career[]>(routes.career.all);
            dispatch(fetchCareersSuccess(careers));
        } catch (error) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(fetchCareersFailure(receivedError));
        }
    };
};

export const createCareer = (career: CareerPreview): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createCareerStart());
            const createdCareer = await apiService.post<Career>(routes.career.add, [career]);
            dispatch(createCareerSuccess(createdCareer));
        } catch (error) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            console.log(receivedError)
            dispatch(createCareerFailure(receivedError));
        }
    };
};

export const updateCareer = (career: CareerPreview): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(updateCareerStart());
            const updatedCareer = await apiService.patch<CareerPreview>(routes.career.update, career);
            dispatch(updateCareerSuccess(updatedCareer));
        } catch (error) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(updateCareerFailure(receivedError));
        }
    }
};