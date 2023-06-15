import {useAppDispatch} from "@/hooks/reduxHooks";
import {FormEvent} from "react";
import Career from "@/interfaces/Career";
import Faculty from "@/interfaces/Faculty";
import {createCareerStart} from "@/state/slices/careerSlice";
import {createCareer} from "@/state/thunks/careerThunk";

const CareerForm = (): JSX.Element => {
    const dispatch = useAppDispatch();

    const newFaculty: Faculty = {
        name: '',
        description: '',
    }

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const newCareer: Career = {
            name: '',
            faculty: newFaculty,
        };
        dispatch(createCareerStart());
        createCareer(newCareer);
    };

    return (
        <div>
            <div className="">
                <h1>Crear Carrera</h1>
                <form onSubmit={handleSubmit}>

                </form>
            </div>
        </div>
    )
}

export default CareerForm;