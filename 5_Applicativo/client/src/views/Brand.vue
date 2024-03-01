<template>
    <div class="grid lg:grid-cols-5 min-h-screen">
        <Sidebar class="hidden lg:block" />

        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col">

            <div class="px-4 py-6 lg:px-8">

                <div class="flex w-full items-center gap-1.5">
                    <Input id="email" type="text" placeholder="Search a watch ..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                </div>

                <div class="mt-5 flex gap-7 items-center">
                    <div>
                        <Button @click="$router.back()" variant="secondary">
                            <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
                            <!-- Go back -->
                        </Button>
                    </div>
                    <div class="flex gap-2">
                        <Badge variant="outline">brand</Badge>
                        <h1 class="font-bold "> {{ brandName }} </h1>
                    </div>
                </div>

                <div class="mt-12 px-1 flex gap-7 items-center">
                    <div class="flex gap-2">
                        <h1 class="font-semibold "> {{ families.length }} families </h1>
                    </div>
                </div>

                <div class="mt-5 flex flex-wrap gap-5">
                    <WatchFamilyCard v-for="family in families" :key="family.id" :family="family" :brandName="brandName" />
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
import { useRoute } from 'vue-router';


const route = useRoute();
const brandName = route.params.brandName;
const families = ref([]);


async function fetchFamiliesOfBrand() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/brands/${brandName}/families`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        families.value = response.data;
    } catch (error) {
        console.error('Failed to fetch families:', error);
    }
}


onMounted(async () => {

    await fetchFamiliesOfBrand();

});
</script>
