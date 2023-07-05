import React, {useEffect} from 'react';
import {Button, Space, Table, Typography} from 'antd';
import type {ColumnsType} from 'antd/es/table';
import {Subject} from "@/interfaces/Subject";
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {fetchSubjects} from "@/state/thunks/subjectThunk";
import {useRouter} from "next/navigation";
import Link from "next/link";

const {Title} = Typography;
const columns: ColumnsType<Subject> = [
    {
        title: 'Cod.',
        dataIndex: 'code',
        key: 'code',
    },
    {
        title: 'Nombre',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'UV',
        dataIndex: 'uv',
        key: 'uv',
    },
    {
        title: 'Acciones',
        key: 'action',
        render: (_, record) => (
            <Space size="middle">
                <Link href={`/subjects/${record.code}`}>Modificar</Link>
                {/*<Link href={''} style={{color: '#DF2935'}}>Eliminar</Link>*/}
            </Space>
        ),
    },
];

const Page = (): JSX.Element => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const subjects = useSelector((state: RootState) => state.subject.data);
    const loading = useSelector((state: RootState) => state.subject.loading);

    useEffect(() => {
        dispatch(fetchSubjects());
    }, [dispatch]);

    return (
        <div style={{
            display: 'flex',
            flexFlow: 'column wrap',
            alignItems: 'center',
            justifyContent: 'flex-start',
            width: '100%',
            height: 'inherit',
        }}
        >
            <Title style={{color: '#FFFFFF', alignSelf: "center"}}>Materias</Title>
            <Button type="primary"
                    onClick={() => router.push('/subjects/new')}
                    style={{
                        borderRadius: '0',
                        backgroundColor: '#275DAD',
                        margin: '1rem 0',
                        alignSelf: 'center',
                        padding: '0.5rem 1rem',
                        height: 'auto',
                    }}
            >
                Agregar Materia
            </Button>
            <div style={{
                padding: 24,
                display: 'flex',
                flexFlow: 'row wrap',
                justifyContent: 'space-around',
                alignItems: 'center',
                width: '100%',
            }}>
                {loading ? (
                        <div>Loading subjects...</div>
                    ) :
                    <Table columns={columns} dataSource={subjects} pagination={false}/>

                }
            </div>
        </div>
    )
};

export default Page;