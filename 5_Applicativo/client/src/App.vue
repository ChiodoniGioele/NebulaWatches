<template>
  <router-view></router-view>
</template>

<script setup>
import axios from 'axios';
import { apiServerAddress } from '@/main.ts'
import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ref } from 'vue';

const router = useRouter();
const route = useRoute();

// role and user
const role = ref("");
const isLoginPage = computed(() => route.path === '/login');

// token counterloop, if invalid login
if (isTokenExpired()) {
  router.push('/login');
}

// if the adjoint token is valid
function isTokenExpired() {
  const token = localStorage.getItem('token');
  if (!token) return true;

  const tokenExp = JSON.parse(atob(token.split('.')[1])).exp * 1000;
  return tokenExp < Date.now();
}


// Allows you to control and manage access to the application routes.

router.beforeEach(async (to, from, next) => {
  if (to.path === '/admin') {
    const token = localStorage.getItem('token');
    if (!token) {
      next(false);
      router.push('/login');
      return;
    }
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const email = payload.sub;
    await getRole(email);
    if (role.value !== "ADMIN") {
      next(false);
      router.push('/');
      return;
    }
  }
  next();
});

// takes the role of the logged in utenet
async function getRole(userEmail) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/admin/getRole/${userEmail}`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        role.value = response.data;
    } catch (error) {
        console.error('Failed to fetch Role', error);
    }
}
</script>