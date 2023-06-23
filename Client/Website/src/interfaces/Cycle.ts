import Pensum from "@/interfaces/Pensum";

interface Cycle {
    id?: string,
    cycleType: number,
    name: string,
    pensum: Pensum,
}

export type CyclePreview = {pensumId: string} & Omit<Cycle, "pensum">;

export default Cycle;