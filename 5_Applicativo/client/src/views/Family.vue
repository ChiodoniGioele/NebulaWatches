<!--
 This page contains the clocks in the families.
 It is loaded when you select a brand and a family
 -->
<template>
    <div class="flex h-screen"> 
        <Sidebar />
        <div class="flex flex-col w-full" > 
            <div class="px-8 py-6">
                <div class="flex w-full items-center gap-1.5">
                    <Input class="border-stone-900" @click="router.push('/search')" @change="router.push('/search')" id="email" type="text" placeholder="Search a watch ..." />
                    <Button @click="router.push('/search')" type="submit"> Search </Button>
                    <Button variant="outline" @click="toFavourite">
                        <img class="m-2 h-[25px] w-[25px]" src="@/assets/favourites.png" alt="favourites"/>
                        <p class="m-2">Favourites</p>
                    </Button>
                </div>
                
                <div class="mt-5 flex gap-7 items-center">
                    <div>
                        <Button @click="$router.back()" variant="secondary"> 
                            <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" alt="go back"/>
                            <!-- Go back -->
                        </Button>
                    </div>
                    <div class="flex gap-2">
                        <Badge variant="outline">brand</Badge>
                        <h1 class="font-bold "> {{ brandName }} </h1>
                    </div>
                    <div class="flex gap-2">
                        <Badge variant="outline">family</Badge>
                        <h1 class="font-bold "> {{ familyName }} </h1>
                    </div>
                </div>

                <div class="mt-12 px-1 flex gap-7 items-center">
                    <div class="flex gap-2">
                        <h1 class="font-semibold "> {{ totalWatchesCount }} watches </h1>
                    </div>
                </div>
                
                <div class="mt-5 flex flex-wrap gap-5">
                    <WatchCard v-for="watch in watches" :key="watch.reference" :watch="watch" />
                </div>

                <div class="mt-12 px-1 w-full gap-7 flex items-center justify-center max-w-full">
                    <Pagination v-slot="{ page }" :total="totalPages * 10" :sibling-count="3" show-edges :default-page="1">
                        <PaginationList v-slot="{ items }" class="flex items-center gap-1 w-full" >
                        <PaginationFirst @click="fetchWatchesOfBrands(1)" />
                        <PaginationPrev @click="fetchWatchesOfBrands(actualPage - 1)"/>

                        <template v-for="(item, index) in items">
                            <PaginationListItem v-if="item.type === 'page'" :key="index" :value="item.value" as-child>
                            <Button class="w-10 h-10 p-0" :variant="item.value === page ? 'default' : 'outline'" @click="fetchWatchesOfBrands(item.value)">
                                {{ item.value }}
                                
                            </Button>
                            </PaginationListItem>
                            <PaginationEllipsis v-else :key="item.type" :index="index" />
                        </template>

                        <PaginationNext @click="fetchWatchesOfBrands(actualPage + 1)" />
                        <PaginationLast  @click="fetchWatchesOfBrands(totalPages)" />
                        </PaginationList>
                    </Pagination>
                    
                </div>
            </div>
        </div>
    </div>

    
    <Chat />
</template>
  
<script setup>

// import
import Chat from '@/components/Chat.vue'
import Sidebar from '@/components/Sidebar.vue'
import WatchCard from '@/components/WatchCard.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
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

const router = useRouter();
const route = useRoute();
const brandName = route.params.brandName;
const familyId = route.params.familyId;
const watches = ref([]);
const familyName = ref('')



const totalPages = ref(1)
const totalWatchesCount = ref(0)
const actualPage = ref(1)

// Takes the clocks present in the household
async function fetchWatchesOfBrands(pageRequestValue) {
    try {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        const response = await axios.get(`${apiServerAddress}/v1/families/${familyId}/watches?page=${(pageRequestValue - 1)}&sortBy=name`, 
        //const response = await axios.get(`${apiServerAddress}/v1/families/${familyId}/watches`, 
        {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        watches.value = response.data.content;
        console.log(watches.value[0]);
        familyName.value = watches.value[0].family;

        totalPages.value = response.data.totalPages;
        totalWatchesCount.value = response.data.totalElements;

        actualPage.value = pageRequestValue;
        
  } catch (error) {
    console.error('Failed to fetch families:', error);
  }
}
// for go to favorite
async function toFavourite(){
  router.push('/favourite');
}

onMounted(async () => {
    fetchWatchesOfBrands(1);
});

</script>
