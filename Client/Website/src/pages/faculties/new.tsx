import React, {useEffect} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Faculty} from "@/interfaces/Faculty";
import {createFaculty, updateFaculty} from "@/state/thunks/facultyThunk";
import {Button, Form, Input, Typography, Upload} from 'antd';
import {PlusOutlined} from '@ant-design/icons';
import {createCareer, updateCareer} from "@/state/thunks/careerThunk";
import {useRouter} from "next/navigation";

const {TextArea} = Input;
const {Title} = Typography;
const normFile = (e: any) => {
    if (Array.isArray(e)) {
        return e;
    }
    return e?.fileList;
};

interface FacultyFormProps {
    faculty?: Faculty;
}

const FacultyForm = ({ faculty }: FacultyFormProps) => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const [form] = Form.useForm();

    useEffect(() => {
        if (faculty) {
            form.setFieldsValue(faculty);
        }
    }, [faculty, form]);

    const handleSubmit = async (values: Faculty) => {
        const {name, description, logo} = values;
        const facultyData: Faculty = {
            name,
            description,
            logo,
        };
        if (faculty) {
            try {
                await dispatch(updateFaculty({ ...faculty, ...facultyData }));
                router.push('/faculties');
            } catch (error) {
                console.log('Updating error:', error);
            }
        } else {
            try {
                await dispatch(createFaculty(facultyData));
                router.push('/faculties');
            } catch (error) {
                console.log('Creation error:', error);
            }
        }
    };

    return (
        <div className="">
            <div className="form-container">
                {
                    faculty ? <Title>Editar Facultad</Title> : <Title>Crear Facultad</Title>
                }
                <Form
                    name="newFaculty"
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
                        rules={[{required: true, message: 'Ingresa el nombre de la facultad!'}]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item
                        label="Descripcion"
                        name="description"
                        rules={[{required: true, message: 'Ingresa la descripción de la facultad!'}]}
                    >
                        <TextArea rows={3}/>
                    </Form.Item>
                    <Form.Item label="Logo"
                               name="logo"
                               valuePropName="fileList"
                               getValueFromEvent={(e) => normFile(e)}
                    >
                        <Upload action="/upload.do"
                                listType="picture-card"
                        >
                            <div>
                                <PlusOutlined/>
                                <div style={{marginTop: 8}}>Upload</div>
                            </div>
                        </Upload>
                    </Form.Item>
                    <Button type="primary" htmlType="submit">
                        Submit
                    </Button>
                </Form>
            </div>
        </div>
    );
};

export default FacultyForm;
