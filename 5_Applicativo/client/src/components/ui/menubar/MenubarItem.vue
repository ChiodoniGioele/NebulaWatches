<script setup lang="ts">
import {
  MenubarItem,
  type MenubarItemEmits,
  type MenubarItemProps,
} from 'radix-vue'
import { cn } from '@/lib/utils'

const props = defineProps<MenubarItemProps & { inset?: boolean; class?: string }>()

const emits = defineEmits<MenubarItemEmits>()
</script>

<template>
  <MenubarItem
    v-bind="props"
    :class="[
      cn(
        'relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none focus:bg-slate-100 focus:text-slate-900 data-[disabled]:pointer-events-none data-[disabled]:opacity-50 dark:focus:bg-slate-800 dark:focus:text-slate-50',
        inset && 'pl-8',
        props.class,
      ),
    ]"
    @select="emits('select', $event)"
  >
    <slot />
  </MenubarItem>
</template>
