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
            try {
                await dispatch(updateCareer({...career, ...careerData}));
                router.push('/careers');
            } catch (error) {
                console.log('Updating error:', error);
            }
        } else {
            try {
                await dispatch(createCareer(careerData));
                router.push('/careers');
            } catch (error) {
                console.log('Creation error:', error);
            }

        }

    };

    return (
        <div className="">
            <div className="form-container">
                <Title>Crear Carrera</Title>
                <Form
                    name="newCareer"
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
                        rules={[{required: true, message: 'Ingresa el nombre de la carrera!'}]}
                    >
                        <Input style={{width: 300}}/>
                    </Form.Item>
                    <Form.Item
                        label="Facultad"
                        name="facultyId"
                        rules={[{required: true, message: 'Selecciona una facultad!'}]}
                    >
                        <Select
                            style={{width: 300}}
                            onChange={value => setFacultyId(value)}
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

export default CareerForm;
