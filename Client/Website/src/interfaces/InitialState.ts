interface InitialState<T> {
    data: T[];
    loading: boolean;
    error: string | null;
}

export default InitialState;