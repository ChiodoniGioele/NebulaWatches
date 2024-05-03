<template>
    <div v-if="isOpen" class="fixed bottom-0 right-0 p-4">
        <div class="bg-white rounded-lg shadow-lg w-[32rem] border-2 border-stone-900 z-50" style="z-index: 9999;">
            <div class="flex justify-between items-center p-4 border-b border-gray-200">
                <div>
                    <div class="text-lg font-semibold flex gap-4 items-center">
                        <img class="h-[20px] max-w-[40px]" src="@/assets/icons/bot_icon.png" alt="bot icon" />
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
                        <span class="bg-gray-200 px-2 py-1 rounded-lg inline-block" v-html="message.content"></span>
                    </div>
                </div>
            </div>
            <div class="p-4 border-t border-gray-200 flex gap-1">
                <Dialog>
                    <DialogTrigger>
                        <Button variant="ghost">
                            <img class="" height="40px" width="40px" src="@/assets/icons/info_icon.png" alt="info" />
                        </Button>
                    </DialogTrigger>
                    <DialogContent>
                        <DialogHeader>
                            <DialogTitle>Info</DialogTitle>
                        </DialogHeader>

                        <p>

                            By engaging with this bot, you explicitly agree to the disclosure of your data to OpenAI and
                            Gio, referred to henceforth as the "Entities". This agreement encompasses all data,
                            inclusive of personal information, usage patterns, and content inputs, generated or provided
                            during interactions with the bot. The Entities reserve the right to utilize this data for
                            diverse purposes, encompassing but not limited to research, development, and enhancement of
                            their respective offerings. It is understood that the Entities will adhere to pertinent data
                            protection laws and regulations, and undertake reasonable measures to maintain the
                            confidentiality and integrity of the shared data.
                        </p>
                    </DialogContent>
                </Dialog>

                <Input v-model="inputMessage" @keyup.enter="sendMessage" type="text"
                    class="mx-1 w-full px-2 py-1 border border-gray-300 rounded-lg focus:outline-none"
                    placeholder="Type a message..." />
                <Button @click="sendMessage">
                    <img class="" height="20px" width="20px" src="@/assets/icons/send_white_icon.png" alt="send" />
                </Button>
                <Button variant="destructive">
                    <img class="" height="20px" width="20px" src="@/assets/icons/trash_icon.png" alt="delete" />
                </Button>
            </div>
        </div>
    </div>
    <div v-else class="fixed bottom-0 right-0 p-4 pb-11">
        <Button variant="outline" @click="toggleChat" class="h-16 px-4 py-4 rounded-lg focus:outline-none">
            <img class="" height="30px" width="30px" src="@/assets/icons/chat_message_icon.png" alt="message" />
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

import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog'


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
            const response = await axios.get(`http://localhost:6969/ask_bot`, {
                params: {
                    query: query
                },
                headers: {
                    jwt: localStorage.getItem('token'),
                },
            });

            messages.value.push({
                type: 'bot', // Indicates that the message is from the bot
                content: response.data.result.replace(/\n/g, '<br>') // The content of the message with newline characters replaced by HTML line breaks
            });

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
