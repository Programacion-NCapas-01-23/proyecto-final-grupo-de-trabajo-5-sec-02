import axios, {AxiosInstance} from "axios/index";

export const baseURL: string = 'http://localhost:8080';

export const api: AxiosInstance = axios.create({
    baseURL
});

export const routes = {
    faculties: {
        newFaculty: `${baseURL}/faculties/add`,
        deleteFaculty: `${baseURL}/faculties/delete`,
        getAllFaculties: `${baseURL}/faculties`,
    },
    careers: {
        newCareer: `${baseURL}/`,
    },
}