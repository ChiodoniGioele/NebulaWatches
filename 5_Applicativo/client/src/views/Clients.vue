<template>
  <div class="grid lg:grid-cols-5 min-h-screen">
    <Sidebar class="hidden lg:block" />

    <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col">

      <div class="px-4 py-6 lg:px-8">


        <div class="mt-5 flex gap-7 items-center">
          <div>
            <Button @click="$router.back()" variant="secondary">
              <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
              <!-- Go back -->
            </Button>
          </div>

            <div class="w-full flex gap-7 items-center"></div>
            <div class="flex gap-2 w-auto">
              <Button variant="outline" @click="">
                Add
              </Button>
            </div>
          
        </div>


        <div class="mt-12 px-1 flex gap-7 items-center">
          <div class="flex gap-2">
            <h1>grafico</h1>
          </div>
        </div>

        <!-- <div class="mt-5 flex flex-wrap gap-5">
                    <WatchFamilyCard v-for="family in families" :key="family.id" :family="family"
                        :brandName="brandName" />
                </div> -->


        <div class="mt-12 px-1 w-full gap-7 flex items-center justify-center">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead class="w-[100px]">
                  Name
                </TableHead>
                <TableHead>Surname</TableHead>
                <TableHead>Email</TableHead>
                <TableHead>Phone</TableHead>
                <TableHead class="text-right">
                  Acquisti
                </TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              <TableRow v-for="client in clients" :key="client.id">
                <TableCell class="font-medium">
                  {{ client.name }}
                </TableCell>
                <TableCell>{{ client.surname }}</TableCell>
                <TableCell>{{ client.email }}</TableCell>
                <TableCell>{{ client.phone }}</TableCell>
                <TableCell class="text-right">
                  Ciao
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </div>


      </div>


    </div>
  </div>
</template>

<script setup>
import Sidebar from '@/components/Sidebar.vue'
import WatchFamilyCard from '@/components/WatchFamilyCard.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components//ui/button'
import { Badge } from '@/components/ui/badge'
import { apiServerAddress } from '@/main.ts'
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table'

const invoices = [
  {
    invoice: 'INV001',
    paymentStatus: 'Paid',
    totalAmount: '$250.00',
    paymentMethod: 'Credit Card',
  },
  {
    invoice: 'INV002',
    paymentStatus: 'Pending',
    totalAmount: '$150.00',
    paymentMethod: 'PayPal',
  },
  {
    invoice: 'INV003',
    paymentStatus: 'Unpaid',
    totalAmount: '$350.00',
    paymentMethod: 'Bank Transfer',
  },
  {
    invoice: 'INV004',
    paymentStatus: 'Paid',
    totalAmount: '$450.00',
    paymentMethod: 'Credit Card',
  },
  {
    invoice: 'INV005',
    paymentStatus: 'Paid',
    totalAmount: '$550.00',
    paymentMethod: 'PayPal',
  },
  {
    invoice: 'INV006',
    paymentStatus: 'Pending',
    totalAmount: '$200.00',
    paymentMethod: 'Bank Transfer',
  },
  {
    invoice: 'INV007',
    paymentStatus: 'Unpaid',
    totalAmount: '$300.00',
    paymentMethod: 'Credit Card',
  },
]

import {
  Pagination,
  PaginationEllipsis,
  PaginationFirst,
  PaginationLast,
  PaginationList,
  PaginationListItem,
  PaginationNext,
  PaginationPrev,
} from '@/components/ui/pagination'

import axios from 'axios';

import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const clients = ref([]);

async function fetchClients() {
  try {

    const response = await axios.get(`${apiServerAddress}/v1/clients/`,
      //const response = await axios.get(`${apiServerAddress}/v1/brands/${brandName}/families`,
      {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
        },
      });

    clients.value = response.data;

    console.log(clients.value);


  } catch (error) {
    console.error('Failed to fetch clients:', error);
  }
}


onMounted(async () => {

  await fetchClients();

});
</script>
