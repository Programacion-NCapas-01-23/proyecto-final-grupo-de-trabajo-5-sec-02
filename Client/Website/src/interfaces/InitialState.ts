interface InitialState<T> {
    data: T[];
    loading: boolean;
    error: null | ErrorResponse;
}

export default InitialState;

export type ErrorResponse = {
    message: string,
    response: {
        data: any,
        status: number,
    }
}