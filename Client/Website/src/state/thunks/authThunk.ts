import {Admin, Login, LoginResponse} from "@/interfaces/Admin";
import {AppDispatch, AppThunk} from "@/state/store";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";
import {ErrorResponse} from "@/interfaces/InitialState";

export const loginAdmin = (login: Login): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            const access = await apiService.post<LoginResponse>(routes.auth.login, login);
            await localStorage.setItem('token', access.token)
        } catch (error: any) {
            // @ts-ignore
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            console.log(error);
        }
    }
}

export const registerAdmin = (admin: Admin): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {

            const user = await apiService.post(routes.auth.register, admin);
            console.log(user);
        } catch (error: any) {
            // @ts-ignore
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            console.log(error);
        }
    }
}