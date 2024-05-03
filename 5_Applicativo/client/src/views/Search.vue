<template>
    <div class="flex h-screen">
        <Sidebar />
        <div class="flex flex-col w-full">
            <div class="px-8 py-6">

                <div class="flex w-full items-center gap-1.5">
                    <Input class="border-stone-900" @change="fetchFilteredWatches(1)" v-model="query" id="email"
                        type="text" placeholder="Search a watch ..." />
                    <Button @click="fetchFilteredWatches(1)" type="submit"> Search </Button>
                </div>

                <div class="mt-5 flex gap-7 items-center">
                    <div>
                        <Button @click="$router.back()" variant="secondary"> 
                            <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" alt="go back logo"/>
                            <!-- Go back -->
                        </Button>
                    </div>

                </div>

                <div class="mt-3 flex gap-2 flex-wrap">
                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Brands ({{
                        brands.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="brand in brands" :key="brand.name" class="flex items-center space-x-2 mb-3">

                                    <Checkbox v-if="brandsSelected.includes(brand)" :id="`${brand.name}-checkbox`"
                                        @click="handleBrandSelection(brand)" checked />
                                    <Checkbox v-else :id="`${brand.name}-checkbox`"
                                        @click="handleBrandSelection(brand)" />

                                    <label :for="`${brand.name}-checkbox`"
                                        class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                                        {{ brand.name }}
                                    </label>
                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                    <div v-if="families.length > 0">
                        <Popover>
                            <PopoverTrigger>
                                <Button class="border-stone-900" variant="outline"> <span class="font-normal">Families
                                        ({{ families.length }})</span> </Button>
                            </PopoverTrigger>
                            <PopoverContent class="w-auto">
                                <ScrollArea class="max-h-[40vh] p-2">
                                    <div v-for="family in families" :key="family.id"
                                        class="flex items-center space-x-2 mb-3">

                                        <Checkbox v-if="familiesSelected.includes(family)" :id="`${family.id}-checkbox`"
                                            @click="handleBrandSelection(brand)" checked />
                                        <Checkbox v-else :id="`${family.name}-checkbox`"
                                            @click="handleFamilySelection(family)" />

                                        <label :for="`${family.name}-checkbox`"
                                            class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                                            {{ family.brand.name }} - {{ family.name }}
                                        </label>

                                    </div>
                                </ScrollArea>
                            </PopoverContent>
                        </Popover>

                    </div>

                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Materials ({{
                        materials.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="material in materials" :key="material.name"
                                    class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="materialsSelected.includes(material)"
                                        :id="`${material.name}-checkbox`"
                                        @click="handleWatchMaterialsSelection(material)" checked />
                                    <Checkbox v-else :id="`${material.name}-checkbox`"
                                        @click="handleWatchMaterialsSelection(material)" />

                                    <label :for="`${material.name}-checkbox`"
                                        class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                                        {{ material.name }}
                                    </label>

                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Case shapes
                                    ({{ shapes.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="max-h-[40vh] p-2"> <!-- Set max-height instead of fixed height -->
                                <div v-for="shape in shapes" :key="shape.name" class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="shapesSelected.includes(shape)" :id="`${shape.name}-checkbox`"
                                        @click="handleShapesSelection(shape)" checked />
                                    <Checkbox v-else :id="`${shape.name}-checkbox`"
                                        @click="handleShapesSelection(shape)" />


                                    <label :for="`${shape.name}-checkbox`"
                                        class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                                        {{ shape.name }}
                                    </label>

                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>


                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Indexes ({{
                        indexes.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="max-h-[40vh] p-2"> <!-- Set max-height instead of fixed height -->
                                <div v-for="indexes_name in indexes" :key="indexes_name.name"
                                    class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="indexesSelected.includes(indexes_name)"
                                        :id="`${indexes_name.name}-checkbox`"
                                        @click="handleIndexesSelection(indexes_name)" checked />
                                    <Checkbox v-else :id="`${indexes_name.name}-checkbox`"
                                        @click="handleIndexesSelection(indexes_name)" />

                                    <label :for="`${indexes_name.name}-checkbox`"
                                        class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                                        {{ indexes_name.name }}
                                    </label>
                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>


                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Dial colors
                                    ({{ dialColors.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="dialColor in dialColors" :key="dialColor.name"
                                    class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="dialColorselected.includes(dialColor)"
                                        :id="`${dialColor.name}-checkbox`" @click="handleDialColorsSelection(dialColor)"
                                        checked />
                                    <Checkbox v-else :id="`${dialColor.name}-checkbox`"
                                        @click="handleDialColorsSelection(dialColor)" />

                                    <label :for="`${dialColor.name}-checkbox`"
                                        class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                                        {{ dialColor.name }}
                                    </label>

                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Dial finishes
                                    ({{ dialFinishes.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="max-h-[40vh] p-2"> <!-- Set max-height instead of fixed height -->
                                <div v-for="dialFinish in dialFinishes" :key="dialFinish.name"
                                    class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="dialFinishesSelected.includes(dialFinish)"
                                        :id="`${dialFinish.name}-checkbox`"
                                        @click="handleDialFinishesSelection(dialFinish)" checked />
                                    <Checkbox v-else :id="`${dialFinish.name}-checkbox`"
                                        @click="handleDialFinishesSelection(dialFinish)" />

                                    <label :for="`${dialFinish.name}-checkbox`"
                                        class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
                                        {{ dialFinish.name }}
                                    </label>

                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>


                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Diameter
                                    mm</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div class="flex gap-4 p-3 text-sm">
                                <div>
                                    <Label for="min_diameter">Min.</Label>
                                    <Input class="mt-4" id="min_diameter" type="number" min="0" v-model="minDiameter"
                                        @change="fetchFilteredWatches(1);" placeholder="Any" />
                                </div>
                                <div>
                                    <Label for="max_diameter">Max.</Label>
                                    <Input class="mt-4" id="max_diameter" type="number" min="0" v-model="maxDiameter"
                                        @change="fetchFilteredWatches(1);" placeholder="Any" />
                                </div>
                            </div>

                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Water
                                    resistance m</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div class="flex gap-4 p-3 text-sm">
                                <div>
                                    <Label for="min_water_resistance">Min.</Label>
                                    <Input class="mt-4" id="min_water_resistance" type="number" min="0"
                                        v-model="minWaterResistance" @change="fetchFilteredWatches(1);"
                                        placeholder="Any" />
                                </div>
                                <div>
                                    <Label for="max_water_resistance">Max.</Label>
                                    <Input class="mt-4" id="max_water_resistance" type="number" min="0"
                                        v-model="maxWaterResistance" @change="fetchFilteredWatches(1);"
                                        placeholder="Any" />
                                </div>
                            </div>

                        </PopoverContent>
                    </Popover>

                    <!-- <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Production time</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div class="flex gap-4 p-3 text-sm">
                                <div>
                                    <Label for="min_production_year">From year</Label>
                                    <Input class="mt-4" id="min_production_year" type="number" min="0" v-model="minProductionTimeYear" placeholder="Any" />
                                </div>
                                <div>
                                    <Label for="max_production_year">Until year</Label>
                                    <Input class="mt-4" id="max_production_year" type="number" min="0" v-model="maxProductionTimeYear" placeholder="Any" />
                                </div>
                            </div>
                            
                        </PopoverContent>
                    </Popover> -->

                    <Popover>
                        <PopoverTrigger>
                            <Button class="border-stone-900" variant="outline"> <span class="font-normal">Price</span>
                            </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div class="flex gap-4 p-3 text-sm">
                                <div>
                                    <Label for="min_price">Min.</Label>
                                    <Input class="mt-4" id="min_price" type="number" min="0" v-model="minPrice"
                                        @change="fetchFilteredWatches(1);" placeholder="Any" />
                                </div>
                                <div>
                                    <Label for="max_price">Max.</Label>
                                    <Input class="mt-4" id="max_price" type="number" min="0" v-model="maxPrice"
                                        @change="fetchFilteredWatches(1);" placeholder="Any" />
                                </div>
                            </div>

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

                <div class="mt-12 px-1 w-full gap-7 flex items-center justify-center max-w-full">
                    <Pagination v-slot="{ page }" :total="totalPages * 10" :sibling-count="3" show-edges
                        :default-page="1">
                        <PaginationList v-slot="{ items }" class="flex items-center gap-1 w-full">
                            <PaginationFirst @click="fetchFilteredWatches(1)" />
                            <PaginationPrev @click="fetchFilteredWatches(actualPage - 1)" />

                            <template v-for="(item, index) in items">
                                <PaginationListItem v-if="item.type === 'page'" :key="index" :value="item.value"
                                    as-child>
                                    <Button class="w-10 h-10 p-0" :variant="item.value === page ? 'default' : 'outline'"
                                        @click="fetchFilteredWatches(item.value)">
                                        {{ item.value }}
                                    </Button>
                                </PaginationListItem>
                                <PaginationEllipsis v-else :key="item.type" :index="index" />
                            </template>

                            <PaginationNext @click="fetchFilteredWatches(actualPage + 1)" />
                            <PaginationLast @click="fetchFilteredWatches(totalPages)" />
                        </PaginationList>
                    </Pagination>
                </div>


            </div>
        </div>
    </div>
    <Chat />
</template>

<script setup>
import Chat from '@/components/Chat.vue'
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
const buttonClicked = ref(false);

const brands = ref([])
const brandsSelected = ref([])

const families = ref([])
const familiesSelected = ref([])

const materials = ref([])
const materialsSelected = ref([])

const shapes = ref([])
const shapesSelected = ref([])

const indexes = ref([])
const indexesSelected = ref([])

const dialColors = ref([])
const dialColorselected = ref([])

const dialFinishes = ref([])
const dialFinishesSelected = ref([])

// const minDiameter = ref(0)
// const maxDiameter = ref(1000)

// const minWaterResistance = ref(0)
// const maxWaterResistance = ref(30000)

// const minProductionTimeYear = ref(0)
// const maxProductionTimeYear = ref(2100)

// const minPrice = ref(0)
// const maxPrice = ref(100000000)

const minDiameter = ref(null)
const maxDiameter = ref(null)

const minWaterResistance = ref(null)
const maxWaterResistance = ref(null)

const minProductionTimeYear = ref(null)
const maxProductionTimeYear = ref(null)

const minPrice = ref(null)
const maxPrice = ref(null)

let previousSearchParams = null;

/*async function fetchSearchedWatches(pageRequestValue) {
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

        for (let watch of watches.value) {
            watchesObjects.value.push({
                "reference": watch[0],
                "name": watch[1],
                "isLimitedTo": watch[2]
            });
        }

    } catch (error) {
        console.error('Failed to fetch watches:', error);
    }
}*/
async function fetchFilteredWatches(pageRequestValue) {
    watchesObjects.value = [];
    try {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        isLoading.value = true;

        const params = {
            query: query.value,
            brands: brandsSelected.value.map(brand => brand.name).join(','), // Serialize brands array
            families: familiesSelected.value.map(family => family.id).join(','), // Assuming family IDs are used

            watchShapes: shapesSelected.value.map(shape => shape.name).join(','),
            watchIndexes: indexesSelected.value.map(indexess => indexess.name).join(','),
            dialFinishes: dialFinishesSelected.value.map(dialFinish => dialFinish.name).join(','),
            dialColors: dialColorselected.value.map(dialColor => dialColor.name).join(','),

            materials: materialsSelected.value.map(material => material.name).join(','),

            minDiameter: minDiameter.value,
            maxDiameter: maxDiameter.value,

            minWaterResistance: minWaterResistance.value,
            maxWaterResistance: maxWaterResistance.value,

            minProductionTimeYear: minProductionTimeYear.value,
            maxProductionTimeYear: maxProductionTimeYear.value,

            minPrice: minPrice.value,
            maxPrice: maxPrice.value,

            page: pageRequestValue - 1,
            sortBy: 'name'
        };
        localStorage.setItem('search', JSON.stringify(params));
        previousSearchParams = params;

        const response = await axios.get(`${apiServerAddress}/v1/watches/filter`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
            params: params // Pass the parameters object
        });

        isLoading.value = false;
        watches.value = response.data.content;

        console.log(response.data.content);

        totalPages.value = response.data.totalPages;
        totalWatchesCount.value = response.data.totalElements;
        actualPage.value = pageRequestValue;


        for (let watch of watches.value) {
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
            const response = await axios.get(`${apiServerAddress}/v1/brands/${brand.name}/families/all`, {
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

async function fetchWatchShapes() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/watch_shapes`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        shapes.value = response.data;
    } catch (error) {
        console.error('Failed to fetch materials:', error);
    }
}

async function fetchWatchIndexes() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/watch_indexes`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        indexes.value = response.data;
    } catch (error) {
        console.error('Failed to fetch materials:', error);
    }
}

async function fetchDialColors() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/dial_colors`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        dialColors.value = response.data;
    } catch (error) {
        console.error('Failed to fetch materials:', error);
    }
}

async function fetchDialFinishes() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/dial_finishes`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        dialFinishes.value = response.data;
    } catch (error) {
        console.error('Failed to fetch materials:', error);
    }
}


function removeArrayItem(array, element) {
    return array.filter(item => item !== element);
}

function handleBrandSelection(brand) {
    if (!brandsSelected.value.includes(brand)) {
        brandsSelected.value.push(brand);
    } else {
        brandsSelected.value = removeArrayItem(brandsSelected.value, brand);
        fetchSelectedBrandsFamilies()
    }
    fetchSelectedBrandsFamilies();
    fetchFilteredWatches(1);
}

function handleFamilySelection(family) {
    if (!familiesSelected.value.includes(family)) {
        familiesSelected.value.push(family);
    } else {
        familiesSelected.value = removeArrayItem(familiesSelected.value, family);
    }
    fetchFilteredWatches(1);
}

function handleWatchMaterialsSelection(material) {
    if (!materialsSelected.value.includes(material)) {
        materialsSelected.value.push(material);
    } else {
        materialsSelected.value = removeArrayItem(materialsSelected.value, material);
    }
    fetchFilteredWatches(1);
}

function handleShapesSelection(shape) {
    if (!shapesSelected.value.includes(shape)) {
        shapesSelected.value.push(shape);
    } else {
        shapesSelected.value = removeArrayItem(shapesSelected.value, shape);
    }
    fetchFilteredWatches(1);
}

function handleIndexesSelection(indexes) {
    if (!indexesSelected.value.includes(indexes)) {
        indexesSelected.value.push(indexes);
    } else {
        indexesSelected.value = removeArrayItem(indexesSelected.value, indexes);
    }
    fetchFilteredWatches(1);
}

function handleDialColorsSelection(dialColor) {
    if (!dialColorselected.value.includes(dialColor)) {
        dialColorselected.value.push(dialColor);
    } else {
        dialColorselected.value = removeArrayItem(dialColorselected.value, dialColor);
    }
    fetchFilteredWatches(1);
}

function handleDialFinishesSelection(dialFinish) {
    if (!dialFinishesSelected.value.includes(dialFinish)) {
        dialFinishesSelected.value.push(dialFinish);
    } else {
        dialFinishesSelected.value = removeArrayItem(dialFinishesSelected.value, dialFinish);
    }
    fetchFilteredWatches(1);
}

function restorePreviousSearch() {
    fetchLastSearch(JSON.parse(localStorage.getItem('search')), 1);
}

function onPageLoad() {
    window.addEventListener('popstate', restorePreviousSearch);
}

function onPageUnload() {
    window.removeEventListener('popstate', restorePreviousSearch);
}

window.addEventListener('load', onPageLoad);

window.addEventListener('unload', onPageUnload);

async function fetchLastSearch(params, page) {
    const testvar = params;
    watchesObjects.value = [];
    try {
        isLoading.value = true;
        const response = await axios.get(`${apiServerAddress}/v1/watches/filter`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
            params: testvar
        });

        isLoading.value = false;
        watches.value = response.data.content;

        totalPages.value = response.data.totalPages;
        totalWatchesCount.value = response.data.totalElements;
        actualPage.value = page;


        for (let watch of watches.value) {
            watchesObjects.value.push({
                "reference": watch[0],
                "name": watch[1],
                "isLimitedTo": watch[2]
            });
        }
    } catch (error) {
        console.error('Failed to fetch last search:', error);
    }
}

function setNullStorage() {
    localStorage.removeItem('search');
}


onMounted(async () => {
    document.getElementById('email').focus();
    fetchBrandsNames();
    fetchWatchMaterials();
    fetchWatchShapes();
    fetchWatchIndexes();
    fetchDialColors();
    fetchDialFinishes();
    if (localStorage.getItem('search') !== null) {
        fetchLastSearch(JSON.parse(localStorage.getItem('search')), 1);
    } else {
        fetchFilteredWatches(1);
    }


});
</script>
