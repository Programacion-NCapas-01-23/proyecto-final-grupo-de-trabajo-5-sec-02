import React, {useEffect} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Button, Form, Select, SelectProps, Typography} from 'antd';
import {classTimes} from "@/api/data/dummy";
import {useRouter} from "next/navigation";
import {useSelector} from "react-redux";
import {RootState} from "@/state/store";
import {fetchSubjects} from "@/state/thunks/subjectThunk";
import {createSchedule} from "@/state/thunks/scheduleThunk";
import {SchedulePreview} from "@/interfaces/Schedule";

const classTimeSelect: SelectProps['options'] = [];
classTimes.map(classTime => {
    classTimeSelect.push({
        value: classTime.id,
        label: `${classTime.day} ${classTime.start} (${classTime.end})`,
    });
})

const {Title} = Typography;

const ScheduleForm = () => {
    const router = useRouter();
    const dispatch = useAppDispatch();
    const subjects = useSelector((state: RootState) => state.subject.data);
    const [form] = Form.useForm();
    const error = useSelector((state: RootState) => state.pensum.error);

    const subjectsSelect: SelectProps['options'] = [];
    subjects.map(subject => {
        subjectsSelect.push({
            value: subject.id,
            label: subject.name,
        });
    })

    useEffect(() => {
        if (error && error.response.status === 401 || error?.status === 401) {
            router.push('/login')
        }
    }, [error])

    useEffect(() => {
        dispatch(fetchSubjects());
    }, [dispatch]);

    const handleSubmit = async (values: SchedulePreview) => {
        const {subjectId, classTimeId} = values;
        console.log(values);
        const scheduleData: SchedulePreview = {
            collection: 1,
            subjectId,
            classTimeId,
        };
        await dispatch(createSchedule(scheduleData));
        if (error && error.response.status === 401 || error?.status === 401) {
            router.push('/login')
        } else {
            router.push('/schedules');
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
                <Title style={{color: '#275DAD', alignSelf: "center"}}>Crear Horario</Title>
                <Form
                    name="newSchedule"
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
                        label="Materia"
                        name="subjectId"
                        rules={[{required: true, message: 'Selecciona el tiempo de clase!'}]}
                    >
                        <Select
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                            options={subjectsSelect}
                            bordered={false}
                        />
                    </Form.Item>
                    <Form.Item
                        label="Tiempo de clase"
                        name="classTimeId"
                        rules={[{required: true, message: 'Selecciona el tiempo de clase!'}]}
                    >
                        <Select
                            style={{width: 360, border: 'none', borderBottom: '2px solid #2B4162', borderRadius: '0'}}
                            options={classTimeSelect}
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

export default ScheduleForm;
