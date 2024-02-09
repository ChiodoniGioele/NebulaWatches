<template>
    <router-view></router-view>
  </template>
  
  <script setup>
  import { computed } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  
  const router = useRouter();
  const route = useRoute();
  const isLoginPage = computed(() => route.path === '/login');
  
  if (!localStorage.getItem('token')) {
    router.push('/login');
  }
  
  </script>
  