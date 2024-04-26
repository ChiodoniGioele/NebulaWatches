<template>
    <h3>Total watches sold: {{ totalSold }}</h3>
    <ScrollArea class=" max-h-[60vh]">
    <div class="mt-5 flex flex-wrap gap-5 mb-1">
        <StorageCard v-for="watch in soldWatches" :key="watch.id" :storage="watch" />
    </div>
</ScrollArea>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { apiServerAddress } from '@/main.ts'
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area'
import StorageCard from '@/components/StorageCard.vue'

const props = defineProps(['team'])
const totalSold = ref();
const soldWatches = ref([]);

async function countSoldWatches(id) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/team/countSold/${id}`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            }
        );
        if (response.data != -1) {
            return response.data;
        }
        console.log('Failed to count sold watches');
        return 0;
    } catch (error) {
        console.error('Failed to count sold watches', error);
    }
}

async function fetchSoldWatches(id) {
    try {
        var teamId = id;
        const response = await axios.get(`${apiServerAddress}/v1/storage/getStorageByTeam/${teamId}`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        soldWatches.value = response.data;
    } catch (error) {
        console.error('Failed to fetch storage data:', error);
    }
}


onMounted(async () => {
    totalSold.value = await countSoldWatches(props.team.id);
    fetchSoldWatches(props.team.id);
});

</script>