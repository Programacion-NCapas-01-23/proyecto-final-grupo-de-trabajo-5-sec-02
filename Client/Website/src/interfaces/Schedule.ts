import Subject from "@/interfaces/Subject";
import ClassTime from "@/interfaces/ClassTime";

interface Schedule {
    id?: string,
    collection: number,
    subject: Subject,
    classTime: ClassTime,
}

export default Schedule;