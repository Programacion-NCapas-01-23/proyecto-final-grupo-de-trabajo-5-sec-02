import React from 'react';
import {Button, Typography} from 'antd';
import {useRouter} from "next/navigation";

const { Title } = Typography;

const Page = (): JSX.Element => {
    const router = useRouter();
    return(
        <div style={{
            display: 'flex',
            flexFlow: 'column wrap',
            alignItems: 'center',
            justifyContent: 'flex-start',
            width: '100%',
            height: 'inherit',
        }}
        >
            <Title style={{color: '#FFFFFF', alignSelf: "center"}}>Horarios</Title>
            <div>
                <Button type="primary"
                        onClick={() => router.push('/schedules/new')}
                        style={{
                            borderRadius: 0,
                            backgroundColor: '#275DAD',
                            margin: '1rem',
                            padding: '0.5rem 1rem',
                            height: 'auto',
                        }}
                >
                    Agregar Horario
                </Button>
                <Button type="primary"
                        onClick={() => router.push('/classTimes/new')}
                        style={{
                            borderRadius: 0,
                            backgroundColor: '#275DAD',
                            margin: '1rem',
                            padding: '0.5rem 1rem',
                            height: 'auto',
                        }}
                >
                    Agregar Tiempo de Clase
                </Button>
            </div>
        </div>
    )
};

export default Page;