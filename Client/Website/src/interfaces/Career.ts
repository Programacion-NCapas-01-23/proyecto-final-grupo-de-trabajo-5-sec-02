import Faculty from "@/interfaces/Faculty";

interface Career {
    id?: string;
    name: string;
    faculty: Faculty;
}

export type CareerPreview = { facultyId: string } & Omit<Career, "faculty">;

export default Career;