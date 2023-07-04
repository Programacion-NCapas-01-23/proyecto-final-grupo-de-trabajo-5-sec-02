import React, {useEffect, useState} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {CareerPreview} from "@/interfaces/Career";
import {createCareer, updateCareer} from "@/state/thunks/careerThunk";
import {Button, Form, Input, Select, SelectProps, Typography} from 'antd';
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {fetchFaculties} from "@/state/thunks/facultyThunk";
import {useRouter} from "next/navigation";

interface CareerFormProps {
    career?: CareerPreview;
}

const {Title} = Typography;

const CareerForm = ({career}: CareerFormProps) => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const [facultyId, setFacultyId] = useState('');
    const faculties = useSelector((state: RootState) => state.faculty.data);
    const error = useSelector((state: RootState) => state.pensum.error);

    const options: SelectProps['options'] = [];
    faculties.map(faculty => {
        options.push({
            value: faculty.id,
            label: faculty.name,
        });
    });

    useEffect(() => {
        if (career) {
            form.setFieldsValue(career);
        }
    }, [career, form]);

    useEffect(() => {
        dispatch(fetchFaculties())
    }, [dispatch]);

    const handleSubmit = async (values: CareerPreview) => {
        const {name, facultyId} = values;
        console.log(values);
        const careerData: CareerPreview = {
            name,
            facultyId,
        };

        if (career) {
            await dispatch(updateCareer({...career, ...careerData}));
            if (error!.response.status === 401) {
                router.push('/login')
            } else {
                router.push('/careers');
            }
        } else {
            await dispatch(createCareer(careerData));
            if (error!.response.status === 401) {
                router.push('/login')
            } else {
                router.push('/careers');
            }
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
                borderRadius: 10,

            }}>
                <Title style={{color: '#275DAD', alignSelf: "center"}}>Crear Carrera</Title>
                <Form
                    name="newCareer"
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
                        rules={[{required: true, message: 'Ingresa el nombre de la carrera!'}]}
                    >
                        <Input
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}/>
                    </Form.Item>
                    <Form.Item
                        label="Facultad"
                        name="facultyId"
                        rules={[{required: true, message: 'Selecciona una facultad!'}]}
                    >
                        <Select
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                            onChange={value => setFacultyId(value)}
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

export default CareerForm;
