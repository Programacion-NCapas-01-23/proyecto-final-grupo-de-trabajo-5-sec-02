import React, {useEffect} from 'react';
import {useAppDispatch} from '@/hooks/reduxHooks';
import {Button, Form, Input, Select, SelectProps, Typography} from 'antd';
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

    const subjectsSelect: SelectProps['options'] = [];
    subjects.map(subject => {
        subjectsSelect.push({
            value: subject.id,
            label: subject.name,
        });
    })

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
        try {
            await dispatch(createSchedule(scheduleData));
            router.push('/schedules');
        } catch (error) {
            console.log('Creation error:', error);
        }
    };

    return (
        <div className="">
            <div className="form-container">
                <Title>Crear Horario</Title>
                <Form
                    name="newSchedule"
                    form={form}
                    labelCol={{span: 8}}
                    wrapperCol={{span: 16}}
                    style={{maxWidth: 600}}
                    initialValues={{remember: true}}
                    onFinish={handleSubmit}
                    autoComplete="off"
                >
                    <Form.Item
                        label="Materia"
                        name="subjectId"
                        rules={[{required: true, message: 'Selecciona el tiempo de clase!'}]}
                    >
                        <Select
                            style={{width: 300}}
                            options={subjectsSelect}
                        />
                    </Form.Item>
                    <Form.Item
                        label="Tiempo de clase"
                        name="classTimeId"
                        rules={[{required: true, message: 'Selecciona el tiempo de clase!'}]}
                    >
                        <Select
                            style={{width: 300}}
                            options={classTimeSelect}
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

export default ScheduleForm;
