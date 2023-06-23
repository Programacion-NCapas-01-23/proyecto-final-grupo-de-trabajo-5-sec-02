import Career from "@/interfaces/Career";

interface Pensum {
    id?: string;
    plan: string;
    career: Career;
}

export type PensumPreview = {careerId: string} & Omit<Pensum, "career">;

export default Pensum;