<template>
    <div class="grid lg:grid-cols-5 min-h-screen"> 
        <Sidebar class="hidden lg:block" />
        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col" > 
            <div class="px-4 py-6 lg:px-8"> 

                <div class="flex w-full items-center gap-2.5">
                    <Input id="email" type="text" placeholder="Search a watch in your storage..." />
                    <Button type="submit" class="bg-blue-600"> Search </Button>
                </div>

                <div class="mt-12 px-1 flex gap-7 items-center">
                    <div class="flex gap-2">
                        <h1 class="font-semibold "> Storage </h1>
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
                                <Input id="reference" class="col-span-3" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="name" class="text-right">
                                    Name
                                </Label>
                                <Input id="name"class="col-span-3" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="desc" class="text-right">
                                    Description
                                </Label>
                                <Input id="desc" class="col-span-3" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="diameter" class="text-right">
                                    Diameter (mm)
                                </Label>
                                <Input id="diameter" class="col-span-3" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="height" class="text-right">
                                    Height (mm)
                                </Label>
                                <Input id="height" class="col-span-3" />
                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">
                                <Label for="water" class="text-right">
                                    Water resistance (m)
                                </Label>
                                <Input id="water" class="col-span-3" />
                                </div>
                            </div>
                            <DialogFooter>
                                <Button type="submit">
                                Save Watch
                                </Button>
                            </DialogFooter>
                            </DialogContent>
                        </Dialog>
                    </div>
                </div>
                
                <div class="mt-5 flex flex-wrap gap-5">
                    <StorageCard v-for="watch in storedWatches" :key="watch.id" :storage="watch" />
                </div>

            </div>
        </div>
    </div>
    
 
</template>
  
<script setup>
import Sidebar from '@/components/Sidebar.vue'
import StorageCard from '@/components/StorageCard.vue'


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

const route = useRoute();
const storedWatches = ref([]);

async function fetchStorage(userEmail) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/storage/${userEmail}`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        storedWatches.value = response.data;
    } catch (error) {
        console.error('Failed to fetch watches by storage and user:', error);
    }
}

onMounted(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const email = payload.sub;

    await fetchStorage(email);
});
</script>
