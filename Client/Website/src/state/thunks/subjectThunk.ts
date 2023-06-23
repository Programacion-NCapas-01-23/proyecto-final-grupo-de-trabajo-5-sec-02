import Subject from "@/interfaces/Subject";
import {AppThunk, RootState} from "@/state/store";
import {Action, ThunkDispatch} from "@reduxjs/toolkit";
import {createSubjectFailure, createSubjectStart, createSubjectSuccess} from "@/state/slices/subjectSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const createSubject = (subject: Subject): AppThunk => {
    return async (dispatch: ThunkDispatch<RootState, null, Action<string>>) => {
        try {
            dispatch(createSubjectStart());
            const subjects = await apiService.post<Subject>(routes.subject.add, [subject]);
            dispatch(createSubjectSuccess(subjects));
        } catch (error) {
            dispatch(createSubjectFailure(error.message));
        }
    };
};