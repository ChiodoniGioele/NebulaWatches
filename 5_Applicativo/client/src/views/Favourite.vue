<template>
    <div class="flex h-screen"> 
        <Sidebar />
        <div class="flex flex-col w-full" > 
            <div class="px-8 py-6">

                <div class="flex w-full items-center gap-2.5">
                    <Input class="border-stone-900 border" @click="router.push('/search')" @change="router.push('/search')" id="email" type="text" placeholder="Search a watch..." />
                    <Button @click="router.push('/search')" type="submit"> Search </Button>
                    <Button variant="outline" @click="toHome"> All Watches</Button>
                </div>
                
                <div class="mt-12 px-1 flex gap-7 items-center">
                    <div class="flex gap-2">
                        <h1 class="font-semibold "> Favourites </h1>
                    </div>
                </div>

                <div class="mt-5 flex flex-wrap gap-5">
                    <WatchCard v-for="watch in favoritedWatches" :key="watch.reference" :watch="watch" />
                </div>

            </div>
        </div>
    </div>
    
    <Chat />
</template>
  
<script setup>
import Chat from '@/components/Chat.vue'
import Sidebar from '@/components/Sidebar.vue'
import WatchCard from '@/components/WatchCard.vue'

import { Input } from '@/components/ui/input'
import { Button } from '@/components//ui/button'
import { Checkbox } from '@/components/ui/checkbox'
import { useRouter } from 'vue-router';

import { apiServerAddress } from '@/main.ts'

import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const router = useRouter();
const favoritedWatches = ref([]);

async function fetchFavourite(userEmail) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/favourite/${userEmail}`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        favoritedWatches.value = response.data;
    } catch (error) {
        console.error('Failed to fetch favourites watches', error);
    }
}

async function toHome(){
  router.push('/');
}

onMounted(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const email = payload.sub;

    await fetchFavourite(email);
    localStorage.removeItem('search');
});
</script>
