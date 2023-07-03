import {Card, Image, Layout} from 'antd';
import React, {MouseEventHandler} from "react";
import Link from 'next/link';

const {Meta} = Card;
const {Content} = Layout;

const cards = [
    {
        key: "1",
        title: "Facultades",
        description: "This is the description",
        source: "https://mundocampusvirtual.com/wp-content/uploads/2022/09/4886002039_8c186c8b17_b-scaled.jpg",
        href: '/faculties',
    },
    {
        key: "2",
        title: "Carreras",
        description: "This is the description",
        source: "https://mundocampusvirtual.com/wp-content/uploads/2022/09/4886002039_8c186c8b17_b-scaled.jpg",
        href: '/careers',
    },
    {
        key: "3",
        title: "Pensum",
        description: "This is the description",
        source: "https://mundocampusvirtual.com/wp-content/uploads/2022/09/4886002039_8c186c8b17_b-scaled.jpg",
        href: '/pensums',
    },
    {
        key: "4",
        title: "Materias",
        description: "This is the description",
        source: "https://mundocampusvirtual.com/wp-content/uploads/2022/09/4886002039_8c186c8b17_b-scaled.jpg",
        href: '/subjects',
    },
    {
        key: "5",
        title: "Horarios",
        description: "This is the description",
        source: "https://mundocampusvirtual.com/wp-content/uploads/2022/09/4886002039_8c186c8b17_b-scaled.jpg",
        href: '/schedules',
    },
    {
        key: "6",
        title: "Descarga",
        description: "Descarga nuestra aplicacion",
        source: "https://mundocampusvirtual.com/wp-content/uploads/2022/09/4886002039_8c186c8b17_b-scaled.jpg",
        href: '/download',
    },
]

export default function Home() {
    const handleClick = (
        e: MouseEventHandler<HTMLDivElement>
    ) => {

    };

    return (
        <>
            <Content
                style={{
                    padding: 24,
                    display: 'flex',
                    flexFlow: 'row wrap',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    width: '100%'
                }}
            >
            {cards.map(card =>
                <Link href={card.href} key={card.key}>
                    <Card
                        style={{width: 300}}
                        cover={
                            <Image
                                alt={card.title}
                                src={card.source}
                                preview={false}
                            />
                        }
                    >
                        <Meta
                            title={card.title}
                            description={card.description}
                        />
                    </Card>
                </Link>)}
            </Content>
        </>
    )
}
