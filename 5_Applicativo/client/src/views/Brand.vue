<template>
    <div class="grid lg:grid-cols-5 min-h-screen">
        <Sidebar class="hidden lg:block" />

        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col">

            <div class="px-4 py-6 lg:px-8">

                <div class="flex w-full items-center gap-1.5">
                    <Input @click="router.push('/search')" @change="router.push('/search')" id="email" type="text" placeholder="Search a watch ..." />
                    <Button @click="router.push('/search')" type="submit" class="bg-blue-600"> Search </Button>
                    <Button variant="outline" @click="toFavourite">
                        <img class="m-2 h-[25px] w-[25px]" src="@/assets/favourites.png"/>
                        <p class="m-2">Favourites</p>
                    </Button>
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
                        <h1 class="font-semibold "> {{ totalFamiliesCount }} families </h1>
                    </div>
                </div>

                <div class="mt-5 flex flex-wrap gap-5">
                    <WatchFamilyCard v-for="family in families" :key="family.id" :family="family" :brandName="brandName" />
                </div>
                

                <div class="mt-12 px-1 w-full gap-7 flex items-center justify-center">
                    <Pagination v-slot="{ page }" :total="totalPages * 10" :sibling-count="3" show-edges :default-page="1">
                        <PaginationList v-slot="{ items }" class="flex items-center gap-1 w-full" >
                        <PaginationFirst @click="fetchFamiliesOfBrand(1)" />
                        <PaginationPrev @click="fetchFamiliesOfBrand(actualPage - 1)"/>

                        <template v-for="(item, index) in items">
                            <PaginationListItem v-if="item.type === 'page'" :key="index" :value="item.value" as-child>
                            <Button class="w-10 h-10 p-0" :variant="item.value === page ? 'default' : 'outline'" @click="fetchFamiliesOfBrand(item.value)">
                                {{ item.value }}
                                
                            </Button>
                            </PaginationListItem>
                            <PaginationEllipsis v-else :key="item.type" :index="index" />
                        </template>

                        <PaginationNext @click="fetchFamiliesOfBrand(actualPage + 1)" />
                        <PaginationLast  @click="fetchFamiliesOfBrand(totalPages)" />
                        </PaginationList>
                    </Pagination>
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
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const brandName = route.params.brandName;
const families = ref([]);

const totalPages = ref(1)
const totalFamiliesCount = ref(0)
const actualPage = ref(1)

async function fetchFamiliesOfBrand(pageRequestValue) {
    try {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        const response = await axios.get(`${apiServerAddress}/v1/brands/${brandName}/families?page=${(pageRequestValue - 1)}&sortBy=name`, 
        //const response = await axios.get(`${apiServerAddress}/v1/brands/${brandName}/families`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        families.value = response.data.content;
        
        totalPages.value = response.data.totalPages;
        totalFamiliesCount.value = response.data.totalElements;

        actualPage.value = pageRequestValue;
    } catch (error) {
        console.error('Failed to fetch families:', error);
    }
}

async function toFavourite(){
  router.push('/favourite');
}

onMounted(async () => {

    await fetchFamiliesOfBrand(1);

});
</script>
