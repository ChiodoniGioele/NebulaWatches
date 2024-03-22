<template>
    <div class="text-center border border-gray-200 rounded-md min-w-[170px]  w-[18%] min-h-[200px] max-[600px]:w-[40%]">
        <div class="h-[50px]">
            <div class="pt-1 pr-1 pl-1 flex justify-between">
                <div class="self-center align-middle text-gray-400 text-sm">
                    <Dialog>
                        <DialogTrigger as-child>
                            <Button variant="ghost">
                                <Pencil class="size-4" />
                            </Button>
                        </DialogTrigger>
                        <DialogContent class="sm:max-w-[425px]">
                            <DialogHeader>
                                <DialogTitle>Edit Watch in Storage</DialogTitle>
                                <AlertDialogDescription>
                                    Here you can change the status and amount of this watch:
                                    <br>
                                    • Current amount: {{ storage.quantity }}
                                    <br>
                                    • Current status: {{ storage.status.name }}
                                </AlertDialogDescription>
                            </DialogHeader>

                            <div class="grid gap-4 py-4">
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="qty" class="text-right">
                                        Change Amount
                                    </Label>
                                    <Input v-model="editStrg.quantity" id="qty" class="col-span-3" type="number" max="{{ storage.quantity  }}" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label class="text-right">
                                        New Status
                                    </Label>
                                    <div class="col-span-3">
                                        <Select v-model="editStrg.status">
                                            <SelectTrigger>
                                                <SelectValue placeholder="Select status" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                <SelectGroup>
                                                    <SelectLabel>Status</SelectLabel>
                                                    <SelectItem value="Sold">Sold</SelectItem>
                                                    <SelectItem value="Shipped">Shipped</SelectItem>
                                                </SelectGroup>
                                            </SelectContent>
                                        </Select>
                                    </div>
                                </div>
                            </div>
                            <DialogFooter>
                                <DialogClose as-child>
                                    <Button type="submit" @click="editStorage">
                                        Save Changes
                                    </Button>
                                </DialogClose>
                            </DialogFooter>
                        </DialogContent>
                    </Dialog>
                </div>
                <div class="self-center align-middle text-gray-400 text-sm">
                    {{ storage.status.name }}, {{ storage.quantity }} pcs.
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

        <router-link v-if="storage.watch" class="block p-5" :to="`/watch/${storage.watch.reference}/true`">
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
                    <h2 class="mt-2 pb-2 font-semibold">{{ storage.watch.reference }}</h2>
                    <ScrollBar orientation="horizontal" />
                </ScrollArea>

                <ScrollArea class="h-[50px]">
                    <span class="text-sm"> {{ storage.watch.name }} </span>
                </ScrollArea>

            </div>
        </router-link>
        <router-link v-else-if="storage.customWatch" class="block p-5"
            :to="`/customWatch/${storage.customWatch.reference}/true`">
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
                    <h2 class="mt-2 pb-2 font-semibold">{{ storage.customWatch.reference }}</h2>
                    <ScrollBar orientation="horizontal" />
                </ScrollArea>

                <ScrollArea class="h-[50px]">
                    <span class="text-sm"> {{ storage.customWatch.name }} </span>
                </ScrollArea>

            </div>
        </router-link>

    </div>



</template>

<script setup>
import { Skeleton } from "@/components/ui/skeleton"
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area'
import { Separator } from '@/components/ui/separator'
import { apiServerAddress } from '@/main.ts'
import { Button } from '@/components/ui/button'
import { Trash2 } from 'lucide-vue-next'
import { Pencil } from 'lucide-vue-next'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
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

const router = useRouter();
const props = defineProps(['storage'])
const image = ref('')
const isLoading = ref(true);

const oldStorage = {
    id: props.storage.id,
};
const editStrg = {
    id: props.storage.id,
    quantity: 1,
    status: ""
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
}

onMounted(async () => {
    try {
        await getImage();
    } finally {
        isLoading.value = false;
    }
});

</script>