<template>
  <div class="flex h-screen">
    <Sidebar/>
    <div class="flex flex-col w-full">
      <div class="px-8 py-6">
<!--        <div class="mt-5 flex flex-wrap gap-5">
            <StorageCard v-for="watch in storedWatchesNotSold" :key="watch.id" :storage="watch"/>
        </div>-->
      </div>
    </div>
  </div>


</template>

<script setup>

import Sidebar from '@/components/Sidebar.vue'
import StorageCard from '@/components/StorageCard.vue'

import {apiServerAddress} from '@/main.ts'
import axios from 'axios';
import {ref, onMounted} from 'vue';
import {useRoute} from 'vue-router';

const route = useRoute();

async function fetchSold() {

  const userEmail = "lele@gmail.com";
  const clientId = 1;

  try {
    const response = await axios.get(`${apiServerAddress}/v1/storage/getWatchSoldByClient?userEmail=${userEmail}&clientId=${clientId}`, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token'),
      },
    });
    console.log(response.data);
  } catch (error) {
    console.error('Fetch failed:', error);
  }

}

onMounted(async () => {
  await fetchSold();
});

</script>
