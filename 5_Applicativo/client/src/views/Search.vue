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

                <div class="mt-3 flex gap-2 flex-wrap">
                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Brands ({{ brands.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="brand in brands" :key="brand.name"  class="flex items-center space-x-2 mb-3">
                                    
                                    <Checkbox v-if="brandsSelected.includes(brand)" :id="`${brand.name}-checkbox`" @click="handleBrandSelection(brand)" checked/>
                                    <Checkbox v-else :id="`${brand.name}-checkbox`" @click="handleBrandSelection(brand)"/>
                                    
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
                                       
                                        <Checkbox v-if="familiesSelected.includes(family)" :id="`${family.id}-checkbox`" @click="handleBrandSelection(brand)" checked/>
                                        <Checkbox v-else :id="`${family.name}-checkbox`" @click="handleFamilySelection(family)"/>
                                        
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
                                    <Checkbox v-if="materialsSelected.includes(material)" :id="`${material.name}-checkbox`" @click="handleWatchMaterialsSelection(material)" checked/>
                                    <Checkbox v-else :id="`${material.name}-checkbox`" @click="handleWatchMaterialsSelection(material)"/>
                                    
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

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Case shapes ({{ shapes.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="shape in shapes" :key="shape.name" class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="shapesSelected.includes(shape)" :id="`${shape.name}-checkbox`" @click="handleShapesSelection(shape)" checked/>
                                    <Checkbox v-else :id="`${shape.name}-checkbox`" @click="handleShapesSelection(shape)"/>
                                    
                                    
                                    <label
                                    :for="`${shape.name}-checkbox`"
                                    class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                    >
                                    {{ shape.name }}
                                    </label>
                                    
                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Indexes ({{ indexes.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="indexes_name in indexes" :key="indexes_name.name" class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="indexesSelected.includes(indexes_name)" :id="`${indexes_name.name}-checkbox`" @click="handleIndexesSelection(indexes_name)" checked/>
                                    <Checkbox v-else :id="`${indexes_name.name}-checkbox`" @click="handleIndexesSelection(indexes_name)"/>
                                    

                                    <label
                                    :for="`${indexes_name.name}-checkbox`"
                                    class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                    >
                                    {{ indexes_name.name }}
                                    </label>
                                    
                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Dial colors ({{ dialColors.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="dialColor in dialColors" :key="dialColor.name" class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="dialColorselected.includes(dialColor)" :id="`${dialColor.name}-checkbox`" @click="handleDialColorsSelection(dialColor)" checked/>
                                    <Checkbox v-else :id="`${dialColor.name}-checkbox`" @click="handleDialColorsSelection(dialColor)"/>
                                    
                                    <label
                                    :for="`${dialColor.name}-checkbox`"
                                    class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                    >
                                    {{ dialColor.name }}
                                    </label>
                                    
                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Dial finishes ({{ dialFinishes.length }})</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <ScrollArea class="h-[40vh] p-2">
                                <div v-for="dialFinish in dialFinishes" :key="dialFinish.name" class="flex items-center space-x-2 mb-3">
                                    <Checkbox v-if="dialFinishesSelected.includes(dialFinish)" :id="`${dialFinish.name}-checkbox`" @click="handleDialFinishesSelection(dialFinish)" checked/>
                                    <Checkbox v-else :id="`${dialFinish.name}-checkbox`" @click="handleDialFinishesSelection(dialFinish)"/>
                                    
                                    <label
                                    :for="`${dialFinish.name}-checkbox`"
                                    class="text-sm leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                    >
                                    {{ dialFinish.name }}
                                    </label>
                                    
                                </div>
                            </ScrollArea>
                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Diameter mm</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div class="flex gap-4 p-3 text-sm">
                                <div>
                                    <Label for="min_diameter">Min.</Label>
                                    <Input class="mt-4" id="min_diameter" type="number" min="0" placeholder="Any" />
                                </div>
                                <div>
                                    <Label for="max_diameter">Max.</Label>
                                    <Input class="mt-4" id="max_diameter" type="number" min="0" placeholder="Any" />
                                </div>
                            </div>
                            
                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Water resistance m</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div class="flex gap-4 p-3 text-sm">
                                <div>
                                    <Label for="min_diameter">Min.</Label>
                                    <Input class="mt-4" id="min_diameter" type="number" min="0" placeholder="Any" />
                                </div>
                                <div>
                                    <Label for="max_diameter">Max.</Label>
                                    <Input class="mt-4" id="max_diameter" type="number" min="0" placeholder="Any" />
                                </div>
                            </div>
                            
                        </PopoverContent>
                    </Popover>

                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Production time</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div class="flex gap-4 p-3 text-sm">
                                <div>
                                    <Label for="min_diameter">From year</Label>
                                    <Input class="mt-4" id="min_diameter" type="number" min="0" placeholder="Any" />
                                </div>
                                <div>
                                    <Label for="max_diameter">Until year</Label>
                                    <Input class="mt-4" id="max_diameter" type="number" min="0" placeholder="Any" />
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

const shapes = ref([])
const shapesSelected = ref([])

const indexes = ref([])
const indexesSelected = ref([])

const dialColors = ref([])
const dialColorselected = ref([])

const dialFinishes = ref([])
const dialFinishesSelected = ref([])

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
async function fetchFilteredWatches(pageRequestValue) {
    watchesObjects.value = [];
    try {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        isLoading.value = true;

        // Serialize materials array into comma-separated values
        const serializedMaterials = materialsSelected.value.map(material => material.name).join(',');

        // Prepare the parameters object
        const params = {
            query: query.value,
            brands: brandsSelected.value.map(brand => brand.name).join(','), // Serialize brands array
            families: familiesSelected.value.map(family => family.id).join(','), // Assuming family IDs are used
            
            watchShapes: shapesSelected.value.map(shape => shape.name).join(','),
            watchIndexes: indexesSelected.value.map(indexess => indexess.name).join(','),
            dialFinishes: dialFinishesSelected.value.map(dialFinish => dialFinish.name).join(','),
            dialColors: dialColorselected.value.map(dialColor => dialColor.name).join(','),

            materials: serializedMaterials, // Serialized materials string
            page: pageRequestValue - 1, // Adjust page value for zero-based indexing
            sortBy: 'name' // Example sort criteria
        };

        const response = await axios.get(`${apiServerAddress}/v1/watches/filter`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
            params: params // Pass the parameters object
        });

        isLoading.value = false;
        watches.value = response.data.content;

        console.log("GIOOOO")
        console.log(response.data.content);


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



onMounted(async () => {
    document.getElementById('email').focus();
    fetchSearchedWatches(1);
    fetchBrandsNames();
    fetchWatchMaterials();
    fetchWatchShapes();
    fetchWatchIndexes();
    fetchDialColors();
    fetchDialFinishes();
    
});
</script>
