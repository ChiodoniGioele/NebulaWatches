<template>
    <router-view></router-view>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  
  const router = useRouter();
  const route = useRoute();
  const isLoginPage = computed(() => route.path === '/login');
  
  router.beforeEach((to, from, next) => {
  if (!localStorage.getItem('token')) {
    window.location.href = 'http://localhost:64321/auth';
  } else {
    next();
  }
});
  
  </script>
  