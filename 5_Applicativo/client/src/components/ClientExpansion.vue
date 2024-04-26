<template>
  <h3>Total watches buys: {{ totalBuys }}</h3> <!-- creare API -->
  <h3>Total watches expenses: {{ totalExpenses }} CHF</h3> <!-- creare API -->
  <ScrollArea class=" max-h-[60vh]">
    <div class="mt-5 flex flex-wrap gap-5 mb-1">
      <StorageCard v-for="watch in buysWatches" :key="watch.id" :storage="watch" /> <!-- per le card con le immagini -->
    </div>
  </ScrollArea>
</template>
<script setup>

import { ref, onMounted } from 'vue';
import axios from 'axios';
import { apiServerAddress } from '@/main.ts'
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area'
import StorageCard from '@/components/StorageCard.vue'

const props = defineProps(['client'])
const totalBuys = ref();
const totalExpenses = ref();
const emailUser = ref();
const buysWatches = ref([]);

async function countBuysWatches() {
  const clientId = props.client.id;
  const userEmail = emailUser.value;

  console.log(clientId);
  console.log(userEmail);

  try {
    const response = await axios.get(`${apiServerAddress}/v1/storage/getWatchSoldByClientNum?userEmail=${userEmail}&clientId=${clientId}`, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token'),
      },
    });

    console.log(response.data); // Stampa il numero di orologi venduti dal cliente

    return response.data; // Ritorna il numero di orologi venduti
  } catch (error) {
    console.error('Fetch failed:', error);
    throw error; // Lancia l'errore per gestirlo altrove, se necessario
  }
}

async function countBuysWatchesExpenses() {

  const clientId = props.client.id;
  const userEmail = emailUser.value;

  try {
    const response = await axios.get(`${apiServerAddress}/v1/storage/getWatchSoldByClientTotalExpenses?userEmail=${userEmail}&clientId=${clientId}`, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token'),
      },
    });
    return response.data;
  } catch (error) {
    console.error('Fetch failed:', error);
    throw error;
  }
}


async function fetchBuysWatches() {

  const clientId = props.client.id;

  console.log(clientId);
  console.log(emailUser.value);

  try {
    const response = await axios.get(`${apiServerAddress}/v1/storage/getWatchSoldByClient?userEmail=${emailUser.value}&clientId=${clientId}`, {
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('token'),
      },
    });
    buysWatches.value = response.data;
    console.log(buysWatches);
    console.log(buysWatches.value.watch);
  } catch (error) {
    console.error('Fetch failed:', error);
  }

}



onMounted(async () => {
  const token = localStorage.getItem('token');
  const parts = token.split('.');
  const payload = JSON.parse(atob(parts[1]));
  emailUser.value = payload.sub;
  totalBuys.value = await countBuysWatches();
  totalExpenses.value = await countBuysWatchesExpenses();
  fetchBuysWatches();
});

</script>