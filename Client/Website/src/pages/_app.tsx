import '@/styles/globals.scss'
import '@/styles/FacultyForm.scss';
import type {AppProps} from 'next/app'
import store from '@/state/store'
import {Provider} from 'react-redux'
import {NavigationBar} from "@/components/NavigationBar";

export default function App({Component, pageProps}: AppProps) {
    return (
        <Provider store={store}>
            <NavigationBar>
                <Component {...pageProps} />
            </NavigationBar>
        </Provider>
    )
}
