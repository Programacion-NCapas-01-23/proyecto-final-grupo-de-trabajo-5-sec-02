import React, {useEffect} from 'react';
import {Button, Card, Image, Layout, Typography} from 'antd';
import {useAppDispatch} from "@/hooks/reduxHooks";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {fetchFaculties} from "@/state/thunks/facultyThunk";
import {useRouter} from "next/navigation";

const {Title} = Typography;
const {Meta} = Card;
const {Content} = Layout;

const Page = (): JSX.Element => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const faculties = useSelector((state: RootState) => state.faculty.data);
    const loading = useSelector((state: RootState) => state.faculty.loading);

    useEffect(() => {
        dispatch(fetchFaculties());
    }, [dispatch]);

    return (
        <>
            <Title>Facultades</Title>
            <Button type="primary" onClick={() => router.push('/faculties/new')}>Agregar Facultad</Button>
            <Content
                style={{
                    padding: 24,
                    display: 'flex',
                    flexFlow: 'row wrap',
                    justifyContent: 'space-around',
                    alignItems: 'center',
                    width: '100%'
                }}
            >
                {loading ? (
                    <div>Loading faculties...</div>
                ) : (
                    faculties.map((faculty) => (
                        <Card
                            key={faculty.id}
                            style={{width: 300}}
                            cover={<Image alt={faculty.name} src={faculty.logo} preview={false}/>}
                        >
                            <Meta title={faculty.name} description={faculty.description}/>
                            {/*<Button type="primary" onClick={() => router.push(`/faculties/${faculty.id}`)}>Modificar</Button>*/}
                            <Button type="primary" onClick={() => router.push(`/faculties/${faculty.id}`)}>Eliminar</Button>
                        </Card>
                    ))
                )}
            </Content>
        </>
    )
};

export default Page;