import Faculty from "@/interfaces/Faculty";

export interface Career {
    id?: string;
    name: string;
    faculty: Faculty;
}

export type CareerPreview = { facultyId: string } & Omit<Career, "faculty">;