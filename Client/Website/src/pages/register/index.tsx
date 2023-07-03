import React, {useEffect} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Button, Form, Input, Typography} from 'antd';
import {Admin} from "@/interfaces/Admin";
import {registerAdmin} from "@/state/thunks/authThunk";
import {useRouter} from "next/navigation";

const {Title} = Typography;

const RegisterForm = () => {
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const router = useRouter();

    const handleSubmit = async (values: Admin) => {
        const {name, username, email, password} = values;

        const newAdmin: Admin = {
            name,
            username,
            email,
            password,
        };
        console.log(newAdmin);
        try {
            await dispatch(registerAdmin(newAdmin));
            router.push('/login');
        } catch (error) {
            console.log('Registration error:', error);
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
                <Title style={{color: '#275DAD', alignSelf: "center"}}>Crea una cuenta</Title>
                <Form
                    name="register"
                    form={form}
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    style={{minWidth: 300}}
                    initialValues={{remember: true}}
                    onFinish={handleSubmit}
                    autoComplete="off"
                    layout="vertical"
                >
                    <Form.Item
                        label="Nombre"
                        name="name"
                        rules={[{required: true, message: 'Ingresa tu nombre'}]}
                    >
                        <Input  style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Form.Item
                        label="Nombre de Usuario"
                        name="username"
                        rules={[{required: true, message: 'Ingresa el nombre de usuario!'}]}
                    >
                        <Input  style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Form.Item
                        label="E-mail"
                        name="email"
                        rules={[{required: true, message: 'Ingresa el email!'}]}
                    >
                        <Input  style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Form.Item
                        label="Contrasena"
                        name="password"
                        rules={[{
                            required: true,
                            message: 'Ingresa una contrasena valida!',
                            pattern: new RegExp(`^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=_*])(?=\\S+$).{8,}$`)
                        }]}
                    >
                        <Input.Password  style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Button type="primary" htmlType="submit" style={{
                        borderRadius: '0',
                        backgroundColor: '#275DAD',
                        margin: '1rem 0',
                    }}>
                        Registrarse
                    </Button>
                </Form>
            </div>
        </div>
    );
};

export default RegisterForm;
