import {Subject} from "@/interfaces/Subject";
import ClassTime from "@/interfaces/ClassTime";

export interface Schedule {
    id?: string,
    collection: number,
    subject: Subject,
    classTime: ClassTime,
};

export type SchedulePreview = {subjectId: string, classTimeId: string} & Omit<Schedule, "subject" | "classTime">;