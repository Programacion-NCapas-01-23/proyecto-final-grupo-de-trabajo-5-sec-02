import React, {useState} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import ClassTime from "@/interfaces/ClassTime";
import {createClassTime} from "@/state/thunks/classTimeThunk";
import {Button, Form, InputNumber, Select, SelectProps, TimePicker, Typography} from 'antd';
import {useRouter} from "next/navigation";

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

    const handleSubmit = async (values: ClassTime) => {
        const {day, start, end} = values;

        const newClassTime: ClassTime = {
            day,
            start: hour,
            end,
        };

        try {
            await dispatch(createClassTime(newClassTime));
            router.push('/schedules');
        } catch (error) {
            console.log('Creation error:', error);
        }
    };

    return (
        <div className="">
            <div className="form-container">
                <Title>Crear Tiempos de Clase</Title>
                <Form
                    name="newClassTime"
                    form={form}
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    style={{maxWidth: 600}}
                    initialValues={{remember: true}}
                    onFinish={handleSubmit}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Dia"
                        name="day"
                        rules={[{required: true, message: 'Selecciona un dia!'}]}
                    >
                        <Select
                            style={{width: 150}}
                            onChange={value => setDays(value)}
                            options={options}
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
                            style={{width: 150}}
                        />
                    </Form.Item>
                    <Form.Item
                        label="Horas de Clase"
                        name="end"
                        rules={[{required: true, message: 'Ingresa la cantidad de horas!'}]}
                    >
                        <InputNumber min={0} max={10} style={{width: 150}}/>
                    </Form.Item>
                    <Button type="primary" htmlType="submit">
                        Submit
                    </Button>
                </Form>
            </div>
        </div>
    );
};

export default ClassTimeForm;
