<template>
  <router-view></router-view>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();
const isLoginPage = computed(() => route.path === '/login');

if (isTokenExpired()) {
  router.push('/login');
}


function isTokenExpired() {
  const token = localStorage.getItem('token');
  if (!token) return true;

  const tokenExp = JSON.parse(atob(token.split('.')[1])).exp * 1000;
  return tokenExp < Date.now();
}

</script>