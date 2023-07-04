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
        <div style={{
            display: 'flex',
            flexFlow: 'column wrap',
            alignItems: 'center',
            justifyContent: 'flex-start',
        }}
        >
            <Title style={{color: '#FFFFFF', alignSelf: "center"}}>Facultades</Title>
            <Button type="primary"
                    onClick={() => router.push('/faculties/new')}
                    style={{
                        borderRadius: '0',
                        backgroundColor: '#275DAD',
                        margin: '1rem 0',
                        alignSelf: 'center',
                        padding: '0.5rem 1rem',
                        height: 'auto',
                    }}
            >
                Agregar Facultad
            </Button>
            <div
                style={{
                    padding: 24,
                    display: 'flex',
                    flexFlow: 'row wrap',
                    justifyContent: 'space-around',
                    alignItems: 'center',
                    width: '100%',
                }}
            >
                {loading ? (
                    <div>Loading faculties...</div>
                ) : (
                    faculties.map((faculty) => (
                        <Card
                            hoverable
                            key={faculty.id}
                            style={{
                                width: 350,
                                border: 'none',
                                borderRadius: 0,
                                margin: '1rem',
                                background: '#F4FFF8',
                            }}
                            cover={
                                <Image alt={faculty.name} src={faculty.logo} preview={false} width={350} height={350}/>
                            }
                        >
                            <Meta title={faculty.name} description={faculty.description}/>
                            {/*<Button type="primary" onClick={() => router.push(`/faculties/${faculty.id}`)}>Modificar</Button>*/}
                            <Button
                                type="primary"
                                onClick={() => router.push(`/faculties/${faculty.id}`)}
                                style={{
                                    borderRadius: '0',
                                    backgroundColor: '#DF2935',
                                    margin: '1rem 0',
                                }}
                            >
                                Eliminar
                            </Button>
                        </Card>
                    ))
                )}
            </div>
        </div>
    )
};

export default Page;

/*<Button type="primary" htmlType="submit" >*/