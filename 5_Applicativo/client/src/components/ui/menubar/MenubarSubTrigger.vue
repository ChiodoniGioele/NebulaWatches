<script setup lang="ts">
import { MenubarSubTrigger, type MenubarSubTriggerProps } from 'radix-vue'
import { ChevronRight } from 'lucide-vue-next'
import { cn } from '@/lib/utils'

const props = defineProps<MenubarSubTriggerProps & { inset?: boolean; class?: string }>()
</script>

<template>
  <MenubarSubTrigger
    v-bind="props"
    :class="[
      cn(
        'flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none focus:bg-slate-100 focus:text-slate-900 data-[state=open]:bg-slate-100 data-[state=open]:text-slate-900 dark:focus:bg-slate-800 dark:focus:text-slate-50 dark:data-[state=open]:bg-slate-800 dark:data-[state=open]:text-slate-50',
        inset && 'pl-8',
        props.class,
      ),
    ]"
  >
    <slot />
    <ChevronRight class="ml-auto h-4 w-4" />
  </MenubarSubTrigger>
</template>
