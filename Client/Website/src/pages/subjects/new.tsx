import React from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Subject} from "@/interfaces/Subject";
import {createSubject} from "@/state/thunks/subjectThunk";
import {Button, Form, Input, InputNumber, Typography} from 'antd';
import {useRouter} from "next/navigation";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";

const {Title} = Typography;

const SubjectForm = () => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const error = useSelector((state: RootState) => state.pensum.error);

    const handleSubmit = async (values: Subject) => {
        const {code, name, uv} = values;

        const newSubject: Subject = {
            code,
            name,
            uv,
        };
        console.log(newSubject);

        await dispatch(createSubject(newSubject));
        if (error!.response.status === 401) {
            router.push('/login')
        } else {
            router.push('/subjects');
        }
    };

    return (
        <div style={{
            display: 'flex',
            flexFlow: 'column wrap',
            background: '#000000',
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
                <Title style={{color: '#275DAD', alignSelf: "center"}}>Crear Materia</Title>
                <Form
                    name="newSubject"
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
                        label="Codigo de Materia"
                        name="code"
                        rules={[{required: true, message: 'Ingresa el codigo de la materia'}]}
                    >
                        <Input
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Form.Item
                        label="Nombre"
                        name="name"
                        rules={[{required: true, message: 'Ingresa el nombre de la materia!'}]}
                    >
                        <Input
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Form.Item
                        label="Unidades Valorativas"
                        name="uv"
                        rules={[{required: true, message: 'Ingresa las Unidades Valorativas!'}]}
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

export default SubjectForm;
