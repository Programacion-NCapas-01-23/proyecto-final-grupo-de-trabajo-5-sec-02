import {AxiosRequestConfig, AxiosResponse} from "axios/index";
import {api} from "@/api/routes";
import {AxiosError} from "axios";
import {useRouter} from "next/navigation";

// Helper functions for different HTTP methods
const apiService = {
    get: <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
        return api.get(url, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                if (error.response?.status === 401) {
                    const router = useRouter();
                    router.push('/login');
                }
                throw error;
            });
    },

    post: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.post(url, data, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                if (error.response?.status === 401) {
                    const router = useRouter();
                    router.push('/login');
                }
                throw error;
            });
    },

    put: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.put(url, data, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                if (error.response?.status === 401) {
                    const router = useRouter();
                    router.push('/login');
                }
                throw error;
            });
    },

    patch: <T>(url: string, data: any, config?: AxiosRequestConfig): Promise<T> => {
        return api.patch(url, data, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                if (error.response?.status === 401) {
                    const router = useRouter();
                    router.push('/login');
                }
                throw error;
            });
    },

    delete: <T>(url: string, config?: AxiosRequestConfig): Promise<T> => {
        return api.delete(url, config)
            .then((response: AxiosResponse<T>) => response.data)
            .catch((error: AxiosError) => {
                if (error.response?.status === 401) {
                    const router = useRouter();
                    router.push('/login');
                }
                throw error;
            });
    },
};

export default apiService;