import React, {useEffect} from 'react';
import {Button, Space, Table, Typography} from 'antd';
import type {ColumnsType} from "antd/es/table";
import {useRouter} from "next/navigation";
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {PensumPreview} from "@/interfaces/Pensum";
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
        <div style={{
            display: 'flex',
            flexFlow: 'column wrap',
            alignItems: 'center',
            justifyContent: 'flex-start',
            width: '100%',
            height: 'inherit',
        }}
        >
            <Title style={{color: '#FFFFFF', alignSelf: "center"}}>Pensums</Title>
            <div style={{
                alignSelf: 'center',
            }}
            >
                <Button type="primary"
                        onClick={() => router.push('/pensums/new')}
                        style={{
                            borderRadius: 0,
                            backgroundColor: '#275DAD',
                            margin: '1rem',
                            padding: '0.5rem 1rem',
                            height: 'auto',
                        }}
                >
                    Agregar Pensum
                </Button>
                <Button type="primary"
                        onClick={() => router.push('/cycles/new')}
                        style={{
                            borderRadius: 0,
                            backgroundColor: '#275DAD',
                            margin: '1rem',
                            padding: '0.5rem 1rem',
                            height: 'auto',
                        }}
                >
                    Agregar Ciclo
                </Button>
            </div>
            {loading ? (
                    <div>Loading pensums...</div>
                ) :
                <Table columns={columns} dataSource={pensums} pagination={false}/>
            }
        </div>
    )
};

export default Page;