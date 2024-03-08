<template>
    <div class="grid lg:grid-cols-5 min-h-screen">
        <Sidebar class="hidden lg:block" />

        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col">

            <div class="px-4 py-6 lg:px-8">

                <div class="flex w-full items-center gap-1.5">
                    <Input @click="router.push('/search')" @change="router.push('/search')" id="email" type="text" placeholder="Search a watch ..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                    <Button variant="outline" @click="toFavourite">
                        <img class="m-2 h-[25px] w-[25px]" src="@/assets/favourites.png"/>
                        <p class="m-2">Favourites</p>
                    </Button>
                </div>

                <div class="mt-5 flex items-center">
                    <div class="w-full flex gap-7 items-center">
                        <div>
                            <Button @click="$router.back()" variant="secondary">
                                <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
                                <!-- Go back -->
                            </Button>
                        </div>
                        <div class="flex gap-2">
                            <Badge variant="outline">brand</Badge>
                            <h1 class="font-bold "> {{ watch.brand }} </h1>
                        </div>
                        <div class="flex gap-2">
                            <Badge variant="outline">family</Badge>
                            <h1 class="font-bold "> {{ watch.family }} </h1>
                        </div>
                    </div>
                    <div class="flex gap-2 w-auto">
                        <Popover ref="popover">
                            <PopoverTrigger as-child>
                                <Button variant="outline">Add to storage</Button>
                            </PopoverTrigger>
                            <PopoverContent class="w-80">
                                <div class="grid gap-4">
                                <div class="space-y-2">
                                    <h4 class="font-medium leading-none">Status</h4>
                                    <p class="text-sm text-muted-foreground">
                                    What is the status of the watch?
                                    </p>
                                </div>
                                <div class="grid gap-2">
                                    <div class="grid grid-cols-3 items-center gap-4">
                                    <Select v-model="selectedStatus">
                                        <SelectTrigger class="w-[180px]">
                                        <SelectValue placeholder="Select status" />
                                        </SelectTrigger>
                                        <SelectContent>
                                        <SelectGroup>
                                            <SelectLabel>Status</SelectLabel>
                                            <SelectItem value="Owned">Owned</SelectItem>
                                            <SelectItem value="Sold">Sold</SelectItem>
                                            <SelectItem value="Shipped">Shipped</SelectItem>
                                        </SelectGroup>
                                        </SelectContent>
                                    </Select>
                                    </div>
                                </div>
                                <Alert variant="success" v-if="storageSuccesfull">
                                    <CheckCircle class="w-4 h-4" />
                                    <AlertTitle>Success</AlertTitle>
                                    <AlertDescription>
                                        Watch added to storage!
                                    </AlertDescription>
                                </Alert>
                                <Button variant="outline" @click="addToStorage">Add to storage</Button>
                                </div>
                            </PopoverContent>
                        </Popover>
                    <Button variant="outline" @click="addOrRemoveFavourite">
                        <img v-if="!isStarClicked" class="m-5 h-[25px] w-[25px]" src="@/assets/star.png"/>
                        <img v-else class="m-5 h-[25px] w-[25px]" src="@/assets/star_full.png"/>
                    </Button>
                    </div>
                </div>
                

                <div class="mt-12 flex flex-wrap gap-5">
                    <div class="flex gap-20">
                        <img class="h-[300px] border border-gray-200 rounded-lg" :src="watchImage" alt="Watch Image" />
                        <div class="pt-2">
                            <ScrollArea class="h-[500px]">
                                <div class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Reference</h1>
                                {{ watch.reference }}
                            </div>
                            <div class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Name</h1>
                                {{ watch.name }}
                            </div>
                            <div v-if="watch.nickname" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Movement</h1>
                                {{ watch.nickname }}
                            </div>
                            <div class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Description</h1>
                                {{ watch.description }}
                            </div>
                            <div v-if="watch.movementName" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Movement</h1>
                                {{ watch.movementName }}
                            </div>
                            <div v-if="watch.isLimitedTo != 'No'" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Limited</h1>
                                {{ watch.isLimitedTo }}
                            </div>
                            <div v-if="watch.productionTime" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Production period</h1>
                                {{ watch.productionTime }}
                            </div>
                            <div v-if="watch.diameterInMm" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Diameter</h1>
                                {{ watch.diameterInMm }} mm
                            </div>
                            <div v-if="watch.heightInMm" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Height</h1>
                                {{ watch.heightInMm }} mm
                            </div>
                            <div v-if="watch.lugWidth" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Lug width</h1>
                                {{ watch.lugWidth }} mm
                            </div>
                            <div v-if="watch.waterResistanceM" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Water resistance</h1>
                                {{ watch.waterResistanceM }} m
                            </div>
                            <div v-if="watch.caseMaterial" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Case material</h1>
                                {{ watch.caseMaterial }}
                            </div>
                            <div v-if="watch.coatingMaterial" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Coating material</h1>
                                {{ watch.coatingMaterial }}
                            </div>
                            <div v-if="watch.bezelMaterial" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Bezel type</h1>
                                {{ watch.bezelMaterial }}
                            </div>
                            <div v-if="watch.dialColor" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Dial color</h1>
                                {{ watch.dialColor }}
                            </div>
                            <div v-if="watch.dialFinish" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Dial finish</h1>
                                {{ watch.dialFinish }}
                            </div>
                            <div v-if="watch.glassMaterial" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Glass material</h1>
                                {{ watch.glassMaterial }}
                            </div>
                            <div v-if="watch.isBackOpen" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Open back</h1>
                                Yes
                            </div>
                            <div v-else class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Open back</h1>
                                No
                            </div>
                            <div v-if="watch.watchShape" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Shape</h1>
                                {{ watch.watchShape }}
                            </div>
                            <div v-if="watch.watchIndexes" class="pb-4">
                                <h1 class="font-semibold text-sm text-gray-400">Indexes</h1>
                                {{ watch.watchIndexes }}
                            </div>
                            </ScrollArea>
                           
                        </div>
                    </div>
                    
                    
                </div>

            </div>


        </div>
    </div>
</template>

<script setup>
import Sidebar from '@/components/Sidebar.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { ScrollArea } from '@/components/ui/scroll-area'
import { apiServerAddress } from '@/main.ts'
import { CheckCircle } from 'lucide-vue-next';
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover'
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'

import axios from 'axios';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();
const reference = route.params.reference;
const watch = ref({});
const watchImage = ref();
const selectedStatus = ref('Owned');
const isStarClicked = ref(false);
const storageSuccesfull = ref(false);

async function fetchWatch() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/watches/${reference}`, 
        {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        watch.value = response.data;
  } catch (error) {
    console.error('Failed to fetch families:', error);
  }
}

async function fetchWatchImage() {
    try {
        const endpoint = `${apiServerAddress}/v1/watches/` + reference + '/image'
        const response = await axios.get(endpoint, {
            responseType: 'arraybuffer',
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
        });

        const imageBase64 = btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''));
        watchImage.value = `data:${response.headers['content-type']};base64,${imageBase64}`;
  } catch (error) {
    watchImage.value = "@assets/no_image.png"
  }
}

const newStorage = {
    user_email: sessionStorage.getItem('email'),
    watch_reference: reference,
    status: "",
};


async function addToStorage() {
    try {
        newStorage.status = selectedStatus.value;
        const response = await axios.post(`${apiServerAddress}/v1/storage/addWatchToStorage`, newStorage, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });
        storageSuccesfull.value = true;
        console.log('Watch added to storage. ', response.data);
    } catch (error) {
        console.error('Failed to add watch to storage:', error);
    }
}

const newFavourite = {
    user_email: sessionStorage.getItem('email'),
    watch_reference: reference,
};

async function addOrRemoveFavourite(){
    if(isStarClicked.value == false){
        try {
            isStarClicked.value = !isStarClicked.value;

            const response = await axios.post(`${apiServerAddress}/v1/favourite/addFavouriteWatch`, newFavourite, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

            console.log('Watch added to favourites. ', response.data);     
        } catch (error) {
            console.error('Failed to add watch to favourites:', error);
        }
    }else{
        try {
            isStarClicked.value = !isStarClicked.value;

            const response = await axios.post(`${apiServerAddress}/v1/favourite/removeFavouriteWatch`, newFavourite, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

            console.log('Watch removed from favourites. ', response.data);     
        } catch (error) {
            console.error('Failed to remove watch from favourites:', error);
        }
    }
    

}

async function setIconStar(){
    try {
        const response = await axios.get(`${apiServerAddress}/v1/favourite/checkFavourite/${reference}/${sessionStorage.getItem('email')}`, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            }
        );
        isStarClicked.value = response.data;
      } catch (error) {
        console.error('Failed to check if watch is favorite:', error);
      }
}

async function toFavourite(){
  router.push('/favourite');
}

onMounted(async () => {
    fetchWatch();
    fetchWatchImage();
    setIconStar();
});

</script>

