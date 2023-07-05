export interface Admin {
    id?: string;
    username: string;
    email: string;
    password: string;
    name: string;
}

export type Login = Omit<Admin, 'email' | 'name'>

export type LoginResponse = {
    message: string,
    token: string,
}