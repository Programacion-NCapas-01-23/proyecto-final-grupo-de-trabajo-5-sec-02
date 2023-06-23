import {Pensum} from "@/interfaces/Pensum";

export interface Cycle {
    id?: string,
    cycleType: number,
    name: string,
    pensum: Pensum,
}

export type CyclePreview = {pensumId: string} & Omit<Cycle, "pensum">;