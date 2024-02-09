<template>

    <div class="grid lg:grid-cols-5 min-h-screen"> 
        <Sidebar class="hidden lg:block" />
        <div class="col-span-3 lg:col-span-4 lg:border-l flex flex-col"> 
            <div class="px-4 py-6 lg:px-8"> 

                <div class="flex w-full items-center gap-1.5">
                    <Input id="email" type="text" placeholder="Search..." />
                    <Button type="submit" variant="outline"> Search </Button>
                </div>

                <div class="mt-3">
                    <Popover>
                        <PopoverTrigger>
                            <Button variant="outline"> <span class="font-normal">Brands (10)</span> </Button>
                        </PopoverTrigger>
                        <PopoverContent class="w-auto">
                            <div class="flex items-center space-x-2">
                                <Checkbox id="omegaselected" />
                                <label
                                for="omegaselected"
                                class="text-sm font-light leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                >
                                Omega
                                </label>
                            </div>
                            <div class="flex items-center space-x-2 mt-3">
                                <Checkbox id="rolexselected" />
                                <label
                                for="rolexselected"
                                class="text-sm font-light leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                                >
                                Rolex
                                </label>
                            </div>

                        </PopoverContent>
                    </Popover>
                </div>
                
                <div class="mt-12 flex flex-wrap gap-5">
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                    <div class="border-2 border-gray-200 rounded-md " style="height: 200px; width: 200px;"></div>
                </div>

            </div>
        </div>

        
    </div>
    
 
</template>
  
<script setup lang="ts">
import Sidebar from '../components/Sidebar.vue'
import { Input } from '../components/ui/input'
import { Button } from '../components//ui/button'
import { Checkbox } from '../components/ui/checkbox'

import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover'


</script>
