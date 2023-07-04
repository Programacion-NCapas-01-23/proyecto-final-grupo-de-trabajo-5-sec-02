import {AxiosRequestConfig, AxiosResponse} from "axios/index";
import {api} from "@/api/routes";
import {AxiosError} from "axios";

// Helper functions for different HTTP methods
const apiService = {
    get: <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
        return api.get(url, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                throw error
            });
    },

    post: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.post(url, data, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                throw error
            });
    },

    put: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.put(url, data, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                throw error
            });
    },

    patch: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.patch(url, data, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                throw error
            });
    },

    delete: <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
        return api.delete(url, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                throw error
            });
    },
};

export default apiService;