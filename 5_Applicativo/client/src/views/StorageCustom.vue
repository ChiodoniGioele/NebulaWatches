<!--
 This page allows you to view the custom clocks that have been created and to add new ones.
 -->
<template>
    <div class="flex h-screen"> 
        <Sidebar />
        <div class="flex flex-col w-full" > 
            <div class="px-8 py-6">

                
                <div class="mt-5 flex gap-7 items-center">
                    <div>
                        <Button @click="$router.back()" variant="secondary">
                            <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" alt="go back">
                        </Button>
                    </div>
                </div>
                <div class="mt-5 px-1 flex gap-3 items-center">


                    <div class="flex gap-2">
                        <h1 class="font-semibold"> CustomWatches </h1>
                    </div>
                    <div class="w-full flex gap-7 items-center"></div>

                    <div class="flex gap-2 w-auto">
                        <Dialog :open="restOpen" @update:open="setNotVisible">
                            <DialogTrigger as-child>
                                <Button variant="outline" class="h-12">
                                    <!--<img class="w-5 h-5 m-1" src="@/assets/icons/plus.png" alt="+">-->
                                    <p>Add Custom Watch</p>
                                </Button>
                            </DialogTrigger>
                            <DialogContent class="sm:max-w-[425px]">
                                <DialogHeader>
                                    <DialogTitle>Add a custom watch</DialogTitle>
                                    <DialogDescription>
                                        If your watch is not listed in our watches you can add it by yourself.
                                    </DialogDescription>
                                </DialogHeader>
                                <div class="grid gap-4 py-4">
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="reference" class="text-right">
                                            Reference *
                                        </Label>
                                        <Input id="reference" class="col-span-3" v-model="customWatch.reference" />
                                    </div>
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="name" class="text-right">
                                            Name *
                                        </Label>
                                        <Input id="name" class="col-span-3" v-model="customWatch.name" />
                                    </div>
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="desc" class="text-right">
                                            Desc *
                                        </Label>
                                        <Input id="desc" class="col-span-3" v-model="customWatch.description" />
                                    </div>
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="retailPrice" class="text-right">
                                            Price *
                                        </Label>
                                        <Input type="number" id="retailPrice" class="col-span-3"
                                            v-model="customWatch.retailPrice" />
                                    </div>
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="image" class="text-right">
                                            Image *
                                        </Label>
                                        <Input type="file" id="image" class="col-span-3" accept="image/*"
                                            @change="handleImageChange" />
                                    </div>
                                </div>
                                <Alert variant="destructive" v-if="invalidData">
                                    <AlertCircle class="w-4 h-4" />
                                    <AlertTitle>Error</AlertTitle>
                                    <AlertDescription>
                                        Please insert valid data!
                                    </AlertDescription>
                                </Alert>
                                <Alert variant="destructive" v-if="invalidRef">
                                    <AlertCircle class="w-4 h-4" />
                                    <AlertTitle>Error</AlertTitle>
                                    <AlertDescription>
                                        Please insert a reference!
                                    </AlertDescription>
                                </Alert>
                                <Alert variant="destructive" v-if="invalidFile">
                                    <AlertCircle class="w-4 h-4" />
                                    <AlertTitle>Error</AlertTitle>
                                    <AlertDescription>
                                        Please insert an image as file!
                                    </AlertDescription>
                                </Alert>
                                <Alert variant="destructive" v-if="fileToBig">
                                    <AlertCircle class="w-4 h-4" />
                                    <AlertTitle>Error</AlertTitle>
                                    <AlertDescription>
                                        Please insert an image smaller than 1MB!
                                    </AlertDescription>
                                </Alert>
                                <Alert variant="destructive" v-if="refExists">
                                    <AlertCircle class="w-4 h-4" />
                                    <AlertTitle>Error</AlertTitle>
                                    <AlertDescription>
                                        This reference is already used!
                                    </AlertDescription>
                                </Alert>
                                <DialogFooter>
                                    <Button type="submit" @click="saveWatch">
                                        Save Watch
                                    </Button>
                                </DialogFooter>
                            </DialogContent>
                        </Dialog>
                    </div>
                </div>

                <div class="mt-5 flex flex-wrap gap-5">
                    <CustomWatchCard v-for="watch in storedWatches" :key="watch.id" :watch="watch" />
                </div>

            </div>
        </div>
    </div>

    <Chat />
</template>

<script setup>
// imports
import Chat from '@/components/Chat.vue'
import Sidebar from '@/components/Sidebar.vue'
import CustomWatchCard from '@/components/CustomWatchCard.vue'


import { Input } from '@/components/ui/input'
import { Button } from '@/components//ui/button'
import { Checkbox } from '@/components/ui/checkbox'
import { useRouter } from 'vue-router';
import { AlertCircle } from 'lucide-vue-next'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'

import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog'
import { Label } from '@/components/ui/label'

import { apiServerAddress } from '@/main.ts'
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import FormData from 'form-data';

// variables
const route = useRoute();
const storedWatches = ref([]);
const emailUs = ref("");
const invalidData = ref(false);
const invalidRef = ref(false);
const invalidFile = ref(false);
const restOpen = ref(false);
const fileToBig = ref(false);
const refExists = ref(false);

const customWatch = {
    reference: '',
    name: '',
    description: '',
    retailPrice: 0,
    email: '',
    image: null
};

// fetch the custom clocks that there are in the st
async function fetchCustomStorage(userEmail) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/storage/custom/${userEmail}`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        storedWatches.value = response.data;
    } catch (error) {
        console.error('Failed to fetch watches by storage and user:', error);
    }
}
// Allows you to create a new custom clock
async function saveWatch() {
    invalidFile.value = false;
    customWatch.email = emailUs.value;
    fileToBig.value = false;
    refExists.value = false;
    const formData = new FormData();
    if (!isNullOrEmpty(customWatch.reference)) {
        if (isValidPrice(customWatch.retailPrice)) {
            invalidData.value = false;
            invalidRef.value = false;
            formData.append('reference', customWatch.reference);
            formData.append('name', customWatch.name);
            formData.append('description', customWatch.description);
            formData.append('retailPrice', customWatch.retailPrice);
            formData.append('email', customWatch.email);

            if (customWatch.image && customWatch.image.type.startsWith('image/')) {
                formData.append('file', customWatch.image);
                try {
                    const response = await axios.post(`${apiServerAddress}/v1/storage/saveCustom`, formData, {
                        headers: {
                            'Authorization': 'Bearer ' + localStorage.getItem('token'),
                        },
                    });
                    if (response.data == "Maximum upload size exceeded. Please upload a smaller file.") {
                        fileToBig.value = true;
                    } else {
                        window.location.reload();
                        console.log('Custom watch saved ', response.data);
                    }

                } catch (error) {
                    if (error.response) {
                        if (error.response.data == "Reference already exists. Please use another") {
                            refExists.value = true;
                        }

                    }
                    if (refExists.value != true) {
                        console.error('Failed to save Custom Watch:', error);
                    }
                }
            } else {
                invalidFile.value = true;
            }
        } else {
            invalidData.value = true;
        }

    } else {
        invalidRef.value = true;
    }
}

const handleImageChange = (event) => {
    customWatch.image = event.target.files[0];
};

// function that is called when the page loads
onMounted(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const email = payload.sub;
    emailUs.value = email;

    await fetchCustomStorage(email);
});


//Utils
function isValidPrice(price) {
    return !isNaN(price) && price >= 0 && price != '';

}
function isNullOrEmpty(str) {
    return !str || str.trim() === '';
}
function setNotVisible() {
    restOpen.value = !restOpen.value;
}
</script>
