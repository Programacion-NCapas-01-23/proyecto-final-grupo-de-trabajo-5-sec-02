import React, {useEffect} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {PensumPreview} from "@/interfaces/Pensum";
import {createPensum} from "@/state/thunks/pensumThunk";
import {Button, Form, Input, Select, SelectProps, Typography} from 'antd';
import {fetchCareers} from "@/state/thunks/careerThunk";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {useRouter} from "next/navigation";

const {Title} = Typography;

const PensumForm = () => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const careers = useSelector((state: RootState) => state.career.data);

    const options: SelectProps['options'] = [];
    careers.map(career => {
        options.push({
            value: career.id,
            label: career.name,
        });
    })

    useEffect(() => {
        dispatch(fetchCareers());
    }, [dispatch]);

    const handleSubmit = async (values: PensumPreview) => {
        const {plan, degreeId} = values;
        console.log(values);
        const newPensum: PensumPreview = {
            plan,
            degreeId,
        };

        try {
            await dispatch(createPensum(newPensum));
            router.push('/pensums');
        } catch (error) {
            console.log('Creation error:', error);
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
                <Title style={{color: '#275DAD', alignSelf: "center"}}>Crear Pensum</Title>
                <Form
                    name="newPensum"
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
                        label="Plan"
                        name="plan"
                        rules={[{required: true, message: 'Ingresa el plan del pensum!'}]}
                    >
                        <Input style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Form.Item
                        label="Carrera"
                        name="degreeId"
                        rules={[{required: true, message: 'Selecciona una carrera!'}]}
                    >
                        <Select
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                            options={options}
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

export default PensumForm;
