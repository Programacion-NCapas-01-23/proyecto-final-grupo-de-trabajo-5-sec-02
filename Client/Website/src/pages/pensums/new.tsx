import React from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {PensumPreview} from "@/interfaces/Pensum";
import {createPensum} from "@/state/thunks/pensumThunk";
import {Button, Form, Input, Select, SelectProps, Typography} from 'antd';
import {careers} from "@/api/data/dummy";

const options: SelectProps['options'] = [];
careers.map(career => {
    options.push({
        value: career.id,
        label: career.name,
    });
})

const {Title} = Typography;

const PensumForm = () => {
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();

    const handleSubmit = async (values: PensumPreview) => {
        const {plan, degreeId} = values;
        console.log(values);
        const newPensum: PensumPreview = {
            plan,
            degreeId,
        };
        dispatch(createPensum(newPensum));
    };

    return (
        <div className="">
            <div className="form-container">
                <Title>Crear Pensum</Title>
                <Form
                    name="newPensum"
                    form={form}
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    style={{maxWidth: 600}}
                    initialValues={{remember: true}}
                    onFinish={handleSubmit}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Plan"
                        name="plan"
                        rules={[{required: true, message: 'Ingresa el plan del pensum!'}]}
                    >
                        <Input style={{width: 300}}/>
                    </Form.Item>
                    <Form.Item
                        label="Carrera"
                        name="degreeId"
                        rules={[{required: true, message: 'Selecciona una carrera!'}]}
                    >
                        <Select
                            style={{width: 300}}
                            options={options}
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

export default PensumForm;
