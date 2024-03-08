<template>
    <div class="grid lg:grid-cols-5 min-h-screen"> 
        <Sidebar class="hidden lg:block" />
        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col" > 
            <div class="px-4 py-6 lg:px-8"> 

                <div class="flex w-full items-center gap-2.5">
                    <Input id="email" type="text" placeholder="Search a watch in your favourites..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                    <Button class="bg-blue-600" @click="toHome"> All Watches</Button>
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
    
 
</template>
  
<script setup>
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
    const userEmail = sessionStorage.getItem('email');
    await fetchFavourite(userEmail);
});
</script>
