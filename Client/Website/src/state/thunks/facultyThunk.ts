import {AppDispatch, AppThunk} from '@/state/store';
import apiService from '@/api/appService';
import {
    createFacultyFailure,
    createFacultyStart,
    createFacultySuccess,
    fetchFacultiesFailure,
    fetchFacultiesStart,
    fetchFacultiesSuccess,
    updateFacultyFailure,
    updateFacultyStart,
    updateFacultySuccess
} from "@/state/slices/facultySlice";
import {Faculty} from "@/interfaces/Faculty";
import {routes} from "@/api/routes";
import {ErrorResponse} from "@/interfaces/InitialState";

export const fetchFaculties = (): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(fetchFacultiesStart());
            const faculties = await apiService.get<Faculty[]>(routes.faculties.all);
            dispatch(fetchFacultiesSuccess(faculties));
        } catch (error) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(fetchFacultiesFailure(receivedError));
        }
    };
};

export const createFaculty = (faculty: Faculty): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createFacultyStart());
            const createdFaculty = await apiService.post<Faculty>(routes.faculties.add, [faculty]);
            dispatch(createFacultySuccess(createdFaculty));
        } catch (error) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(createFacultyFailure(receivedError));
        }
    };
};


export const updateFaculty = (faculty: Faculty): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(updateFacultyStart());
            const updatedFaculty = await apiService.patch<Faculty>(routes.faculties.update, faculty);
            dispatch(updateFacultySuccess(updatedFaculty));
        } catch (error) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(updateFacultyFailure(receivedError));
        }
    }
};
