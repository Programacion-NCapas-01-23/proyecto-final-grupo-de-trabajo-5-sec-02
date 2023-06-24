import React, {useState} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {CareerPreview} from "@/interfaces/Career";
import {createCareer} from "@/state/thunks/careerThunk";
import {Button, Form, Input, Typography, Select, SelectProps} from 'antd';
import {faculties} from "@/api/data/dummy";

const options: SelectProps['options'] = [];
faculties.map(faculty => {
    options.push({
        value: faculty.id,
        label: faculty.name,
    });
})

const {Title} = Typography;
const normFile = (e: any) => {
    if (Array.isArray(e)) {
        return e;
    }
    return e?.fileList;
};

const CareerForm = () => {
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();
    const [facultyId, setFacultyId] = useState('');

    const handleSubmit = async (values: CareerPreview) => {
        const {name, facultyId} = values;
        console.log(values);
        const newCareer: CareerPreview = {
            name,
            facultyId,
        };
        dispatch(createCareer(newCareer));
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
