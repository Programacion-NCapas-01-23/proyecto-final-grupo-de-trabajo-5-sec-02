import {Action, configureStore, ThunkAction} from '@reduxjs/toolkit'
import facultyReducer from './slices/facultySlice'
import careerReducer from './slices/careerSlice'
import pensumReducer from "@/state/slices/pensumSlice";

const store =  configureStore({
    reducer: {
        faculty: facultyReducer,
        career: careerReducer,
        pensum: pensumReducer,
    }
})

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch
export type AppThunk = ThunkAction<void, RootState, null, Action<string>>

export default store;