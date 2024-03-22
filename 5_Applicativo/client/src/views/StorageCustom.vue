<template>
    <div class="grid lg:grid-cols-5 min-h-screen"> 
        <Sidebar class="hidden lg:block" />
        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col" > 
            <div class="px-4 py-6 lg:px-8"> 

                <div class="flex w-full items-center gap-2.5">
                    <Input id="email" type="text" placeholder="Search a watch in your custom watches..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                </div>
                <div class="mt-5 flex gap-7 items-center">
                    <div>
                        <Button @click="$router.back()" variant="secondary">
                            <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
                        </Button>
                    </div>
                </div>
                <div class="mt-5 px-1 flex gap-3 items-center">
                   

                    <div class="flex gap-2">
                        <h1 class="font-semibold"> CustomWatches </h1>
                    </div>
                    <div class="w-full flex gap-7 items-center"></div>
                                        
                    <div class="flex gap-2 w-auto">
                        <Dialog>
                            <DialogTrigger as-child>
                            <Button variant="outline" class="h-12">
                                <!--<img class="w-5 h-5 m-1" src="@/assets/icons/plus.png" alt="+">--> 
                                <p >Add Custom Watch</p>
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
                                    Reference
                                </Label>
                                <Input id="reference" class="col-span-3" v-model="customWatch.reference"/>
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="name" class="text-right">
                                    Name
                                </Label>
                                <Input id="name"class="col-span-3" v-model="customWatch.name"/>
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="desc" class="text-right">
                                    Description
                                </Label>
                                <Input id="desc" class="col-span-3" v-model="customWatch.description"/>
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="retailPrice" class="text-right">
                                    Price
                                </Label>
                                <Input type="number" id="retailPrice" class="col-span-3" v-model="customWatch.retailPrice"/>
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="image" class="text-right">
                                    Image
                                </Label>
                                <Input type="file" id="image" class="col-span-3" accept="image/*" @change="handleImageChange" />
                                </div>
                            </div>
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
    
 
</template>
  
<script setup>
import Sidebar from '@/components/Sidebar.vue'
import CustomWatchCard from '@/components/CustomWatchCard.vue'


import { Input } from '@/components/ui/input'
import { Button } from '@/components//ui/button'
import { Checkbox } from '@/components/ui/checkbox'
import { useRouter } from 'vue-router';

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

const route = useRoute();
const storedWatches = ref([]);
const emailUs = ref("");

const customWatch = {
  reference: '',
  name: '',
  description: '',
  retailPrice: 0,
  email: '',
  image: null
};

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

async function saveWatch() {
    customWatch.email = emailUs.value;
    const formData = new FormData();
    formData.append('reference', customWatch.reference);
    formData.append('name', customWatch.name);
    formData.append('description', customWatch.description);
    formData.append('retailPrice', customWatch.retailPrice);
    formData.append('email', customWatch.email);
    formData.append('file', customWatch.image);
    console.log(formData.get('file'))
    try {
        const response = await axios.post(`${apiServerAddress}/v1/storage/saveCustom`, formData, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
            },
        });
        window.location.reload();
        console.log('Custom watch saved ', response.data);
        
    } catch (error) {
        console.error('Failed to save Custom Watch:', error);
    }
}

const handleImageChange = (event) => {
    customWatch.image = event.target.files[0];
};

onMounted(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const email = payload.sub;
    emailUs.value = email;

    await fetchCustomStorage(email);
});
</script>
