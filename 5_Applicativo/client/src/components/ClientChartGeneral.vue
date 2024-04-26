<template>
  <Bar v-if="loaded" id="teamChart" :options="chartOptions" :data="chartData"/>
  <Skeleton v-else class="w-[25vw] h-[12vw] rounded-md"/>
</template>

<script setup>
import { Skeleton } from "@/components/ui/skeleton";
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { apiServerAddress } from '@/main.ts'
import { Bar, Chart } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'


ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const loaded = ref(false);
const clients = ref([]);
const chartData = ref({
  labels: null,
  datasets: [
    {
      label: 'Watches Bought',
      backgroundColor: '',
      borderColor: '',
      borderWidth: 1,
      data: null
    }
  ]
});
const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: true,
  scales: {
    y: {
      ticks: {
        color: 'black',
        stepSize: 1
      }
    },
    x: {
      ticks: {
        color: 'black'
      }
    }
  },
  plugins: {
    title: {
      display: true,
      text: "Watches Bought By Clients"
    },
    legend: {
      display: true
    }
  }
});


const emailUser = ref();

async function getNames() {
  var clientName = [];
  var soldWatches = [];
  for (var i = 0; i < clients.value.length; i++) {
    clientName.push(clients.value[i].name);
    soldWatches.push(await countBuysWatches(clients.value[i].id));
  }
  console.log(clientName);
  console.log(soldWatches);
  chartData.value.labels = clientName;
  chartData.value.datasets[0].data = soldWatches;
  var colors = generateRandomColors(clientName.length);
  chartData.value.datasets[0].backgroundColor = colors;
  chartData.value.datasets[0].borderColor = colors.map(color => color.replace(/,[^,]+?\)$/, ', 1)'));
  loaded.value = true;
}


async function countBuysWatches(clientId) {
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

function generateRandomColors(count) {
  const colors = [];
  for (let i = 0; i < count; i++) {
    let color = generateRandomColor();
    while (isTooSimilar(color, colors)) {
      color = generateRandomColor();
    }
    colors.push(color);
  }
  return colors;
}
function isTooSimilar(color, colors) {
  const threshold = 30;
  for (const existingColor of colors) {
    if (colorDistance(color, existingColor) < threshold) {
      return true;
    }
  }
  return false;
}
function colorDistance(color1, color2) {

  return Math.sqrt(
      Math.pow(color2.r - color1.r, 2) +
      Math.pow(color2.g - color1.g, 2) +
      Math.pow(color2.b - color1.b, 2)
  );
}
function generateRandomColor() {
  const r = Math.floor(Math.random() * 256);
  const g = Math.floor(Math.random() * 256);
  const b = Math.floor(Math.random() * 256);

  return `rgb(${r}, ${g}, ${b}, 0.5)`;
}


// trovare i clienti
async function fetchClients(email) {
  try {
    const response = await axios.get(`${apiServerAddress}/v1/clients/all/${email}`,
        {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token'),
          },
        });
    clients.value = response.data;
  } catch (error) {
    console.error('Failed to fetch clients:', error);
  }
}


onMounted(async () => {
  const token = localStorage.getItem('token');
  const parts = token.split('.');
  const payload = JSON.parse(atob(parts[1]));
  emailUser.value = payload.sub;
  await fetchClients(emailUser.value);
  await getNames();
});

</script>