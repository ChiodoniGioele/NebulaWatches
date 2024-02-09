import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import Home from './views/Home.vue';
import Login from './views/Login.vue';
import Register from './views/Register.vue';

const routes: Array<RouteRecordRaw> = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
  });
  
  export default router;
  