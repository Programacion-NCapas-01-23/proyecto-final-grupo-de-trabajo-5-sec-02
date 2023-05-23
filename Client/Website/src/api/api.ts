import { NextApiResponse } from 'next';
import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';
import apiService from "@/api/appService";

export const api: AxiosInstance = axios.create({
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
