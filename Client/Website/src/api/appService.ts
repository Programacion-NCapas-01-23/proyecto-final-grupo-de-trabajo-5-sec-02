import {AxiosRequestConfig, AxiosResponse} from "axios/index";
import {api} from "@/api/routes";

// Helper functions for different HTTP methods
const apiService = {
    get: <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
        return api.get(url, config).then((response: AxiosResponse<T>) => response.data);
    },

    post: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.post(url, data, config).then((response: AxiosResponse<T>) => response.data);
    },

    put: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.put(url, data, config).then((response: AxiosResponse<T>) => response.data);
    },

    patch: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.patch(url, data, config).then((response: AxiosResponse<T>) => response.data);
    },

    delete: <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
        return api.delete(url, config).then((response: AxiosResponse<T>) => response.data);
    },
};

export default apiService;