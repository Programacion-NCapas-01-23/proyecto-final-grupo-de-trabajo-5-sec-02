import React from 'react';
import {Card, Image, Typography} from 'antd';
import Faculty from "@/interfaces/Faculty";

const { Title } = Typography;
const {Meta} = Card;

const cards: Faculty[] = [
    {
        id: "1",
        name: "FIA",
        description: "This is the description",
        logo: "https://redconose.org//wp-content/uploads/2017/08/LogoUCAnegro-PNG2016-222x300.png",
    },
    {
        id: "2",
        name: "Economia y Negocios",
        description: "This is the description",
        logo: "https://redconose.org//wp-content/uploads/2017/08/LogoUCAnegro-PNG2016-222x300.png",
    },
    {
        id: "3",
        name: "Humanidades",
        description: "This is the description",
        logo: "https://redconose.org//wp-content/uploads/2017/08/LogoUCAnegro-PNG2016-222x300.png",
    },
]

const Page = (): JSX.Element => {
    return(
        <>
            <Title>Facultades</Title>
            {cards.map(card =>
                    <Card
                        key={card.id}
                        style={{width: 300}}
                        cover={
                            <Image
                                alt={card.name}
                                src={card.logo}
                                preview={false}
                            />
                        }
                    >
                        <Meta
                            title={card.name}
                            description={card.description}
                        />
                    </Card>)}
        </>
    )
};

export default Page;