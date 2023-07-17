import { Action, configureStore, ThunkAction } from '@reduxjs/toolkit'
import facultyReducer from './slices/facultySlice'
import careerReducer from './slices/careerSlice'
import classTimeReducer from './slices/classTimeSlice'
import cycleReducer from './slices/cycleSlice'
import pensumReducer from './slices/pensumSlice'
import scheduleReducer from './slices/scheduleSlice'
import subjectReducer from './slices/subjectSlice'
import authReducer from './slices/authSlice'

const store = configureStore({
    reducer: {
        faculty: facultyReducer,
        career: careerReducer,
        classTime: classTimeReducer,
        cycle: cycleReducer,
        pensum: pensumReducer,
        schedule: scheduleReducer,
        subject: subjectReducer,
        auth: authReducer,
    },
})

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: { faculty: FacultyState; career: CareerState; classTime: ClassTimeState; cycle: CycleState; pensum: PensumState; schedule: ScheduleState; subject: SubjectState; }
export type AppDispatch = typeof store.dispatch
export type AppThunk<ReturnType = void> = ThunkAction<ReturnType, RootState, undefined, Action<string>>

export default store;
