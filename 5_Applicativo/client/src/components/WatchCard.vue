<!--
Watch card, which if selected goes to the page with the watch in detail. (Displayed on the home page)
-->
<template>
    <div class="text-center border-stone-500 border-2 rounded-md min-w-[170px]  w-[18%] min-h-[200px] max-[600px]:w-[40%] pt-2">
        <div class="h-[20px]">
            <div v-if="watch.isLimitedTo != 'No'" class="pt-1 text-gray-400 text-sm">
                {{ watch.isLimitedTo.replace('Yes,', 'Limited to') }}
            </div>
        </div>
        <router-link class="block p-5" :to="`/watch/${watch.reference}`" :brand="brand">
            <div>
                <div v-if="!isLoading">
                    <div class="flex items-center h-[140px] justify-center">
                        <img class="max-w-full max-h-[140px]" :src="randomWatchFromBrandImage" alt="Watch Image" />
                    </div>
                </div>
                <div v-else>
                    <div class="flex items-center h-[140px] justify-center">
                        <Skeleton class="w-[150px] h-[130px] rounded-md" />
                    </div>
                    
                </div>

                <ScrollArea class="max-w-full whitespace-nowrap">
                    <h2 class="mt-2 pb-2 font-semibold">{{ watch.reference }}</h2>
                    <ScrollBar orientation="horizontal" />
                </ScrollArea>

                <ScrollArea class="h-[50px]">
                    <span class="text-sm"> {{ watch.name }} </span>
                </ScrollArea>
                
            </div>
        </router-link>
    </div>
    

    
</template>

<script setup>
import { Skeleton } from "@/components/ui/skeleton"
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area'
import { Separator } from '@/components/ui/separator'
import { apiServerAddress } from '@/main.ts'

import axios from 'axios';
import { ref, onMounted } from 'vue';

const props = defineProps(['watch'])
const randomWatchFromBrandImage = ref('')
const isLoading = ref(true);

const piecesProduced = ref('')



async function fetchRandomWatchFromBrandImage() {
    try {
        const endpoint = `${apiServerAddress}/v1/watches/` + props.watch.reference + '/image'
        const response = await axios.get(endpoint, {
            responseType: 'arraybuffer',
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
        });
        const imageBase64 = btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''));
        randomWatchFromBrandImage.value = `data:${response.headers['content-type']};base64,${imageBase64}`;
  } catch (error) {
    randomWatchFromBrandImage.value = "@assets/no_image.png"
  }
}

onMounted(async () => {
    try {
        await fetchRandomWatchFromBrandImage();
    } finally {
        isLoading.value = false;
    }
});

</script>