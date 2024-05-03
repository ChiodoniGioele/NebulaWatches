<template>
    <div class="flex h-screen"> 
        <Sidebar />
        <div class="flex flex-col w-full" > 
            <div class="px-8 py-6">
                
                <!-- <div class="flex w-full items-center gap-2.5">
                    <Input id="email" type="text" placeholder="Search a watch in your storage..." />
                    <Button type="submit"> Search </Button>
                </div> -->

                <div class="mt-12 px-1 flex gap-3 items-center">
                    <div class="flex gap-2">
                        <h1 class="font-semibold "> Storage </h1>
                    </div>
                    <div class="w-full flex gap-7 items-center"></div>
                    <Button variant="outline" class="h-12" @click="toCustom">
                        <p>Custom Watches</p>
                    </Button>

                </div>
                <Tabs default-value="owned">
                    <TabsList>
                        <TabsTrigger value="owned">
                            Owned
                        </TabsTrigger>
                        <TabsTrigger value="sold">
                            Sold
                        </TabsTrigger>
                    </TabsList>
                    <TabsContent value="owned">
                        <div class="mt-5 flex flex-wrap gap-5">
                            <StorageCard v-for="watch in storedWatchesNotSold" :key="watch.id" :storage="watch" />
                        </div>
                    </TabsContent>
                    <TabsContent value="sold">
                        <div class="mt-5 flex flex-wrap gap-5">
                            <StorageCard v-for="watch in storedWatchesSold" :key="watch.id" :storage="watch" />
                        </div>
                    </TabsContent>
                </Tabs>
            </div>
        </div>
    </div>

    <Chat />
</template>

<script setup>
import Chat from '@/components/Chat.vue'
import Sidebar from '@/components/Sidebar.vue'
import StorageCard from '@/components/StorageCard.vue'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'

import { Input } from '@/components/ui/input'
import { Button } from '@/components//ui/button'
import { Checkbox } from '@/components/ui/checkbox'
import { useRouter } from 'vue-router';

import { apiServerAddress } from '@/main.ts'
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const router = useRouter();
const storedWatches = ref([]);
const storedWatchesNotSold = ref([]);
const storedWatchesSold = ref([]);

async function fetchStorage(userEmail) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/storage/${userEmail}`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        storedWatches.value = response.data;
        distributeWatches();
    } catch (error) {
        console.error('Failed to fetch watches by storage and user:', error);
    }
}

async function distributeWatches(){
    for(let i = 0; i < storedWatches.value.length; i++){
        if(storedWatches.value[i].status.name == "Sold"){
            storedWatchesSold.value.push(storedWatches.value[i]);
        }else{
            storedWatchesNotSold.value.push(storedWatches.value[i]);
        }
    }
}

async function toCustom() {
    router.push("/storageCustom");
}

onMounted(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const email = payload.sub;

    await fetchStorage(email);
    localStorage.removeItem('search');
});


</script>
