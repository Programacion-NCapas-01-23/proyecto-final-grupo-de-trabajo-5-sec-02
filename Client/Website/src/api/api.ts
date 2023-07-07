import {NextApiResponse} from 'next';
import {AxiosError, AxiosResponse} from 'axios';
import apiService from "@/api/appService";
import {api} from "@/api/routes";
import {useRouter} from "next/navigation";

// Response interceptor
api.interceptors.response.use(
    (response: AxiosResponse) => {
        console.log(response)
        // You can handle successful responses here if needed
        return response;
    },
    (error: AxiosError) => {
        if (error.response) {
            // The request was made, but the server responded with a non-success status code
            // You can handle specific error status codes here
            const {status, data} = error.response;

            console.error(`HTTP Error ${status}:`, data);

            if (status === 401) {
                // Redirect to login page
                const router = useRouter();
                router.push('/login');
            }
            // Return the error response to the client
            return Promise.reject(data);
        } else if (error.request) {
            // The request was made but no response was received
            console.error('No response received from the server.');
        } else {
            // Something happened in setting up the request that triggered an error
            console.error('Error:', error.message);
        }
        // Return a generic error message to the client
        return Promise.reject({message: 'An error occurred. Please try again.'});
    }
);

export interface ApiRequest {
    url: string;
    method: string;
    data?: any;
}

