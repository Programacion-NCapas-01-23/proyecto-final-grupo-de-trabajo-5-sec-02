import {Subject} from "@/interfaces/Subject";
import {AppDispatch, AppThunk, RootState} from "@/state/store";
import {createSubjectFailure, createSubjectStart, createSubjectSuccess} from "@/state/slices/subjectSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createSubject = (subject: Subject): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createSubjectStart());
            const createdSubject = await apiService.post<Subject>(routes.subject.add, [subject]);
            dispatch(createSubjectSuccess(createdSubject));
        } catch (error) {
            dispatch(createSubjectFailure(error.message));
        }
    };
};