import React, {useEffect} from 'react';
import {Button, Layout, Typography, Table, Space} from 'antd';
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {fetchCareers} from "@/state/thunks/careerThunk";
import {fetchFaculties} from "@/state/thunks/facultyThunk";
import {useRouter} from "next/navigation";
import {CareerPreview} from "@/interfaces/Career";
import type { ColumnsType } from 'antd/es/table';
import Link from 'next/link';

const {Title, Text} = Typography;
const {Content} = Layout;

const columns: ColumnsType<CareerPreview> = [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Acciones',
        key: 'action',
        render: (_, record) => (
            <Space size="middle">
                <Link href={`/${record.id}`}>Modificar</Link>
                <Link href={''}>Eliminar</Link>
            </Space>
        ),
    },
];


const Page = (): JSX.Element => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const careers = useSelector((state: RootState) => state.career.data);
    const faculties = useSelector((state: RootState) => state.faculty.data);
    const loading = useSelector((state: RootState) => state.career.loading);

    useEffect(() => {
        dispatch(fetchFaculties())
        dispatch(fetchCareers());
    }, [dispatch]);

    return (
        <>
            <Title>Carreras</Title>
            <Button type="primary" onClick={() => router.push('/careers/new')}>Agregar Carrera</Button>
            {loading ? (
                <div>Loading careers...</div>
            ) : (
                faculties.map(faculty => {
                    const careersByFaculty = careers.filter(career => career.facultyId === faculty.id)
                    return (
                        <>
                            <Title level={2} key={faculty.id}>{faculty.name}</Title>
                            {
                                <Table columns={columns} dataSource={careersByFaculty} pagination={false}/>
                            }
                        </>
                    )
                })
            )
            }
        </>
    )
};

export default Page;