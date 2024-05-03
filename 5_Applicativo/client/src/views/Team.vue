<!--
  This page allows you to see my team members, add a new member.
  It also shows the sales graph of the respective team members.
-->
<template>
    <div class="flex h-screen">
        <Sidebar />
        <div class="flex flex-col w-full">
            <div class="px-8 py-6">

                <div class="mt-5 flex gap-7 items-center">
                    <!--<div>
              <Button @click="$router.back()" variant="secondary">
                <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
              </Button>
            </div>-->

                    <div class="w-full flex gap-7 items-center"></div>
                    <Dialog :open="restOpen" @update:open="setNotVisible">
                        <DialogTrigger as-child>
                            <Button variant="outline">
                                New Team Member
                            </Button>
                        </DialogTrigger>
                        <DialogContent class="sm:max-w-[425px]">
                            <DialogHeader>
                                <DialogTitle>New Team Member</DialogTitle>
                            </DialogHeader>
                            <div class="grid gap-4 py-4">
                                <div class="grid grid-cols-4 items-center gap-4">

                                    <Label for="name" class="text-right">
                                        Name *
                                    </Label>
                                    <Input id="name" v-model="name" class="col-span-3" />

                                </div>
                                <div class="grid grid-cols-4 items-center gap-4">

                                    <Label for="surname" class="text-right">
                                        Surname *
                                    </Label>
                                    <Input id="surname" v-model="surname" class="col-span-3" />

                                </div>

                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="email" class="text-right">
                                        Email *
                                    </Label>
                                    <Input id="email" v-model="email" class="col-span-3" />

                                </div>

                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="phone" class="text-right">
                                        Phone
                                    </Label>
                                    <Input id="phone" v-model="phone" class="col-span-3" />
                                </div>

                                <div class="grid grid-cols-4 items-center gap-4">
                                    <Label for="role" class="text-right">
                                        Role
                                    </Label>
                                    <Input id="phone" v-model="role" class="col-span-3" />
                                </div>

                            </div>
                            <Alert variant="destructive" v-if="emptyFields">
                                <AlertCircle class="w-4 h-4" />
                                <AlertTitle>Error</AlertTitle>
                                <AlertDescription>
                                    Failed, please fill name, surname and email!
                                </AlertDescription>
                            </Alert>
                            <Alert variant="destructive" v-if="emailNotValid">
                                <AlertCircle class="w-4 h-4" />
                                <AlertTitle>Error</AlertTitle>
                                <AlertDescription>
                                    Failed, please provide a valid email!
                                </AlertDescription>
                            </Alert>
                            <Alert variant="destructive" v-if="phoneNotValid">
                                <AlertCircle class="w-4 h-4" />
                                <AlertTitle>Error</AlertTitle>
                                <AlertDescription>
                                    Failed, please provide a valid phone number!
                                </AlertDescription>
                            </Alert>
                            <Alert variant="destructive" v-if="roleNotValid">
                                <AlertCircle class="w-4 h-4" />
                                <AlertTitle>Error</AlertTitle>
                                <AlertDescription>
                                    Failed, please provide a valid role, less than 50 charachters!!
                                </AlertDescription>
                            </Alert>
                            <Alert variant="destructive" v-if="inputTooLong">
                                <AlertCircle class="w-4 h-4" />
                                <AlertTitle>Error</AlertTitle>
                                <AlertDescription>
                                    Failed, email, name or surname too long, max 50 charachters!
                                </AlertDescription>
                            </Alert>
                            <DialogFooter>
                                <Button @click="saveTeam">
                                    Save
                                </Button>
                            </DialogFooter>
                        </DialogContent>
                    </Dialog>
                </div>


            </div>

            <div class="px-10 flex">
                <h1 class="font-semibold "> Team </h1>
            </div>

            <Tabs default-value="general" class="px-10 mt-5">
                <TabsList>
                    <TabsTrigger value="general">
                        General
                    </TabsTrigger>
                    <TabsTrigger value="specific">
                        All Time
                    </TabsTrigger>
                </TabsList>
                <TabsContent value="general">
                    <div class="flex justify-center">
                        <div class="w-1/2">
                            <TeamChartSpecific></TeamChartSpecific>
                        </div>
                    </div>
                </TabsContent>
                <TabsContent value="specific">
                    <div class="flex justify-center">
                        <div class="w-1/3">
                            <TeamChartGeneral></TeamChartGeneral>
                        </div>
                    </div>
                </TabsContent>
            </Tabs>
            <br>
            <div class="px-10 flex mt-7">
                <h1 class="font-semibold "> Team Members </h1>
            </div>

            <div class="mt-5 w-full gap-7 flex items-center justify-center px-10 ">
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead class="w-[100px]">
                                Name
                            </TableHead>
                            <TableHead>Surname</TableHead>
                            <TableHead>Email</TableHead>
                            <TableHead>Phone</TableHead>
                            <TableHead>Role</TableHead>
                            <TableHead></TableHead>
                            <TableHead></TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        <TableRow v-for="team in teams" :key="team.id">

                            <TableCell>{{ team.name }}</TableCell>
                            <TableCell>{{ team.surname }}</TableCell>
                            <TableCell>{{ team.email }}</TableCell>
                            <TableCell>{{ team.phone }}</TableCell>
                            <TableCell>{{ team.role }}</TableCell>

                            <TableCell class="flex space-x-4">
                                <Dialog>
                                    <DialogTrigger as-child>
                                        <Button variant="outline">
                                            MODIFY
                                        </Button>
                                    </DialogTrigger>
                                    <DialogContent class="sm:max-w-[425px]">
                                        <DialogHeader>
                                            <DialogTitle></DialogTitle>
                                        </DialogHeader>
                                        <div class="grid gap-4 py-4">
                                            <div class="grid grid-cols-4 items-center gap-4">
                                                <Label for="nameMod" class="text-right">
                                                    Name *
                                                </Label>
                                                <Input id="nameMod" v-model="team.name" class="col-span-3" />
                                            </div>
                                            <div class="grid grid-cols-4 items-center gap-4">

                                                <Label for="surnameMod" class="text-right">
                                                    Surname *
                                                </Label>
                                                <Input id="surnameMod" v-model="team.surname" class="col-span-3" />
                                            </div>
                                            <div class="grid grid-cols-4 items-center gap-4">
                                                <Label for="emailMod" class="text-right">
                                                    Email *
                                                </Label>
                                                <Input id="emailMod" v-model="team.email" type="email"
                                                    class="col-span-3" />
                                            </div>

                                            <div class="grid grid-cols-4 items-center gap-4">
                                                <Label for="phoneMod" class="text-right">
                                                    Phone
                                                </Label>
                                                <Input id="phoneMod" v-model="team.phone" class="col-span-3" />
                                            </div>

                                            <div class="grid grid-cols-4 items-center gap-4">
                                                <Label for="roleMod" class="text-right">
                                                    Role
                                                </Label>
                                                <Input id="roleMod" v-model="team.role" class="col-span-3" />
                                            </div>
                                        </div>
                                        <DialogFooter>
                                            <DialogClose as-child>
                                                <Button @click="mod(team.id)">
                                                    Update
                                                </Button>
                                            </DialogClose>
                                        </DialogFooter>
                                    </DialogContent>
                                </Dialog>
                                <AlertDialog>
                                    <AlertDialogTrigger as-child>
                                        <Button variant="destructive">
                                            DELETE
                                        </Button>
                                    </AlertDialogTrigger>
                                    <AlertDialogContent>
                                        <AlertDialogHeader>
                                            <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
                                            <AlertDialogDescription>
                                                This action cannot be undone. This will permanently delete this team
                                                member
                                                and all his related storage.
                                            </AlertDialogDescription>
                                        </AlertDialogHeader>
                                        <AlertDialogFooter>
                                            <AlertDialogCancel>Cancel</AlertDialogCancel>
                                            <AlertDialogAction @click="deleteTeam(team.id)">Delete</AlertDialogAction>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialog>
                            </TableCell>
                            <TableCell>
                                <Dialog>
                                    <DialogTrigger>
                                        <Button variant="outline">Details</Button>
                                    </DialogTrigger>
                                    <DialogOverlay>
                                        <DialogContent class="max-h-[80vh] max-w-[70vw]">
                                            <DialogHeader>
                                                <DialogTitle>Informations of {{ team.name }} {{ team.surname }}
                                                </DialogTitle>
                                                <DialogDescription><b>Email:</b> {{ team.email }} <b>Phone:</b> {{
                        team.phone }} <b>Role:</b> {{ team.role }}</DialogDescription>
                                            </DialogHeader>

                                            <TeamExpansion :key="team.id" :team="team"></TeamExpansion>
                                        </DialogContent>

                                    </DialogOverlay>
                                </Dialog>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>

            </div>
        </div>
    </div>
    <Chat />
</template>

<script setup>

// import
import Chat from '@/components/Chat.vue'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { AlertCircle } from 'lucide-vue-next'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
    DialogClose
} from '@/components/ui/dialog'
import { DialogOverlay } from 'radix-vue'
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

import { Label } from '@/components/ui/label'
import Sidebar from '@/components/Sidebar.vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components//ui/button'
import { apiServerAddress } from '@/main.ts'
import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from '@/components/ui/table'

import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import TeamExpansion from '@/components/TeamExpansion.vue'
import TeamChartGeneral from '@/components/TeamChartGeneral.vue';
import TeamChartSpecific from '@/components/TeamChartSpecific.vue';

// variables
const route = useRoute();
const router = useRouter();
const teams = ref([]);
const emailUser = ref('');
const user = ref();
const restOpen = ref(false);
const name = ref('');
const surname = ref('');
const email = ref('');
const phone = ref('');
const role = ref('');
const saveFailed = ref(false);
const emailNotValid = ref(false);
const emptyFields = ref(false);
const phoneNotValid = ref(false);
const roleNotValid = ref(false);
const inputTooLong = ref(false);

// returns all teams linked to the user
async function fetchTeam(email) {
    try {
        const response = await axios.get(`${apiServerAddress}/v1/team/getTeam/${email}`,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        teams.value = response.data;
    } catch (error) {
        console.error('Failed to fetch team:', error);
    }
}

// allows you to save a new team member.
async function saveTeam() {
    const newTeam = {
        name: name.value,
        surname: surname.value,
        email: email.value,
        phone: phone.value,
        role: role.value,
        userEmail: emailUser.value
    }
    emailNotValid.value = false;
    emptyFields.value = false;
    roleNotValid.value = false;
    phoneNotValid.value = false;
    inputTooLong.value = false;    

    if (isNullOrEmpty(newTeam.name) || isNullOrEmpty(newTeam.surname) || isNullOrEmpty(newTeam.email)) {
        emptyFields.value = true;
    }


    if (!emptyFields.value) {
        if (isEmailValid(newTeam.email)) {
            if (!isNullOrEmpty(newTeam.phone)) {
                if (!verifyPhone(newTeam.phone)) {
                    phoneNotValid.value = true;
                }
            }
            if (!isNullOrEmpty(newTeam.role)) {
                if (!verifyString(newTeam.role)) {
                    roleNotValid.value = true;
                }
            }
            if(verifyString(newTeam.email) && verifyString(newTeam.name) && verifyString(newTeam.surname)){
                if (!roleNotValid.value && !phoneNotValid.value) {
                try {
                    const response = await axios.post(`${apiServerAddress}/v1/team/add`, newTeam,
                        {
                            headers: {
                                Authorization: 'Bearer ' + localStorage.getItem('token'),
                            },
                        });

                    setNotVisible();
                    fetchTeam(emailUser.value);
                    console.log("Team member saved!", response.data)

                } catch (error) {
                    console.error('Save failed failed:', error);
                    saveFailed.value = true;
                }
                name.value = null;
                surname.value = null;
                email.value = null;
                phone.value = null;
            }
            }else{
                inputTooLong.value = true;
            }
            
        } else {
            emailNotValid.value = true;
        }
    }

}

// allows you to edit a team member
async function mod(id) {
    const teamMod = {
        id: id,
        name: nameMod.value,
        surname: surnameMod.value,
        email: emailMod.value,
        phone: phoneMod.value,
        role: roleMod.value,
        userEmail: emailUser.value
    }
    try {
        const response = await axios.post(`${apiServerAddress}/v1/team/updateTeam`, teamMod,
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            });

        fetchTeam(emailUser.value);
        console.log("Update successfull. " + response.data)
    } catch (error) {
        console.error('Change failed:', error);
        saveFailed.value = true;
    }
}

// allows you to disable/delete a team
async function deleteTeam(id) {
    try {
        const response = await axios.post(`${apiServerAddress}/v1/team/delete/${id}`,
            {},
            {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('token'),
                },
            }
        );

        console.log("Team member deleted successfully. " + response.data);
        await fetchTeam(emailUser.value);
    } catch (error) {
        console.error('Delete of team member failed:', error);
    }
}

// is called when the page is loaded
onMounted(async () => {
    const token = localStorage.getItem('token');
    const parts = token.split('.');
    const payload = JSON.parse(atob(parts[1]));
    emailUser.value = payload.sub;
    await fetchTeam(emailUser.value);
    localStorage.removeItem('search');
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
function verifyPhone(phoneNumber) {
    const phoneRegex = /^\+[1-9]\d{1,14}$/;
    return phoneRegex.test(phoneNumber);
}
function verifyString(inputString) {
    return inputString.length <= 50;
}
</script>