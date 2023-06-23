type cycleRelation = [{
    id: string,
    correlative: number,
}]

export interface Subject {
    id?: string;
    estimatedGrade?: number;
    name: string;
    uv: number;
    cycleRelation?: cycleRelation;
}

export type SubjectPreview = Subject;
