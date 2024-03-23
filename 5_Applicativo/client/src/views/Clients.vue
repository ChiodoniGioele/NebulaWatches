<template>
  <div class="grid lg:grid-cols-5 min-h-screen">
    <Sidebar class="hidden lg:block" />

    <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col">

      <div class="px-4 py-6 lg:px-8">


        <div class="mt-5 flex gap-7 items-center">
          <div>
            <Button @click="$router.back()" variant="secondary">
              <img class="max-h-[25px] opacity-40" src="@/assets/icons/back.png" />
              <!-- Go back -->
            </Button>
          </div>

          <div class="w-full flex gap-7 items-center"></div>
          <Dialog>
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
                    Name
                  </Label>
                  <Input id="name" v-model="name" class="col-span-3" />

                </div>
                <div class="grid grid-cols-4 items-center gap-4">

                  <Label for="surname" class="text-right">
                    Surname
                  </Label>
                  <Input id="surname" v-model="surname" class="col-span-3" />

                </div>

                <div class="grid grid-cols-4 items-center gap-4">
                  <Label for="email" class="text-right">
                    Email
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
              <DialogFooter>
                <DialogClose as-child>
                  <Button @click="saveClient">
                    Save
                  </Button>
                </DialogClose>
              </DialogFooter>
            </DialogContent>
          </Dialog>
        </div>


      </div>


      <div class="mt-12 px-1 flex gap-7 items-center">
        <div class="flex gap-2">
          <h1>grafico</h1>
        </div>
      </div>

      <!-- <div class="mt-5 flex flex-wrap gap-5">
                    <WatchFamilyCard v-for="family in families" :key="family.id" :family="family"
                        :brandName="brandName" />
                </div> -->


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
              <TableHead>Acquisti</TableHead>
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
              <TableCell>Ciao</TableCell>



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
                          Name
                        </Label>
                        <Input id="nameMod" v-model="client.name" class="col-span-3" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">

                        <Label for="surnameMod" class="text-right">
                          Surname
                        </Label>
                        <Input id="surnameMod" v-model="client.surname" class="col-span-3" />
                      </div>
                      <div class="grid grid-cols-4 items-center gap-4">
                        <Label for="emailMod" class="text-right">
                          Email
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
                        This action cannot be undone. This will permanently delete your
                        account and remove your data from our servers.
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction @click="del(client.id)">Delete</AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>

      </div>

    </div>


  </div>


</template>

<script setup>


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

const route = useRoute();
const router = useRouter();
const clients = ref([]);
const emailUser = ref('');
const user = ref();


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
  try {
    const response = await axios.post(`${apiServerAddress}/v1/clients/add`, newClient,
      {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('token'),
        },
      });
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




</script>
