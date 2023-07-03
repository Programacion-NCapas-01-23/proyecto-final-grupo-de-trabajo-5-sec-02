import React from 'react';
import {Layout, Typography, Image} from "antd";
import ScreenCum from "@/assets/icons/ScreenCum";

const {Title, Text} = Typography;
const {Content} = Layout;

export default function DownloadPage() {
    return (
        <Layout style={{
            display: 'flex',
            flexFlow: 'row wrap',
            alignItems: 'center',
            justifyContent: 'space-around',
            background: 'linear-gradient(135deg, rgba(255, 251, 255, 0.35) 0%, rgba(146, 220, 229, 0.35) 28.13%, rgba(19, 89, 140, 0.35) 53.65%, rgba(10, 36, 99, 0.35) 81.25%)',
            width: '100%'
        }}>
            <div
                style={{
                    display: "flex",
                    flexFlow: "column wrap",
                    background: "transparent",
                    maxWidth: '375px',
                }}
            >
                <Title>U-Tracker</Title>
                <Text style={{
                    fontSize: '1.2rem',
                    color: '#FFF',
                    marginBottom: '3rem',
                }}>
                    The Student App is a powerful tool that helps you track records, subjects, and manage your student
                    information effectively.
                </Text>
                <Image
                    alt="Google PLay"
                    width={200}
                    src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png"
                />
            </div>
            <div
                style={{
                    background: "transparent",
                    maxWidth: '360px',
                }}
            >
                <ScreenCum style={{
                    borderRadius: '0.625rem',
                    border: '10px solid #FFCA3A',
                    background: 'transparent',
                }}/>
            </div>
        </Layout>
    );
};
