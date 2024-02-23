<template>
    <div class="grid lg:grid-cols-5 min-h-screen"> 
        <Sidebar class="hidden lg:block" />
        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col"> 
            <div class="px-4 py-6 lg:px-8"> 

                <div class="flex w-full items-center gap-1.5">
                    <Input id="email" type="text" placeholder="Search a watch ..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                </div>
                
                <div class="mt-5 flex gap-7 items-center">
                    <div>
                        <Button @click="$router.back()" variant="secondary"> 
                            <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png"/>
                            <!-- Go back -->
                        </Button>
                    </div>
                    <div class="flex gap-2">
                        <Badge variant="outline">brand</Badge>
                        <h1 class="font-bold "> {{ brandName }} </h1>
                    </div>
                    <div class="flex gap-2">
                        <Badge variant="outline">family</Badge>
                        <h1 class="font-bold "> {{ familyName }} </h1>
                    </div>
                </div>

                <div class="mt-12 px-1 flex gap-7 items-center">
                    <div class="flex gap-2">
                        <h1 class="font-semibold "> {{ watches.length }} watches </h1>
                    </div>
                </div>
                
                <div class="mt-5 flex flex-wrap gap-5">
                    <WatchCard v-for="watch in watches" :key="watch.reference" :watch="watch" />
                </div>

            </div>
        </div>
    </div>
    
 
</template>
  
<script setup lang="ts">
import Sidebar from '@/components/Sidebar.vue'
import WatchCard from '@/components/WatchCard.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'

import axios from 'axios';

import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';


const route = useRoute();
const brandName = route.params.brandName;
const familyId = route.params.familyId;
const watches = ref([]);
const familyName = ref('')

async function fetchFamiliesOfBrand() {
    try {
        const response = await axios.get(`http://localhost:64321/v1/families/${familyId}/watches`, 
        {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        watches.value = response.data;
        familyName.value = watches.value[0].family.name;
  } catch (error) {
    console.error('Failed to fetch families:', error);
  }
}


onMounted(async () => {
    fetchFamiliesOfBrand();
});
</script>
