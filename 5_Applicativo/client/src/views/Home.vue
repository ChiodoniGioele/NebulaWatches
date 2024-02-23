<template>
    <div class="grid lg:grid-cols-5 min-h-screen"> 
        <Sidebar class="hidden lg:block" />
        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col"> 
            <div class="px-4 py-6 lg:px-8"> 

                <div class="flex w-full items-center gap-2.5">
                    <Input id="email" type="text" placeholder="Search a watch..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                </div>

                <!-- <div class="mt-3 flex gap-2">
                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Brands ({{ brands.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div v-for="brand in brands" :key="brand.name" :brand="brand" class="flex items-center space-x-2 mb-3">
                                <Checkbox id="{{ brand.name }}selected" />
                                <label
                                for="{{ brand.name }}selected"
                                class="text-sm font-light leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                >
                                {{ brand.name }}
                                </label>
                            </div>

                        </PopoverContent>
                    </Popover> 

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Brands ({{ brands.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div v-for="brand in brands" :key="brand.name" :brand="brand" class="flex items-center space-x-2 mb-3">
                                <Checkbox id="{{ brand.name }}selected" />
                                <label
                                for="{{ brand.name }}selected"
                                class="text-sm font-light leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                >
                                {{ brand.name }}
                                </label>
                            </div>

                        </PopoverContent>
                    </Popover>
                </div> -->

                <div class="mt-12 px-1 flex gap-7 items-center">
                    <div class="flex gap-2">
                        <h1 class="font-semibold "> {{ brands.length }} brands </h1>
                    </div>
                </div>
                
                <div class="mt-5 flex flex-wrap gap-5">
                    <WatchBrandCard v-for="brand in brands" :key="brand.name" :brand="brand" />
                </div>

            </div>
        </div>
    </div>
    
 
</template>
  
<script setup>
import Sidebar from '@/components/Sidebar.vue'
import WatchBrandCard from '@/components/WatchBrandCard.vue'

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

const brands = ref([])

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
