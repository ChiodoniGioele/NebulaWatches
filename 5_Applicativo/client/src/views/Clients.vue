<template>
  <div class="flex h-screen">
    <Sidebar />
    <div class="flex flex-col w-full">
      <div class="py-6 px-8">
        <div class="mt-5 flex gap-7 items-center">
          <div>
            <Button @click="$router.back()" variant="secondary">
              <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
              <!-- Go back -->
            </Button>
          </div>

          <div class="w-full flex gap-7 items-center"></div>
          <Dialog :open="restOpen" @update:open="setNotVisible">
            <DialogTrigger as-child>
              <Button variant="outline">
                New Client
              </Button>
            </DialogTrigger>
            <DialogContent class="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>New Client</DialogTitle>
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
              <DialogFooter>
                <Button @click="saveClient">
                  Save
                </Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>
        </div>
      </div>



        <div class="px-10 flex">
          <h1 class="font-semibold "> Client </h1>
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
                <ClientChartSpecific></ClientChartSpecific>
              </div>
            </div>
          </TabsContent>
          <TabsContent value="specific">
            <div class="flex justify-center">
              <div class="w-1/3">
                <ClientChartGeneral></ClientChartGeneral>
              </div>
            </div>
          </TabsContent>
        </Tabs>



      <div class="mt-12 w-full gap-7 flex items-center justify-center px-10 ">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead class="w-[100px]">
                Name
              </TableHead>
              <TableHead>Surname</TableHead>
              <TableHead>Email</TableHead>
              <TableHead>Phone</TableHead>
              <!--<TableHead>Acquisti</TableHead> -->
              <TableHead></TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            <TableRow v-for="client in clients" :key="client.id">
              <TableCell class="font-medium">
                {{ client.name }}
              </TableCell>
              <TableCell>{{ client.surname }}</TableCell>
              <TableCell>{{ client.email }}</TableCell>
              <TableCell>{{ client.phone }}</TableCell>

              <!--<TableCell>
                {{ client.acquisti }}
              </TableCell>  -->

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
                        <Input id="nameMod" v-model="client.name" class="col-span-3" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label for="surnameMod" class="text-right">
                          Surname *
                        </Label>
                        <Input id="surnameMod" v-model="client.surname" class="col-span-3" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label for="emailMod" class="text-right">
                          Email *
                        </Label>
                        <Input id="emailMod" v-model="client.email" type="email" class="col-span-3" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label for="phoneMod" class="text-right">
                          Phone
                        </Label>
                        <Input id="phoneMod" v-model="client.phone" class="col-span-3" />
                      </div>
                    </div>
                    <DialogFooter>
                      <DialogClose as-child>
                        <Button @click="mod(client.id)">
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
                        This action cannot be undone. This will delete the client and all of his storage data.
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction @click="del(client.id)">Delete</AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
                <Dialog>
                  <DialogTrigger>
                    <Button variant="outline">Details</Button>
                  </DialogTrigger>
                  <DialogOverlay>
                    <DialogContent class="max-h-[80vh] max-w-[70vw]">
                      <DialogHeader>
                        <DialogTitle>Informations of {{ client.name }} {{ client.surname }}
                        </DialogTitle>
                        <DialogDescription>
                          <b>Email:</b> {{ client.email }} <b>Phone:</b> {{client.phone }}
                        </DialogDescription>
                      </DialogHeader>
                      <ClientExpansion :key="client.id" :client="client"></ClientExpansion>
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
import Chat from '@/components/Chat.vue'
import { AlertCircle } from 'lucide-vue-next'
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'



import ClientExpansion from "@/components/ClientExpansion.vue";
import ClientChartGeneral from "@/components/ClientChartGeneral.vue";

import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
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

import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import TeamChartGeneral from "@/components/TeamChartGeneral.vue";
import TeamChartSpecific from "@/components/TeamChartSpecific.vue";
import ClientChartSpecific from "@/components/ClientChartSpecific.vue";

const clients = ref([]);
const emailUser = ref('');
const emailNotValid = ref(false);
const emptyFields = ref(false);
const restOpen = ref(false);


async function fetchClients(email) {
  try {
    const response = await axios.get(`${apiServerAddress}/v1/clients/all/${email}`,
      {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
        },
      });
    clients.value = response.data;
  } catch (error) {
    console.error('Failed to fetch clients:', error);
  }
}

const name = ref('');
const surname = ref('');
const email = ref('');
const phone = ref('');
const saveFailed = ref(false);

async function saveClient() {
  const newClient = {
    name: name.value,
    surname: surname.value,
    email: email.value,
    phone: phone.value,
    notes: "",
    userEmail: emailUser.value
  }
  emptyFields.value = false;
  emailNotValid.value = false;
  if (isNullOrEmpty(newClient.name) || isNullOrEmpty(newClient.surname) || isNullOrEmpty(newClient.email)) {
    emptyFields.value = true;
  }

  if (!emptyFields.value) {
    if (isEmailValid(newClient.email)) {
      try {
        const response = await axios.post(`${apiServerAddress}/v1/clients/add`, newClient,
          {
            headers: {
              Authorization: 'Bearer ' + localStorage.getItem('token'),
            },
          });
          setNotVisible();
        fetchClients(emailUser.value);
        console.log("Client saved!", response.data)

      } catch (error) {
        console.error('Registration failed:', error);
        saveFailed.value = true;
      }
      name.value = null;
      surname.value = null;
      email.value = null;
      phone.value = null;
    } else {
      emailNotValid.value = true;
    }
  }
}


onMounted(async () => {
  const token = localStorage.getItem('token');
  const parts = token.split('.');
  const payload = JSON.parse(atob(parts[1]));
  emailUser.value = payload.sub;
  await fetchClients(emailUser.value);
});

async function mod(id) {
  const userMod = {
    name: nameMod.value,
    surname: surnameMod.value,
    email: emailMod.value,
    phone: phoneMod.value,
    notesMod: ""
  }
  try {
    const response = await axios.put(`${apiServerAddress}/v1/clients/update/${id}`, userMod,
      {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
        },
      });

    fetchClients(emailUser.value);

  } catch (error) {
    console.error('Registration failed:', error);
    saveFailed.value = true;
  }
}

async function del(id) {
  try {
    const response = await axios.delete(`${apiServerAddress}/v1/clients/delete/${id}`,
      {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
        },
      });
    fetchClients(emailUser.value);

  } catch (error) {

    console.error('Registration failed:', error);
    saveFailed.value = true;
  }
}

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





</script>
