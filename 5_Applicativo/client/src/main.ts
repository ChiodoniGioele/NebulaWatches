import './assets/index.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

export const apiServerAddress = `http://localhost:64321`;

const app = createApp(App)

app.use(router)

app.mount('#app')

export var myVar = 'my variable';
