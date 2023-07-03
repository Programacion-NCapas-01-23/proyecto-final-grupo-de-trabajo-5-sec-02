import React from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Button, Form, Input, Typography} from 'antd';
import {Login} from "@/interfaces/Admin";
import {loginAdmin} from "@/state/thunks/authThunk";
import {useRouter} from "next/navigation";

const {Title} = Typography;

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
            console.log('Registration error:', error);
        }
    };

    return (
        <div className="">
            <div className="form-container">
                <Title>Login</Title>
                <Form
                    name="login"
                    form={form}
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    style={{maxWidth: 600}}
                    initialValues={{remember: true}}
                    onFinish={handleSubmit}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Nombre de Usuario"
                        name="username"
                        rules={[{required: true, message: 'Ingresa el nombre de usuario!'}]}
                    >
                        <Input style={{width: 300}}/>
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
                        <Input.Password/>
                    </Form.Item>
                    <Button type="primary" htmlType="submit">
                        Submit
                    </Button>
                </Form>
            </div>
        </div>
    );
};

export default LoginForm;
