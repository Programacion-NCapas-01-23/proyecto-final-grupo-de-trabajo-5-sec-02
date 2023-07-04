import React, {useEffect, useState} from 'react';
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useRouter} from "next/router";
import {fetchCareers} from "@/state/thunks/careerThunk";
import CareerForm from "@/pages/careers/new";

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

    const career = useSelector((state: RootState) => state.career.data.find(career => career.id === identity));

    useEffect(() => {
        dispatch(fetchCareers())
    }, [dispatch]);

    return (
        <>
            <CareerForm career={career}/>
        </>
    );
};

export default Page;
