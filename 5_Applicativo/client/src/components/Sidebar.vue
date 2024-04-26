<template>
  <!-- <Chat /> -->

  <button @click="toggleSidebar"
          class="lg:hidden fixed top-0 left-0 z-50 bg-white p-3 rounded-full m-4 text-gray-700 hover:text-gray-900 focus:outline-none focus:text-gray-900 focus:bg-gray-200 transition duration-300 ease-in-out">
    <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16m-7 6h7"></path>
    </svg>
  </button>

  <div :class="{ 'hidden': !isSidebarOpen, 'lg:flex': isSidebarOpen }"
       class="fixed top-0 left-0 h-full w-80 bg-grey-50 border-r  overflow-y-auto z-10 ">

    <!-- Logo and User Info -->
    <div class="flex flex-col p-4">
      <div class="space-y-4 py-4 flex-1">
        <img src="@/assets/Logo.png" alt="">
        <div class="px-1 pt-8 w-[100%] ">
          <div class="">
            <Button variant="ghost" class="flex items-center py-0 gap-2 w-full justify-start pl-8" @click="toHome">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Watch.png"/>
              <span class="text-inherit text-base font-bold">Watches</span>
            </Button>
            <Button variant="ghost" class="flex items-center py-0 gap-2 w-full justify-start pl-8" @click="toStorage">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Boxes.png"/>
              <span class="text-inherit text-base font-bold">Storage</span>
            </Button>
            <Button variant="ghost" class="flex items-center py-0 gap-2 w-full justify-start pl-8"
                    @click="router.push('/clients');">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Customer.png"/>
              <span class="text-inherit text-base font-bold">Clients</span>
            </Button>
            <Button variant="ghost" class="flex items-center py-0 gap-2 w-full justify-start pl-8" @click="toTeam">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/User Account.png"/>
              <span class="text-inherit text-base font-bold">Team</span>
            </Button>
            <Button variant="ghost" class="flex items-center py-0 gap-2 w-full justify-start pl-8"
                    @click="router.push('/comunication');">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Chat.png"/>
              <span class="text-inherit text-base font-bold">Communication</span>
            </Button>
          </div>
        </div>
      </div>
    </div>

    <!-- User Profile and Logout -->
    <div class="absolute bottom-0 left-0 right-0 p-4 border-t pb-10 bg-white">
      <Button variant="ghost" class="w-full justify-start">
        <div class="flex items-center space-x-3 rtl:space-x-reverse">
          <div class="flex-shrink-0">
            <img class="w-8 h-8 rounded-full" src="@/assets/icons/Profile.png" alt="User image">
          </div>
          <div class="flex-1 min-w-0">
            <p class="text-sm font-semibold text-inherit-900 truncate dark:text-white">
              {{ username }}
            </p>
            <p class="text-sm text-gray-500 truncate dark:text-gray-400">
              {{ email }}
            </p>
          </div>
          <div class="flex space-x-3">


            <Popover>
              <PopoverTrigger as-child>
                <img class="w-5 h-5 rounded-full" src="@/assets/icons/Settings.png" alt="Settings image" variant="outline">
              </PopoverTrigger>
              <PopoverContent class="w-80">
                <div class="grid gap-4">
                  <div class="space-y-2">
                    <h4 class="font-medium leading-none">
                      {{ username }}
                    </h4>
                    <p class="text-sm text-muted-foreground">
                      <b> Email: </b> {{ email }} <br>
                    </p>
                  </div>
                </div>
              </PopoverContent>
            </Popover>


            <img class="w-5 h-5" src="@/assets/icons/Exit.png" alt="Logout image" @click="logout">
          </div>
        </div>
      </Button>
    </div>
  </div>

  <!-- Content -->
  <div :class="{ 'ml-0': !isSidebarOpen, 'ml-80': isSidebarOpen }" class="flex flex-col flex-1 p-4">
  </div>

</template>

<script setup>
import Chat from '@/components/Chat.vue'
import {Button} from '../components/ui/button'
import axios from 'axios';
import {ref, onMounted} from 'vue';
import {useRouter} from 'vue-router';
import {apiServerAddress} from '@/main.ts'


import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover'
import {Input} from '@/components/ui/input'
import {Label} from '@/components/ui/label'

const router = useRouter();
const username = ref('');
const email = ref('');
const phone = ref('');

const isSidebarOpen = ref(window.innerWidth >= 1024);

const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
}

onMounted(() => {
  window.addEventListener('resize', () => {
    isSidebarOpen.value = window.innerWidth >= 1024;
  });
});

async function logout() {
  try {
    localStorage.removeItem('token');
    router.push('/login');
  } catch (error) {
    console.error(error)
  }
}

async function fetchUserName() {
  try {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const mail = payload.sub;
    email.value = mail;
    const response = await axios.get(`${apiServerAddress}/v1/user/getName/${email.value}`, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token'),
      },
    });
    username.value = response.data;
  } catch (error) {
    console.error('Failed to get username:', error);
  }
}

async function toTeam() {
  router.push('/team');
}

async function toHome() {
  router.push('/');
}

async function toStorage() {
  router.push('/storage');
}

onMounted(async () => {
  fetchUserName();
});
</script>

<style></style>
