import React from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Button, Form, Input, Typography} from 'antd';
import {Login} from "@/interfaces/Admin";
import {loginAdmin} from "@/state/thunks/authThunk";
import {useRouter} from "next/navigation";
import Link from "next/link";

const {Title, Text} = Typography;

const LoginForm = () => {
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const router = useRouter();

    const handleSubmit = async (values: Login) => {
        const {username, password} = values;

        const login: Login = {
            username,
            password,
        };
        console.log(login);
        try {
            await dispatch(loginAdmin(login));
            router.push('/');
        } catch (error) {
            console.log('Login error:', error);
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
                <Title style={{color: '#275DAD', alignSelf: "center"}}>Inicio de Sesion</Title>
                <Form
                    name="login"
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
                        label="Usuario"
                        name="username"
                        rules={[{required: true, message: 'Ingresa el nombre de usuario!'}]}
                    >
                        <Input style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
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
                        <Input.Password style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Button type="primary" htmlType="submit" style={{
                        borderRadius: '0',
                        backgroundColor: '#275DAD',
                        margin: '1rem 1em 1em 0',
                    }}>
                        Iniciar Sesion
                    </Button>
                    <Text>No tienes cuenta? <Link href='/register' style={{color: '#275DAD'}}>Registrate</Link></Text>
                </Form>
            </div>
        </div>
    );
};

export default LoginForm;
