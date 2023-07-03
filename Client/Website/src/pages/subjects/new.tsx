import React from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Subject} from "@/interfaces/Subject";
import {createSubject} from "@/state/thunks/subjectThunk";
import {Button, Form, Input, InputNumber, Typography} from 'antd';
import {useRouter} from "next/navigation";

const {Title} = Typography;

const SubjectForm = () => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();

    const handleSubmit = async (values: Subject) => {
        const {code, name, uv} = values;

        const newSubject: Subject = {
            code,
            name,
            uv,
        };
        console.log(newSubject);

        try {
            await dispatch(createSubject(newSubject));
            router.push('/subjects');
        } catch (error) {
            console.log('Creation error:', error);
        }
    };

    return (
        <div className="">
            <div className="form-container">
                <Title>Crear Materia</Title>
                <Form
                    name="newSubject"
                    form={form}
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    style={{maxWidth: 600}}
                    initialValues={{remember: true}}
                    onFinish={handleSubmit}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Codigo de Materia"
                        name="code"
                        rules={[{required: true, message: 'Ingresa el codigo de la materia'}]}
                    >
                        <Input style={{width: 300}}/>
                    </Form.Item>
                    <Form.Item
                        label="Nombre"
                        name="name"
                        rules={[{required: true, message: 'Ingresa el nombre de la materia!'}]}
                    >
                        <Input style={{width: 300}}/>
                    </Form.Item>
                    <Form.Item
                        label="Unidades Valorativas"
                        name="uv"
                        rules={[{required: true, message: 'Ingresa las Unidades Valorativas!'}]}
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

export default SubjectForm;
