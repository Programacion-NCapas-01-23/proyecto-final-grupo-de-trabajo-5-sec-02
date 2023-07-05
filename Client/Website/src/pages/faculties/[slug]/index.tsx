import React, {useEffect, useState} from 'react';
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import FacultyForm from "@/pages/faculties/new";
import {fetchFaculties} from "@/state/thunks/facultyThunk";
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useRouter} from "next/router";

const Page = () => {
    const router = useRouter();
    const { slug } = router.query;

    const dispatch = useAppDispatch();
    const [identity, setIdentity] = useState('');

    useEffect(() => {
        if(slug && !Array.isArray(slug)) {
            setIdentity(slug);
            console.log(identity)
        }
    }, [slug])

    const faculty = useSelector((state: RootState) => state.faculty.data.find(f => f.id === identity));

    useEffect(() => {
        dispatch(fetchFaculties())
    }, [dispatch]);

    return (
        <div>
            <FacultyForm faculty={faculty}/>
        </div>
    );
};

export default Page;
