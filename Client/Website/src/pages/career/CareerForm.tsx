import {useAppDispatch} from "@/hooks/reduxHooks";
import React, {ChangeEvent, FormEvent, useState} from "react";
import Career from "@/interfaces/Career";
import Faculty from "@/interfaces/Faculty";
import {createCareerStart} from "@/state/slices/careerSlice";
import {createCareer} from "@/state/thunks/careerThunk";

const CareerForm = (): JSX.Element => {
    const dispatch = useAppDispatch();
    // const [facultyName, setFacultyName] = useState('');
    // const [selectedUniversity, setSelectedUniversity] = useState('');

    const newFaculty: Faculty = {
        name: '',
        description: '',
    }

    /*
    const handleFacultyNameChange = (e: ChangeEvent<HTMLInputElement>) => {
        setFacultyName(e.target.value);
    };

    const handleUniversityChange = (e: ChangeEvent<HTMLSelectElement>) => {
        setSelectedUniversity(e.target.value);
    };
    */

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
        <div className="p-4 sm:ml-64">
            <h1 className="text-2xl font-bold mb-4">Crear Carrera</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nombre de la Carrera: </label>
                    <input type="text"
                           id="careerName"
                           className="border rounded px-4 py-2 w-full"
                           value={}
                           onChange={}
                           required
                    />
                </div>
                <div>
                    <label>Año del Plan: </label>
                    <input type="text"
                           id="pensumPlan"
                           className="border rounded px-4 py-2 w-full"
                           value={}
                           onChange={}
                           required
                    />
                </div>
                <div className="mb-4">
                    <label htmlFor="faculty" className="block text-gray-700 font-bold mb-2">
                        Facultad
                    </label>
                    {/*<select
                        id="faculty"
                        className="border rounded px-4 py-2 w-full"
                        value={selectedUniversity}
                        onChange={handleUniversityChange}
                        required
                    >
                        <option value="">Seleciona la facultad...</option>
                        {universities.map((university) => (
                            <option key={university} value={university}>
                                {university}
                            </option>
                        ))}
                    </select>*/}
                </div>
                <button
                    type="submit"
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >
                    Save
                </button>
            </form>
        <div/>
    );
};

export default CareerForm;