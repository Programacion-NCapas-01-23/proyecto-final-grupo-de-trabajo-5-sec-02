import {Cycle, CyclePreview} from "@/interfaces/Cycle";
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

export const fetchCycles = (): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(fetchCyclesStart());
            const careers = await apiService.get<Cycle[]>(routes.cycle.all);
            dispatch(fetchCyclesSuccess(careers));
        } catch (error) {
            dispatch(fetchCyclesFailure(error.message));
        }
    };
};

export const createCycle = (cycle: CyclePreview): AppThunk => {
    return async (dispatch: AppDispatch) => {
        try {
            dispatch(createCycleStart());
            const createdCycle = await apiService.post<Cycle>(routes.cycle.add, [cycle]);
            dispatch(createCycleSuccess(createdCycle));
        } catch (error) {
            dispatch(createCycleFailure(error.message));
        }
    };
};