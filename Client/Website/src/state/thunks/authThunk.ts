import {Admin, Login} from "@/interfaces/Admin";
import {AppDispatch, AppThunk} from "@/state/store";
import {
    loginFailure,
    loginStart,
    loginSuccess,
    registerFailure,
    registerStart,
    registerSuccess
} from "@/state/slices/authSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";

export const loginAdmin = (login: Login): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(loginStart());
            const access = await apiService.post<Login>(routes.auth.login, login);
            dispatch(loginSuccess(access));
        } catch (error) {
            dispatch(loginFailure(error.message))
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