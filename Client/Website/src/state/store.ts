import {Action, configureStore, ThunkAction} from '@reduxjs/toolkit'
import facultyReducer from './slices/facultySlice'
import careerReducer from './slices/careerSlice'
import classTimeReducer from './slices/classTimeSlice'
import cycleReducer from './slices/cycleSlice'
import pensumReducer from './slices/pensumSlice'
import scheduleReducer from './slices/scheduleSlice'
import subjectReducer from './slices/subjectSlice'

const store =  configureStore({
    reducer: {
        faculty: facultyReducer,
        career: careerReducer,
        classTime: classTimeReducer,
        cycle: cycleReducer,
        pensum: pensumReducer,
        schedule: scheduleReducer,
        subject: subjectReducer,
    }
})

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch
export type AppThunk = ThunkAction<void, RootState, null, Action<string>>

export default store;