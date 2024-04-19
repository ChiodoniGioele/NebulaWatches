<template>
    <div v-if="isOpen" class="fixed bottom-0 right-0 p-4">
        <div class="bg-white rounded-lg shadow-lg w-[32rem] border-2 border-stone-900 z-50" style="z-index: 9999;">
            <div class="flex justify-between items-center p-4 border-b border-gray-200">
                <div>
                    <div class="text-lg font-semibold flex gap-4 items-center"> 
                        <img class="h-[20px] max-w-[40px]" src="@/assets/icons/bot_icon.png" />
                        <h3>Gio</h3>
                        
                    </div>
                </div>
                
                <button @click="toggleChat" class="text-gray-500 hover:text-gray-600 focus:outline-none">
                    <svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                        stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M6 18L18 6M6 6l12 12" />
                    </svg>
                </button>
            </div>
            <div class="p-4 h-60 overflow-y-auto">
                <div v-for="(message, index) in messages" :key="index" class="mb-2">
                    <div v-if="message.type === 'user'" class="text-right">
                        <span class="bg-black text-white px-2 py-1 rounded-lg inline-block">
                            {{ message.content }}
                        </span>
                    </div>
                    <div v-else class="text-left">
                        <span class="bg-gray-200 px-2 py-1 rounded-lg inline-block">
                            {{ message.content }}
                        </span>
                    </div>
                </div>
            </div>
            <div class="p-4 border-t border-gray-200 flex gap-1">
                <Button variant="ghost"> 
                    <img class="" height="20px" width="20px" src="@/assets/icons/info_icon.png" />
                </Button>
                <Input v-model="inputMessage" @keyup.enter="sendMessage" type="text"
                    class="mx-1 w-full px-2 py-1 border border-gray-300 rounded-lg focus:outline-none"
                    placeholder="Type a message..." />
                <Button @click="sendMessage"> 
                    <img class="" height="20px" width="20px" src="@/assets/icons/send_white_icon.png" />
                </Button>
                <Button variant="destructive"> 
                    <img class="" height="20px" width="20px" src="@/assets/icons/trash_icon.png" />
                </Button>
            </div>
        </div>
    </div>
    <div v-else class="fixed bottom-0 right-0 p-4">
        <Button variant="outline" @click="toggleChat" class="h-16 px-4 py-4 rounded-lg focus:outline-none">
            <img class="" height="30px" width="30px" src="@/assets/icons/chat_message_icon.png" />
        </Button>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import {
  HoverCard,
  HoverCardContent,
  HoverCardTrigger,
} from '@/components/ui/hover-card'


const isOpen = ref(false);
const messages = ref([]);
const inputMessage = ref('');

messages.value.push({ type: 'bot', content: 'How can i help you today?' });

const sendMessage = async () => {
    if (inputMessage.value.trim() !== '') {
        messages.value.push({ type: 'user', content: inputMessage.value.trim() });
        const query = encodeURIComponent(inputMessage.value.trim());
        inputMessage.value = '';

        try {
            const response = await axios.get(`http://127.0.0.1:8000/ask_bot`, {
                params: {
                    jwt: localStorage.getItem('token'),
                    query: query
                },
                headers: {
                    'accept': 'application/json'
                }
            });

            messages.value.push({ type: 'bot', content: response.data.result });
        } catch (error) {
            console.error('Error fetching response:', error);
            messages.value.push({ type: 'bot', content: 'An error occurred while fetching the response.' });
        }
    }
}

const toggleChat = () => {
    isOpen.value = !isOpen.value;
}
</script>

<style scoped>
/* Add Tailwind CSS classes here */
</style>