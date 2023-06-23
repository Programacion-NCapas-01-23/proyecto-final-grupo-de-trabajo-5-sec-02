import Faculty from "@/interfaces/Faculty";
import InitialState from "@/interfaces/InitialState";
import Career from "@/interfaces/Career";

export type RootState = {
    faculty: InitialState<Faculty>;
    career: InitialState<Career>
}