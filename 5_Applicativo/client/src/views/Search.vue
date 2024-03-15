<template>
    <div class="grid lg:grid-cols-5 min-h-screen"> 
        <Sidebar class="hidden lg:block" />
        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col"> 
            <div class="px-4 py-6 lg:px-8"> 

                <div class="flex w-full items-center gap-1.5">
                    <Input @change="fetchSearchedWatches(1)" v-model="query" id="email" type="text" placeholder="Search a watch ..." />
                    <Button @click="fetchSearchedWatches(1)" type="submit" class="bg-blue-600"> Search </Button>
                </div>
                
                <div class="mt-5 flex gap-7 items-center">
                    <div>
                        <Button @click="$router.back()" variant="secondary"> 
                            <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png"/>
                            <!-- Go back -->
                        </Button>
                    </div>
                    
                </div>

                <div class="mt-3 flex gap-2">
                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Brands ({{ brands.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="brand in brands" :key="brand.name"  class="flex items-center space-x-2 mb-3">
                                    
                                    <Checkbox v-if="brandsSelected.includes(brand.name)" :id="`${brand.name}-checkbox`" @click="handleBrandSelection(brand.name)" checked/>
                                    <Checkbox v-else :id="`${brand.name}-checkbox`" @click="handleBrandSelection(brand.name)"/>
                                    
                                    <label
                                        :for="`${brand.name}-checkbox`"
                                        class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                    >
                                        {{ brand.name }}
                                    </label>
                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                    <div v-if="families.length > 0">
                        <Popover>
                            <PopoverTrigger>
                                <Button variant="outline"> <span class="font-normal">Families ({{ families.length }})</span> </Button>
                            </PopoverTrigger>
                            <PopoverContent class="w-auto">
                                <ScrollArea class="h-[40vh] p-2">
                                    <div v-for="family in families" :key="family.id" class="flex items-center space-x-2 mb-3">
                                        <Checkbox :id="`${family.name}-checkbox`"  />
                                        <label
                                        :for="`${family.name}-checkbox`"
                                        class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                        >
                                        {{ family.brand.name }} - {{ family.name }}
                                        </label>
                                        
                                    </div>
                                </ScrollArea>
                            </PopoverContent>
                        </Popover>

                    </div>

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Materials ({{ materials.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="material in materials" :key="material.name" class="flex items-center space-x-2 mb-3">
                                    <Checkbox :id="`${material.name}-checkbox`"  />
                                    <label
                                    :for="`${material.name}-checkbox`"
                                    class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                    >
                                    {{ material.name }}
                                    </label>
                                    
                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                </div>

               
                <div class="mt-12 px-1 flex gap-7 items-center">
                    <div class="flex gap-2">
                        <h1 class="font-semibold "> {{ totalWatchesCount }} watches </h1>
                    </div>
                </div>
                
                <div class="mt-5 flex flex-wrap gap-5">
                    <WatchCard v-for="watch in watchesObjects" :key="watch.reference" :watch="watch" />
                </div>

                <div class="mt-12 px-1 w-full gap-7 flex items-center justify-center">
                    <Pagination v-slot="{ page }" :total="totalPages * 10" :sibling-count="3" show-edges :default-page="1">
                        <PaginationList v-slot="{ items }" class="flex items-center gap-1 w-full" >
                            <PaginationFirst @click="fetchSearchedWatches(1)" />
                            <PaginationPrev @click="fetchSearchedWatches(actualPage - 1)"/>

                            <template v-for="(item, index) in items">
                                <PaginationListItem v-if="item.type === 'page'" :key="index" :value="item.value" as-child>
                                    <Button class="w-10 h-10 p-0" :variant="item.value === page ? 'default' : 'outline'" @click="fetchSearchedWatches(item.value)">
                                        {{ item.value }}
                                    </Button>
                                </PaginationListItem>
                                <PaginationEllipsis v-else :key="item.type" :index="index" />
                            </template>

                            <PaginationNext @click="fetchSearchedWatches(actualPage + 1)" />
                            <PaginationLast  @click="fetchSearchedWatches(totalPages)" />
                        </PaginationList>
                    </Pagination>
                </div>
                
            </div>
        </div>
    </div>
</template>
  
<script setup>
import Sidebar from '@/components/Sidebar.vue'
import WatchCard from '@/components/WatchCard.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { apiServerAddress } from '@/main.ts'
import { Checkbox } from '@/components/ui/checkbox'
import { ScrollArea } from '@/components/ui/scroll-area'
import { Popover, PopoverTrigger, PopoverContent } from '@/components/ui/popover'
import { Pagination, PaginationEllipsis, PaginationFirst, PaginationLast, PaginationList, PaginationListItem, PaginationNext, PaginationPrev } from '@/components/ui/pagination'
import axios from 'axios';
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';

const watches = ref([])
const watchesObjects = ref([]);
const query = ref("")
const totalPages = ref(1)
const totalWatchesCount = ref(0)
const actualPage = ref(1)
const isLoading = ref(true)

const brands = ref([])
const brandsSelected = ref([])

const families = ref([])
const familiesSelected = ref([])

const materials = ref([])
const materialsSelected = ref([])

async function fetchSearchedWatches(pageRequestValue) {
    watchesObjects.value = []
    try {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        isLoading.value = true;
        const response = await axios.get(`${apiServerAddress}/v1/watches/search?query=${query.value}&page=${(pageRequestValue - 1)}&sortBy=name`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        isLoading.value = false;
        watches.value = response.data.content;
        totalPages.value = response.data.totalPages;
        totalWatchesCount.value = response.data.totalElements;
        actualPage.value = pageRequestValue;

        for(let watch of watches.value){
            watchesObjects.value.push({
                "reference": watch[0],
                "name": watch[1],
                "isLimitedTo": watch[2]
            });
        }

    } catch (error) {
        console.error('Failed to fetch watches:', error);
    }
}

async function fetchBrandsNames() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/brands/all`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        brands.value = response.data;
    } catch (error) {
        console.error('Failed to fetch brand names:', error);
    }
}

async function fetchSelectedBrandsFamilies() {
    families.value = []
    for (let brand of brandsSelected.value) {
        try {
            const response = await axios.get(`${apiServerAddress}/v1/brands/${brand}/families/all`, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

            families.value = families.value.concat(response.data);
            //console.log(families.value)
        } catch (error) {
            console.error('Failed to fetch families:', error);
        }
    }
}

async function fetchWatchMaterials() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/materials`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        materials.value = response.data;
    } catch (error) {
        console.error('Failed to fetch materials:', error);
    }
}

function removeArrayItem(array, element) {
    return array.filter(item => item !== element);
}

function handleBrandSelection(brandName) {
    if (!brandsSelected.value.includes(brandName)) {
        brandsSelected.value.push(brandName);

        // if(!query.value){
        //     fetchSearchedWatchesByBrand();
        // }
    } else {
        brandsSelected.value = removeArrayItem(brandsSelected.value, brandName);
        fetchSelectedBrandsFamilies()
    }
    fetchSelectedBrandsFamilies();
}

onMounted(async () => {
    fetchSearchedWatches(1);
    fetchBrandsNames();
    fetchWatchMaterials();
});
</script>
