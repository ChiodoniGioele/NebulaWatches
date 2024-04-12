<template>
    <div class="text-center border border-gray-200 rounded-md min-w-[170px]  w-[18%] min-h-[200px] max-[600px]:w-[40%]">
        <div class="h-[50px]">
            <div class="pt-1 pr-1 pl-1 flex justify-between">
                <div class="self-center align-middle text-gray-400 text-sm">
                    <div class="self-center align-middle text-gray-400 text-sm invisible"
                        v-if="storage.status.name == 'Sold'">
                        aaaaaa
                    </div>
                    <Dialog v-if="storage.status.name != 'Sold'" :open="restOpen" @update:open="setNotVisible">
                        <DialogTrigger as-child>
                            <Button variant="ghost" @click="setRest()">
                                <Pencil class="size-4" />
                            </Button>
                        </DialogTrigger>
                        <DialogContent class="sm:max-w-[425px]">
                            <DialogHeader>
                                <DialogTitle>Set Watch Sold:</DialogTitle>
                                <AlertDialogDescription>
                                    Here you can edit this when you sell it:
                                    <br>
                                    • Current amount: {{ storage.quantity }}
                                    <br>
                                    • Current status: {{ storage.status.name }}
                                    <br>
                                    • Bought for: {{ storage.buyPrice }}
                                    <br>
                                    • Purchase date: <span
                                        v-if="new Date(storage.purchaseDate).toLocaleDateString('en-GB') !== '01/01/1970'">
                                        {{ new Date(storage.purchaseDate).toLocaleDateString('en-GB') }}
                                    </span>
                                    <span v-else>
                                        -
                                    </span>
                                </AlertDialogDescription>
                            </DialogHeader>

                            <div class="grid gap-4 py-4">
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="qty" class="text-right">
                                        Amount
                                    </Label>
                                    <Input v-model="editStrg.quantity" id="qty" class="col-span-3" type="number"
                                        max="{{ storage.quantity  }}" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="price" class="text-right">
                                        Price
                                    </Label>
                                    <Input v-model="editStrg.sellPrice" id="price" class="col-span-3" type="number"
                                        min="0" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="date" class="text-right">
                                        Sell Date
                                    </Label>
                                    <Input v-model="editStrg.sellDate" id="date" class="col-span-3" type="date" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label class="text-right">
                                        Client
                                    </Label>
                                    <div class="col-span-3">
                                        <Select v-model="editStrg.clientId">
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
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4" v-if="editStrg.status == 'Sold'">
                                    <Label class="text-right">
                                        Team
                                    </Label>
                                    <div class="col-span-3">
                                        <Select v-model="editStrg.teamId">
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
                                </div>
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
                            <DialogFooter>
                                <Button type="submit" @click="editStorage">
                                    Save Changes
                                </Button>
                            </DialogFooter>
                        </DialogContent>
                    </Dialog>
                </div>
                <div class="self-center align-middle text-gray-400 text-sm">
                    {{ storage.quantity }} pcs.
                </div>
                <div>
                    <AlertDialog>
                        <AlertDialogTrigger as-child>
                            <Button variant="ghost">
                                <Trash2 class="size-4" />
                            </Button>
                        </AlertDialogTrigger>
                        <AlertDialogContent>
                            <AlertDialogHeader>
                                <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                                <AlertDialogDescription>
                                    Are you sure that you want to delete this watch from your storage?
                                </AlertDialogDescription>
                            </AlertDialogHeader>
                            <AlertDialogFooter>
                                <AlertDialogCancel>Cancel</AlertDialogCancel>
                                <AlertDialogAction @click="deleteFromStorage()">Delete</AlertDialogAction>
                            </AlertDialogFooter>
                        </AlertDialogContent>
                    </AlertDialog>
                </div>

            </div>
        </div>

        <div style="cursor: pointer;" class="block p-5">
            <Dialog>
                <DialogTrigger as-child>
                    <div>
                        <div v-if="!isLoading">
                            <div class="flex items-center h-[140px] justify-center">
                                <img class="max-w-full max-h-[140px]" :src="image" alt="Watch Image" />
                            </div>
                        </div>
                        <div v-else>
                            <div class="flex items-center h-[140px] justify-center">
                                <Skeleton class="w-[150px] h-[130px] rounded-md" />
                            </div>
                        </div>
                        <ScrollArea class="max-w-full whitespace-nowrap">
                            <h2 v-if="storage.watch" class="mt-2 pb-2 font-semibold">{{ storage.watch.reference }}</h2>
                            <h2 v-else-if="storage.customWatch" class="mt-2 pb-2 font-semibold">{{
                            storage.customWatch.reference }}</h2>
                            <ScrollBar orientation="horizontal" />
                        </ScrollArea>

                        <ScrollArea class="h-[50px]">
                            <span v-if="storage.watch" class="text-sm"> {{ storage.watch.name }} </span>
                            <span v-else-if="storage.customWatch" class="text-sm"> {{ storage.customWatch.name }}
                            </span>
                        </ScrollArea>
                    </div>
                </DialogTrigger>
                <DialogContent class="sm:max-w-[425px]">
                    <DialogHeader>
                        <DialogTitle>Informations</DialogTitle>
                        <AlertDialogDescription>
                            <br>
                            <div v-if="storage.watch">
                                <b>Reference</b> <br>{{ storage.watch.reference }}
                                <div class="mt-2"></div>
                                <b>Name</b> <br>{{ storage.watch.name }}
                            </div>
                            <div v-else-if="storage.customWatch">
                                <b>Reference</b> <br>{{ storage.customWatch.reference }}
                                <div class="mt-2"></div>
                                <b>Name</b> <br>{{ storage.customWatch.name }}
                            </div>
                            <div class="mt-2"></div>
                            <b>Amount</b><br> {{ storage.quantity }}
                            <div class="mt-2"></div>
                            <b>Status</b> <br>{{ storage.status.name }}
                            <div class="mt-2"></div>
                            <b>Bought for</b><br> {{ storage.buyPrice }} Fr.
                            <div class="mt-2"></div>
                            <b>Purchase date</b><br>
                            <span v-if="new Date(storage.purchaseDate).toLocaleDateString('en-GB') !== '01/01/1970'">
                                {{ new Date(storage.purchaseDate).toLocaleDateString('en-GB') }}
                            </span>
                            <span v-else>-</span>
                            <div class="mt-2"></div>
                            <div v-if="storage.status.name == 'Sold'">
                                <b>Sold for</b><br> {{ storage.sellPrice }} Fr.
                                <div class="mt-2"></div>
                                <b>Sale date</b><br>
                                <div v-if="storage.sellDate">{{ new Date(storage.sellDate).toLocaleDateString('en-GB')
                                    }}</div>
                                <div v-else>-</div>
                                <div class="mt-2"></div>
                                <b>Client</b><br>
                                <div v-if="storage.client">{{ storage.client.name }} {{ storage.client.surname }}</div>
                                <div v-else>-</div>
                                <div class="mt-2"></div>
                                <b>Team</b><br>
                                <div v-if="storage.team">{{ storage.team.name }} {{ storage.team.surname }}</div>
                                <div v-else>-</div>
                                <div class="mt-2"></div>
                            </div>
                            <br>
                            <Button variant="outline">
                                <Info class="size-4" /> &nbsp;&nbsp;<router-link v-if="storage.watch"
                                    :to="`/watch/${storage.watch.reference}/true`">Watch Info</router-link>
                                <router-link v-if="storage.customWatch"
                                    :to="`/customWatch/${storage.customWatch.reference}/true`">Watch Info</router-link>
                            </Button>
                        </AlertDialogDescription>
                    </DialogHeader>
                </DialogContent>
            </Dialog>
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
</template>

<script setup>
import { Skeleton } from "@/components/ui/skeleton"
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area'
import { Separator } from '@/components/ui/separator'
import { apiServerAddress } from '@/main.ts'
import { Button } from '@/components/ui/button'
import { Trash2 } from 'lucide-vue-next'
import { Pencil } from 'lucide-vue-next'
import { Info } from 'lucide-vue-next'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { CheckCircle } from "lucide-vue-next";
import { AlertCircle } from 'lucide-vue-next'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
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
    DialogClose,
} from '@/components/ui/dialog'
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
import { getJSDocImplementsTags } from "typescript"

const router = useRouter();
const props = defineProps(['storage'])
const image = ref('')
const isLoading = ref(true);
const clients = ref([]);
const teams = ref([]);
const emailUser = ref('');
const invalidData = ref(false);
const invalidDate = ref(false);
const showDialogPrice = ref(false);
const assertClient = ref(false);
const restOpen = ref(false);

const oldStorage = {
    id: props.storage.id,
};
const editStrg = {
    id: props.storage.id,
    quantity: 1,
    status: "Sold",
    buyPrice: props.storage.buyPrice,
    sellPrice: 0,
    purchaseDate: props.storage.purchaseDate,
    sellDate: null,
    clientId: 0,
    teamId: 0
};

async function getImage() {
    try {

        let endpoint = "";
        if (props.storage.watch) {
            endpoint = `${apiServerAddress}/v1/watches/` + props.storage.watch.reference + '/image';
        } else if (props.storage.customWatch) {
            endpoint = `${apiServerAddress}/v1/storage/custom/` + props.storage.customWatch.reference + '/image'
        }
        const response = await axios.get(endpoint, {
            responseType: 'arraybuffer',
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
        });

        const imageBase64 = btoa(new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), ''));
        image.value = `data:${response.headers['content-type']};base64,${imageBase64}`;
    } catch (error) {
        image.value = "@assets/no_image.png"
    }
}

async function deleteFromStorage() {
    try {
        const response = await axios.post(`${apiServerAddress}/v1/storage/removeStorageWatch`, oldStorage, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        console.log('Watch removed from storage. ', response.data);
        window.location.reload();
    } catch (error) {
        console.error('Failed to remove watch from storage:', error);
    }
}

async function editStorage() {
    invalidData.value = false;
    if (editStrg.clientId == 0) {
        invalidData.value = true;
    }
    if (editStrg.teamId == 0) {
        invalidData.value = true;
    }
    if (editStrg.sellDate == null || !isValidDate(editStrg.sellDate)) {
        invalidData.value = true;
    }
    if (!isValidPrice(editStrg.sellPrice)) {
        invalidData.value = true;
    }
    if (!isValidPrice(editStrg.quantity)) {
        invalidData.value = true;
    }

    if (!invalidData.value) {
        var flag = soldAfterPurchase(editStrg.sellDate, editStrg.purchaseDate);
        var flag2 = soldForMoreOrEqual(editStrg.sellPrice, editStrg.buyPrice);
        if (flag) {
            invalidDate.value = false;
            if (flag2 || assertClient.value) {
                showDialogPrice.value = false;
                assertClient.value = false;
                try {
                    const response = await axios.post(`${apiServerAddress}/v1/storage/editStorageWatch`, editStrg, {
                        headers: {
                            Authorization: 'Bearer ' + localStorage.getItem('token'),
                        },
                    });
                    console.log('Watch editet in storage. ', response.data);
                    window.location.reload();
                } catch (error) {
                    console.error('Failed to edit watch in storage:', error);
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
    try {
        await getImage();
    } finally {
        isLoading.value = false;
    }
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    emailUser.value = payload.sub;
    await getClients(emailUser.value);
    await getTeam(emailUser.value);
});

function isValidDate(date) {
    const dateFormat = /^\d{4}-\d{2}-\d{2}$/;
    return dateFormat.test(date);
}
function soldAfterPurchase(dateSold, datePurchase) {
    return dateSold > datePurchase;
}
function soldForMoreOrEqual(priceSold, pricePurchase) {
    return priceSold >= pricePurchase;
}
function isValidPrice(price) {
    return !isNaN(price) && price >= 0 && price != '';
}
function redo() {
    assertClient.value = true;
    editStorage();
}
function res() {
    showDialogPrice.value = false;
}
function setRest() {
    restOpen.value = true;
}
function setNotVisible() {
    restOpen.value = !restOpen.value;
}
</script>