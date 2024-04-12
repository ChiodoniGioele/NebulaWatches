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
const teams = ref([]);

const chartData = ref({
    labels: null,
    datasets: [
        {
            label: 'Watches Sold',
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
        text: "Sold Watches by Team"
      },
      legend: {
        display: true
      }
    }
});

async function getNames() {
    var names = [];
    var soldWatches = [];
    for (var i = 0; i < teams.value.length; i++) {
        names.push(teams.value[i].name);
        soldWatches.push(await countSoldWatches(teams.value[i].id));
    }
    chartData.value.labels = names;
    chartData.value.datasets[0].data = soldWatches;
    var colors = generateRandomColors(names.length);
    chartData.value.datasets[0].backgroundColor = colors;
    chartData.value.datasets[0].borderColor = colors.map(color => color.replace(/,[^,]+?\)$/, ', 1)'));
    loaded.value = true;
}

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

async function fetchTeam(email) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/team/getTeam/${email}`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        teams.value = response.data;
    } catch (error) {
        console.error('Failed to fetch team:', error);
    }
}


onMounted(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    await fetchTeam(payload.sub);
    await getNames();
});

</script>