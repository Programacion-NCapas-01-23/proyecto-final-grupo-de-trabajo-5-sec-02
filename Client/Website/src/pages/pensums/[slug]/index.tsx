import React, {useEffect, useState} from 'react';
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useRouter} from "next/router";
import {fetchCareers} from "@/state/thunks/careerThunk";
import PensumForm from "@/pages/pensums/new";

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

    const pensum = useSelector((state: RootState) => state.pensum.data.find(pensum => pensum.id === identity));

    useEffect(() => {
        dispatch(fetchCareers())
    }, [dispatch]);

    return (
        <>
            <PensumForm pensum={pensum}/>
        </>
    );
};

export default Page;
