<template>
  <div class="flex flex-col center justify-center h-screen">
    <div class="space-y-4 py-4 flex-1">
      <img src="@/assets/Logo.png" alt="">

      <div class="px-3 py-2 w-[100%] ">
        <div class="space-y-1 flex flex-wrap justify-center items-center">
          <div class="w-[60%]">
            <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Watch.png" />
              <span class="text-gray-800 text-base font-semibold">Watches</span>
            </Button>
          </div>

          <div class="w-[60%]">
            <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Boxes.png" />
              <span class="text-gray-800 text-base font-semibold">Storage</span>
            </Button>

          </div>

          <div class="w-[60%]">
            <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Customer.png" />
              <span class="text-gray-800 text-base font-semibold">Clients</span>
            </Button>

          </div>

          <div class="w-[60%]">
            <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/User Account.png" />
              <span class="text-gray-800 text-base font-semibold">Team</span>
            </Button>

          </div>

          <div class="w-[60%]">
            <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Chat.png" />
              <span class="text-gray-800 text-base font-semibold">Comunication</span>
            </Button>
          </div>

        </div>
        <div class="flex flex-col justify-end">
          <div role="list" class="max-w-sm divide-y divide-gray-200 dark:divide-gray-700">
              <div class="py-3 sm:py-4"></div>
              <div class="py-3 sm:py-4">
                  <Button variant="ghost" class="w-full justify-start">
                      <div class="flex items-center space-x-3 rtl:space-x-reverse">
                          <div class="flex-shrink-0">
                              <img class="w-8 h-8 rounded-full" src="@/assets/icons/Profile.png" alt="User image">  
                          </div>
                          <div class="flex-1 min-w-0">
                              <p class="text-sm font-semibold text-gray-900 truncate dark:text-white">
                                  Username
                              </p>
                              <p class="text-sm text-gray-500 truncate dark:text-gray-400">
                                  email@test.com
                              </p>
                          </div>
                          <div class="flex ml-auto items-center">
                              <img class="w-5 h-5 rounded-full" src="@/assets/icons/Settings.png" alt="Settings image">
                              <img class="w-5 h-5" src="@/assets/icons/Exit.png" alt="Logout image" @click="logout">
                          </div>
                      </div>
                  </Button>
              </div>
          </div>
      </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Button } from '../components/ui/button'
import { useRouter } from 'vue-router';
const router = useRouter();
async function logout() {
    try {        
        localStorage.removeItem('token');
        router.push('/login');
    } catch (error) {
        console.error(error)
    }
}
</script>
