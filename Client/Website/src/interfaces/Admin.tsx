export interface Admin {
    id?: string;
    username: string;
    email: string;
    password: string;
    name: string;
}

export type Login = Omit<Admin, 'email' | 'name'>
