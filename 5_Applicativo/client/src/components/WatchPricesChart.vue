<template>
    <Line
        id="watch-prices-chart"
        :options="chartOptions"
        :data="chartData"
    />
</template>

<script setup>
import { ref, defineProps, onMounted } from 'vue'
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

const props = defineProps(['prices', 'labels'])

const chartOptions = ref(
    { 
        responsive: true,
        maintainAspectRatio: true,
        layout: {
            scales: {
                x: {
                    offset: true,
                }
            },
        }
    }
);
const chartData = ref({});

chartData.value = {
    labels: props.labels,
    datasets: [
        {
            label: 'Price (USD)',
            backgroundColor: '#32CD32',
            data: props.prices,
            pointRadius: 5 
        }
    ]
}

</script>
