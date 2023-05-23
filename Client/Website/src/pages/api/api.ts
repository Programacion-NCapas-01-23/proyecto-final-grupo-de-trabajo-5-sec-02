import { NextApiResponse } from 'next';
import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';

const api: AxiosInstance = axios.create({
  baseURL: 'YOUR_API_BASE_URL', // Replace with your API base URL
});

// Response interceptor
api.interceptors.response.use(
    (response: AxiosResponse) => {
      // You can handle successful responses here if needed
      return response;
    },
    (error: AxiosError) => {
      if (error.response) {
        // The request was made, but the server responded with a non-success status code
        // You can handle specific error status codes here
        const { status, data } = error.response;
        console.error(`HTTP Error ${status}:`, data);
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
      return Promise.reject({ message: 'An error occurred. Please try again.' });
    }
);

// Helper functions for different HTTP methods
export const apiService = {
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

export interface ApiRequest {
  url: string;
  method: string;
  data?: any;
}

export default async function handler(req: ApiRequest, res: NextApiResponse) {
  const { url, method, data } = req;

  try {
    const response = await apiService[method](url, data);
    res.status(200).json(response);
  } catch (error) {
    console.error('API Error:', error);
    const status = error.status || 500;
    const message = error.message || 'An error occurred.';
    res.status(status).json({ message });
  }
}
