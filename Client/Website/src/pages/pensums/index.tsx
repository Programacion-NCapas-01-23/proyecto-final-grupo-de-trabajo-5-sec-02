import React, {useEffect, useState} from 'react';
import {Button, Space, Table, Typography} from 'antd';
import type {ColumnsType} from "antd/es/table";
import {useRouter} from "next/navigation";
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {fetchSubjects} from "@/state/thunks/subjectThunk";
import {PensumPreview, PensumTable} from "@/interfaces/Pensum";
import {fetchPensums} from "@/state/thunks/pensumThunk";
import {fetchCareers} from "@/state/thunks/careerThunk";

const {Title} = Typography;

const columns: ColumnsType<PensumPreview> = [
    {
        title: 'Plan',
        dataIndex: 'plan',
        key: 'name',
    },
    {
        title: 'Carrera',
        dataIndex: 'degreeId',
        key: 'degreeId',
    },
    {
        title: 'Acciones',
        key: 'action',
        render: (_, record) => (
            <Space size="middle">
                <a>Modificar</a>
                <a>Eliminar</a>
            </Space>
        ),
    },
];

const Page = (): JSX.Element => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const pensums = useSelector((state: RootState) => state.pensum.data);
    const loading = useSelector((state: RootState) => state.pensum.loading);


    useEffect(() => {
        dispatch(fetchPensums());
        dispatch(fetchCareers());
    }, [dispatch]);

    return (
        <>
            <Title>Pensums</Title>
            <Button type="primary" onClick={() => router.push('/pensums/new')}>Agregar Pensum</Button>
            <Button type="primary" onClick={() => router.push('/cycles/new')}>Agregar Ciclo</Button>
            {loading ? (
                    <div>Loading pensums...</div>
                ) :
                <Table columns={columns} dataSource={pensums} pagination={false}/>
            }
        </>
    )
};

export default Page;