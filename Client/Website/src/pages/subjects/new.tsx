import React, {useEffect} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Subject} from "@/interfaces/Subject";
import {createSubject, updateSubject} from "@/state/thunks/subjectThunk";
import {Button, Form, Input, InputNumber, Select, SelectProps, Space, Typography, notification} from 'antd';
import {useRouter} from "next/navigation";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {MinusCircleOutlined, PlusOutlined} from '@ant-design/icons';
import {fetchCycles} from "@/state/thunks/cycleThunk";
import {ErrorResponse} from "@/interfaces/InitialState";

type NotificationType = 'success' | 'info' | 'warning' | 'error';
interface SubjectFormProps {
    subject?: Subject;
}

const {Title} = Typography;

const SubjectForm = ({subject}: SubjectFormProps) => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const error = useSelector((state: RootState) => state.subject.error);
    const cycles = useSelector((state: RootState) => state.cycle.data);
    const [api, contextHolder] = notification.useNotification();

    const openNotificationWithIcon = (type: NotificationType, error?: ErrorResponse | string) => {
        console.log(error)
        api[type]({
            message: `Status: ${typeof error === 'string' ? error : error && error.response ? error.response.status : error?.status}`,
            description: '',
        });
    };

    const options: SelectProps['options'] = [];
    cycles.map(cycle => {
        options.push({
            value: cycle.id,
            label: cycle.name,
        });
    });

    useEffect(() => {
        dispatch(fetchCycles());
    }, [dispatch]);

    useEffect(() => {
        if (error && error.response.status === 401) {
            router.push('/login')
        } if(error && error.response.status === 500) {
        openNotificationWithIcon('error', error)
    }
    }, [error, ])

    useEffect(() => {
        if (subject) {
            form.setFieldsValue(subject);
        }
    }, [subject, form]);

    const handleError = async () => {
        if (error && error.response.status === 401 || error?.status === 401) {
            openNotificationWithIcon('error', error)
            router.push('/login')
        } if(error && error.response.status === 500 || error?.status === 500) {
            openNotificationWithIcon('error', error)
        }
        else {
            router.push('/subjects');
        }
    }

    const handleSubmit = async (values: Subject) => {
        const {code, name, uv, cycleRelation} = values;

        console.log(values);
        const newSubject: Subject = {
            code,
            name,
            uv,
            cycleRelation,
        };

        if (subject) {
            await dispatch(updateSubject(newSubject));
            await handleError();
        } else {
            await dispatch(createSubject(newSubject));
            openNotificationWithIcon('success')
            await handleError();
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
                {
                    subject ? <Title style={{color: '#275DAD', alignSelf: "center"}}>Editar Materia</Title> :
                        <Title style={{color: '#275DAD', alignSelf: "center"}}>Crear Materia</Title>
                }
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
                    <Form.List name="cycleRelation">
                        {(fields, {add, remove}) => (
                            <>
                                {fields.map((field) => (
                                    <Space key={field.key} align="baseline">
                                        <Form.Item
                                            noStyle
                                            shouldUpdate={(prevValues, curValues) =>
                                                prevValues.area !== curValues.area || prevValues.sights !== curValues.sights
                                            }
                                        >
                                            {() => (
                                                <Form.Item
                                                    {...field}
                                                    label="Ciclo"
                                                    name={[field.name, 'id']}
                                                    rules={[{required: true, message: 'Missing cycle and pensum'}]}
                                                >
                                                    <Select
                                                        style={{
                                                            width: 150,
                                                            border: 'none',
                                                            borderBottom: '2px solid #2B4162',
                                                            borderRadius: '0'
                                                        }}
                                                        options={options}
                                                        bordered={false}
                                                    />
                                                </Form.Item>
                                            )}
                                        </Form.Item>
                                        <Form.Item
                                            {...field}
                                            label="correlative"
                                            name={[field.name, 'correlative']}
                                            rules={[{required: true, message: 'Missing correlative'}]}
                                        >
                                            <InputNumber min={0} style={{
                                                width: 150,
                                                border: 'none',
                                                borderBottom: '2px solid #2B4162',
                                                borderRadius: '0'
                                            }}/>
                                        </Form.Item>

                                        <MinusCircleOutlined onClick={() => remove(field.name)}/>
                                    </Space>
                                ))}

                                <Form.Item>
                                    <Button type="dashed" onClick={() => add()} block icon={<PlusOutlined/>}>
                                        Agregar Ciclo correlativo
                                    </Button>
                                </Form.Item>
                            </>
                        )}
                    </Form.List>
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
