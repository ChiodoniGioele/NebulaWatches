<template>
    <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <router-link to="/" class="flex items-center mb-8 text-4xl text-gray-900 dark:text-white">
            <!---<img class="w-16 h-16 mr-2" src="../assets/Logo.jpg" alt="logo">-->
            <!--NebulaWatches-->
        </router-link>
        <br><br><br>
        <Card class="w-full md:w-1/2 lg:w-1/3 xl:w-1/4">
            <CardHeader class="space-y-1">
                <CardTitle class="text-2xl">
                    Register now
                </CardTitle>
            </CardHeader>
            <CardContent class="grid gap-4">
                <div class="grid gap-2">
                    <Label for="username">Username</Label>
                    <Input id="username" type="text" v-model="username" placeholder="ExampleUser" class="bg-gray-50" required />
                </div>
                <div class="grid gap-2">
                    <Label for="email">Email</Label>
                    <Input id="email" type="email" v-model="email" placeholder="example@gmail.com" class="bg-gray-50" required />
                </div>
                <div class="grid gap-2">
                    <Label for="password">Password</Label>
                    <Input id="password" type="password" v-model="password" placeholder="••••••••" class="bg-gray-50" required />
                </div>
                <Button class="w-full" @click="register">
                    Register
                </Button>
                <Alert variant="destructive" v-if="registerFailed">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Registration failed, username alredy used!
                    </AlertDescription>
                </Alert>
            </CardContent>
            <CardFooter>
                <Label>Already have an account? <router-link to="login" class="text-blue-500 hover:underline">Login
                        now</router-link></Label>
            </CardFooter>
        </Card>
    </div>
</template>

<script setup lang="ts">
import { Separator } from 'radix-vue';
import { Button } from '../components/ui/button'
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from '../components/ui/card'
import { Input } from '../components/ui/input'
import { Label } from '../components/ui/label'

import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const username = ref('');
const email = ref('');
const password = ref('');
const router = useRouter();
const registerFailed = ref(false);

async function register() {
    try {
        const response = await axios.post('http://localhost:64321/auth/register', {
            username: username.value,
            email: email.value,
            password: password.value
        });

        router.push('/');
    } catch (error) {
        console.error('Registration failed:', error);
        registerFailed.value = true;
    }
}
</script>