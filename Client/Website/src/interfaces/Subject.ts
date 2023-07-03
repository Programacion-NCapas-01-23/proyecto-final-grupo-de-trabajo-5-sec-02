type cycleRelation = [{
    id: string,
    correlative: number,
}]

export interface Subject {
    id?: string;
    code: string;
    estimatedGrade?: number;
    name: string;
    uv: number;
    cycleRelation?: cycleRelation;
}

