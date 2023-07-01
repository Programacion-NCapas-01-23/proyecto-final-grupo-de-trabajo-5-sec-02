import axios, {AxiosInstance} from "axios";

export const baseURL: string = 'http://localhost:8080/admin';
const token: string = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0NDQ0NTQ0OS1hYzA3LTQ5ZjItYTAwOC1jNTVjMGQ1ZmJlNmIiLCJpYXQiOjE2ODc1NjkxNjIsImV4cCI6MTY4NzYzMzk2Mn0.QbDWy6hdKHs4wpw0iZofEkzbssCW8VrieuRsKpWebn0';

export const api: AxiosInstance = axios.create({
    baseURL,
    headers: {
        Authorization: `Bearer ${token}`,
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
    },
    career: {
        add: `${baseURL}/degrees/add`,
        delete: `${baseURL}/degrees/delete`,
        update: `${baseURL}/degrees/update`,
    },
    faculties: {
        add: `${baseURL}/faculties/add`,
        delete: `${baseURL}/faculties/delete`,
        update: `${baseURL}/faculties/update`,
    },
    pensum: {
        add: `${baseURL}/pensum/add`,
        delete: `${baseURL}/pensum/delete`,
        update: `${baseURL}/pensum/update`,
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
    }
}