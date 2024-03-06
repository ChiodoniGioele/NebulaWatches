import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import Home from '@/views/Home.vue';
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';
import Brand from '@/views/Brand.vue';
import Family from '@/views/Family.vue';
import Watch from '@/views/Watch.vue';
import Team from './views/Team.vue';


const routes: Array<RouteRecordRaw> = [
    { path: '/home', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/brand/:brandName', component: Brand },
    { path: '/brand/:brandName/family/:familyId/watches', component: Family},
    { path: '/watch/:reference', component: Watch },
    { path: '/team', component: Team },
    { path: '/', redirect: '/home' },  // Redirect root URL ("/") to "/home"
];

const router = createRouter({
    history: createWebHistory(),
    routes,
  });
  
  export default router;
  