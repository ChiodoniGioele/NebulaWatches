<template>
    <div class="grid lg:grid-cols-5 min-h-screen">
        <Sidebar class="hidden lg:block" />

        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col">

            <div class="px-4 py-6 lg:px-8">

                <div class="flex w-full items-center gap-1.5">
                    <Input @click="router.push('/search')" @change="router.push('/search')" id="email" type="text"
                        placeholder="Search a watch ..." />
                    <Button @click="router.push('/search')" type="submit" class="bg-blue-600"> Search </Button>

                </div>

                <div class="mt-5 flex items-center">
                    <div class="w-full flex gap-7 items-center">
                        <div>
                            <Button @click="$router.back()" variant="secondary">
                                <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
                                <!-- Go back -->
                            </Button>
                        </div>

                    </div>
                    <div class="flex gap-2 w-auto" v-if="!selector">
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
                                            <Select v-model="selectedStatus" @change="testing">
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
                                    <div class="w-3/4">
                                        <Input v-model="selectedQuantity" type="number" placeholder="Quantity" />
                                    </div>
                                    <div class="w-3/4">
                                        <Input v-model="buyPrice" type="number" placeholder="Bought for" />
                                    </div>
                                    <div class="w-3/4" v-if="selectedStatus == 'Sold'">
                                        <Input v-model="sellPrice" type="number" placeholder="Sold for" />
                                    </div>
                                    <div>Selected: {{ selectedStatus }}</div>

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
                    </div>
                </div>


                <div class="mt-12 flex flex-wrap gap-5">
                    <div class="flex gap-20">
                        <img class="h-[300px] border border-gray-200 rounded-lg" :src="watchImage" alt="Watch Image" />
                        <div class="pt-2">
                            <ScrollArea class="h-[70vh] p-7">
                                <div class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Reference</h1>
                                    {{ watch.reference }}
                                </div>
                                <div class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Name</h1>
                                    {{ watch.name }}
                                </div>
                                <div class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Description</h1>
                                    {{ watch.description }}
                                </div>
                                <div class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Price</h1>
                                    {{ watch.retailPrice }} Fr.
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
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import test from 'node:test'

const router = useRouter();
const route = useRoute();
const reference = route.params.reference;
const watch = ref({});
const watchImage = ref();
const selectedStatus = ref('Owned');
const selectedQuantity = ref();
const buyPrice = ref();
const sellPrice = ref();
const storageSuccesfull = ref(false);
const email = ref('');
const selector = route.params.sel;

async function fetchWatch() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/storage/getCustomInfo/${reference}`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        watch.value = response.data;
    } catch (error) {
        console.error('Failed to fetch custom watch:', error);
    }
}

async function fetchWatchImage() {
    try {
        const endpoint = `${apiServerAddress}/v1/storage/custom/` + reference + '/image'
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


async function addToStorage() {
    const newStorage = {
        user_email: email.value,
        watch_reference: "",
        status: selectedStatus.value,
        quantity: selectedQuantity.value,
        custom_watch_reference: reference,
        buy_price: buyPrice.value,
        sell_price: 0
    };
    if (sellPrice.value == undefined) {
        newStorage.sell_price = 0;
    } else {
        newStorage.sell_price = sellPrice.value;
    }

    try {
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


onMounted(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const mail = payload.sub;
    email.value = mail;

    fetchWatch();
    fetchWatchImage();
});

const testing = () => {
    console.log('Selected status changed to:', selectedStatus.value);
};

</script>
