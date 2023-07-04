import React, {useEffect, useState} from 'react';
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useRouter} from "next/router";
import {fetchCareers} from "@/state/thunks/careerThunk";
import SubjectForm from "@/pages/subjects/new";

const Page = () => {
    const router = useRouter();
    const {slug} = router.query;

    const dispatch = useAppDispatch();
    const [identity, setIdentity] = useState('');

    useEffect(() => {
        if (slug && !Array.isArray(slug)) {
            setIdentity(slug);
        }
    }, [slug])

    const subject = useSelector((state: RootState) => state.subject.data.find(subject => subject.code === identity));

    useEffect(() => {
        dispatch(fetchCareers())
    }, [dispatch]);

    return (
        <>
            <SubjectForm subject={subject}/>
        </>
    );
};

export default Page;
