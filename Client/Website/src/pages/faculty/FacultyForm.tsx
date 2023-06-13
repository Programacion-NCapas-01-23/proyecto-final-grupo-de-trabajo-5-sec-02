import React, {ChangeEvent, FormEvent, useState} from 'react';
import { useAppDispatch } from '@/hooks/reduxHooks';
import axios from 'axios';

import { createFacultyStart } from '@/state/slices/facultySlice';

const universities = ['University A', 'University B', 'University C']; // Replace with your actual list of universities

const FacultyForm = () => {
    const dispatch = useAppDispatch();
    const [facultyName, setFacultyName] = useState('');
    const [selectedUniversity, setSelectedUniversity] = useState('');

    const handleFacultyNameChange = (e: ChangeEvent<HTMLInputElement>) => {
        setFacultyName(e.target.value);
    };

    const handleUniversityChange = (e: ChangeEvent<HTMLSelectElement>) => {
        setSelectedUniversity(e.target.value);
    };

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        try {
            // Replace 'API_URL' with your actual API endpoint
            const response = await axios.post('API_URL', {
                facultyName,
                university: selectedUniversity,
            });
            dispatch(createFacultyStart(response.data)); // Dispatch an action to save the faculty in Redux
            // Redirect or show success message here
        } catch (error) {
            console.error('Error:', error);
            // Handle error here (show error message, etc.)
        }
    };

    return (
        <div className="p-4 sm:ml-64">
            <h1 className="text-2xl font-bold mb-4">Create Faculty</h1>
            <form onSubmit={handleSubmit}>
                <div className="mb-4">
                    <label htmlFor="facultyName" className="block text-gray-700 font-bold mb-2">
                        Faculty Name
                    </label>
                    <input
                        type="text"
                        id="facultyName"
                        className="border rounded px-4 py-2 w-full"
                        value={facultyName}
                        onChange={handleFacultyNameChange}
                        required
                    />
                </div>
                <div className="mb-4">
                    <label htmlFor="university" className="block text-gray-700 font-bold mb-2">
                        University
                    </label>
                    <select
                        id="university"
                        className="border rounded px-4 py-2 w-full"
                        value={selectedUniversity}
                        onChange={handleUniversityChange}
                        required
                    >
                        <option value="">Select University</option>
                        {universities.map((university) => (
                            <option key={university} value={university}>
                                {university}
                            </option>
                        ))}
                    </select>
                </div>
                <button
                    type="submit"
                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                >
                    Save
                </button>
            </form>
        </div>
    );
};

export default FacultyForm;
