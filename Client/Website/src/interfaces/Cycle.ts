import Pensum from "@/interfaces/Pensum";

interface Cycle {
    id?: string,
    cycleType: number,
    name: string,
    pensum: Pensum,
}

export default Cycle;