import React, {useState} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import ClassTime from "@/interfaces/ClassTime";
import {createClassTime} from "@/state/thunks/classTimeThunk";
import {Button, Form, InputNumber, Select, SelectProps, TimePicker, Typography} from 'antd';
import {useRouter} from "next/navigation";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";

enum Days {
    Lunes,
    Martes,
    Miercoles,
    Jueves,
    Viernes,
    Sabado,
}

const options: SelectProps['options'] = [];

const keys = Object.keys(Days).filter((v) => isNaN(Number(v)));
keys.forEach((key, index) => {
    options.push({
        value: index,
        label: key,
    });
});


const {Title} = Typography;

const ClassTimeForm = () => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const [days, setDays] = useState(0);
    const [hour, setHour] = useState("");
    const error = useSelector((state: RootState) => state.pensum.error);

    const handleSubmit = async (values: ClassTime) => {
        const {day, start, end} = values;

        const newClassTime: ClassTime = {
            day,
            start: hour,
            end,
        };

        await dispatch(createClassTime(newClassTime));
        if (error!.response.status === 401) {
            router.push('/login')
        } else {
            router.push('/schedules');
        }
    };

    return (
        <div style={{
            display: 'flex',
            flexFlow: 'column wrap',
            alignItems: "center",
            justifyContent: "center",
            width: '100%',
            height: '100%',
        }}>
            <div style={{
                display: 'flex',
                flexFlow: 'column nowrap',
                minWidth: '360px',
                background: '#FFFFFF',
                padding: '32px',

            }}>
                <Title style={{color: '#275DAD', alignSelf: "center"}}>Crear Tiempos de Clase</Title>
                <Form
                    name="newClassTime"
                    form={form}
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    style={{maxWidth: 600}}
                    initialValues={{remember: true}}
                    onFinish={handleSubmit}
                    autoComplete="off"
                    layout="vertical"
                >
                    <Form.Item
                        label="Dia"
                        name="day"
                        rules={[{required: true, message: 'Selecciona un dia!'}]}
                    >
                        <Select
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                            onChange={value => setDays(value)}
                            options={options}
                            bordered={false}
                        />
                    </Form.Item>
                    <Form.Item
                        label="Hora de Inicio"
                        name="start"
                        rules={[{required: true, message: 'Ingresa la hora de inicio!'}]}
                    >
                        <TimePicker
                            onChange={value => setHour(value!.format("HH:mm").toString())}
                            format={'HH:mm'}
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                        />
                    </Form.Item>
                    <Form.Item
                        label="Horas de Clase"
                        name="end"
                        rules={[{required: true, message: 'Ingresa la cantidad de horas!'}]}
                    >
                        <InputNumber min={0} max={10} style={{
                            width: 360,
                            border: 'none',
                            borderBottom: '2px solid #2B4162',
                            borderRadius: '0'
                        }}/>
                    </Form.Item>
                    <Button type="primary" htmlType="submit" style={{
                        borderRadius: '0',
                        backgroundColor: '#275DAD',
                        margin: '1rem 0',
                    }}>
                        Submit
                    </Button>
                </Form>
            </div>
        </div>
    );
};

export default ClassTimeForm;
