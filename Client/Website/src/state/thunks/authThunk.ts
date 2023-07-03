import {Admin, Login, LoginResponse} from "@/interfaces/Admin";
import {AppDispatch, AppThunk} from "@/state/store";
import {registerFailure, registerStart, registerSuccess} from "@/state/slices/authSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const loginAdmin = (login: Login): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            const access = await apiService.post<LoginResponse>(routes.auth.login, login);
            await localStorage.setItem('token', access.token)
        } catch (error) {
            console.log(error);
        }
    }
}

export const registerAdmin = (admin: Admin): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(registerStart());
            const user = await apiService.post<Admin>(routes.auth.register, admin);
            dispatch(registerSuccess(user));
        } catch (error) {
            dispatch(registerFailure(error.message))
        }
    }
}