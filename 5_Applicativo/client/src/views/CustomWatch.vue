<template>
    <div class="flex h-screen"> 
        <Sidebar />
        <div class="flex flex-col w-full" > 
            <div class="px-8 py-6">

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
                        <Popover ref="popover" :open="restOpen" @update:open="setNotVisible">
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
                                                        <!--<SelectItem value="Shipped">Shipped</SelectItem>-->
                                                    </SelectGroup>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                    </div>
                                    <div class="w-3/4">
---------------------------                                        <Input v-model="selectedQuantity" type="number" placeholder="Quantity" />
                                    </div>
                                    <div class="w-3/4">
                                        <Input v-model="buyPrice" type="number" placeholder="Bought for" />
                                    </div>
                                    <div class="w-3/4">
                                        <Input v-model="purchaseDate" type="date" placeholder="Purchase Date" />
                                    </div>
                                    <div class="w-3/4" v-if="selectedStatus == 'Sold'">
                                        <Input v-model="sellPrice" type="number" placeholder="Sold for" />
                                    </div>
                                    <div class="w-3/4" v-if="selectedStatus == 'Sold'">
                                        <Input v-model="sellDate" type="date" placeholder="Sell Date" />
                                    </div>
                                    <div class="w-3/4" v-if="selectedStatus == 'Sold'">
                                        <Select v-model="clientId">
                                            <SelectTrigger>
                                                <SelectValue placeholder="Select client" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Clients</SelectLabel>
                                                    <SelectItem v-for="client in clients" :key="client.id"
                                                        :value="client.id">{{ client.name }}</SelectItem>
                                                </SelectGroup>
                                            </SelectContent>
                                        </Select>
                                    </div>
                                    <div class="w-3/4" v-if="selectedStatus == 'Sold'">
                                        <Select v-model="teamId">
                                            <SelectTrigger>
                                                <SelectValue placeholder="Select team member" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Members</SelectLabel>
                                                    <SelectItem v-for="team in teams" :key="team.id" :value="team.id">{{team.name }}</SelectItem>
                                                </SelectGroup>
                                            </SelectContent>
                                        </Select>
                                    </div>

                                    <Alert variant="destructive" v-if="invalidData">
                                        <AlertCircle class="w-4 h-4" />
                                        <AlertTitle>Error</AlertTitle>
                                        <AlertDescription>
                                            Please insert valid data!
                                        </AlertDescription>
                                    </Alert>
                                    <Alert variant="destructive" v-if="invalidDate">
                                        <AlertCircle class="w-4 h-4" />
                                        <AlertTitle>Error</AlertTitle>
                                        <AlertDescription>
                                            The sell date must be after the purchase date!
                                        </AlertDescription>
                                    </Alert>

                                    <Alert variant="success" v-if="storageSuccesfull">
                                        <CheckCircle class="w-4 h-4" />
                                        <AlertTitle>Success</AlertTitle>
                                        <AlertDescription>
                                            Watch added to storage!
                                        </AlertDescription>
                                    </Alert>
                                    <div
                                        class="flex items-center text-gray-500 border border-gray-300 rounded-md p-2 text-sm">
                                        <Info class="w-4 h-4 mr-2" />
                                        <span>All fields are required!</span>
                                    </div>
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
    <AlertDialog :open="showDialogPrice" onOpenChange="">
        <AlertDialogContent>
            <AlertDialogHeader>
                <AlertDialogTitle>Attention!</AlertDialogTitle>
                <AlertDialogDescription>
                    The sell price is lower than the buy price! Is this correct?
                </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
                <AlertDialogCancel @click="res()">Cancel</AlertDialogCancel>
                <AlertDialogAction @click="redo()">Yes</AlertDialogAction>
            </AlertDialogFooter>
        </AlertDialogContent>
    </AlertDialog>
    <Chat />
</template>

<script setup>
import Chat from '@/components/Chat.vue'
import Sidebar from '@/components/Sidebar.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { ScrollArea } from '@/components/ui/scroll-area'
import { apiServerAddress } from '@/main.ts'
import { CheckCircle } from 'lucide-vue-next';
import { AlertCircle } from 'lucide-vue-next'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { Info } from 'lucide-vue-next'
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

import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from '@/components/ui/alert-dialog'

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
const clientId = ref();
const teamId = ref();
const purchaseDate = ref();
const sellDate = ref();
const storageSuccesfull = ref(false);
const invalidData = ref(false);
const invalidDate = ref(false);
const showDialogPrice = ref(false);
const email = ref('');
const selector = route.params.sel;
const clients = ref([]);
const teams = ref([]);
const assertClient = ref(false);
const restOpen = ref(false);

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
        buyPrice: 0,
        sellPrice: 0,
        clientId: null,
        teamId: null,
        purchaseDate: null,
        sellDate: null
    };
    console.log(selectedQuantity.value);
    invalidData.value = false;
    storageSuccesfull.value = false;
    if (isValidPrice(buyPrice.value)) {
        newStorage.buyPrice = buyPrice.value;
    } else {
        invalidData.value = true;
    }
    if (isValidPrice(selectedQuantity.value)) {
        newStorage.selectedQuantity = selectedQuantity.value;
    } else {
        invalidData.value = true;
    }
    if (sellPrice.value != undefined && isValidPrice(sellPrice.value)) {
        newStorage.sellPrice = sellPrice.value;
    } else {
        if (sellPrice.value != undefined) {
            invalidData.value = true;
        }
    }
    if (teamId.value != undefined) {
        newStorage.teamId = teamId.value;
    }
    if (clientId.value != undefined) {
        newStorage.clientId = clientId.value;
    }

    if (purchaseDate.value != undefined && isValidDate(purchaseDate.value)) {
        newStorage.purchaseDate = purchaseDate.value;
    } else {
        invalidData.value = true;
    }

    if (sellDate.value != undefined && isValidDate(sellDate.value)) {
        newStorage.sellDate = sellDate.value;
    } else {
        if (sellDate.value != undefined) {
            invalidData.value = true;
        }
    }

    if (!invalidData.value) {
        var flag = true;
        var flag2 = true;
        if (newStorage.status == "Sold") {
            flag = soldAfterPurchase(newStorage.sellDate, newStorage.purchaseDate);
            flag2 = soldForMoreOrEqual(newStorage.sellPrice, newStorage.buyPrice);
        }
        if (flag) {
            invalidDate.value = false;
            if (flag2 || assertClient.value) {
                showDialogPrice.value = false;
                assertClient.value = false;
                try {
                    const response = await axios.post(`${apiServerAddress}/v1/storage/addWatchToStorage`, newStorage, {
                        headers: {
                            Authorization: 'Bearer ' + localStorage.getItem('token'),
                        },
                    });
                    storageSuccesfull.value = true;
                    console.log('Custom watch added to storage. ', response.data);
                    buyPrice.value = null;
                    selectedQuantity.value = null;
                    purchaseDate.value = null;
                    sellPrice.value = null;
                    sellDate.value = null;
                } catch (error) {
                    console.log(newStorage)
                    console.error('Failed to add custom watch to storage:', error);
                }
            } else {
                showDialogPrice.value = true;
            }

        } else {
            invalidDate.value = true;
        }
    }
}

async function getClients(email) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/clients/all/${email}`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        clients.value = response.data;
    } catch (error) {
        console.error('Failed to fetch clients:', error);
    }
}

async function getTeam(email) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/team/getTeam/${email}`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        teams.value = response.data;
    } catch (error) {
        console.error('Failed to fetch clients:', error);
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
    getClients(email.value);
    getTeam(email.value);
});


//Utils
function isNullOrEmpty(str) {
    return !str || str.trim() === '';
}
function isValidDate(date) {
    const dateFormat = /^\d{4}-\d{2}-\d{2}$/;
    return dateFormat.test(date);
}
function soldAfterPurchase(dateSold, datePurchase) {
    const milliseconds1 = Date.parse(dateSold);
    const milliseconds2 = Date.parse(datePurchase);
    return milliseconds1 >= milliseconds2;
}
function soldForMoreOrEqual(priceSold, pricePurchase) {
    return priceSold >= pricePurchase;
}
function isValidPrice(price) {
    return !isNaN(price) && price >= 0 && price != '';;
}
function redo() {
    assertClient.value = true;
    addToStorage();
}
function res() {
    showDialogPrice.value = false;
}
function setNotVisible() {
    restOpen.value = !restOpen.value;
    if(!restOpen.value){
        invalidData.value = false;
        storageSuccesfull.value = false;
        invalidDate.value = false;
        buyPrice.value = null;
        selectedQuantity.value = null;
        purchaseDate.value = null;
        sellPrice.value = null;
        sellDate.value = null;
    }
}
</script>
