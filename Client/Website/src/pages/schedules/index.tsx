import React from 'react';
import {Button, Typography} from 'antd';
import {useRouter} from "next/navigation";

const { Title } = Typography;

const Page = (): JSX.Element => {
    const router = useRouter();
    return(
        <>
            <Title>Horarios</Title>
            <Button type="primary" onClick={() => router.push('/schedules/new')}>Agregar Horario</Button>
            <Button type="primary" onClick={() => router.push('/classTimes/new')}>Agregar Tiempo de Clase</Button>
        </>
    )
};

export default Page;