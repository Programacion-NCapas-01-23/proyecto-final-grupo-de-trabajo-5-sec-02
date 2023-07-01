export interface DefaultState <T>{
    data: T | any;
    loading: boolean;
    error?: string | null;
}