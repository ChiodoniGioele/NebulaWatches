<template>
    <div class="flex h-screen">
        <Sidebar />
        <div class="flex flex-col w-full">
            <div class="px-8 py-6">
                <div class="flex w-full items-center gap-1.5">
                    <Input class="border-stone-900" @click="router.push('/search')" @change="router.push('/search')"
                        id="email" type="text" placeholder="Search a watch ..." />
                    <Button @click="router.push('/search')" type="submit">
                        Search
                    </Button>
                    <Button variant="outline" @click="toFavourite">
                        <img class="m-2 h-[25px] w-[25px]" src="@/assets/favourites.png" alt="favourites" />
                        <p class="m-2">Favourites</p>
                    </Button>
                </div>

                <div class="mt-5 flex items-center">
                    <div class="w-full flex gap-7 items-center">
                        <div>
                            <Button @click="$router.back()" variant="secondary">
                                <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" alt="go back" />
                                <!-- Go back -->
                            </Button>
                        </div>
                        <div class="flex gap-2">
                            <Badge variant="outline">brand</Badge>
                            <h1 class="font-bold">{{ watch.brand }}</h1>
                        </div>
                        <div class="flex gap-2">
                            <Badge variant="outline">family</Badge>
                            <h1 class="font-bold">{{ watch.family }}</h1>
                        </div>
                    </div>
                    <div class="flex gap-2 w-auto">
                        <Popover ref="popover" v-if="!selector" :open="restOpen" @update:open="setNotVisible">
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
                                        <Input v-model="selectedQuantity" type="number" placeholder="Quantity" />
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
                                                    <SelectItem v-for="team in teams" :key="team.id" :value="team.id">
                                                        {{ team.name }}</SelectItem>
                                                </SelectGroup>
                                            </SelectContent>
                                        </Select>
                                    </div>

                                    <Alert variant="success" v-if="storageSuccesfull">
                                        <CheckCircle class="w-4 h-4" />
                                        <AlertTitle>Success</AlertTitle>
                                        <AlertDescription>
                                            Watch added to storage!
                                        </AlertDescription>
                                    </Alert>
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
                                    <div
                                        class="flex items-center text-gray-500 border border-gray-300 rounded-md p-2 text-sm">
                                        <Info class="w-4 h-4 mr-2" />
                                        <span>All fields are required!</span>
                                    </div>
                                    <Button variant="outline" @click="addToStorage">Add to storage</Button>
                                </div>
                            </PopoverContent>
                        </Popover>
                        <Button variant="outline" @click="addOrRemoveFavourite">
                            <img v-if="!isStarClicked" class="m-5 h-[25px] w-[25px]" src="@/assets/star.png" alt="star" />
                            <img v-else class="m-5 h-[25px] w-[25px]" src="@/assets/star_full.png" alt="full star"/>
                        </Button>
                    </div>
                </div>

                <div class="mt-12 flex flex-wrap gap-5">
                    <div class="flex gap-20">

                        <div class="w-3/12">
                            <div class="w-fu">
                                <img class="w-full h-auto max-h-[40vh] object-contain border border-gray-200 rounded-lg"
                                    :src="watchImage" alt="Watch Image" />
                            </div>

                            <h1 class="font-semibold text-sm text-gray-400 mt-5">Price</h1>
                            <div v-if="prices.length > 1">
                                {{ prices[prices.length - 1] }} USD
                                <br>
                                <TooltipProvider :delayDuration="100">
                                    <Tooltip>
                                        <TooltipTrigger>
                                            <Dialog>
                                                <DialogTrigger>
                                                    <Button variant="outline" class="mt-3"> Show more </Button>
                                                </DialogTrigger>
                                                <DialogContent>
                                                    <DialogHeader>
                                                        <DialogTitle>Prices</DialogTitle>
                                                    </DialogHeader>

                                                    <WatchPricesChart :prices="prices" :labels="dates" class="mb-8" />

                                                    <TooltipProvider :delayDuration="400" class="">
                                                        <Tooltip class="">
                                                            <TooltipTrigger>

                                                                <Button variant="outline" class="w-full"
                                                                    @click="redirectWatchChartsAnalytics()">
                                                                    <div class="flex gap-2">
                                                                        <div>
                                                                            <img class="h-6"
                                                                                src="@/assets/icons/watchcharts.png" alt="watchcharts logo" />
                                                                        </div>
                                                                        <div>
                                                                            Watchcharts
                                                                        </div>
                                                                    </div>
                                                                </Button>
                                                            </TooltipTrigger>
                                                            <TooltipContent>
                                                                <p>Get market value and analytics</p>
                                                            </TooltipContent>
                                                        </Tooltip>
                                                    </TooltipProvider>
                                                </DialogContent>
                                            </Dialog>
                                        </TooltipTrigger>
                                        <TooltipContent>
                                            <WatchPricesChart :prices="prices" :labels="dates" />
                                        </TooltipContent>
                                    </Tooltip>
                                </TooltipProvider>

                            </div>
                            <div v-else>
                                <div v-if="prices.length > 0">
                                    {{ prices[0] }} USD
                                </div>
                                <div v-else>
                                    No available information
                                </div>
                            </div>

                            <h1 class="font-semibold text-sm text-gray-400 mt-5">Buy</h1>
                            <div class="flex gap-2 flex-wrap mt-3">
                                <Button variant="outline" class="block" @click="redirectWatchChartsMarketplace()">
                                    <div class="flex gap-2">
                                        <div>
                                            <img class="h-6" src="@/assets/icons/watchcharts.png" alt="watchcharts logo" />
                                        </div>
                                        <div>
                                            Watchcharts Marketplace
                                        </div>
                                    </div>
                                </Button>

                                <Button variant="outline" class="block" @click="redirectChrono24()">
                                    <div class="flex gap-2">
                                        <div>
                                            <img class="h-6" src="@/assets/icons/chrono24.png" alt="chrono24 logo"/>
                                        </div>
                                        <div>
                                            Chrono24
                                        </div>
                                    </div>
                                </Button>

                                <!-- <Button variant="outline" class="block" @click="redirectEbay()">
                                    <div class="flex gap-2">
                                        <div>
                                            <img class="h-5" src="@/assets/icons/ebay.png" />
                                        </div>

                                    </div>
                                </Button> -->
                            </div>

                        </div>



                        <div class="pt-2 w-9/12">
                            <ScrollArea class="h-[70vh] p-7 pt-0">
                                <div class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Reference</h1>
                                    {{ watch.reference }}
                                </div>
                                <div class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Name</h1>
                                    {{ watch.name }}
                                </div>
                                <div v-if="watch.nickname" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Nickname</h1>
                                    {{ watch.nickname }}
                                </div>
                                <div class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Description
                                    </h1>
                                    {{ watch.description }}
                                </div>
                                <div v-if="watch.movement" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Movement</h1>
                                    {{ watch.movement }}
                                </div>
                                <div v-if="watch.isLimitedTo != 'No'" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Limited</h1>
                                    {{ watch.isLimitedTo }}
                                </div>
                                <div v-if="watch.productionTime" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Production period
                                    </h1>
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
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Water resistance
                                    </h1>
                                    {{ watch.waterResistanceM }} m
                                </div>
                                <div v-if="watch.caseMaterial" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Case material
                                    </h1>
                                    {{ watch.caseMaterial }}
                                </div>
                                <div v-if="watch.coatingMaterial" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Coating material
                                    </h1>
                                    {{ watch.coatingMaterial }}
                                </div>
                                <div v-if="watch.bezelMaterial" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Bezel type
                                    </h1>
                                    {{ watch.bezelMaterial }}
                                </div>
                                <div v-if="watch.dialColor" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Dial color
                                    </h1>
                                    {{ watch.dialColor }}
                                </div>
                                <div v-if="watch.dialFinish" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Dial finish
                                    </h1>
                                    {{ watch.dialFinish }}
                                </div>
                                <div v-if="watch.glassMaterial" class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">
                                        Glass material
                                    </h1>
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
                                <div class="pb-4">
                                    <h1 class="font-semibold text-sm text-gray-400">Materials</h1>
                                    <ul>
                                        <li v-for="(material, index) in materialsUsedNames" :key="index">{{ material }}
                                        </li>
                                    </ul>
                                </div>
                            </ScrollArea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <Chat />
    
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
    
</template>

<script setup>
import Chat from '@/components/Chat.vue'
import Sidebar from "@/components/Sidebar.vue";
import WatchPricesChart from '@/components/WatchPricesChart.vue'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { ScrollArea } from "@/components/ui/scroll-area";
import { apiServerAddress } from "@/main.ts";
import { CheckCircle } from "lucide-vue-next";
import { AlertCircle } from 'lucide-vue-next'
import { Info } from 'lucide-vue-next'
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover";
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select";
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

import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog'

import {
    Tooltip,
    TooltipContent,
    TooltipProvider,
    TooltipTrigger
} from '@/components/ui/tooltip'

import axios from "axios";
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

const router = useRouter();
const route = useRoute();
const reference = route.params.reference;
const selector = route.params.sel;
const watch = ref({});
const watchImage = ref();
const selectedStatus = ref('Owned');
const buyPrice = ref();
const sellPrice = ref();
const selectedQuantity = ref();
const clientId = ref();
const teamId = ref();
const purchaseDate = ref();
const sellDate = ref();
const isStarClicked = ref(false);
const storageSuccesfull = ref(false);
const invalidData = ref(false);
const invalidDate = ref(false);
const showDialogPrice = ref(false);
const email = ref('');
const materialsUsedNames = ref('');
const prices = ref([]);
const dates = ref([]);
const restOpen = ref(false);

function redirectWatchChartsAnalytics() {
    window.open(`https://watchcharts.com/watches/search?q=${watch.value.reference}`, '_blank')
}

function redirectWatchChartsMarketplace() {
    window.open(`https://marketplace.watchcharts.com/listings?page=1&q=${watch.value.reference}`, '_blank')
}

function redirectChrono24() {
    window.open(`https://www.chrono24.it/search/index.htm?query=${watch.value.reference}&dosearch=true&searchexplain=1}`, '_blank')
}

function redirectEbay() {
    window.open(`https://www.ebay.com/`, '_blank')
}

function removeItemFromArray(arr, value) {
    var i = 0;
    while (i < arr.length) {
        if (arr[i] === value) {
            arr.splice(i, 1);
        } else {
            ++i;
        }
    }
    return arr;
}
const clients = ref([]);
const teams = ref([]);
const assertClient = ref(false);


async function fetchWatch() {
    try {
        const response = await axios.get(
            `${apiServerAddress}/v1/watches/${reference}`,
            {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("token"),
                },
            }
        );

        watch.value = response.data;
        materialsUsedNames.value = removeItemFromArray(watch.value.materialsUsedNames, watch.value.bezelMaterial);
        materialsUsedNames.value = removeItemFromArray(materialsUsedNames.value, watch.value.glassMaterial)
        prices.value = watch.value.prices;
        dates.value = watch.value.dates;
    } catch (error) {
        console.error("Failed to fetch families:", error);
    }
}

async function fetchWatchImage() {
    try {
        const endpoint = `${apiServerAddress}/v1/watches/` + reference + "/image";
        const response = await axios.get(endpoint, {
            responseType: "arraybuffer",
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token"),
            },
        });

        const imageBase64 = btoa(
            new Uint8Array(response.data).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                ""
            )
        );
        watchImage.value = `data:image/png;base64,${imageBase64}`;
    } catch (error) {
        watchImage.value = "@assets/no_image.png";
    }
}


async function addToStorage() {
    const newStorage = {
        user_email: email.value,
        watch_reference: reference,
        status: selectedStatus.value,
        quantity: 1,
        custom_watch_reference: "",
        buyPrice: 0,
        sellPrice: 0,
        clientId: null,
        teamId: null,
        purchaseDate: null,
        sellDate: null
    };
    invalidData.value = false;
    storageSuccesfull.value = false;
    if (isValidPrice(buyPrice.value)) {
        newStorage.buyPrice = buyPrice.value;
    } else {
        invalidData.value = true;
    }
    if (isValidPrice(selectedQuantity.value)) {
        newStorage.quantity = selectedQuantity.value;
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
                    console.log('Watch added to storage. ', response.data);
                    buyPrice.value = null;
                    selectedQuantity.value = null;
                    purchaseDate.value = null;
                    sellPrice.value = null;
                    sellDate.value = null;
                } catch (error) {
                    console.error('Failed to add watch to storage:', error);
                }
            } else {
                showDialogPrice.value = true;
            }

        } else {
            invalidDate.value = true;
        }
    }
}

async function addOrRemoveFavourite() {
    const newFavourite = {
        user_email: email.value,
        watch_reference: reference,
    };
    if (isStarClicked.value == false) {
        try {
            isStarClicked.value = !isStarClicked.value;

            const response = await axios.post(
                `${apiServerAddress}/v1/favourite/addFavouriteWatch`,
                newFavourite,
                {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                }
            );

            console.log("Watch added to favourites. ", response.data);
        } catch (error) {
            console.error("Failed to add watch to favourites:", error);
        }
    } else {
        try {
            isStarClicked.value = !isStarClicked.value;

            const response = await axios.post(
                `${apiServerAddress}/v1/favourite/removeFavouriteWatch`,
                newFavourite,
                {
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("token"),
                    },
                }
            );

            console.log("Watch removed from favourites. ", response.data);
        } catch (error) {
            console.error("Failed to remove watch from favourites:", error);
        }
    }

}

async function setIconStar() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/favourite/checkFavourite/${reference}/${email.value}`, {
            headers: {
                Authorization: "Bearer " + localStorage.getItem("token"),
            },
        }
        );
        isStarClicked.value = response.data;
    } catch (error) {
        console.error("Failed to check if watch is favorite:", error);
    }
}



async function toFavourite() {
    router.push('/favourite');
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
    setIconStar();
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
    return !isNaN(price) && price >= 0 && price != '' && price < 2000000000;

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
