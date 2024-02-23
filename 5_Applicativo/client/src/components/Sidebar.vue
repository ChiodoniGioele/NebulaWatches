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
      </div>
    </div>
  </div>
</template>

<script setup>
import { Button } from '@/components/ui/button'
</script>
