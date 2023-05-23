import { AppThunk } from '@/state/store';
import apiService from '@/api/appService';
import {
    createFacultyFailure,
    createFacultyStart, createFacultySuccess,
    fetchFacultiesFailure,
    fetchFacultiesStart,
    fetchFacultiesSuccess, updateFacultyFailure, updateFacultyStart, updateFacultySuccess
} from "@/state/slices/facultySlice";
import {Faculty} from "@/interfaces/Faculty";

export const fetchFaculties = (): AppThunk => {
    return async (dispatch) => {
        try {
            dispatch(fetchFacultiesStart());
            const faculties = await apiService.get<Faculty[]>('/faculties');
            dispatch(fetchFacultiesSuccess(faculties));
        } catch (error) {
            dispatch(fetchFacultiesFailure(error.message));
        }
    };
};

export const createFaculty = (faculty: Faculty): AppThunk => async (dispatch) => {
    try {
        dispatch(createFacultyStart());
        const createdFaculty = await apiService.post<Faculty>('/faculties', faculty);
        dispatch(createFacultySuccess(createdFaculty));
    } catch (error) {
        dispatch(createFacultyFailure(error.message));
    }
};

export const updateFaculty = (faculty: Faculty): AppThunk => async (dispatch) => {
    try {
        dispatch(updateFacultyStart());
        const updatedFaculty = await apiService.patch<Faculty>(`/faculties/${faculty.id}`, faculty);
        dispatch(updateFacultySuccess(updatedFaculty));
    } catch (error) {
        dispatch(updateFacultyFailure(error.message));
    }
};
