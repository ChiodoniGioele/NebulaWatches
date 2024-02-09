<template>
  <div class="flex flex-col"> <!-- Ensure the sidebar is a flex container with column direction -->
    <div class="space-y-4 py-4 flex-1"> <!-- Use flex-1 to allow the sidebar to take full height -->
      <div class="px-3 py-2">
        <div class="space-y-1">
          <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Watch.png"/>
              <span class="text-gray-800">Watches</span>
          </Button>
          <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Boxes.png"/>
              <span class="text-gray-800">Storage</span>
          </Button>
          <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Customer.png"/>
              <span class="text-gray-800">Clients</span>
          </Button>
          <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/User Account.png"/>
              <span class="text-gray-800">Team</span>              
          </Button>
          <Button variant="ghost" class="w-full justify-start">
              <img class="me-3" height="20px" width="20px" src="@/assets/icons/Chat.png"/>
              <span class="text-gray-800">Comunication</span>              
          </Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Button } from '../components/ui/button'
</script>
