import '@/styles/globals.css'
import '@/styles/FacultyForm.scss';
import type { AppProps } from 'next/app'
import store from '@/state/store'
import { Provider } from 'react-redux'

export default function App({ Component, pageProps }: AppProps) {
  return (
      <Provider store={store}>
          <Component {...pageProps} />
      </Provider>
  )
}
