<template>
  <Line v-if="loaded" id="teamChart2" :options="chartOptions" :data="chartData" />
  <Skeleton v-else class="w-[37vw] h-[19vw] rounded-md"/>
</template>

<script setup>
import { Skeleton } from "@/components/ui/skeleton";
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { apiServerAddress } from '@/main.ts'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js'
import { Line } from 'vue-chartjs'

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
)
const emailUser = ref();
const loaded = ref(false);
const clients = ref([]);
const chartData = ref({
  labels: [],
  datasets: []
});
const chartOptions = ref({
  responsive: true,
  amaintainAspectRatio: true,
  scales: {
    y: {
      ticks: {
        color: 'black',
        stepSize: 1
      }
    },
    x: {
      ticks: {
        color: 'black',
        offset: true
      }
    }
  },
  plugins: {
    title: {
      display: true,
      text: "Watches Bought Last Year"
    },
    legend: {
      display: true
    }
  }
});


async function fetchValueForMonth(id, month) {
  try {
    const response = await axios.get(`${apiServerAddress}/v1/storage/getWatchesOwnedByClientMonth?id=${id}&month=${month}`,
        {
          headers: {
            Authorization: 'Bearer ' + localStorage.getItem('token'),
          },
        }
    );
    if (response.data != -1) {
      return response.data;
    }
    console.log('Failed to count bought watches');
    return 0;
  } catch (error) {
    console.error('Failed to count bought watches', error);
  }
}

async function getNames() {
  var names = [];
  var soldWatches = [];
  for (var i = 0; i < clients.value.length; i++) {
    names.push(clients.value[i].name);

    soldWatches.push(await getDataForName(clients.value[i].id));
  }
  chartData.value.labels = getLast12MonthsNames();
  chartData.value.datasets = generateDatasets(names, soldWatches);
  loaded.value = true;
}

async function getDataForName(id) {
  var data = [];
  for (var month = 11; month >= 0; month--) {
    var valueForMonth = await fetchValueForMonth(id, month);
    data.push(valueForMonth);
  }
  return data;
}

function generateDatasets(names, soldWatches) {
  const datasets = [];
  const colors = [];
  for (let i = 0; i < names.length; i++) {
    var color = generateRandomColor();
    while(isTooSimilar(color, colors) || isTooLight(color)){
      color = generateRandomColor();
    }
    var color2 = color.replace(/,[^,]+?\)$/, ', 1)');
    colors.push(color);
    datasets.push({
      label: names[i],
      data: soldWatches[i],
      backgroundColor: color,
      borderColor: color2,
      borderWidth: 1
    });
  }
  return datasets;
}

function isTooLight(color) {
  const threshold = 250;
  const brightness = (color.r * 0.299) + (color.g * 0.587) + (color.b * 0.114);
  return brightness > threshold;
}

function isTooSimilar(color, colors) {
  const threshold = 40;
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

function getLast12MonthsNames() {
  const months = [
    "January", "February", "March", "April",
    "May", "June", "July", "August",
    "September", "October", "November", "December"
  ];
  const today = new Date();
  const last12Months = [];

  for (let i = 0; i < 12; i++) {
    const month = (today.getMonth() - i + 12) % 12;
    var year = today.getFullYear();
    if(today.getMonth() - i < 0){
      year = year-1;
    }

    last12Months.push(`${months[month]} ${year}`);
  }
  return last12Months.reverse();
}

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