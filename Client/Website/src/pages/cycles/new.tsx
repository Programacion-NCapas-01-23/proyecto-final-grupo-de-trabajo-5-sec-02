import React from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {CyclePreview} from "@/interfaces/Cycle";
import {createCycle} from "@/state/thunks/cycleThunk";
import {Button, Form, Input, Select, SelectProps, Typography} from 'antd';
import {pensums} from "@/api/data/dummy";

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

const pensumSelect: SelectProps['options'] = [];
pensums.map(pensum => {
    pensumSelect.push({
        value: pensum.id,
        label: pensum.plan,
    });
})

const {Title} = Typography;

const CycleForm = () => {
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();

    const handleSubmit = async (values: CyclePreview) => {
        const {type, name, pensumId} = values;
        console.log(values);
        const newCycle: CyclePreview = {
            type,
            name,
            pensumId
        };
        dispatch(createCycle(newCycle));
    };

    return (
        <div className="">
            <div className="form-container">
                <Title>Crear Ciclo</Title>
                <Form
                    name="newCycle"
                    form={form}
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    style={{maxWidth: 600}}
                    initialValues={{remember: true}}
                    onFinish={handleSubmit}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Nombre"
                        name="name"
                        rules={[{required: true, message: 'Ingresa el nombre del ciclo!'}]}
                    >
                        <Input style={{width: 300}} placeholder="Ciclo X"/>
                    </Form.Item>
                    <Form.Item
                        label="Tipo"
                        name="type"
                        rules={[{required: true, message: 'Selecciona el tipo de ciclo!'}]}
                    >
                        <Select
                            style={{width: 300}}
                            options={cycleTypeSelect}
                        />
                    </Form.Item>
                    <Form.Item
                        label="Pensum"
                        name="pensumId"
                        rules={[{required: true, message: 'Selecciona el pensum al que pertenece!'}]}
                    >
                        <Select
                            style={{width: 300}}
                            options={pensumSelect}
                        />
                    </Form.Item>
                    <Button type="primary" htmlType="submit">
                        Submit
                    </Button>
                </Form>
            </div>
        </div>
    );
};

export default CycleForm;
