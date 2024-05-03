<!--
  This page is used to verify that the user's email is true.
  It is displayed when the user confirms their email
-->
<template>
    <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <Card class="w-full md:w-1/2 lg:w-1/3 xl:w-1/4">
            <CardHeader class="space-y-1">
                <CardTitle class="text-2xl">
                    Verify your Account
                </CardTitle>
            </CardHeader>
            
            <CardContent class="grid gap-4">
                <div class="grid gap-2">
                    <Label for="pin">Pin</Label>
                    <Input id="pin" v-model="pin" type="number" class="bg-gray-50" required />
                </div>
                <Button class="w-full" @click="verifyPin">
                    Verify
                </Button>

                <Alert variant="destructive" v-if="verificationFailed">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Incorrect Pin!
                    </AlertDescription>
                </Alert>
                <Alert variant="destructive" v-if="validPin">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Please insert a valid Pin (100000 - 999999)!
                    </AlertDescription>
                </Alert>
            </CardContent>
            <CardFooter>
                <Label>Don't have verification code? <span @click="sendAgain" class="text-blue-500 hover:underline cursor-pointer">Send
                    again</span></Label>
                <Label>Don't have an account? <router-link to="register" class="text-blue-500 hover:underline">Register
                        now</router-link></Label>

            </CardFooter>
        </Card>
    </div>
</template>

<script setup>
// imports
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
import router from '@/router';

// variables
const verificationFailed = ref(false);
const validPin = ref(false);
const pin = ref(0);
const email = localStorage.getItem('email');

// allows you to check whether the pin entered matches the pin generated
async function verifyPin() {
    validPin.value = false;
    verificationFailed.value = false;
    if (isValidPin(pin.value)) {
        if (!isNullOrEmpty(email) && isEmailValid(email)) {
            try {
                var code = pin.value;
                // richiama l'API per inviare l'email
                const response = await axios.post(`${apiServerAddress}/auth/verify?code=${code}&email=${email}`);
                const token = response.data.token;
                if (!isNullOrEmpty(token)) {
                    localStorage.setItem('token', token);
                    localStorage.removeItem('email');
                    router.push('/');
                } else {
                    verificationFailed.value = true;
                }
            } catch (error) {
                verificationFailed.value = true;
            }

        } else {
            router.push('/login');
        }

    } else {
        validPin.value = true;
    }
}

// allows you to resend the email in case it did not arrive
async function sendAgain() {
    try {
        await axios.get(`${apiServerAddress}/auth/sendAgain?email=${email}`);
        
    } catch (error) {
        console.log("Error!");
    }
}

// check if the pin is valid
function isValidPin(pin) {
    const pinNumber = parseInt(pin);
    if (!isNaN(pinNumber)) {
        return (pinNumber >= 100000 && pinNumber <= 999999);
    } else {
        return false;
    }
}

// utils
function isNullOrEmpty(str) {
    return !str || str.trim() === '';
}
function isEmailValid(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}
</script>