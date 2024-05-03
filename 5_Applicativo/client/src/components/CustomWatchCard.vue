<!--
Card for displaying custom watches. This is is used in CustomWath to show the watches already entered.
-->
<template>
    <div class="text-center border-stone-500 border-2  rounded-md min-w-[170px]  w-[18%] min-h-[200px] max-[600px]:w-[40%]">
        <div class="h-[50px]">
            <div class="pt-1 pr-1 pl-1 flex justify-end">
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
                            Are you sure that you want to delete this custom watch?
                            </AlertDialogDescription>
                        </AlertDialogHeader>
                        <AlertDialogFooter>
                            <AlertDialogCancel>Cancel</AlertDialogCancel>
                            <AlertDialogAction @click="deleteCustomWatch()">Delete</AlertDialogAction>
                        </AlertDialogFooter>
                        </AlertDialogContent>
                    </AlertDialog>
                </div>
                
            </div>
        </div>
        <router-link class="block p-5" :to="`/customWatch/${watch.reference}`">
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
                    <h2 class="mt-2 pb-2 font-semibold">{{ watch.reference }}</h2>
                    <ScrollBar orientation="horizontal" />
                </ScrollArea>

                <ScrollArea class="h-[50px]">
                    <span class="text-sm"> {{ watch.name }} </span>
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

const props = defineProps(['watch'])
const image = ref('')
const isLoading = ref(true);

const piecesProduced = ref('')


async function getImage() {
    try {
        const endpoint = `${apiServerAddress}/v1/storage/custom/` + props.watch.reference + '/image'
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

const oldCustom = {
    reference: props.watch.reference,
};

async function deleteCustomWatch(){
    try {
        const response = await axios.post(`${apiServerAddress}/v1/storage/removeCustom`, oldCustom, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });
        
        console.log('Custom Watch removed. ', response.data); 
        window.location.reload();
    } catch (error) {
        console.error('Failed to remove custom Watch.', error);
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