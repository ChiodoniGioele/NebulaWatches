<template>
    <div class="grid lg:grid-cols-5 min-h-screen">
        <Sidebar class="hidden lg:block" />

        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col">

            <div class="px-4 py-6 lg:px-8">

                <div class="flex w-full items-center gap-1.5">
                    <Input id="email" type="text" placeholder="Search a watch ..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                </div>

                <div class="mt-5 flex items-center">
                    <div class="w-full flex gap-7 items-center">
                        <div>
                            <Button @click="$router.back()" variant="secondary">
                                <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
                                <!-- Go back -->
                            </Button>
                        </div>
                        <div class="flex gap-2">
                            <Badge variant="outline">brand</Badge>
                            <h1 class="font-bold "> {{ watch.brand }} </h1>
                        </div>
                        <div class="flex gap-2">
                            <Badge variant="outline">family</Badge>
                            <h1 class="font-bold "> {{ watch.family }} </h1>
                        </div>
                    </div>
                    <div class="flex gap-2 w-auto">
                        <Button variant="outline">Add to storage</Button>
                        <Button variant="outline">
                            <img class="m-5 h-[25px] w-[25px]" src="@/assets/star.png"/>
                        </Button>
                        
                    </div>
                </div>
                

                <div class="mt-12 flex flex-wrap gap-5">
                    <div class="flex gap-20">
                        <img class="h-[300px] border border-gray-200 rounded-lg" :src="watchImage" alt="Watch Image" />
                        <div class="pt-2">
                            <ScrollArea class="h-[500px]">
                                <div class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Reference</h1>
                                {{ watch.reference }}
                            </div>
                            <div class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Name</h1>
                                {{ watch.name }}
                            </div>
                            <div v-if="watch.nickname" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Movement</h1>
                                {{ watch.nickname }}
                            </div>
                            <div class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Description</h1>
                                {{ watch.description }}
                            </div>
                            <div v-if="watch.movementName" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Movement</h1>
                                {{ watch.movementName }}
                            </div>
                            <div v-if="watch.isLimitedTo != 'No'" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Limited</h1>
                                {{ watch.isLimitedTo }}
                            </div>
                            <div v-if="watch.productionTime" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Production period</h1>
                                {{ watch.productionTime }}
                            </div>
                            <div v-if="watch.diameterInMm" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Diameter</h1>
                                {{ watch.diameterInMm }} mm
                            </div>
                            <div v-if="watch.heightInMm" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Height</h1>
                                {{ watch.heightInMm }} mm
                            </div>
                            <div v-if="watch.lugWidth" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Lug width</h1>
                                {{ watch.lugWidth }} mm
                            </div>
                            <div v-if="watch.waterResistanceM" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Water resistance</h1>
                                {{ watch.waterResistanceM }} m
                            </div>
                            <div v-if="watch.caseMaterial" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Case material</h1>
                                {{ watch.caseMaterial }}
                            </div>
                            <div v-if="watch.coatingMaterial" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Coating material</h1>
                                {{ watch.coatingMaterial }}
                            </div>
                            <div v-if="watch.bezelMaterial" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Bezel type</h1>
                                {{ watch.bezelMaterial }}
                            </div>
                            <div v-if="watch.dialColor" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Dial color</h1>
                                {{ watch.dialColor }}
                            </div>
                            <div v-if="watch.dialFinish" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Dial finish</h1>
                                {{ watch.dialFinish }}
                            </div>
                            <div v-if="watch.glassMaterial" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Glass material</h1>
                                {{ watch.glassMaterial }}
                            </div>
                            <div v-if="watch.isBackOpen" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Open back</h1>
                                Yes
                            </div>
                            <div v-else class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Open back</h1>
                                No
                            </div>
                            <div v-if="watch.watchShape" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Shape</h1>
                                {{ watch.watchShape }}
                            </div>
                            <div v-if="watch.watchIndexes" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Indexes</h1>
                                {{ watch.watchIndexes }}
                            </div>
                            </ScrollArea>
                           
                        </div>
                    </div>
                    
                    
                </div>

            </div>


        </div>
    </div>
</template>

<script setup>
import Sidebar from '@/components/Sidebar.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { ScrollArea } from '@/components/ui/scroll-area'

import axios from 'axios';

import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';


const route = useRoute();
const reference = route.params.reference;
const watch = ref({});
const watchImage = ref();

async function fetchWatch() {
    try {
        const response = await axios.get(`http://localhost:64321/v1/watches/${reference}`, 
        {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        watch.value = response.data;
        console.log(watch.value);
  } catch (error) {
    console.error('Failed to fetch families:', error);
  }
}

async function fetchWatchImage() {
    try {
        const endpoint = 'http://localhost:64321/v1/watches/' + reference + '/image'
        const response = await axios.get(endpoint, {
            responseType: 'arraybuffer',
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
        });

        const imageBase64 = btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''));
        watchImage.value = `data:${response.headers['content-type']};base64,${imageBase64}`;
  } catch (error) {
    watchImage.value = "@assets/no_image.png"
  }
}


onMounted(async () => {
    fetchWatch();
    fetchWatchImage();
});

</script>