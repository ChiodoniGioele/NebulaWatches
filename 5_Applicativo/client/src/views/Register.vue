<!--
this page allows users to register.
-->
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
                    <Input id="username" type="text" v-model="username" placeholder="ExampleUser" class="bg-gray-50"
                        required />
                </div>
                <div class="grid gap-2">
                    <Label for="email">Email</Label>
                    <Input id="email" type="email" v-model="email" placeholder="example@gmail.com" class="bg-gray-50"
                        required />
                </div>
                <div class="grid gap-2">
                    <Label for="password">Password</Label>
                    <Input id="password" type="password" v-model="password" placeholder="YourPassword"
                        class="bg-gray-50" required />
                </div>
                <Button class="w-full" @click="register">
                    Register
                </Button>
                <Alert variant="destructive" v-if="registerFailed">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Registration failed, email alredy used!
                    </AlertDescription>
                </Alert>
                <Alert variant="destructive" v-if="passwordShort">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Password must be at least 5 characters long and contain at least 1 uppercase letter, 1 lowercase
                        letter, 1 number, and 1 special character.
                    </AlertDescription>
                </Alert>
                <Alert variant="destructive" v-if="emptyFields">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Registration failed, please fill all fields!
                    </AlertDescription>
                </Alert>
                <Alert variant="destructive" v-if="emailNotValid">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Registration failed, please provide an correct email!
                    </AlertDescription>
                </Alert>
                <Alert variant="destructive" v-if="passwordLong">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Failed, password too long! Max 25 charachters
                    </AlertDescription>
                </Alert>
                <Alert variant="destructive" v-if="emailUsed">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Failed, email is already used!
                    </AlertDescription>
                </Alert>
                <Alert variant="destructive" v-if="inputTooLong">
                    <AlertCircle class="w-4 h-4" />
                    <AlertTitle>Error</AlertTitle>
                    <AlertDescription>
                        Failed, email or username too long, max 50 charachters!
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

<script setup>
// import
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
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { AlertCircle } from 'lucide-vue-next'

import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

import { apiServerAddress } from '@/main.ts'

// varibles
const username = ref('');
const email = ref('');
const password = ref('');
const router = useRouter();
const registerFailed = ref(false);
const passwordShort = ref(false);
const emailNotValid = ref(false);
const emptyFields = ref(false);
const passwordLong = ref(false);
const emailUsed = ref(false);
const inputTooLong = ref(false);


// This function registers the new user
async function register() {
    emptyFields.value = false;
    passwordShort.value = false;
    emailNotValid.value = false;
    passwordLong.value = false;
    registerFailed.value = false;
    isEmailUsed.value = false;
    inputTooLong.value = false;
    if (!isNullOrEmpty(username.value) && !isNullOrEmpty(email.value) && !isNullOrEmpty(password.value)) {
        if (isPasswordValid(password.value)) {
            if (!tooLong(password.value)) {
                if (isEmailValid(email.value)) {
                    if (! await isEmailUsed(email.value)) {
                        if (isGoodLenght(email.value) && isGoodLenght(username.value)) {
                            try {
                                const response = await axios.post(`${apiServerAddress}/auth/register`, {
                                    username: username.value,
                                    email: email.value,
                                    password: password.value
                                });

                                localStorage.setItem('email', email.value);
                                router.push('/verify');
                            } catch (error) {
                                registerFailed.value = true;
                            }
                        }else{
                            inputTooLong.value = true;
                        }
                        
                    } else {
                        emailUsed.value = true;
                    }

                } else {
                    emailNotValid.value = true;
                }
            } else {
                passwordLong.value = true;
            }

        } else {
            passwordShort.value = true;
        }

    } else {
        emptyFields.value = true;
    }
}

// This function allows you to check whether the email has already been used
async function isEmailUsed(email) {
    try {
        const response = await axios.get(`${apiServerAddress}/auth/exists/${email}`, {
            headers: {

            },
        });
        return response.data;
    } catch (error) {
        console.error('Failed to check email:', error);
    }
}

//Utils
function isNullOrEmpty(str) {
    return !str || str.trim() === '';
}
function isPasswordValid(password) {
    if (password.length < 5) {
        return false;
    }
    const complexityRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/;
    if (!complexityRegex.test(password)) {
        return false;
    }
    return true;
}
function isEmailValid(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}
function tooLong(password) {
    if (password.length > 25) {
        return true;
    }
}
function isGoodLenght(testString) {
    if (testString.length < 50) {
        return true;
    }
}
</script>