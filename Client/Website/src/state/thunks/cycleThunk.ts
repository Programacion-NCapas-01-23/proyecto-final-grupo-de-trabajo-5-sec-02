import {Cycle, CyclePreview, CycleResponse} from "@/interfaces/Cycle";
import {AppDispatch, AppThunk} from "@/state/store";
import {
    createCycleFailure,
    createCycleStart,
    createCycleSuccess,
    fetchCyclesFailure,
    fetchCyclesStart,
    fetchCyclesSuccess
} from "@/state/slices/cycleSlice";
import apiService from "@/api/appService";
import {routes} from "@/api/routes";
import {ErrorResponse} from "@/interfaces/InitialState";

export const fetchCycles = (): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(fetchCyclesStart());
            const cycles = await apiService.get<CycleResponse[]>(routes.cycle.all);
            dispatch(fetchCyclesSuccess(cycles));
        } catch (error: any) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(fetchCyclesFailure(receivedError));
        }
    };
};

export const createCycle = (cycle: CyclePreview): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createCycleStart());
            const createdCycle = await apiService.post<CyclePreview>(routes.cycle.add, [cycle]);
            dispatch(createCycleSuccess(createdCycle));
        } catch (error: any) {
            const receivedError: ErrorResponse = {
                message: error.message,
                response: {
                    data: error.response.data,
                    status: error.response.status,
                }
            }
            dispatch(createCycleFailure(receivedError));
        }
    };
};