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
                    Login to your account
                </CardTitle>
            </CardHeader>
            <CardContent class="grid gap-4">

                <div class="grid gap-2">
                    <Label for="email">Email</Label>
                    <Input id="email" v-model="email" type="email" placeholder="example@gmail.com" class="bg-gray-50" required />
                </div>
                <div class="grid gap-2">
                    <Label for="password">Password</Label>
                    <Input id="password" v-model="password" type="password" placeholder="••••••••" class="bg-gray-50" required />
                </div>
                <Button class="w-full" @click="login">
                    Login
                </Button>

                <Alert variant="destructive" v-if="loginFailed">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Email or Password Incorrect. Pleasy try again
                    </AlertDescription>
                </Alert>

                <div class="relative">
                    <div class="absolute inset-0 flex items-center">
                        <span class="w-full border-t" />
                    </div>
                    <div class="relative flex justify-center text-xs uppercase">
                        <span class="bg-background px-2 text-muted-foreground">
                            Or continue with
                        </span>
                    </div>
                </div>
                <div class="grid gap-6">

                    <Button variant="outline">
                        <svg role="img" viewBox="0 0 24 24" class="mr-2 h-4 w-4">
                            <path fill="currentColor"
                                d="M12.48 10.92v3.28h7.84c-.24 1.84-.853 3.187-1.787 4.133-1.147 1.147-2.933 2.4-6.053 2.4-4.827 0-8.6-3.893-8.6-8.72s3.773-8.72 8.6-8.72c2.6 0 4.507 1.027 5.907 2.347l2.307-2.307C18.747 1.44 16.133 0 12.48 0 5.867 0 .307 5.387.307 12s5.56 12 12.173 12c3.573 0 6.267-1.173 8.373-3.36 2.16-2.16 2.84-5.213 2.84-7.667 0-.76-.053-1.467-.173-2.053H12.48z" />
                        </svg>
                        Google
                    </Button>
                </div>
            </CardContent>
            <CardFooter>
                <Label>Don't have an account? <router-link to="register" class="text-blue-500 hover:underline">Register
                        now</router-link></Label>
            </CardFooter>
        </Card>
    </div>
</template>

<script setup>
import { Separator } from 'radix-vue';
import { Button } from '@/components/ui/button'
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { AlertCircle } from 'lucide-vue-next'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { apiServerAddress } from '@/main.ts'


const email = ref('');
const password = ref('');
const router = useRouter();
const loginFailed = ref(false);

async function login() {
    console.log(email.value);
    try {
        const response = await axios.post(`${apiServerAddress}/auth/authenticate`, {
            email: email.value,
            password: password.value
        });
        
        const token = response.data.token;
        localStorage.setItem('token', token);
        sessionStorage.setItem('email', email.value);
        router.push('/');
    } catch (error) {
        loginFailed.value = true;
        //console.error('Login failed:', error.response.data.message);
        console.error(error)
    }
}
</script>
