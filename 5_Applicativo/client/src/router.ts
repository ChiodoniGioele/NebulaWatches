import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import Home from '@/views/Home.vue';
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';
import Brand from '@/views/Brand.vue';
import Family from '@/views/Family.vue';
import Watch from '@/views/Watch.vue';
import Team from './views/Team.vue';
import Storage from './views/Storage.vue';
import Favourite from './views/Favourite.vue';
import Search from './views/Search.vue';
import Admin from './views/Admin.vue';
import StorageCustom from './views/StorageCustom.vue';
import CustomWatch from './views/CustomWatch.vue';


const routes: Array<RouteRecordRaw> = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/brand/:brandName', component: Brand },
    { path: '/brand/:brandName/family/:familyId/watches', component: Family},
    { path: '/watch/:reference/:sel?', component: Watch },
    { path: '/team', component: Team },
    { path: '/storage', component: Storage },
    { path: '/favourite', component: Favourite },
    { path: '/search', component: Search },
    { path: '/admin', component: Admin },
    { path: '/storageCustom', component: StorageCustom },
    { path: '/customWatch/:reference/:sel?', component: CustomWatch },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
  });
  
  export default router;

  