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
                <Link href={`/careers/${record.id}`}>Modificar</Link>
                {/*<Link href={''} style={{color: '#DF2935'}}>Eliminar</Link>*/}
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
        <div style={{
            display: 'flex',
            flexFlow: 'column wrap',
            alignItems: 'center',
            justifyContent: 'flex-start',
            width: '100%',
        }}
        >
            <Title style={{color: '#FFFFFF', alignSelf: "center"}}>Carreras</Title>
            <Button type="primary"
                    onClick={() => router.push('/careers/new')}
                    style={{
                        borderRadius: '0',
                        backgroundColor: '#275DAD',
                        margin: '1rem 0',
                        alignSelf: 'center',
                        padding: '0.5rem 1rem',
                        height: 'auto',
                    }}
            >
                Agregar Carrera
            </Button>
            <div style={{
                    padding: 24,
                    display: 'flex',
                    flexFlow: 'row wrap',
                    justifyContent: 'space-around',
                    alignItems: 'flex-start',
                    width: '100%',
                }}
            >
            {loading ? (
                <Title style={{color: '#FFFFFF', alignSelf: "center"}}>Loading careers...</Title>
            ) : (
                faculties.map(faculty => {
                    const careersByFaculty = careers.filter(career => career.facultyId === faculty.id)
                    return (
                        <div key={faculty.id}
                             style={{
                                 display: 'flex',
                                 flexFlow: 'column wrap',
                                 width: '550px'
                             }}
                        >
                            <Title level={2} key={faculty.id}  style={{color: '#FFFFFF', alignSelf: "center"}}>{faculty.name}</Title>
                            {
                                <Table columns={columns} dataSource={careersByFaculty} pagination={false}/>
                            }
                        </div>
                    )
                })
            )
            }
            </div>
        </div>
    )
};

export default Page;