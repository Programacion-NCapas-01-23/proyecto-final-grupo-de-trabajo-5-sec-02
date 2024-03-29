import {Subject} from "@/interfaces/Subject";
import {AppDispatch, AppThunk} from "@/state/store";
import {
    createSubjectFailure,
    createSubjectStart,
    createSubjectSuccess,
    fetchSubjectsFailure,
    fetchSubjectsStart,
    fetchSubjectsSuccess, updateSubjectFailure, updateSubjectStart, updateSubjectSuccess
} from "@/state/slices/subjectSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";
import {ErrorResponse} from "@/interfaces/InitialState";
import {CareerPreview} from "@/interfaces/Career";
import {updateCareerFailure, updateCareerStart, updateCareerSuccess} from "@/state/slices/careerSlice";

export const fetchSubjects = (): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(fetchSubjectsStart());
            const subjects = await apiService.get<Subject[]>(routes.subject.all);
            dispatch(fetchSubjectsSuccess(subjects));
        } catch (error: any) {
            // @ts-ignore
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(fetchSubjectsFailure(receivedError));
        }
    };
};

export const createSubject = (subject: Subject): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createSubjectStart());
            const createdSubject = await apiService.post<Subject>(routes.subject.add, [subject]);
            dispatch(createSubjectSuccess(createdSubject));
        } catch (error: any) {
            // @ts-ignore
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(createSubjectFailure(receivedError));
        }
    };
};

export const updateSubject = (subject: Subject): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(updateSubjectStart());
            const updatedSubject = await apiService.patch<Subject>(routes.subject.update, subject);
            dispatch(updateSubjectSuccess(updatedSubject));
        } catch (error: any) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(updateSubjectFailure(receivedError));
        }
    }
};