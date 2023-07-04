import axios, {AxiosInstance} from "axios";

export const baseURL: string = 'http://20.127.25.48:8080/admin';

const getAuthToken = () => {
    if (typeof window !== 'undefined') {
        console.log(localStorage.getItem('token'))
        return localStorage.getItem('token');
    }
    return null; // Running on the server-side
};

export const api: AxiosInstance = axios.create({
    baseURL,
    headers: {
        ...(getAuthToken() && {
            Authorization: `Bearer ${getAuthToken()}`,
        }),
        "Content-Type": "application/json",
    },
});

export const routes = {
    auth: {
        login: `${baseURL}/auth/login`,
        register: `${baseURL}/auth/Register`,
    },
    classTime: {
        add: `${baseURL}/class-time/add`,
        update: `${baseURL}/class-time/update`,
        delete: `${baseURL}/class-time/delete`,
    },
    cycle: {
        add: `${baseURL}/cycles/add`,
        delete: `${baseURL}/cycles/delete`,
        update: `${baseURL}/cycles/update`,
        all: `${baseURL}/cycles/all`,
    },
    career: {
        add: `${baseURL}/degrees/add`,
        delete: `${baseURL}/degrees/delete`,
        update: `${baseURL}/degrees/update`,
        all: `${baseURL}/degrees/all`,
    },
    faculties: {
        add: `${baseURL}/faculties/add`,
        delete: `${baseURL}/faculties/delete`,
        update: `${baseURL}/faculties/update`,
        all: `${baseURL}/faculties/all`,
    },
    pensum: {
        add: `${baseURL}/pensum/add`,
        delete: `${baseURL}/pensum/delete`,
        update: `${baseURL}/pensum/update`,
        all: `${baseURL}/pensum/all`,
    },
    schedule: {
        add: `${baseURL}/schedules/add`,
        delete: `${baseURL}/schedules/delete`,
        deleteAll: `${baseURL}/schedules/delete/all`,
        update: `${baseURL}/schedules/update`,
    },
    subject: {
        add: `${baseURL}/subjects/add`,
        delete: `${baseURL}/subjects/delete`,
        deleteAll: `${baseURL}/subjects/delete/all`,
        update: `${baseURL}/subjects/update`,
        all: `${baseURL}/subjects/getAllSubjects`,
    }
}