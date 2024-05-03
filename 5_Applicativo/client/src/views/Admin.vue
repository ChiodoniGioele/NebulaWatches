<template>
    <section class="bg-gray-50 dark:bg-gray-900 p-3 sm:p-5 antialiased">

        <div class="mx-auto max-w-screen-2xl px-4 lg:px-12">
            <div class="flex space-x-3 justify-between">
                <h2 class="text-3xl font-bold tracking-tight mb-10 mt-1">User Dashboard </h2>
                <Button variant="outline" class="h-12 flex-">
                    <img class="w-5 h-5" src="@/assets/icons/Exit.png" alt="Logout image" @click="logout">
                </Button>
            </div>

            <div class="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden">
                <div
                    class="flex flex-col md:flex-row md:items-center md:justify-between space-y-3 md:space-y-0 md:space-x-4 p-4">
                    <div class="flex-1 flex items-center space-x-2">
                        <h5>
                            <span class="text-gray-500">Users: </span>
                            <span class="dark:text-white">{{ getNumberOfUsers() }}</span>
                        </h5>
                    </div>

                </div>
                <div
                    class="flex flex-col md:flex-row items-stretch md:items-center md:space-x-3 space-y-3 md:space-y-0 justify-between mx-4 py-4 border-t dark:border-gray-700">
                    <div class="w-full md:w-1/2">
                        <form class="flex items-center" @submit.prevent>
                            <label for="simple-search" class="sr-only">Search</label>
                            <div class="relative w-full">
                                <Input @keyup="searchUser()" v-model="searchContent" type="text" placeholder="Search for users" />
                            </div>
                        </form>
                    </div>

                    <div class="flex gap-2 w-auto">
                        <Dialog :open="restOpen" @update:open="setNotVisible">
                            <DialogTrigger as-child>
                                <Button variant="outline" class="h-12">
                                    <p>Add User</p>
                                </Button>
                            </DialogTrigger>
                            <DialogContent class="sm:max-w-[425px]">
                                <DialogHeader>
                                    <DialogTitle>Add User</DialogTitle>
                                    <AlertDialogDescription>
                                        Here you can add a new user.
                                    </AlertDialogDescription>
                                </DialogHeader>
                                <div class="grid gap-4 py-4">
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="email" class="text-right">
                                            Email *
                                        </Label>
                                        <Input id="email" class="col-span-3" v-model="newUser.email" required />
                                    </div>
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="username" class="text-right">
                                            Username *
                                        </Label>
                                        <Input id="username" class="col-span-3" v-model="newUser.username" required />
                                    </div>
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label for="password" class="text-right">
                                            Password *
                                        </Label>
                                        <Input id="password" class="col-span-3" type="password"
                                            v-model="newUser.password" required />
                                    </div>
                                    <!--<div class="grid grid-cols-4 items-center gap-4">
                                        <Label class="text-right">
                                            Mode
                                        </Label>
                                        <div class="col-span-3">
                                            <Select v-model="newUser.loginMode">
                                                <SelectTrigger>
                                                    <SelectValue />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectGroup>
                                                        <SelectItem value=false>
                                                            Account
                                                        </SelectItem>
                                                        <SelectItem value=true>
                                                            Google
                                                        </SelectItem>
                                                    </SelectGroup>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                    </div>-->
                                    <div class="grid grid-cols-4 items-center gap-4">
                                        <Label class="text-right">
                                            Verified
                                        </Label>
                                        <div class="col-span-3">
                                            <Select v-model="newUser.verified">
                                                <SelectTrigger>
                                                    <SelectValue />
                                                </SelectTrigger>
                                                <SelectContent>
                                                    <SelectGroup>
                                                        <SelectItem value=true>
                                                            True
                                                        </SelectItem>
                                                        <SelectItem value=false>
                                                            False
                                                        </SelectItem>
                                                    </SelectGroup>
                                                </SelectContent>
                                            </Select>
                                        </div>
                                    </div>
                                </div>
                                <Alert variant="destructive" v-if="emptyFields">
                                    <AlertCircle class="w-4 h-4" />
                                    <AlertTitle>Error</AlertTitle>
                                    <AlertDescription>
                                        Failed, please fill email, username and password
                                    </AlertDescription>
                                </Alert>
                                <Alert variant="destructive" v-if="emailNotValid">
                                    <AlertCircle class="w-4 h-4" />
                                    <AlertTitle>Error</AlertTitle>
                                    <AlertDescription>
                                        Failed, please provide a valid email!
                                    </AlertDescription>
                                </Alert>
                                <Alert variant="destructive" v-if="passwordShort">
                                    <AlertCircle class="w-4 h-4" />
                                    <AlertTitle>Error</AlertTitle>
                                    <AlertDescription>
                                        Password must be at least 5 characters long and contain at least 1 uppercase
                                        letter, 1 lowercase letter, 1 number, and 1 special character.
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
                                <DialogFooter>
                                    <Button type="submit" @click="saveUser">
                                        Save User
                                    </Button>
                                </DialogFooter>
                            </DialogContent>
                        </Dialog>
                    </div>

                </div>

                <div class="overflow-x-auto">
                    <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                            <tr>
                                <th scope="col" class="p-4">Email</th>
                                <th scope="col" class="p-4">Username</th>
                                <th scope="col" class="p-4">Password</th>
                                <th scope="col" class="p-4">Role</th>
                                <th scope="col" class="p-4">Login Mode</th>
                                <th scope="col" class="p-4">Verified</th>
                                <th scope="col" class="p-4">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(user, index) in users" :key="index"
                                class="border-b dark:border-gray-600 hover:bg-gray-100 dark:hover:bg-gray-700">
                                <td class="p-4 text-gray-700 font-medium">{{ user.email }}</td>
                                <td class="p-4">{{ user.name }}</td>
                                <td class="p-4">********</td>
                                <td class="p-4">{{ user.role }}</td>
                                <td class="p-4">{{ user.loginMode ? 'Google' : 'Account' }}</td>
                                <td class="p-4">{{ user.verified }}</td>

                                <td class="px-4 py-3 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                    <div class="flex items-center space-x-4">
                                        <div class="flex gap-2 w-auto">
                                            <Dialog>
                                                <DialogTrigger as-child>
                                                    <Button variant="outline" class="h-12" @click="selectedUser = user">
                                                        <p>Edit User</p>
                                                    </Button>
                                                </DialogTrigger>
                                                <DialogContent class="sm:max-w-[425px]">
                                                    <DialogHeader>
                                                        <DialogTitle>Edit User</DialogTitle>
                                                        <AlertDialogDescription>
                                                            Here you can edit this user.
                                                        </AlertDialogDescription>
                                                    </DialogHeader>
                                                    <div class="grid gap-4 py-4">
                                                        <div class="grid grid-cols-4 items-center gap-4">
                                                            <Label for="email" class="text-right">
                                                                Email *
                                                            </Label>
                                                            <Input id="email" class="col-span-3"
                                                                v-model="selectedUser.email" />
                                                        </div>
                                                        <div class="grid grid-cols-4 items-center gap-4">
                                                            <Label for="username" class="text-right">
                                                                Username *
                                                            </Label>
                                                            <Input id="username" class="col-span-3"
                                                                v-model="selectedUser.name" />
                                                        </div>
                                                        <div class="grid grid-cols-4 items-center gap-4">
                                                            <Label for="password" class="text-right">
                                                                Password *
                                                            </Label>
                                                            <Input id="password" class="col-span-3" type="password"
                                                                v-model="editUser.password" />
                                                        </div>
                                                        <!--<div class="grid grid-cols-4 items-center gap-4">
                                                            <Label class="text-right">
                                                                Mode
                                                            </Label>
                                                            <div class="col-span-3">
                                                                <Select default-value="{{ selectedUser.loginMode }}"
                                                                    v-model="selectedUser.loginMode">
                                                                    <SelectTrigger>
                                                                        <SelectValue />
                                                                    </SelectTrigger>
                                                                    <SelectContent>
                                                                        <SelectGroup>
                                                                            <SelectItem value=false>
                                                                                Account
                                                                            </SelectItem>
                                                                            <SelectItem value=true>
                                                                                Google
                                                                            </SelectItem>
                                                                        </SelectGroup>
                                                                    </SelectContent>
                                                                </Select>
                                                            </div>
                                                        </div>-->
                                                        <div class="grid grid-cols-4 items-center gap-4">
                                                            <Label class="text-right">
                                                                Verified
                                                            </Label>
                                                            <div class="col-span-3">
                                                                <Select default-value="{{ selectedUser.verified }}"
                                                                    v-model="selectedUser.verified">
                                                                    <SelectTrigger>
                                                                        <SelectValue />
                                                                    </SelectTrigger>
                                                                    <SelectContent>
                                                                        <SelectGroup>
                                                                            <SelectItem value=true>
                                                                                True
                                                                            </SelectItem>
                                                                            <SelectItem value=false>
                                                                                False
                                                                            </SelectItem>
                                                                        </SelectGroup>
                                                                    </SelectContent>
                                                                </Select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <DialogFooter>
                                                        <DialogClose as-child>
                                                            <Button type="submit" @click="updateUser">
                                                                Save
                                                            </Button>
                                                        </DialogClose>
                                                    </DialogFooter>
                                                </DialogContent>
                                            </Dialog>
                                        </div>

                                        <AlertDialog>
                                            <AlertDialogTrigger as-child>
                                                <Button variant="destructive">
                                                    Delete
                                                </Button>
                                            </AlertDialogTrigger>
                                            <AlertDialogContent>
                                                <AlertDialogHeader>
                                                    <AlertDialogTitle>Confirm delete</AlertDialogTitle>
                                                    <AlertDialogDescription>
                                                        Are you sure that you want to delete this user? All his related
                                                        data willl be deleted!
                                                    </AlertDialogDescription>
                                                </AlertDialogHeader>
                                                <AlertDialogFooter>
                                                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                                                    <AlertDialogAction @click="deleteUser(user.email)">Delete
                                                    </AlertDialogAction>
                                                </AlertDialogFooter>
                                            </AlertDialogContent>
                                        </AlertDialog>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <nav class="flex flex-col md:flex-row justify-between items-start md:items-center space-y-3 md:space-y-0 p-4"
                    aria-label="Table navigation">
                    <span class="text-sm font-normal text-gray-500 dark:text-gray-400">
                        Showing
                        <span class="font-semibold text-gray-900 dark:text-white">1-{{ getNumberOfUsers() }}</span>
                        of
                        <span class="font-semibold text-gray-900 dark:text-white">{{ getNumberOfUsers() }}</span>
                    </span>
                </nav>
            </div>
        </div>
        <div class="mt-12 px-1 w-full gap-7 flex items-center justify-center">
            <Pagination v-slot="{ page }" :total="totalPages * 10" :sibling-count="3" show-edges :default-page="1">
                <PaginationList v-slot="{ items }" class="flex items-center gap-1 w-full">
                    <PaginationFirst @click="fetchBrands(1)" />
                    <PaginationPrev @click="fetchBrands(actualPage - 1)" />

                    <template v-for="(item, index) in items">
                        <PaginationListItem v-if="item.type === 'page'" :key="index" :value="item.value" as-child>
                            <Button class="w-10 h-10 p-0" :variant="item.value === page ? 'default' : 'outline'"
                                @click="fetchBrands(item.value); scrollToTop()">
                                {{ item.value }}
                            </Button>
                        </PaginationListItem>
                        <PaginationEllipsis v-else :key="item.type" :index="index" />
                    </template>

                    <PaginationNext @click="fetchBrands(actualPage + 1)" />
                    <PaginationLast @click="fetchBrands(totalPages)" />
                </PaginationList>
            </Pagination>
        </div>
    </section>
</template>

<script setup>
import Sidebar from '@/components/Sidebar.vue'
import { AlertCircle } from 'lucide-vue-next'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'

import { Input } from '@/components/ui/input'
import { Button } from '@/components//ui/button'
import { Label } from '@/components/ui/label'
import { useRouter } from 'vue-router';
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
    DialogClose,
} from '@/components/ui/dialog'
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
import {
    Pagination,
    PaginationEllipsis,
    PaginationFirst,
    PaginationLast,
    PaginationList,
    PaginationListItem,
    PaginationNext,
    PaginationPrev,
} from '@/components/ui/pagination'
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from '@/components/ui/select'

import { apiServerAddress } from '@/main.ts'

import axios from 'axios';
import { ref, onMounted, onBeforeMount } from 'vue';
import { useRoute } from 'vue-router';
import { Trash2 } from 'lucide-vue-next'

const route = useRoute();
const router = useRouter();
const users = ref([]);
const role = ref("");
const selectedUser = ref({});
const sel = ref(0);
const searchContent = ref();
const emailNotValid = ref(false);
const emptyFields = ref(false);
const restOpen = ref(false);
const passwordShort = ref(false);
const passwordLong = ref(false);
const emailUsed = ref(false);
const totalPages = ref(1);
const actualPage = ref(1)

async function fetchUsers() {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/admin/getUsers`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        users.value = response.data;
    } catch (error) {
        console.error('Failed to fetch users', error);
    }
}
const getNumberOfUsers = () => {
    return users.value.length;
};

async function toHome() {
    router.push('/');
}

async function getRole(userEmail) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/admin/getRole/${userEmail}`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        role.value = response.data;
    } catch (error) {
        console.error('Failed to fetch Role', error);
    }
}

async function deleteUser(userEmail) {
    try {
        const response = await axios.post(`${apiServerAddress}/v1/admin/deleteUser`, { "email": userEmail }, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });

        console.log('Watch removed from storage. ', response.data);
        await fetchUsers();
    } catch (error) {
        console.error('Failed to remove watch from storage:', error);
    }
}

async function isEmailUsed(email) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/admin/isEmailUsed/${email}`, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });
        return response.data;
    } catch (error) {
        console.error('Failed to check email:', error);
    }
}

const newUser = {
    id: 0,
    email: "",
    loginMode: "",
    password: "",
    username: "",
    verified: ""
};

async function saveUser() {
    emailNotValid.value = false;
    emptyFields.value = false;
    passwordShort.value = false;
    passwordLong.value = false;
    newUser.loginMode = false;
    emailUsed.value = false;

    if (!isNullOrEmpty(newUser.email) && !isNullOrEmpty(newUser.username) && !isNullOrEmpty(newUser.password)) {
        if (isEmailValid(newUser.email)) {
            if (isPasswordValid(newUser.password)) {
                if (!tooLong(newUser.password)) {
                    if(! await isEmailUsed(newUser.email)){
                        try {
                        const response = await axios.post(`${apiServerAddress}/v1/admin/saveUser`, newUser, {
                            headers: {
                                Authorization: 'Bearer ' + localStorage.getItem('token'),
                            },
                        });
                        console.log('User saved. ', response.data);
                        setNotVisible();
                        await fetchUsers();

                        newUser.email = "";
                        newUser.password = "";
                        newUser.username = "";
                        newUser.verified = "";
                    } catch (error) {
                        console.error('Failed to save user:', error);
                    }
                    }else{
                        emailUsed.value = true;
                    }
                    
                } else {
                    passwordLong.value = true;
                }
            } else {
                passwordShort.value = true;
            }
        } else {
            emailNotValid.value = true;
        }
    } else {
        emptyFields.value = true;
    }
}

const editUser = {
    id: 0,
    email: "",
    loginMode: "",
    password: "",
    username: "",
    verified: ""
};


async function updateUser() {
    editUser.loginMode = false;
    try {
        await setValues();

        const response = await axios.post(`${apiServerAddress}/v1/admin/updateUser`, editUser, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
        });
        console.log('User updated. ', response.data);
        await delValues();
        await fetchUsers();
    } catch (error) {
        console.error('Failed to update user:', error);
    }
}

async function setValues() {
    editUser.id = selectedUser.value.id;
    editUser.email = selectedUser.value.email;
    editUser.loginMode = selectedUser.value.loginMode;
    editUser.username = selectedUser.value.name;
    editUser.verified = selectedUser.value.verified;
}

async function delValues() {
    editUser.id = 0;
    editUser.email = "";
    editUser.loginMode = "";
    editUser.username = "";
    editUser.verified = "";
}

const searchCt = {
    searchContent: ""
};

async function searchUser() {
    searchCt.searchContent = searchContent.value;
    try {
        const response = await axios.post(`${apiServerAddress}/v1/admin/searchUser`, searchCt, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('token')
            },
        });

        console.log('Searched for ' + searchCt.searchContent);
        users.value = response.data;
    } catch (error) {
        console.error('Failed to search user:', error);
    }
}

async function logout() {
    try {
        localStorage.removeItem('token');
        router.push('/login');
    } catch (error) {
        console.error(error)
    }
}

onMounted(async () => {
    await fetchUsers();
});

onBeforeMount(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    const email = payload.sub;

    await getRole(email);
    if (role.value != "ADMIN") {
        router.push('/');
    }

});

//Utils
function isNullOrEmpty(str) {
    return !str || str.trim() === '';
}
function isEmailValid(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}
function setNotVisible() {
    restOpen.value = !restOpen.value;
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
function tooLong(password) {
    if (password.length > 25) {
        return true;
    }
}
</script>
