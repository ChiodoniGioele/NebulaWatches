<script setup lang="ts">
import {
  NavigationMenuViewport,
  type NavigationMenuViewportProps,
} from 'radix-vue'
import { cn } from '@/lib/utils'

const props = defineProps<NavigationMenuViewportProps>()
</script>

<template>
  <div class="absolute left-0 top-full flex justify-center">
    <NavigationMenuViewport
      v-bind="props"
      :class="
        cn(
          'origin-top-center relative mt-1.5 h-[var(--radix-navigation-menu-viewport-height)] w-full overflow-hidden rounded-md border border-slate-200 bg-white text-slate-950 shadow-lg data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-90 md:w-[var(--radix-navigation-menu-viewport-width)] dark:border-slate-800 dark:bg-slate-950 dark:text-slate-50',
          $attrs.class ?? '',
        )
      "
    />
  </div>
</template>
