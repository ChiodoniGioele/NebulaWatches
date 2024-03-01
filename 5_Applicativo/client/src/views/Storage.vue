<template>
    <div class="grid lg:grid-cols-5 min-h-screen"> 
        <Sidebar class="hidden lg:block" />
        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col" > 
            <div class="px-4 py-6 lg:px-8"> 

                <div class="flex w-full items-center gap-2.5">
                    <Input id="email" type="text" placeholder="Search a watch in your storage..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                </div>
                
                <div class="mt-5 flex flex-wrap gap-5">
                    <WatchCard v-for="watch in watches" :key="watch.reference" :watch="watch" />
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

import { apiServerAddress } from '@/main.ts'

import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover'

import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const watches = ref([]);

async function fetchBrands() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/brands`, 
        {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

    brands.value = response.data;
  } catch (error) {
    console.error('Failed to fetch brands:', error);
  }
}

onMounted(async () => {
    fetchBrands();
});

</script>
