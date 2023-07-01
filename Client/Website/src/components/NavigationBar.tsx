import IconDashboard from "@/assets/icons/IconDashboard";
import IconSchool from "@/assets/icons/IconSchool";
import IconStudent from "@/assets/icons/IconStudent";
import IconSchedule from "@/assets/icons/IconSchedule";
import IconBxsBook from "@/assets/icons/IconBxsBook";
import IconCircleUser from "@/assets/icons/IconCircleUser";
import React, {ReactNode, useState} from "react";
import { Layout, Menu, Button, theme, Typography } from 'antd';
import IconGraduationCap from "@/assets/icons/IconGraduationCap";
import { useRouter } from 'next/navigation'

const { Header, Sider, Content } = Layout;
const { Title } = Typography;

export const NavigationBar = ({ children }: { children: ReactNode }) => {
    const [collapsed, setCollapsed] = useState(false);
    const {
        token: { colorBgContainer },
    } = theme.useToken();

    const router = useRouter();

    return(
        <>
            <Layout  style={{ minHeight: '100vh' }} hasSider>
                <Sider collapsible
                       collapsed={collapsed}
                       onCollapse={(value) => setCollapsed(value)}
                       style={{
                           overflow: 'auto',
                           height: '100vh',
                           left: 0,
                           top: 0,
                           bottom: 0,
                       }}
                >
                    <div className="demo-logo-vertical">
                        <IconGraduationCap />
                    </div>
                    <Menu
                        theme="dark"
                        mode="inline"
                        defaultSelectedKeys={['1']}
                        items={[
                            {
                                key: '1',
                                icon: <IconDashboard />,
                                label: 'Dashboard',
                                onClick: () => router.push('/'),
                            },
                            {
                                key: '2',
                                icon: <IconSchool />,
                                label: 'Facultades',
                                onClick: () => router.push('/faculties'),
                                children: [
                                    {
                                        key: '8',
                                        icon: <IconSchool />,
                                        label: 'Losing control',
                                        onClick: () => router.push('/'),
                                    }
                                ]
                            },
                            {
                                key: '3',
                                icon: <IconStudent />,
                                label: 'Carreras',
                                onClick: () => router.push('/careers'),
                            },
                            {
                                key: '4',
                                icon: <IconGraduationCap />,
                                label: 'Pensum',
                                onClick: () => router.push('/pensums'),
                            },
                            {
                                key: '5',
                                icon: <IconBxsBook />,
                                label: 'Materias',
                                onClick: () => router.push('/subjects'),
                            },
                            {
                                key: '6',
                                icon: <IconSchedule />,
                                label: 'Horarios',
                                onClick: () => router.push('/schedules'),
                            },
                            {
                                key: '7',
                                icon: <IconCircleUser />,
                                label: 'Mi Perfil',
                                onClick: () => router.push('/'),
                            },
                        ]}
                    />
                </Sider>
                <Layout>
                    <Header style={{ padding: 0, background: colorBgContainer }}>
                        <Title style={{ margin: 0 }}>U-Helper</Title>
                    </Header>
                    <Content
                        style={{
                            margin: '24px 16px',
                            padding: 24,
                            minHeight: 280,
                            background: colorBgContainer,
                            display: 'flex',
                            flexFlow: 'row wrap',
                            justifyContent: 'space-between',
                            alignItems: 'baseline',
                        }}
                    >
                        {children}
                    </Content>
                </Layout>
            </Layout>
        </>
    )
}