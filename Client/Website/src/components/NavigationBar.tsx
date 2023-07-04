import React, {ReactNode, useState} from "react";
import type {MenuProps} from 'antd';
import {Layout, Menu, theme} from 'antd';
import Link from 'next/link'

const {Header, Content} = Layout;

const items: MenuProps['items'] = [
    {
        label: (
            <Link href="/">
                Home
            </Link>
        ),
        key: 'home',
    },
    {
        label: (
            <Link href="/faculties">
                Facultades
            </Link>
        ),
        key: 'faculty',
    },
    {
        label: (
            <Link href="/careers">
                Carreras
            </Link>
        ),
        key: 'career',
    },
    {
        label: (
            <Link href="/pensums">
                Pensum
            </Link>
        ),
        key: 'pensum',
    },
    {
        label: (
            <Link href="/schedules">
                Horarios
            </Link>
        ),
        key: 'schedule',
    },
    {
        label: (
            <Link href="/subjects">
                Materias
            </Link>
        ),
        key: 'subjects',
    },
    {
        label: (
            <Link href="/download">
                Descarga
            </Link>
        ),
        key: 'alipay',
    },
];

export const NavigationBar = ({children}: { children: ReactNode }) => {
    const [current, setCurrent] = useState('mail');

    const {
        token: {colorBgContainer},
    } = theme.useToken();

    return (
        <>
            <Layout style={{minHeight: '100vh'}}>
                <Header style={{
                    padding: 0,
                    background:
                    colorBgContainer,
                    position: 'sticky',
                    top: 0,
                    zIndex: 1,
                    width: '100%',
                }}
                >
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['2']}
                        items={items}
                    />

                </Header>
                <Content
                    style={{
                        margin: 0,
                        background: '#000000',
                        display: 'flex',
                        flexFlow: 'column wrap',
                        alignItems: 'flex-start',
                    }}
                >
                    {children}
                </Content>
            </Layout>
        </>
    )
}