import {Pensum} from "@/interfaces/Pensum";
import {Subject} from "@/interfaces/Subject";

export interface Cycle {
    id?: string,
    type: number,
    name: string,
    pensum: Pensum,
}

export type CyclePreview = {pensumId: string} & Omit<Cycle, "pensum">;

export type CycleResponse = {
    "cycleType": string,
    "orderValue": number,
    "subjects": Subject[],
} & Omit<Cycle, 'pensum' | 'type'>

type Assessments = {
    id: string,
    name: string,
    percentage: number,
    date: string,
    grade: number,
}