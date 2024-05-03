<!--
Card representing the brand's family of watches with a random image of a watch present in the family.
-->
<template>
     <div class="text-center border-stone-500 border-2 rounded-md min-w-[170px]  w-[18%] min-h-[200px] max-[600px]:w-[40%] pt-2">
        <router-link class="block p-5" :to="`/brand/${brandName}/family/${family.id}/watches`">
            <div>
                <div v-if="!isLoading">
                    <div class="flex items-center h-[140px] justify-center">
                        <img class="max-w-full max-h-[140px]" :src="randomWatchFromFamilyImage" alt="Watch Image" />
                    </div>
                </div>
                <div v-else>
                    <div class="flex items-center h-[140px] justify-center">
                        <Skeleton class="w-[150px] h-[130px] rounded-md" />
                    </div>
                </div>
                <h2 class="mt-2">{{ family.name }}</h2>
            </div>
        </router-link>
    </div>
</template>

<script setup>
import { Skeleton } from "@/components/ui/skeleton"
import { apiServerAddress } from '@/main.ts'

import axios from 'axios';
import { ref, onMounted } from 'vue';

const props = defineProps(['brandName','family'])
const randomWatchFromFamilyImage = ref('')
const isLoading = ref(true);

async function fetchRandomWatchFromBrandImage() {
    try {
        const endpoint = `${apiServerAddress}/v1/families/` + props.family.id + '/rndimage'
        const response = await axios.get(endpoint, {
            responseType: 'arraybuffer',
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
        });

        const imageBase64 = btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''));
        randomWatchFromFamilyImage.value = `data:${response.headers['content-type']};base64,${imageBase64}`;
  } catch (error) {
    console.error('Failed to fetch image:', error);
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