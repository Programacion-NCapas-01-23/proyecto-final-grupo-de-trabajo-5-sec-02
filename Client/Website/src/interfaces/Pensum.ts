import {Career} from "@/interfaces/Career";

export interface Pensum {
    id?: string;
    plan: string;
    career: Career;
}

export type PensumPreview = {degreeId: string} & Omit<Pensum, "career">;