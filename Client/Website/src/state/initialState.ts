import Faculty from "@/interfaces/Faculty";
import InitialState from "@/interfaces/InitialState";
import Career from "@/interfaces/Career";
import ClassTime from "@/interfaces/ClassTime";
import Cycle from "@/interfaces/Cycle";
import Pensum from "@/interfaces/Pensum";
import Schedule from "@/interfaces/Schedule";
import Subject from "@/interfaces/Subject";

export type RootState = {
    faculty: InitialState<Faculty>;
    career: InitialState<Career>;
    classTime: InitialState<ClassTime>;
    cycle: InitialState<Cycle>;
    pensum: InitialState<Pensum>;
    schedule: InitialState<Schedule>;
    subject: InitialState<Subject>;
}