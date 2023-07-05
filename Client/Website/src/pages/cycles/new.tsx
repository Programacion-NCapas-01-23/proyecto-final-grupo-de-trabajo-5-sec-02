import React, {useEffect} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {CyclePreview} from "@/interfaces/Cycle";
import {createCycle} from "@/state/thunks/cycleThunk";
import {Button, Form, Input, Select, SelectProps, Typography} from 'antd';
import {useRouter} from "next/navigation";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";

enum cycleTypes {
    IMPAR,
    PAR,
    INTERCICLO,
}

const cycleTypeSelect: SelectProps['options'] = [];

const keys = Object.keys(cycleTypes).filter((v) => isNaN(Number(v)));
keys.forEach((key, index) => {
    cycleTypeSelect.push({
        value: index,
        label: key,
    });
});

const {Title} = Typography;

const CycleForm = () => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const pensums = useSelector((state: RootState) => state.pensum.data);
    const error = useSelector((state: RootState) => state.cycle.error);

    const pensumSelect: SelectProps['options'] = [];
    pensums.map(pensum => {
        pensumSelect.push({
            value: pensum.id,
            label: pensum.plan,
        });
    })

    useEffect(() => {
        if (error && error.response.status === 401 || error?.status === 401) {
            router.push('/login')
        }
    }, [error])

    const handleSubmit = async (values: CyclePreview) => {
        const {type, name, pensumId} = values;
        console.log(values);
        const newCycle: CyclePreview = {
            type,
            name,
            pensumId
        };
        await dispatch(createCycle(newCycle));
        if (error && error.response.status === 401 || error?.status === 401) {
            router.push('/login')
        } else {
            router.push('/pensums');
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
                borderRadius: 20,

            }}>
                <Title style={{color: '#275DAD', alignSelf: "center"}}>Crear Ciclo</Title>
                <Form
                    name="newCycle"
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
                        label="Nombre"
                        name="name"
                        rules={[{required: true, message: 'Ingresa el nombre del ciclo!'}]}
                    >
                        <Input
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                            placeholder="Ciclo X"/>
                    </Form.Item>
                    <Form.Item
                        label="Tipo"
                        name="type"
                        rules={[{required: true, message: 'Selecciona el tipo de ciclo!'}]}
                    >
                        <Select
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                            options={cycleTypeSelect}
                            bordered={false}
                        />
                    </Form.Item>
                    <Form.Item
                        label="Pensum"
                        name="pensumId"
                        rules={[{required: true, message: 'Selecciona el pensum al que pertenece!'}]}
                    >
                        <Select
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                            options={pensumSelect}
                            bordered={false}
                        />
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

export default CycleForm;
